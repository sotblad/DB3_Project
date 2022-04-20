package se_project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Countries;
import se_project.entity.Indicators;
import se_project.entity.Statistics;

@Repository
public interface CountriesDAO extends JpaRepository<Countries, Integer> {
	
	public Countries findById(int theId);
	
	public Countries findByCode(String theCode);
		
}
