package com.genill.user.repo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findByAge(int age);
	
	Customer findByFirstName(String firstname);
}
