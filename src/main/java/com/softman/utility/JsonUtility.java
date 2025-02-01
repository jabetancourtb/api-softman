package com.softman.utility;

import java.io.StringWriter;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Service
public class JsonUtility {

	public String objectToJson(Object obj) throws Exception {

		try {
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();
			// configure Object mapper for pretty print
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

			// writing to console, can write to any output stream such as file
			StringWriter stringEmp = new StringWriter();
			objectMapper.writeValue(stringEmp, obj);				
			return "" + stringEmp;
		} 
		catch (Exception exception) {
			throw exception;
		}
	}
	
	
	public Object jsonToObject(String jsonString, Class<?> classObj) throws Exception {
		try {
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();
			// configure Object mapper for pretty print
			Object obj = objectMapper.readValue(jsonString, classObj);
			
			return obj;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
}
