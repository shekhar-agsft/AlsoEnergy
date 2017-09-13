package com.alsoenergy.common.utility;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReturnObject {
	int version;
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

	String key;
	String lastChanged;
	boolean merge;
	String mergeHash;
	
	@JsonIgnore
	String sites;
	

	public String getSites() {
		return sites;
	}

	public void setSites(String sites) {
		this.sites = sites;
	}

	public ReturnObject() {

	}

	@Override
	public String toString() {
		return "ReturnObject [version=" + version + ", key=" + key
				+ ", lastChanged=" + lastChanged + ", merge=" + merge
				+ ", mergeHash=" + mergeHash + ", sites=" + sites + "]";
	}
}
