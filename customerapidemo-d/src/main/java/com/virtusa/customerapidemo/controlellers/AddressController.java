package com.virtusa.customerapidemo.controlellers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.services.AddressService;
import com.virtusa.customerapidemo.services.CustomerService;

@RestController
public class AddressController {

	@Autowired
	private AddressService service;
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers/{id}/addresses")
	public ResponseEntity<?> createAddress(@PathVariable("id") Long id, @RequestBody Address address){
	
		
		if(this.customerService.findCustomerById(id).isPresent()) {
			address.setCustomer(this.customerService.findCustomerById(id).orElse(null));
			
			this.service.saveAddress(address);
//			return ResponseEntity.accepted().body(customerService.updateCustomerAddress(id, address));
			return ResponseEntity.accepted().body(address);
			
		}
		else {
			return ResponseEntity.ok("Customer not found");
		}
		
		
		
//		Address savedAddress = service.saveAddress(address);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//				.buildAndExpand(savedAddress.getAddressId()).toUri();
//		
//		return ResponseEntity.created(location).build();
	}
	
	
	@GetMapping("/customers/{id}/addresses")
	public ResponseEntity<?> getCustomerAddresses(@PathVariable("id") Long id){
		return ResponseEntity.ok(this.service.findAddressByCustomerId(id));
	}
	

	@GetMapping("/addresses")
	public List<Address> retrieveAllAddresses(@RequestParam (name="zipcode", required =false) String zipcode){
		if(zipcode!=null) {
			return this.service.findAllAddressByZipCode(zipcode);
			
		}
		else {
			return this.service.findAllAddress();
		}
		
	}
	

	
	@GetMapping("/addresses/{id}")
	public Address retrieveAddressById(@PathVariable Long id){
		return this.service.findAddressById(id);
	}
	
	@DeleteMapping("/addresses/{id}")
	public void deleteAddressById(@PathVariable Long id){
		 this.service.deleteAddressById(id);
	}
	
}
