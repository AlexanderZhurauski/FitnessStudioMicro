package org.mycompany.report.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public class MinioProperty {

    private String serverUrl;
    private String secretKey;
    private String accessKey;
    private String excelBucket;

    public String getServerUrl() {
        return this.serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getExcelBucket() {
        return this.excelBucket;
    }

    public void setExcelBucket(String excelBucket) {
        this.excelBucket = excelBucket;
    }
}
