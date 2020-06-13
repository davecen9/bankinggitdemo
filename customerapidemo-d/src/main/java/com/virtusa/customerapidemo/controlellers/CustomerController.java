package com.virtusa.customerapidemo.controlellers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.services.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService service;

	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody Customer customer) {
		return this.service.saveCustomer(customer);
	}
	

	@GetMapping(path="/customers",produces= { "application/json", "application/xml" })
	public ResponseEntity<List<Customer>> retrieveAllCustomers(@RequestParam(name="v",required=false,defaultValue="1")String version){
		System.out.println(version);
		if( version.equals("1")) {
		return ResponseEntity.ok().header("version","v1").body(this.service.findAllCustomers());
		}
		else if(version.equals("2")) {
			return ResponseEntity.ok().header("version","v2").body(this.service.findAllCustomers());
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
//	
//	@GetMapping("/customers/{pageNo}/{pageSize}")
//	public List<Customer> retrivePaginatedCustomers(@PathVariable int pageNo, @PathVariable int pageSize ){
//
//		return this.service.findPaginated(pageNo, pageSize);
//	
//	}
	
	
	@GetMapping("/customers/rsql")
    public Page<Customer> query(@RequestParam String condition,
                                @RequestParam(required = false,defaultValue = "1") int page,
                                @RequestParam(required = false,defaultValue = "2") int size,
                                @RequestParam(defaultValue = "customerId") String sortBy){
        return this.service.query(condition,PageRequest.of(page, size));
    }
		
	
	
	@GetMapping("/customers/sorter")
    public ResponseEntity<?> findAllCustomers(
                                @RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "2") int pageSize,
                                @RequestParam(defaultValue = "customerId") String sortBy){
        List<Customer> list = service.findAllCustomers(pageNo,pageSize,sortBy);
        
        return list.size()>0? new ResponseEntity<List<Customer>>(list,new HttpHeaders(), HttpStatus.OK) : ResponseEntity.ok("Customer Not Found");
        
    }
	
	@GetMapping("/customer/{id}")
	public Optional<Customer> findCustomerById(@PathVariable Long id){
		return this.service.findCustomerById(id);
	}
	
	
	
}
