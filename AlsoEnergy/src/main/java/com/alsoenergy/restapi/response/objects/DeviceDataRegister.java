package com.alsoenergy.restapi.response.objects;

public class DeviceDataRegister 
{
	String name;
	int dataType;
	double value;
	int unit;
	String formattedValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public String getFormattedValue() {
		return formattedValue;
	}
	public void setFormattedValue(String formattedValue) {
		this.formattedValue = formattedValue;
	}
	
	@Override
	public String toString() {
		return "DeviceDataRegister [name=" + name + ", dataType=" + dataType + ", value=" + value + ", unit=" + unit
				+ ", formattedValue=" + formattedValue + "]";
	}
	

}
