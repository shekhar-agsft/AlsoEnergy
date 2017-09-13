package com.alsoenergy.restapi.response.objects;

import java.util.Arrays;

public class SiteDevice 
{

	int version;
	String key;
	String lastChanged;
	Hardware hardware[];
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLastChanged() {
		return lastChanged;
	}
	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}
	public Hardware[] getHardware() {
		return hardware;
	}
	public void setHardware(Hardware[] hardware) {
		this.hardware = hardware;
	}
	@Override
	public String toString() {
		return "SiteDevice [version=" + version + ", key=" + key + ", lastChanged=" + lastChanged + ", hardware="
				+ Arrays.toString(hardware) + "]";
	}
	
	
}
