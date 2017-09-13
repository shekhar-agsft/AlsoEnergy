package com.alsoenergy.restapi.response.objects;

import java.util.Arrays;

public class Portfolio {

	int version;
	String key;
	String lastChanged;
	boolean merge;
	String mergeHash;
	Sites sites[];
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
	public boolean isMerge() {
		return merge;
	}
	public void setMerge(boolean merge) {
		this.merge = merge;
	}
	public String getMergeHash() {
		return mergeHash;
	}
	public void setMergeHash(String mergeHash) {
		this.mergeHash = mergeHash;
	}
	public Sites[] getSites() {
		return sites;
	}
	public void setSites(Sites[] sites) {
		this.sites = sites;
	}
	@Override
	public String toString() {
		return "Portfolio [version=" + version + ", key=" + key + ", lastChanged=" + lastChanged + ", merge=" + merge
				+ ", mergeHash=" + mergeHash + ", sites=" + Arrays.toString(sites) + "]";
	}
	
	
}
