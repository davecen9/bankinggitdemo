package com.virtusa.customerapidemo.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.repositories.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	public List<Address> findAddressByCustomerId(Long id) {
		return this.addressRepository.findByCustomerId(id);
		
	}
	
	public Address findAddressById(long id) {
		return this.addressRepository.findById(id).orElse(null);
	}
	
	public List<Address> findAllAddress(){
		return this.addressRepository.findAll();
	}
	
	public List<Address> findAllAddressByZipCode(String zipcode){
		return this.addressRepository.findAddressesByZipCode(zipcode);
	}
	
	public Address saveAddress(Address address) {
		return this.addressRepository.save(address);
	}
	
	public void deleteAddressById(Long id) {
		this.addressRepository.deleteById(id);
	}
	
}
