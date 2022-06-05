package com.ecommerce.service.image.impl;

import com.ecommerce.properties.ImageResizerProperties;
import com.ecommerce.service.image.ImageResizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageResizerServiceImpl implements ImageResizerService {

    private final ImageResizerProperties imageResizerProperties;

    @Override
    public InputStream resizeImage(String sourceImageUrl) {
        var restTemplate = new RestTemplate();
        byte[] object;
        var url = String.format(imageResizerProperties.getUrl(),
                URLDecoder.decode(sourceImageUrl, StandardCharsets.UTF_8));
        log.debug("resize url {]", url);
        object = restTemplate.getForObject(URI.create(url), byte[].class);
        if (object == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Image resizer error");
        }
        return new ByteArrayInputStream(object);
    }
}
