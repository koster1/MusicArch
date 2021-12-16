package com.jcg.hibernate.maven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * User Request class defines the parameters used by User Requests in Hibernate's ORM.
 * @author Alex
 *
 */
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
	/**
	 * Returns a request's ID in the database
	 * @return requestID
	 */
	public int getRequestID() {
		return this.requestID;
	}
	/**
	 * Sets a title for a User Request with a given String parameter
	 * @param rTitle
	 */
	public void setRequestTitle(String rTitle) {
		this.requestTitle = rTitle;
	}
	/**
	 * Returns a User Request's title
	 * @return requestTitle
	 */
	public String getRequestTitle() {
		return this.requestTitle;
	}
	/**
	 * Sets the contents for a User Request with a given String parameter
	 * @param rContent
	 */
	public void setRequestContents(String rContent) {
		this.requestContent = rContent;
	}
	/**
	 * Returns a User Request's title
	 * @return
	 */
	public String getRequestContents() {
		return this.requestContent;
	}
}
