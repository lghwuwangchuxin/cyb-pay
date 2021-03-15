package com.parkingpayweb.dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class ParkingGetChannelMchntReq extends BaseReq {

    @XmlElement
    private String parkList;
    @XmlElement
    private String parkId;
    @XmlElement
    private String attach;

    public String getParkList() {
        return parkList;
    }

    public void setParkList(String parkList) {
        this.parkList = parkList;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    @Override
    public String toString() {
        return "ParkingGetChannelMchntReq{" +
                "parkList='" + parkList + '\'' +
                ", parkId='" + parkId + '\'' +
                ", attach='" + attach + '\'' +
                "} " + super.toString();
    }
}
