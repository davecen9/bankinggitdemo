package com.virtusa.customerapidemo.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name="customer")
@Data
@XmlRootElement(name="customer")
public class Customer {
	
	public Customer() {
		super();
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_id")
	private long customerId;
	

	@Embedded
	@Column(name="name",nullable=false)
	private FullName name;
	

	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	@Column(name="DOB")
	private Date dob;
	
	@Column(name="Email",nullable=false, length = 100)
	private String email;
	
	@Column(name="phone_number")
	private long phoneNo;

}
