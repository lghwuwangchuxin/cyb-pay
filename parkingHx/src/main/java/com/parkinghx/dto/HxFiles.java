package com.parkinghx.dto;


// 扫描文件类
public class HxFiles {

    private String jksName; // jks名称
    private String cerName; // 银行公钥证书
    private String propertiesName; // 密码文件

    public String getJksName() {
        return jksName;
    }

    public void setJksName(String jksName) {
        this.jksName = jksName;
    }

    public String getCerName() {
        return cerName;
    }

    public void setCerName(String cerName) {
        this.cerName = cerName;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    @Override
    public String toString() {
        return "HxFiles{" +
                "jksName='" + jksName + '\'' +
                ", cerName='" + cerName + '\'' +
                ", propertiesName='" + propertiesName + '\'' +
                '}';
    }
}
