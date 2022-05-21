package se_project.service;

import java.util.List;

import se_project.entity.Country;

public interface CountriesService {

	public List<Country> findAll();
	
	public Country findById(int theId);
	
	public Country findByCode(String theCode);
	
	public List<Country> getCountriesByStrings(List<String> countries);
	
}
