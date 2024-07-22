package com.qdd.designmall.tboms.service;

import org.springframework.web.multipart.MultipartFile;

public interface TaobaoOrderUploadService {
    void upload(MultipartFile file, Long shopId);
}
