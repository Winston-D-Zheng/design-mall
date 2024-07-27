package com.qdd.designmall.mallexternal.service.impl;

import com.qdd.designmall.mallexternal.config.QiniuyunProperties;
import com.qdd.designmall.common.service.PicService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QiniuyunPicServiceImpl implements PicService {
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


}