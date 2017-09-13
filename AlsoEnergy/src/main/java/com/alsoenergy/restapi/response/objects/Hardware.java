package com.alsoenergy.restapi.response.objects;

import java.util.Arrays;
import java.util.Date;

public class Hardware 
{
	String key;
	String name;
	boolean enabled;
	String serialNum;
	Date installDate;
	String lastChanged;
	String lastAttempt;
	String lastAttemptLocal;
	String lastSuccess;
	String lastSuccessLocal;
	String lastData;
	int functionCode;
	DeviceDataRegister dataRegisters[];
	DeviceImage deviceImage;
	String defaultChart;
	
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getDefaultChart() {
		return defaultChart;
	}
	public void setDefaultChart(String defaultChart) {
		this.defaultChart = defaultChart;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Date getInstallDate() {
		return installDate;
	}
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	public String getLastChanged() {
		return lastChanged;
	}
	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}
	public String getLastAttempt() {
		return lastAttempt;
	}
	public void setLastAttempt(String lastAttempt) {
		this.lastAttempt = lastAttempt;
	}
	public String getLastAttemptLocal() {
		return lastAttemptLocal;
	}
	public void setLastAttemptLocal(String lastAttemptLocal) {
		this.lastAttemptLocal = lastAttemptLocal;
	}
	public String getLastSuccess() {
		return lastSuccess;
	}
	public void setLastSuccess(String lastSuccess) {
		this.lastSuccess = lastSuccess;
	}
	public String getLastSuccessLocal() {
		return lastSuccessLocal;
	}
	public void setLastSuccessLocal(String lastSuccessLocal) {
		this.lastSuccessLocal = lastSuccessLocal;
	}
	public String getLastData() {
		return lastData;
	}
	public void setLastData(String lastData) {
		this.lastData = lastData;
	}
	public int getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(int functionCode) {
		this.functionCode = functionCode;
	}
	public DeviceDataRegister[] getDataRegisters() {
		return dataRegisters;
	}
	public void setDataRegisters(DeviceDataRegister[] dataRegisters) {
		this.dataRegisters = dataRegisters;
	}
	public DeviceImage getDeviceImage() {
		return deviceImage;
	}
	public void setDeviceImage(DeviceImage deviceImage) {
		this.deviceImage = deviceImage;
	}
	@Override
	public String toString() {
		return "Hardware [key=" + key + ", name=" + name + ", enabled=" + enabled + ", serialNum=" + serialNum
				+ ", installDate=" + installDate + ", lastChanged=" + lastChanged + ", lastAttempt=" + lastAttempt
				+ ", lastAttemptLocal=" + lastAttemptLocal + ", lastSuccess=" + lastSuccess + ", lastSuccessLocal="
				+ lastSuccessLocal + ", lastData=" + lastData + ", functionCode=" + functionCode + ", dataRegisters="
				+ Arrays.toString(dataRegisters) + ", deviceImage=" + deviceImage + "]";
	}
	
	
    
   
}
