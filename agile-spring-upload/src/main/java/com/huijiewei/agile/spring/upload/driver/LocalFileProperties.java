package com.huijiewei.agile.spring.upload.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "agile.spring.upload.local-file")
public class LocalFileProperties {
    private String uploadPath;
    private String accessPath;
    private String corpAction;
    private String uploadAction;
    private String policyKey;
    private String filenameHash = "random"; //支持 random/md5_file/original
}