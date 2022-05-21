package se_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Indicator;

@Repository
public interface IndicatorsDAO extends JpaRepository<Indicator, Integer> {
	
	public Indicator findById(int theId);
	
	public Indicator findByCode(String stat);
		
}
