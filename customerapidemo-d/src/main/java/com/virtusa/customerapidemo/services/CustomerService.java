package com.virtusa.customerapidemo.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.repositories.CustomerRepository;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;


@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	
	//save one customer object
	
	public Customer saveCustomer(Customer customer) {
		
		return this.customerRepo.save(customer);
	}
	
	
	public Optional<Customer> findCustomerById(Long id) {
		return this.customerRepo.findById(id);
	}
	
	
	public List<Customer> findAllCustomers(Integer pageNo, Integer pageSize, String sortBy){
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<Customer> pagedResult = customerRepo.findAll(paging);
		
		if(pagedResult.hasContent()) {
			return pagedResult.getContent();
		}else {
			return new ArrayList<Customer>();
		}
		
	}
	
	public List<Customer> findAllCustomers(){
		return this.customerRepo.findAll();
	}
	
	
	public void deleteCustomerById(Long id) {
		this.customerRepo.deleteById(id);
	}
	
	
	public List<Customer> findPaginated(int pageNo, int pageSize){
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Customer> pagedResult = customerRepo.findAll(paging);
		return pagedResult.toList();
	}
	
	public Page<Customer> query(String condition, Pageable pageable){
        // 1.Create the JPA Visitor
        RSQLVisitor<CriteriaQuery<Customer>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Customer>();
        // 2.Parse a RSQL into a Node
        Node rootNode = new RSQLParser().parse(condition);
        // 3.Create CriteriaQuery
        CriteriaQuery<Customer> criteriaQuery = rootNode.accept(visitor, entityManager);
        List<Customer> total = entityManager.createQuery(criteriaQuery).getResultList();
        List<Customer> resultList = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(resultList,pageable, total.size());
    }
	
	
//	public Customer updateCustomerAddress(Long id, Address address) {
//		
//		Customer targetCustomer = this.customerRepo.findById(id).orElse(null);
//		List<Address> customerAddressList = targetCustomer.getAddresses();
//		customerAddressList.add(address);
//		targetCustomer.setAddresses(customerAddressList);
//		return targetCustomer;
//	}
}
