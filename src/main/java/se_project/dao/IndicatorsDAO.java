package se_project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se_project.entity.Indicators;
import se_project.entity.Statistics;

@Repository
public interface IndicatorsDAO extends JpaRepository<Indicators, Integer> {
	
	public Indicators findById(int theId);
		
}
