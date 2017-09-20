package com.daniel.springbatch.model.bean;

import java.io.Serializable;

public class DeviceCommand implements Serializable {
	private String id;
	private String status;
	public DeviceCommand() {
		super();
	}
	public DeviceCommand(String id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "DeviceCommand [id=" + id + ", status=" + status + "]";
	}
	
}
