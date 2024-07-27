package com.qdd.designmall.common.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PicService {
    String getUpToken();

    String getUrl(String picName);

    void delPic(Set<String> picNames);
}