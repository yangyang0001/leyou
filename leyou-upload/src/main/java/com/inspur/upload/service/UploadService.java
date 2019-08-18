package com.inspur.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-10 16:30
 */
public interface UploadService {

    public static final List<String> IMAGE_CONTENT_TYPE = Arrays.asList("image/gif", "image/jpeg", "image/png");

    public String uploadImage(MultipartFile file);
}
