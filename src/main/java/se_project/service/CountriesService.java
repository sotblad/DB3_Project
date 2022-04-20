package se_project.service;

import java.util.List;

import se_project.entity.Countries;

public interface CountriesService {

	public List<Countries> findAll();
	
	public Countries findById(int theId);
	
	public Countries findByCode(String theCode);
	
}
