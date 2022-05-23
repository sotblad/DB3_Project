package db3.service;

import java.util.List;

import org.json.JSONArray;

import db3.entity.Country;
import db3.entity.Indicator;
import db3.entity.Statistic;

public interface StatisticsService {

	public List<Statistic> findAll();
	
	public Statistic findById(int theId);
	
	public List<Statistic> findByCountryAndIndicator(String country, String indicator);

	public List<Statistic> findByCountriesAndIndicators(List<Country> countries, List<Indicator> indicators);
	
	public List<Integer> getYearsList(List<Statistic> stats);
	
	public JSONArray getAggregatedByYear(JSONArray data, int aggregationYears, String chartType);
	
}
