package se_project.service;

import java.util.List;

import se_project.entity.Indicator;

public interface IndicatorsService {

	public List<Indicator> findAll();
	
	public Indicator findById(int theId);
	
	public Indicator findByCode(String stat);
	
	public List<Indicator> getIndicatorsByStrings(List<String> indicators);
	
}
