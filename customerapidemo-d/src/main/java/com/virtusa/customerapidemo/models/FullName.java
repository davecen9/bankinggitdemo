package com.virtusa.customerapidemo.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class FullName {

	@Column(name="first_name",nullable=false)
	private String firstName;
	
	
	@Column(name="last_name",nullable=false)
	private String lastName;
}
