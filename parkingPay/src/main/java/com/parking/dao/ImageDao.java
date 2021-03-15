package com.parking.dao;


import com.parking.domain.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("imageDao")
public interface ImageDao {

    /*
        test 类
     */
    int addImage(Image image);

    List<Image> getAllImages();
}
