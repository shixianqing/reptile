package com.example.reptile.model;

/**
 * @Author:shixianqing
 * @Date:2018/9/2813:24
 * @Description:
 **/
public class Medicine {
    private String medicineName;
    private String medicineNameCommon;//中文通用名称
    private String productEnterprise;//生产企业
    private String medicineStandard;//药品规格
    private int convertRatio;//转换比
    private String packagingMaterial;//包装材质
    private String unit;//单位
    private String medicineForm;//药品剂型
    private String selfPaymentRatio;//自负比例
    private String childRatio;//少儿比例
    private String bearRatio;//生育比例
    private String injuryRatio;//工伤比例
    private String prescriptionFlag;//处方标志
    private String cancleFlag;//作废标志
    private long medicineClassify;//药品分类
    private String payRange;//支付范围

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineNameCommon() {
        return medicineNameCommon;
    }

    public void setMedicineNameCommon(String medicineNameCommon) {
        this.medicineNameCommon = medicineNameCommon;
    }

    public String getProductEnterprise() {
        return productEnterprise;
    }

    public void setProductEnterprise(String productEnterprise) {
        this.productEnterprise = productEnterprise;
    }

    public String getMedicineStandard() {
        return medicineStandard;
    }

    public void setMedicineStandard(String medicineStandard) {
        this.medicineStandard = medicineStandard;
    }

    public int getConvertRatio() {
        return convertRatio;
    }

    public void setConvertRatio(int convertRatio) {
        this.convertRatio = convertRatio;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMedicineForm() {
        return medicineForm;
    }

    public void setMedicineForm(String medicineForm) {
        this.medicineForm = medicineForm;
    }

    public String getSelfPaymentRatio() {
        return selfPaymentRatio;
    }

    public void setSelfPaymentRatio(String selfPaymentRatio) {
        this.selfPaymentRatio = selfPaymentRatio;
    }

    public String getChildRatio() {
        return childRatio;
    }

    public void setChildRatio(String childRatio) {
        this.childRatio = childRatio;
    }

    public String getBearRatio() {
        return bearRatio;
    }

    public void setBearRatio(String bearRatio) {
        this.bearRatio = bearRatio;
    }

    public String getInjuryRatio() {
        return injuryRatio;
    }

    public void setInjuryRatio(String injuryRatio) {
        this.injuryRatio = injuryRatio;
    }

    public String getPrescriptionFlag() {
        return prescriptionFlag;
    }

    public void setPrescriptionFlag(String prescriptionFlag) {
        this.prescriptionFlag = prescriptionFlag;
    }

    public String getCancleFlag() {
        return cancleFlag;
    }

    public void setCancleFlag(String cancleFlag) {
        this.cancleFlag = cancleFlag;
    }

    public long getMedicineClassify() {
        return medicineClassify;
    }

    public void setMedicineClassify(long medicineClassify) {
        this.medicineClassify = medicineClassify;
    }

    public String getPayRange() {
        return payRange;
    }

    public void setPayRange(String payRange) {
        this.payRange = payRange;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineName='" + medicineName + '\'' +
                ", medicineNameCommon='" + medicineNameCommon + '\'' +
                ", productEnterprise='" + productEnterprise + '\'' +
                ", medicineStandard='" + medicineStandard + '\'' +
                ", convertRatio=" + convertRatio +
                ", packagingMaterial='" + packagingMaterial + '\'' +
                ", unit='" + unit + '\'' +
                ", medicineForm='" + medicineForm + '\'' +
                ", selfPaymentRatio=" + selfPaymentRatio +
                ", childRatio=" + childRatio +
                ", bearRatio=" + bearRatio +
                ", injuryRatio=" + injuryRatio +
                ", prescriptionFlag='" + prescriptionFlag + '\'' +
                ", cancleFlag='" + cancleFlag + '\'' +
                ", medicineClassify=" + medicineClassify +
                ", payRange='" + payRange + '\'' +
                '}';
    }
}
