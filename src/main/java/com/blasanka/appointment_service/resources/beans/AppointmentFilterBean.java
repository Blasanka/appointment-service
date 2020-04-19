package com.blasanka.appointment_service.resources.beans;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;


public class AppointmentFilterBean {

	@HeaderParam("id") long id;
	@HeaderParam("username") String username;
	@HeaderParam("type") String type;
	@QueryParam("sort") String sortBy;
	@QueryParam("start") int start;
	@QueryParam("size") int size;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
