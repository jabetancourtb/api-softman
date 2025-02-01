package com.softman.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "logs_history")
@Data
public class LogHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	protected Long id;
	
	private Timestamp requestDateTime;
	
	@Column(name = "request_url", columnDefinition = "TEXT")
	private String requestUrl;
	
	private String requestMethodHttp;
	
	private String requestScheme;
	
	@Column(name = "request_host", columnDefinition = "TEXT")
	private String requestHost;
	
	@Column(name = "request_path", columnDefinition = "TEXT")
	private String requestPath;
	
	@Column(name = "request_query_url", columnDefinition = "TEXT")
	private String requestQueryUrl;
	
	private String requestApiResource;
	
	private String requestApiResourceMethodName;
	
	@Column(name = "request_body", columnDefinition = "TEXT")
	private String requestBody;
	
	private Timestamp responseDateTime;
	
	@Column(name = "response_body", columnDefinition = "TEXT")
	private String responseBody;
	
	private int responseStatus;
	
	@Column(name = "message_exception", columnDefinition = "TEXT")
	private String messageException;
	
	@Column(name = "object_exception", columnDefinition = "TEXT")
	private String objectException;
	
	private int processTimeInMs;
	
}
