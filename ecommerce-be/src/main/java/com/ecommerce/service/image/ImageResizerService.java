package com.ecommerce.service.image;

import java.io.InputStream;

public interface ImageResizerService {

    InputStream resizeImage(String sourceImageUrl);
}
