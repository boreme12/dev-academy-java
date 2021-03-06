package com.answerdigital.colourstest.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.exception.NotImplementedException;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRespository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        // TODO STEP 1
        //
        // Implement a JSON endpoint that returns the full list
        // of people from the PeopleRepository. If there are zero
        // people returned from PeopleRepository then an empty
        // JSON array should be returned.
    	List<Person> data = this.peopleRespository.findAll();
    	List<Person> emptyList = Arrays.asList();
    	
    	if(!data.isEmpty()) {
    		return new ResponseEntity<>(data, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(emptyList, HttpStatus.OK);
    	}
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        // TODO: Step 2
        //
        // Implement a JSON endpoint that returns a single person
        // from the PeopleRepository based on the id parameter.
        // If null is returned from the PeopleRepository with
        // the supplied id then a NotFound should be returned.
    	Optional<Person> person = this.peopleRespository.findById(id);
    	
    	if(person.isPresent()) {
    		return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
    	} else {
    		return  new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
    	}
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        // TODO STEP 3
        //
        // Implement an endpoint that recieves a JSON object to
        // update a person using the PeopleRepository based on
        // the id parameter. Once the person has been sucessfullly
        // updated, the person should be returned from the endpoint.
        // If null is returned from the PeopleRepository then a
        // NotFound should be returned.
    	Optional<Person> person = this.peopleRespository.findById(id);
    	
    	if(person.isPresent()) {
    		person.get().setAuthorised(personUpdate.isAuthorised());
        	person.get().setEnabled(personUpdate.isEnabled());
        	person.get().setColours(personUpdate.getColours());
        	this.peopleRespository.save(person.get());
        	return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
    	}
    	
    	
    }

}
