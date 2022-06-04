package com.ecommerce.service.aws.impl;

import com.ecommerce.service.aws.AwsS3Service;
import com.ecommerce.service.aws.model.AwsS3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {
    @Override
    public AwsS3Object uploadToS3(String fileName, InputStream inputStream) {
        return null;
    }

    @Override
    public InputStream downloadFromS3(String fileName) {
        return null;
    }
}
