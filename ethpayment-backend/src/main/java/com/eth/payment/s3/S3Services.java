package com.eth.payment.s3;

public interface S3Services {

    String downloadFile(String keyName);

    void uploadFile(String keyName, String uploadFilePath);


}
