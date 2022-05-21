package se_project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Statistic;

@Repository
public interface StatisticsDAO extends JpaRepository<Statistic, Integer> {
	
	public Statistic findById(int theId);
	
	public List<Statistic> findByCountryAndIndicator(String country, String indicator);
		
}
