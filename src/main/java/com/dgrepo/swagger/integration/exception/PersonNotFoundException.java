package com.dgrepo.swagger.integration.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PersonNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 8121252794388417877L;
	
	public PersonNotFoundException(final String email) {
		super(Response.status(Status.NOT_FOUND).build());
	}
}
