package com.huijiewei.agile.spring.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadDriver {
    default UploadRequest build(Integer size, List<String> types) {
        UploadRequest request = this.option(size, types);

        request.setSizeLimit(size);
        request.setTypesLimit(types);

        return request;
    }

    UploadRequest option(Integer size, List<String> types);

    String paramName();

    default UploadResponse upload(String policy, MultipartFile file) {
        throw new RuntimeException("方法未实现");
    }

    default UploadResponse crop(String policy, ImageCropRequest request) {
        throw new RuntimeException("方法未实现");
    }
}