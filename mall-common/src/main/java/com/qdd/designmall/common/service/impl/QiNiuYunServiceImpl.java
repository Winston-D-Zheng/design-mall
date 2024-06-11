package com.qdd.designmall.common.service.impl;

import com.qdd.designmall.common.config.property.QiniuyunProperties;
import com.qdd.designmall.common.service.QiNiuYunService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QiNiuYunServiceImpl implements QiNiuYunService {
    private final QiniuyunProperties qiniuyunProperties;

    @Override
    public String getUpToken() {
        Auth auth = Auth.create(qiniuyunProperties.getAccessKey(), qiniuyunProperties.getSecretKey());
        return auth.uploadToken(qiniuyunProperties.getBucket());
    }

    @Override
    public String getUrl(String picName) {
        DownloadUrl url = new DownloadUrl(qiniuyunProperties.getDomin(), false, picName);
        // 带有效期
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        long deadline = System.currentTimeMillis() / 1000 + expireInSeconds;
        Auth auth = Auth.create(qiniuyunProperties.getAccessKey(), qiniuyunProperties.getSecretKey());
        try {
            return url.buildURL(auth, deadline);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delPic(Set<String> picNames) {
        // 转为数组
        String[] picNamesArray = picNames.toArray(new String[0]);

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
        String accessKey = qiniuyunProperties.getAccessKey();
        String secretKey = qiniuyunProperties.getSecretKey();
        String bucket = qiniuyunProperties.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, picNamesArray);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < picNamesArray.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = picNamesArray[i];
                if (status.code != 200) {
                    log.error("七牛云图片key=" + key + "删除失败，错误信息为" + status.data.error);
                }
            }
        } catch (QiniuException ex) {
            log.error("七牛云图片删除失败，错误信息为" + ex.response.toString());
        }
    }

    @Override
    public void asyncDelPic(Set<String> picNames) {
        Thread.ofVirtual().name("delPic-" + picNames).start(() -> delPic(picNames));
    }

    @Override
    public List<String> getUrls(String... keys) {
        if (keys.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(keys).map(this::getUrl).toList();
    }

    @Override
    public String[] getUrls(String keys) {
        if (StringUtils.isBlank(keys)) {
            return new String[0];
        }

        return  Arrays.stream(keys.split(DELIMITER)).map(this::getUrl).toArray(String[]::new);
    }
}