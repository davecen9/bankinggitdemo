package com.virtusa.customerapidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virtusa.customerapidemo.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
