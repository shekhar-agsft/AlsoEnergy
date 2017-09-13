/**
 * 
 */
package com.alsoenergy.restapi.response.objects;

/**
 * @author nilesh
 *
 */
public class RuleToolSummary {

	String timestamp;
	int communication;
	int configuration;
	int data;
	int performance;
	String message;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getCommunication() {
		return communication;
	}
	public void setCommunication(int communication) {
		this.communication = communication;
	}
	public int getConfiguration() {
		return configuration;
	}
	public void setConfiguration(int configuration) {
		this.configuration = configuration;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public int getPerformance() {
		return performance;
	}
	public void setPerformance(int performance) {
		this.performance = performance;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "RuleToolSummary [timestamp=" + timestamp + ", communication=" + communication + ", configuration="
				+ configuration + ", data=" + data + ", performance=" + performance + ", message=" + message + "]";
	}
	
}
