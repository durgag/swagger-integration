package com.staples.swagger.integration.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.staples.swagger.integration.dto.Person;
import com.staples.swagger.integration.exception.PersonAlreadyExistsException;
import com.staples.swagger.integration.exception.PersonNotFoundException;

@Service
public class PeopleService {
	
	private final ConcurrentHashMap<String, Person> persons = 
			new ConcurrentHashMap<String, Person>();
	
	public List<Person> getPeople(int page,int pageSize) {
		final List<Person> returnList = new ArrayList<Person>(pageSize);
		
		final Iterator<Person> iterate = persons.values().iterator();
        for( int i = 0; returnList.size() < pageSize && iterate.hasNext(); ) {
        	if( ++i > ( ( page - 1 ) * pageSize ) ) {
        		returnList.add( iterate.next() );
        	}
        }		
		return returnList;
	}
	
	public Person addPerson(final String email, final String firstName, final String lastName) {
		final Person person = new Person(email);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		
		if(persons.putIfAbsent(email, person) != null) {
			throw new PersonAlreadyExistsException(email);
		}
		return person;
	}
	
	public void removePerson(final String email) {
		if(persons.remove(email) == null) {
			throw new PersonNotFoundException(email);
		}
	}
}
