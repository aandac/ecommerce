package com.ecommerce.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "imagemagick")
public class ImageResizerProperties{

    private String url;
}
