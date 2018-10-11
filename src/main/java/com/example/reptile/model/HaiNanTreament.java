package com.example.reptile.model;

/**
 * @Author:shixianqing
 * @Date:2018/10/1117:40
 * @Description:
 **/
public class HaiNanTreament {
    private String treamentNo;//诊疗项目编码
    private String hospitalLevel;//医院等级
    private String treamentName;//诊疗项目名称
    private String unit;//单位
    private double standardPrice;//标准价格
    private double selfPayRatio;//自付比例

    public HaiNanTreament(String treamentNo, String hospitalLevel, String treamentName, String unit, double standardPrice, double selfPayRatio) {
        this.treamentNo = treamentNo;
        this.hospitalLevel = hospitalLevel;
        this.treamentName = treamentName;
        this.unit = unit;
        this.standardPrice = standardPrice;
        this.selfPayRatio = selfPayRatio;
    }

    public HaiNanTreament() {
    }

    public String getTreamentNo() {
        return treamentNo;
    }

    public void setTreamentNo(String treamentNo) {
        this.treamentNo = treamentNo;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getTreamentName() {
        return treamentName;
    }

    public void setTreamentName(String treamentName) {
        this.treamentName = treamentName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public double getSelfPayRatio() {
        return selfPayRatio;
    }

    public void setSelfPayRatio(double selfPayRatio) {
        this.selfPayRatio = selfPayRatio;
    }

    @Override
    public String toString() {
        return "HaiNanTreament{" +
                "treamentNo='" + treamentNo + '\'' +
                ", hospitalLevel='" + hospitalLevel + '\'' +
                ", treamentName='" + treamentName + '\'' +
                ", unit='" + unit + '\'' +
                ", standardPrice=" + standardPrice +
                ", selfPayRatio=" + selfPayRatio +
                '}';
    }
}
