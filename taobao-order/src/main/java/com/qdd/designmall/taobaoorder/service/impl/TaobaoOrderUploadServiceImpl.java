package com.qdd.designmall.taobaoorder.service.impl;

import com.qdd.designmall.mbp.model.DbTaobaoOrder;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import com.qdd.designmall.mbp.service.DbTaobaoOrderService;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Service
@Scope(SCOPE_REQUEST)
@Lazy
@RequiredArgsConstructor
public class TaobaoOrderUploadServiceImpl implements TaobaoOrderUploadService {
    private final SecurityUserService securityUserService;
    private final DbSmsShopService dbSmsShopService;
    private final DbTaobaoOrderService dbTaobaoOrderService;

    // 用户上传订单列表必须的标题
    private static final Set<String> necessaryTitle = Set.of("订单编号", "支付单号", "买家实际支付金额");
    // 标题映射
    private final Map<String, Integer> titleMap = new HashMap<>();
    private Long shopId;

    @Override
    public void upload(MultipartFile file, Long shopId) {
        Long userId = securityUserService.currentUserDetails().getUserId();
        this.shopId = shopId;

        // 验证店铺是否属于当前用户
        boolean exists = dbSmsShopService.lambdaQuery().eq(SmsShop::getOwnerId, userId).eq(SmsShop::getId, shopId).exists();
        if (!exists) {
            throw new RuntimeException("该店铺不属于该用户");
        }

        // 验证文件格式
        if (!isExcel(file)) {
            throw new RuntimeException("文件格式不正确");
        }

        // 文件保存到MongoDB
        String mongoDbId = saveToMongoDB(file, userId);

        // 解析excel
        ArrayList<DbTaobaoOrder> entities = parseExcel(file);

        // 保存到数据库
        for (DbTaobaoOrder entity : entities) {
            dbTaobaoOrderService.saveOrUpdateByShopIdAndOrderNo(entity);
        }
    }

    /**
     * 验证文件格式是否是excel
     */
    boolean isExcel(MultipartFile file) {
        final List<String> EXCEL_MIME_TYPES = Arrays.asList("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return EXCEL_MIME_TYPES.contains(file.getContentType());
    }

    /**
     * 保存文件到MongoDB
     *
     * @return 目标地址
     */
    String saveToMongoDB(MultipartFile file, Long userId) {
        // 文件名：taobao_当前用户id_时间
        String id = "taobao_" + userId + "_" + LocalDateTime.now();

        //TODO 文件保存到MongoDB


        return id;
    }

    /**
     * 解析excel
     */
    private ArrayList<DbTaobaoOrder> parseExcel(MultipartFile file) {
        ArrayList<DbTaobaoOrder> rt = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            ArrayList<String> parseFailed = new ArrayList<>();  // 解析失败信息
            for (var row : sheet) {
                if (row.getRowNum() == 0) {
                    processTitleRow(row);
                } else {
                    try {
                        DbTaobaoOrder entity = new DbTaobaoOrder();
                        // 填充信息
                        fillEntity(entity, row);
                        rt.add(entity);
                    } catch (RuntimeException e) {
                        parseFailed.add("第" + (row.getRowNum() + 1) + "行" + e.getMessage() + "解析失败\n");
                    }
                }
            }
            if (!parseFailed.isEmpty()) {
                throw new RuntimeException(String.join("\n", parseFailed));
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }

    /**
     * 填充数据
     */
    private void fillEntity(DbTaobaoOrder entity, Row row) {
        ArrayList<String> parseFailed = new ArrayList<>();

        String shopName = getCellValue(row, parseFailed, "店铺名称");
        LocalDateTime payTime = getCellValue(row, parseFailed, "订单付款时间");
        String orderNo = getCellValue(row, parseFailed, "订单编号");
        Long buyerActualPaymentAmount = getCellValue(row, parseFailed, "买家实际支付金额");
        LocalDateTime refundTime = getCellValue(row, parseFailed, "退款时间");
        Long refundAmount = getCellValue(row, parseFailed, "退款金额");
        String orderStatus = getCellValue(row, parseFailed, "订单状态");
        String merchantNotes = getCellValue(row, parseFailed, "商家备注");

        if (!parseFailed.isEmpty()) {
            throw new RuntimeException("，列（" + String.join(",", parseFailed) + "）");
        }

        entity.setShopId(shopId);   // 设置店铺id
        entity.setShopName(shopName);
        entity.setOrderNo(orderNo);
        entity.setPayTime(payTime);
        entity.setBuyerActualPaymentAmount(BigDecimal.valueOf(buyerActualPaymentAmount));
        entity.setRefundTime(refundTime);
        entity.setRefundAmount(BigDecimal.valueOf(refundAmount));
        entity.setOrderStatus(orderStatus);
        entity.setMerchantNotes(merchantNotes);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdaterId(securityUserService.currentUserDetails().getUserId());
    }

    /**
     * 读取cell值
     *
     * @param row   行
     * @param title 标题
     * @param <R>   值类型
     * @return 值
     * @throws RuntimeException 列错误信息
     */
    private <R> R getCellValue(Row row, List<String> parseFailed, String title) {

        try {
            Cell cell = row.getCell(titleMap.get(title));
            if (cell.getCellType() == CellType.NUMERIC) {
                return (R) Double.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.STRING) {
                return (R) cell.getStringCellValue();
            } else {
                return (R) cell.getLocalDateTimeCellValue();
            }
        } catch (Exception e) {
            parseFailed.add(title);
        }
        return null;
    }

    /**
     * 处理标题行
     * 如果缺少必要字段，则抛出异常
     */
    private void processTitleRow(Row row) {

        Map<String, Integer> necessaryTitleMap = necessaryTitle.stream().collect(Collectors.toMap(n -> n, n -> 0));
        for (var cell : row) {
            int columnIndex = cell.getColumnIndex();
            String cellValue = cell.getStringCellValue();
            if (necessaryTitle.contains(cellValue)) {
                this.titleMap.put(cellValue, columnIndex);
                necessaryTitleMap.computeIfPresent(cellValue, (key, value) -> value + 1);
            }
        }


        // 验证必要字段
        Set<Map.Entry<String, Integer>> entries = necessaryTitleMap.entrySet();
        // 缺少的title
        List<Map.Entry<String, Integer>> lostTitle = entries.stream().filter(entry -> entry.getValue() == 0).toList();
        // 重复的title
        List<Map.Entry<String, Integer>> repeatTitle = entries.stream().filter(entry -> entry.getValue() > 1).toList();


        // 生成错误信息
        StringBuilder sb = new StringBuilder();
        if (!lostTitle.isEmpty()) {
            sb.append("缺少必要的标题：");
            lostTitle.forEach(entry -> sb.append(entry.getKey()).append(","));
        }
        if (!repeatTitle.isEmpty()) {
            sb.append("重复的标题：");
            repeatTitle.forEach(entry -> sb.append(entry.getKey()).append(","));
        }
        if (!sb.isEmpty()) {
            throw new RuntimeException(sb.toString());
        }
    }

}
