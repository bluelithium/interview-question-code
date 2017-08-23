package com.jwtk.znlover.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jwtk_message")
public class JwtkMessage {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="access_ip")
	private String accessIP;
	
	@Column(name="message")
	private String message;
	
	@Column(name="date")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessIP() {
		return accessIP;
	}

	public void setAccessIP(String accessIP) {
		this.accessIP = accessIP;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
