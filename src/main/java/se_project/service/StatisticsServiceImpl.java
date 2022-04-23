package se_project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se_project.dao.StatisticsDAO;
import se_project.entity.Countries;
import se_project.entity.Indicators;
import se_project.entity.Statistics;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsDAO statisticsRepository;
	
	public StatisticsServiceImpl() {
		super();
	}

	@Autowired
	public StatisticsServiceImpl(StatisticsDAO theStatisticsRepository) {
		statisticsRepository = theStatisticsRepository;
	}
	
	@Override
	@Transactional
	public List<Statistics> findAll() {
		return statisticsRepository.findAll();
	}

	@Override
	@Transactional
	public Statistics findById(int theId) {
		Statistics result = statisticsRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the Course
			throw new RuntimeException("Did not find stat id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public List<Statistics> findByCountryAndIndicator(String Country, String Indicator) {
		List<Statistics> result = statisticsRepository.findByCountryAndIndicator(Country, Indicator);
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the Course
			throw new RuntimeException("Did not find.");
		}
	}

	@Override
	public List<Statistics> findByCountriesAndIndicators(List<Countries> countries, List<Indicators> indicators) {
		List<Statistics> allData = new ArrayList<>();
		for(Indicators indicator : indicators) {
			for(Countries country : countries) {
				List<Statistics> statistics = this.findByCountryAndIndicator(country.getCode(), indicator.getCode());
				for(Statistics statistic : statistics) {
					allData.add(statistic);
				}
			}
		}
		
		Collections.sort(allData, new Comparator<Statistics>(){
			public int compare(Statistics o1, Statistics o2){
				return o1.getYear() - o2.getYear();
			}
		});
		
		return allData;
	}

	@Override
	public List<Integer> getYearsList(List<Statistics> stats) {
		List<Integer> years = new ArrayList<>();
		for(Statistics statistic : stats) {
			if(!years.contains(statistic.getYear())) {
				years.add(statistic.getYear());
			}
		}
		Collections.sort(years);
		
		return years;
	}

	@Override
	public JSONArray getAggregatedByYear(JSONArray json, int aggregationYears, String chartType) {
		List<HashMap<String, List<Float>>> parsedData = new ArrayList<>();
		List<String> pairs = new ArrayList<>();
		List<Integer> years = new ArrayList<>();
		for(int i = 0;i<json.length();i++) {
			years.add(json.getJSONObject(i).getInt("xCoord"));
			for(int j = 0;j<json.getJSONObject(i).getJSONArray("values").length();j++) {
				if(!pairs.contains(json.getJSONObject(i).getJSONArray("values").getJSONObject(j).getString("pair"))) {
					pairs.add(json.getJSONObject(i).getJSONArray("values").getJSONObject(j).getString("pair"));
				}
			}
		}
		List<Integer> tmpyr = new ArrayList<Integer>();
		int tmpyear = years.get(0);
		
		for(int year : years) {
				if(year-tmpyear >= aggregationYears) {
					tmpyear = year;
					tmpyr.add(tmpyear);
				}
		}
		
		HashMap<String, List<Float>> tmpHolder = new HashMap<String, List<Float>>();
		
		for(String k : pairs) {
			List<Float> fltmp = new ArrayList<>();
			fltmp.add((float) 0);
			fltmp.add((float) 0);
			tmpHolder.put(k, fltmp);
		}
		
		for(int i = 0; i<json.length();i++) {
			if(tmpyr.contains(json.getJSONObject(i).getInt("xCoord")) || (json.getJSONObject(i).getInt("xCoord")>tmpyr.get(tmpyr.size()-1) && (i+1) == json.length() )) {
				if((json.getJSONObject(i).getInt("xCoord")>tmpyr.get(tmpyr.size()-1) && (i+1) == json.length() ))
					tmpyr.add((json.getJSONObject(i).getInt("xCoord")));
				tmpHolder.values().removeIf(f -> f.get(0) == 0f);
				parsedData.add(tmpHolder);
				tmpHolder = new HashMap<String, List<Float>>();
				for(String k : pairs) {
					List<Float> fltmp = new ArrayList<>();
					fltmp.add((float) 0);
					fltmp.add((float) 0);
					tmpHolder.put(k, fltmp);
				}
			}
			for(Object jj : json.getJSONObject(i).getJSONArray("values")) {
				List<Float> fltmp = new ArrayList<>();
				fltmp.add(tmpHolder.get(((JSONObject) jj).getString("pair")).get(0) + ((JSONObject) jj).getFloat("value"));
				fltmp.add(tmpHolder.get(((JSONObject) jj).getString("pair")).get(1)+1);
				tmpHolder.put(((JSONObject) jj).getString("pair"), fltmp);
			}
		}
		
		json = new JSONArray();
		List<Integer> teemp = years;
		for(int i = 0;i<tmpyr.size();i++) {
			JSONObject obj = new JSONObject();
			obj.put("xCoord", tmpyr.get(i));
			if(chartType == "barc")
				obj.put("xCoord", teemp.get(0) + "-" + tmpyr.get(i));
			teemp = teemp.subList(teemp.indexOf(tmpyr.get(i)), teemp.size());
			
			JSONArray tmpArr = new JSONArray();
			parsedData.get(i).forEach(
		            (key, value)
		                -> {
		                	float total = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[0]);
		                	Float denum = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[1]);
		                	JSONObject tmpObj = new JSONObject();
		                	tmpObj.put("country", key.toString().split(" ")[0]);
							tmpObj.put("pair", key);
							tmpObj.put("value", total/denum);
							tmpArr.put(tmpObj);
		                });
			obj.put("values", tmpArr);
			json.put(obj);
		}
		return json;
	}
}






