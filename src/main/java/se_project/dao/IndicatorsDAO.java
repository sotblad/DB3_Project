package se_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Indicators;

@Repository
public interface IndicatorsDAO extends JpaRepository<Indicators, Integer> {
	
	public Indicators findById(int theId);
	
	public Indicators findByCode(String stat);
		
}
