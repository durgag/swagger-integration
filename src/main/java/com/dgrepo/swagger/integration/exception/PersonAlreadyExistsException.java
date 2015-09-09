package com.dgrepo.swagger.integration.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PersonAlreadyExistsException extends WebApplicationException {

	private static final long serialVersionUID = -7823376377506334752L;

	public PersonAlreadyExistsException(final String email) {
		super(Response.status(Status.CONFLICT).build());
	}
}
