package com.summer.service.Impl;

import com.summer.common.pojo.PictureResult;
import com.summer.common.utils.FastDFSClient;
import com.summer.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by toy on 6/10/16.
 */
@Service
public class PictureServiceImpl implements PictureService {


    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;

    @Override
    public PictureResult uploadPic(MultipartFile picFile) {
        PictureResult result = new PictureResult();
        if (picFile.isEmpty()) {
            result.setError(1);
            result.setMessage("Empty Picture!!!");
            return result;
        }
        try {
            String originalFileName = picFile.getOriginalFilename();
            String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
            String url = client.uploadFile(picFile.getBytes(), extName);
            url = IMAGE_SERVER_BASE_URL + url;
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("Fail to upload pictures.");
        }
        return result;
    }
}
