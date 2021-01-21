package com.blog.crm.email;

/**
 * 
 * @author Vema Reddy custom exceptions
 */
public class InvalidEmailException extends Exception {

	public InvalidEmailException(String msg) {
		super(msg);
	}
}
