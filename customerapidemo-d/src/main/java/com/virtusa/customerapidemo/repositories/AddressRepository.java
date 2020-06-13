package com.virtusa.customerapidemo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.virtusa.customerapidemo.models.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

	@Query("From Address a WHERE a.zipcode = ?1")
	List<Address> findAddressesByZipCode(String zipcode);
	
	@Query( value="Select * From Address a WHERE a.customer_id=1",
			nativeQuery=true)
	List<Address> findByCustomerId(Long id);
}
