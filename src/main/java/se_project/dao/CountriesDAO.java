package se_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Country;

@Repository
public interface CountriesDAO extends JpaRepository<Country, Integer> {
	
	public Country findById(int theId);
	
	public Country findByCode(String theCode);
		
}
