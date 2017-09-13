package com.alsoenergy.restapi.response.objects;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Sites 
{
	String key;
	String name;
	String parentKey;
	Date lastUpload;
	double power;
    double powerAvg15;
    double powerAvg15Est;
    double power24;
    double power24Est;
    String message;
    double thisMonth;
    double thisYear;
    double lifetime;
    double sizeKW;
    double sizeDC;
    int ground;
    int status;
    int alertSeverity;
    String alertName;
    int id;
    int type;
    double irradiance;
    int kiosks;
    int kioskStatus;
    @JsonIgnore
    double insolation;
    int inverterCount;
    int inverterFaults;
    double kwPercent;
    double kwhPercent;
    RuleToolSummary ruleToolSummary;
    
	public double getInsolation() {
		return insolation;
	}
	public void setInsolation(double insolation) {
		this.insolation = insolation;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	public Date getLastUpload() {
		return lastUpload;
	}
	public void setLastUpload(Date lastUpload) {
		this.lastUpload = lastUpload;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public double getPowerAvg15() {
		return powerAvg15;
	}
	public void setPowerAvg15(double powerAvg15) {
		this.powerAvg15 = powerAvg15;
	}
	public double getPowerAvg15Est() {
		return powerAvg15Est;
	}
	public void setPowerAvg15Est(double powerAvg15Est) {
		this.powerAvg15Est = powerAvg15Est;
	}
	public double getPower24() {
		return power24;
	}
	public void setPower24(double power24) {
		this.power24 = power24;
	}
	public double getPower24Est() {
		return power24Est;
	}
	public void setPower24Est(double power24Est) {
		this.power24Est = power24Est;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getThisMonth() {
		return thisMonth;
	}
	public void setThisMonth(double thisMonth) {
		this.thisMonth = thisMonth;
	}
	public double getThisYear() {
		return thisYear;
	}
	public void setThisYear(double thisYear) {
		this.thisYear = thisYear;
	}
	
	public double getLifetime() {
		return lifetime;
	}
	public void setLifetime(double lifetime) {
		this.lifetime = lifetime;
	}
	
	public double getSizeKW() {
		return sizeKW;
	}
	public void setSizeKW(double sizeKW) {
		this.sizeKW = sizeKW;
	}
	public double getSizeDC() {
		return sizeDC;
	}
	public void setSizeDC(double sizeDC) {
		this.sizeDC = sizeDC;
	}
	public int getGround() {
		return ground;
	}
	public void setGround(int ground) {
		this.ground = ground;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAlertSeverity() {
		return alertSeverity;
	}
	public void setAlertSeverity(int alertSeverity) {
		this.alertSeverity = alertSeverity;
	}
	public String getAlertName() {
		return alertName;
	}
	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(double irradiance) {
		this.irradiance = irradiance;
	}
	public int getKiosks() {
		return kiosks;
	}
	public void setKiosks(int kiosks) {
		this.kiosks = kiosks;
	}
	public int getKioskStatus() {
		return kioskStatus;
	}
	public void setKioskStatus(int kioskStatus) {
		this.kioskStatus = kioskStatus;
	}
	public int getInverterCount() {
		return inverterCount;
	}
	public void setInverterCount(int inverterCount) {
		this.inverterCount = inverterCount;
	}
	public int getInverterFaults() {
		return inverterFaults;
	}
	public void setInverterFaults(int inverterFaults) {
		this.inverterFaults = inverterFaults;
	}
	public double getKwPercent() {
		return kwPercent;
	}
	public void setKwPercent(double kwPercent) {
		this.kwPercent = kwPercent;
	}
	public double getKwhPercent() {
		return kwhPercent;
	}
	public void setKwhPercent(double kwhPercent) {
		this.kwhPercent = kwhPercent;
	}
	public RuleToolSummary getRuleToolSummary() {
		return ruleToolSummary;
	}
	public void setRuleToolSummary(RuleToolSummary ruleToolSummary) {
		this.ruleToolSummary = ruleToolSummary;
	}
	@Override
	public String toString() {
		return "Sites [key=" + key + ", name=" + name + ", parentKey=" + parentKey + ", lastUpload=" + lastUpload
				+ ", power=" + power + ", powerAvg15=" + powerAvg15 + ", powerAvg15Est=" + powerAvg15Est + ", power24="
				+ power24 + ", power24Est=" + power24Est + ", message=" + message + ", thisMonth=" + thisMonth
				+ ", thisYear=" + thisYear + ", lifetime=" + lifetime + ", sizeKW=" + sizeKW + ", sizeDC=" + sizeDC
				+ ", ground=" + ground + ", status=" + status + ", alertSeverity=" + alertSeverity + ", alertName="
				+ alertName + ", id=" + id + ", type=" + type + ", irradiance=" + irradiance + ", kiosks=" + kiosks
				+ ", kioskStatus=" + kioskStatus + ", insolation=" + insolation + ", inverterCount=" + inverterCount
				+ ", inverterFaults=" + inverterFaults + ", kwPercent=" + kwPercent + ", kwhPercent=" + kwhPercent
				+ ", ruleToolSummary=" + ruleToolSummary + "]";
	}
    
    
}
