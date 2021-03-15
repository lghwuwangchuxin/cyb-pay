package com.parking.service.impl;

import com.parking.dao.ImageDao;
import com.parking.domain.Image;
import com.parking.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("imageService")
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public int addImage(Image image) {
        return 2;
    }

    @Override
    public List<Image> getAllImages() {
        return imageDao.getAllImages();
    }
}
