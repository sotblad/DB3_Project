package db3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db3.dao.CountriesDAO;
import db3.entity.Country;

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
	public List<Country> findAll() {
		return countriesRepository.findAll();
	}

	@Override
	@Transactional
	public Country findById(int theId) {
		Country result = countriesRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find country id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public Country findByCode(String theCode) {
		Country result = countriesRepository.findByCode(theCode);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find country code - " + theCode);
		}
	}

	@Override
	public List<Country> getCountriesByStrings(List<String> countries) {
		List<Country> countriesList = new ArrayList<>();
		
		for(String country : countries) {
			countriesList.add(this.findByCode(country));
		}
		return countriesList;
	}
}






