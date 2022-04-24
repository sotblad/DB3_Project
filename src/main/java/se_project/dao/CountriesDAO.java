package se_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Countries;

@Repository
public interface CountriesDAO extends JpaRepository<Countries, Integer> {
	
	public Countries findById(int theId);
	
	public Countries findByCode(String theCode);
		
}
