package com.dgrepo.swagger.integration.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.dgrepo.swagger.integration.dto.Person;
import com.dgrepo.swagger.integration.services.PeopleService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/people")
@Api(value="/people", description="Manage people")
public class PeopleRestService {
	
	@Autowired
	private PeopleService peopleService;
	
	@Produces({MediaType.APPLICATION_JSON})
	@GET
	@ApiOperation(value = "List all People", notes = "List all people using paging", response = Person.class, responseContainer = "List")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Person with such email address doesnt exists")
	})
	public List<Person> getPeople(@ApiParam(value="Page to fetch", required=true)@QueryParam("page") @DefaultValue("1") final int page) {
		return peopleService.getPeople(page, 5);
	}
	
	@Produces({MediaType.APPLICATION_JSON})
	@POST
	@ApiOperation(value = "Create new Person", notes = "Create new person")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Person created successfully"),
		@ApiResponse(code = 409, message = "Person with such email already exists")
	})
	public Response addPerson(@Context final UriInfo uriInfo, 
		@ApiParam(value = "E-mail", required = true)@FormParam("email") final String email, 
		@ApiParam(value = "First Name", required = true)@FormParam("firstName") final String firstName, 
		@ApiParam(value = "Last Name", required = true)@FormParam("lastName") final String lastName) {
		
		peopleService.addPerson(email, firstName, lastName);
		
		return Response.created(uriInfo.getRequestUriBuilder().path(email).build()).build();
	}
	
	@Path("/{email}")
	@DELETE
	@ApiOperation(value = "Delete existing person", notes = "Delete existing person", response = Person.class)
	@ApiResponses({
		@ApiResponse(code = 404, message = "Person with such email doesnt exists")
	})
	public Response deletePerson(@ApiParam(value="email", required = true)@PathParam("email") final String email) {
		peopleService.removePerson(email);
		return Response.ok().build();
	}
}
