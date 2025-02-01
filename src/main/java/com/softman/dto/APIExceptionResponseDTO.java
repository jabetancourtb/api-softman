package com.softman.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIExceptionResponseDTO {

	private String type;
	    	
	private String message;
	
	private String exceptionClass;
	
	private HttpStatus httpStatus;
	
}
