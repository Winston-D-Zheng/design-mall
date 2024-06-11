package com.qdd.designmall.common.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface QiNiuYunService {
    String  DELIMITER = ",";

    String getUpToken();

    String getUrl(String picName);

    List<String> getUrls(String... keys);

    String[] getUrls(String keys);

    void delPic(Set<String> picNames);

    void asyncDelPic(Set<String> picNames);

}