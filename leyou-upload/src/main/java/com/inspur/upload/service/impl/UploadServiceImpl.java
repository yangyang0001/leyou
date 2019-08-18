package com.inspur.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.inspur.upload.service.UploadService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.java2d.pipe.BufferedBufImgOps;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-10 16:30
 */
@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            //1.判断文件类型
            String contentType = file.getContentType();
            if(!IMAGE_CONTENT_TYPE.contains(contentType)) {
                LOGGER.error("文件类型不合法 : {}", file.getOriginalFilename());
                return null;
            }

            //2.判断文件内容是否合法
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage == null){
                LOGGER.error("文件内容不合法 : {}", file.getOriginalFilename());
                return null;
            }

            //3.保存
            //file.transferTo(new File("E:\\heima_workspace\\leyou\\upload_image\\" + file.getOriginalFilename()));
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtension, null);
            String filePath = storePath.getFullPath();

            //4.返回
//        return "http://image.leyou.com/" + file.getOriginalFilename();
            return "http://image.leyou.com/" + filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
