package com.ecommerce.service.aws.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.ecommerce.config.AwsS3Config;
import com.ecommerce.service.aws.AwsS3Service;
import com.ecommerce.service.aws.model.AwsS3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final AmazonS3 amazonS3;
    private final AwsS3Config awsS3Config;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public AwsS3Object uploadToS3(String fileName, InputStream inputStream) {
        return uploadToS3(fileName, inputStream, false);
    }

    @Override
    public AwsS3Object uploadToS3(String fileName, InputStream inputStream, boolean tempDirectory) {
        String bucketName = tempDirectory ? awsS3Config.getTempBucketName() : awsS3Config.getBucketName();
        log.debug("Uploading file '{}' to bucket: '{}' ", fileName, bucketName);
        File scratchFile = null;
        String fileUrl;
        try {
            scratchFile = File.createTempFile("prefix", "suffix");
            FileCopyUtils.copy(inputStream, new FileOutputStream(scratchFile));
            fileUrl = awsS3Config.getS3ExternalEndpointUrl() + "/" + bucketName + "/" + fileName;
            var putObjectResult = amazonS3.putObject(bucketName, fileName, scratchFile);
            if (Objects.isNull(putObjectResult)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Aws S3 file upload error");
            }
        } catch (Exception e) {
            log.error("error on uploading file to s3 ", e);
            throw new RuntimeException(e);
        } finally {
            if (scratchFile != null && scratchFile.exists()) {
                scratchFile.delete();
            }
        }

        return new AwsS3Object(fileName, fileUrl);
    }

    @Override
    public InputStream downloadFromS3(String fileName) {
        log.debug("Downloading file '{}' from bucket: '{}' ", fileName, awsS3Config.getBucketName());
        final S3Object s3Object = amazonS3.getObject(awsS3Config.getBucketName(), fileName);
        return s3Object.getObjectContent();
    }

    @Override
    public void deleteS3Object(String fileName, boolean tempDirectory) {
        String bucketName = tempDirectory ? awsS3Config.getTempBucketName() : awsS3Config.getBucketName();
        log.debug("Deleting file '{}' to bucket: '{}' ", fileName, bucketName);
        amazonS3.deleteObject(bucketName, fileName);
    }
}
