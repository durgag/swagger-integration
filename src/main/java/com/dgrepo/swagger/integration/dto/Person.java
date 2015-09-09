package com.dgrepo.swagger.integration.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="Person", description="Person resource representation")
public class Person {
	
	@ApiModelProperty(value="Person's first Name", required=true)
	private String firstName;
	@ApiModelProperty(value="Person's Last Name", required=true)
	private String lastName;
	@ApiModelProperty(value="Person's Email Address", required=true)
	private String email;
	
	public Person(final String email) {
		this.email = email;
	}
	
	public Person(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
