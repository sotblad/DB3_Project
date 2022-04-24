package se_project.service;

import java.util.List;

import se_project.entity.Indicators;

public interface IndicatorsService {

	public List<Indicators> findAll();
	
	public Indicators findById(int theId);
	
	public Indicators findByCode(String stat);
	
	public List<Indicators> getIndicatorsByStrings(List<String> indicators);
	
}
