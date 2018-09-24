package com.example.reptile.model;

/**
 * @Author:shixianqing
 * @Date:2018/9/2118:09
 * @Description:
 **/
public class Hospital {
    private String hospitalUrl;//医院网址
    private String hospitalName;//医院名称
    private String telephoneNo;//医院联系方式
    private String hospitalFax;//医院传真
    private String hospitalAddr;//医院地址
    private String hospitalPost;//邮编
    private String hospitalLocation;//所在地
    private String hospitalLeaderName;//院长姓名
    private String buildTime;//建院时间
    private String hospitalType;//医院类型
    private String hospitalLevel;//医院等级
    private String isMedicalInsurance;//是否医保

    public String getHospitalUrl() {
        return hospitalUrl;
    }

    public void setHospitalUrl(String hospitalUrl) {
        this.hospitalUrl = hospitalUrl;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getHospitalFax() {
        return hospitalFax;
    }

    public void setHospitalFax(String hospitalFax) {
        this.hospitalFax = hospitalFax;
    }

    public String getHospitalAddr() {
        return hospitalAddr;
    }

    public void setHospitalAddr(String hospitalAddr) {
        this.hospitalAddr = hospitalAddr;
    }

    public String getHospitalPost() {
        return hospitalPost;
    }

    public void setHospitalPost(String hospitalPost) {
        this.hospitalPost = hospitalPost;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public void setHospitalLocation(String hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public String getHospitalLeaderName() {
        return hospitalLeaderName;
    }

    public void setHospitalLeaderName(String hospitalLeaderName) {
        this.hospitalLeaderName = hospitalLeaderName;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getIsMedicalInsurance() {
        return isMedicalInsurance;
    }

    public void setIsMedicalInsurance(String isMedicalInsurance) {
        this.isMedicalInsurance = isMedicalInsurance;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalUrl='" + hospitalUrl + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", telephoneNo='" + telephoneNo + '\'' +
                ", hospitalFax='" + hospitalFax + '\'' +
                ", hospitalAddr='" + hospitalAddr + '\'' +
                ", hospitalPost='" + hospitalPost + '\'' +
                ", hospitalLocation='" + hospitalLocation + '\'' +
                ", hospitalLeaderName='" + hospitalLeaderName + '\'' +
                ", buildTime='" + buildTime + '\'' +
                ", hospitalType='" + hospitalType + '\'' +
                ", hospitalLevel='" + hospitalLevel + '\'' +
                ", isMedicalInsurance='" + isMedicalInsurance + '\'' +
                '}';
    }
}
