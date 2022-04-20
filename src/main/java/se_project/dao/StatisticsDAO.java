package se_project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Statistics;

@Repository
public interface StatisticsDAO extends JpaRepository<Statistics, Integer> {
	
	public Statistics findById(int theId);
	
	public List<Statistics> findByCountryAndIndicator(String country, String indicator);
		
}
