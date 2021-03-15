package com.parkingynnx.dto;

import java.io.Serializable;

public class Goods implements Serializable {

    private String goodsName;
    private String goodsSelectNum;
    private String goodsPrice;
    private String goodsCode;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSelectNum() {
        return goodsSelectNum;
    }

    public void setGoodsSelectNum(String goodsSelectNum) {
        this.goodsSelectNum = goodsSelectNum;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsSelectNum='" + goodsSelectNum + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                '}';
    }
}
