package com.pakring.test;


import com.parking.util.FileUtil;
import java.io.IOException;

public class MyTest {

    public static void main(String[] args) throws IOException {

        System.out.println(FileUtil.readResourceFile("wxpayOrder").replace("${timeStamp}","1677"));
    }
}
