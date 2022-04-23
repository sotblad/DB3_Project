package se_project.service;

import java.util.List;

import org.json.JSONArray;

import se_project.entity.Countries;
import se_project.entity.Indicators;
import se_project.entity.Statistics;

public interface StatisticsService {

	public List<Statistics> findAll();
	
	public Statistics findById(int theId);
	
	public List<Statistics> findByCountryAndIndicator(String country, String indicator);

	public List<Statistics> findByCountriesAndIndicators(List<Countries> countries, List<Indicators> indicators);
	
	public List<Integer> getYearsList(List<Statistics> stats);
	
	public JSONArray getAggregatedByYear(JSONArray data, int aggregationYears, String chartType);
	
}
