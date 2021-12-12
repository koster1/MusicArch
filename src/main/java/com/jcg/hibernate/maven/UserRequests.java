package com.jcg.hibernate.maven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserRequests")
public class UserRequests {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RequestID", updatable=false, nullable=false)
	private int requestID;
	
	@Column(name="RequestTitle")
	private String requestTitle;
	
	@Column(name="RequestContent")
	private String requestContent;
	
	public int getRequestID() {
		return this.requestID;
	}
	
	public void setRequestTitle(String rTitle) {
		this.requestTitle = rTitle;
	}
	public String getRequestTitle() {
		return this.requestTitle;
	}
	public void setRequestContents(String rContent) {
		this.requestContent = rContent;
	}
	public String getRequestContents() {
		return this.requestContent;
	}
}
