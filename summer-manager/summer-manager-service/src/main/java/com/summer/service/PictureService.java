package com.summer.service;

import com.summer.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by toy on 6/10/16.
 */
public interface PictureService {
    PictureResult uploadPic(MultipartFile picFile);
}
