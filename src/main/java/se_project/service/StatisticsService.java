package se_project.service;

import java.util.List;

import org.json.JSONArray;

import se_project.entity.Country;
import se_project.entity.Indicator;
import se_project.entity.Statistic;

public interface StatisticsService {

	public List<Statistic> findAll();
	
	public Statistic findById(int theId);
	
	public List<Statistic> findByCountryAndIndicator(String country, String indicator);

	public List<Statistic> findByCountriesAndIndicators(List<Country> countries, List<Indicator> indicators);
	
	public List<Integer> getYearsList(List<Statistic> stats);
	
	public JSONArray getAggregatedByYear(JSONArray data, int aggregationYears, String chartType);
	
}
