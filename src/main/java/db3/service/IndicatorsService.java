package db3.service;

import java.util.List;

import db3.entity.Indicator;

public interface IndicatorsService {

	public List<Indicator> findAll();
	
	public Indicator findById(int theId);
	
	public Indicator findByCode(String stat);
	
	public List<Indicator> getIndicatorsByStrings(List<String> indicators);
	
}
