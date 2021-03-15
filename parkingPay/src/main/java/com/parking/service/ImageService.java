package com.parking.service;

import com.parking.domain.Image;

import java.util.List;

public interface ImageService {

    int addImage(Image image);

    List<Image> getAllImages();
}
