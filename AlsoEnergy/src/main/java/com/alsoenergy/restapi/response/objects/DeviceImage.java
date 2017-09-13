/**
 * 
 */
package com.alsoenergy.restapi.response.objects;

/**
 * @author nilesh
 *
 */
public class DeviceImage 
{
	String uri;
	String key;
	String fileName;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "DeviceImage [uri=" + uri + ", key=" + key + ", fileName=" + fileName + "]";
	}
	
	
}
