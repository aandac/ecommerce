package com.ecommerce.service.aws;

import com.ecommerce.service.aws.model.AwsS3Object;

import java.io.InputStream;

public interface AwsS3Service {

    AwsS3Object uploadToS3(String fileName, InputStream inputStream);

    AwsS3Object uploadToS3(String fileName, InputStream inputStream, boolean tempDirectory);

    InputStream downloadFromS3(String fileName);

    void deleteS3Object(String fileName, boolean tempDirectory);
}
