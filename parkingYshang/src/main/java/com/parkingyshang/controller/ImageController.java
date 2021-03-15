package com.parkingyshang.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ImageController.class);

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String images() throws Exception {

        return "hello word";


    }
}
