package se_project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se_project.dao.CountriesDAO;
import se_project.entity.Countries;

@Service
public class CountriesServiceImpl implements CountriesService {

	@Autowired
	private CountriesDAO countriesRepository;
	
	public CountriesServiceImpl() {
		super();
	}

	@Autowired
	public CountriesServiceImpl(CountriesDAO theCountriesRepository) {
		countriesRepository = theCountriesRepository;
	}
	
	@Override
	@Transactional
	public List<Countries> findAll() {
		return countriesRepository.findAll();
	}

	@Override
	@Transactional
	public Countries findById(int theId) {
		Countries result = countriesRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find country id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public Countries findByCode(String theCode) {
		Countries result = countriesRepository.findByCode(theCode);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find country code - " + theCode);
		}
	}

	@Override
	public List<Countries> getCountriesByStrings(List<String> countries) {
		List<Countries> countriesList = new ArrayList<>();
		
		for(String country : countries) {
			countriesList.add(this.findByCode(country));
		}
		return countriesList;
	}
}






