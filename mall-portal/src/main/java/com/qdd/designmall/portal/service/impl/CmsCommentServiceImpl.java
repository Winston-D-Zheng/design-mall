package com.qdd.designmall.portal.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mbp.model.CmsProductComment;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.mbp.service.DbCmsProductCommentService;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import com.qdd.designmall.portal.po.CmsPortalProductCommentPageParam;
import com.qdd.designmall.portal.po.CmsProductCommentCreateParam;
import com.qdd.designmall.portal.service.UmsMemberService;
import com.qdd.designmall.portal.service.CmsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CmsCommentServiceImpl implements CmsCommentService {
    private final DbPmsProductService dbPmsProductService;
    private final DbCmsProductCommentService dbCmsProductCommentService;
    private final UmsMemberService umsMemberService;

    @Override
    public void create(CmsProductCommentCreateParam param) {
        CmsProductComment cmsProductComment = new CmsProductComment();
        ZBeanUtils.copyProperties(param,cmsProductComment);
        // 获取对应的产品
        PmsProduct product = dbPmsProductService.getById(cmsProductComment.getProductId());
        if(product == null){
            throw new RuntimeException("商品不存在");
        }

        //用户信息
        UmsMember user = umsMemberService.userInfo();
        cmsProductComment.setMemberId(user.getId());
        cmsProductComment.setMemberNickname(user.getNickname());

        // 其他信息
        cmsProductComment.setCreateTime(LocalDateTime.now());   // 评论时间
        cmsProductComment.setDeleteStatus(0);                   // 删除状态
        cmsProductComment.setShopId(product.getShopId());       // 店铺ID

        dbCmsProductCommentService.save(cmsProductComment);
    }

    @Override
    public void delete(Long commentId) {
        // 当前用户
        Long userId = umsMemberService.currentUserId();
        // 获取评论
        CmsProductComment comment = dbCmsProductCommentService.lambdaQuery()
                .eq(CmsProductComment::getMemberId, userId)
                .eq(CmsProductComment::getId, commentId)
                .one();
        // 验证评论是否存在
        if(comment == null){
            throw new RuntimeException("评论不存在,或当前用户无法操作该评论");
        }
        // 删除评论
        comment.setDeleteStatus(1);
        dbCmsProductCommentService.updateById(comment);
    }


    @Override
    public IPage<CmsProductComment> page(CmsPortalProductCommentPageParam param) {
        return dbCmsProductCommentService.lambdaQuery()
                .eq(CmsProductComment::getProductId, param.getProductId())
                .eq(CmsProductComment::getDeleteStatus, 0)
                .page(param.getPagePo().iPage());
    }
}
