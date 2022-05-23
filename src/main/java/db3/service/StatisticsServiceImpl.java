package db3.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db3.dao.StatisticsDAO;
import db3.entity.Country;
import db3.entity.Indicator;
import db3.entity.Statistic;

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
	public List<Statistic> findAll() {
		return statisticsRepository.findAll();
	}

	@Override
	@Transactional
	public Statistic findById(int theId) {
		Statistic result = statisticsRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find stat id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public List<Statistic> findByCountryAndIndicator(String Country, String Indicator) {
		List<Statistic> result = statisticsRepository.findByCountryAndIndicator(Country, Indicator);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find stat.");
		}
	}

	@Override
	public List<Statistic> findByCountriesAndIndicators(List<Country> countries, List<Indicator> indicators) {
		List<Statistic> allData = new ArrayList<>();
		for(Indicator indicator : indicators) {
			for(Country country : countries) {
				List<Statistic> statistics = this.findByCountryAndIndicator(country.getCode(), indicator.getCode());
				for(Statistic statistic : statistics) {
					allData.add(statistic);
				}
			}
		}
		
		Collections.sort(allData, new Comparator<Statistic>(){
			public int compare(Statistic o1, Statistic o2){
				return o1.getYear() - o2.getYear();
			}
		});
		
		return allData;
	}

	@Override
	public List<Integer> getYearsList(List<Statistic> stats) {
		List<Integer> years = new ArrayList<>();
		for(Statistic statistic : stats) {
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
		List<String> countries = new ArrayList<>();
		List<Integer> years = new ArrayList<>();
		
		for(int i = 0;i<json.length();i++) {
			years.add(json.getJSONObject(i).getInt("xCoord"));
			
			if(!chartType.equals("scatter")) {
				for(int j = 0;j<json.getJSONObject(i).getJSONArray("values").length();j++) {
					if(!pairs.contains(json.getJSONObject(i).getJSONArray("values").getJSONObject(j).getString("pair"))) {
						pairs.add(json.getJSONObject(i).getJSONArray("values").getJSONObject(j).getString("pair"));
					}
				}
			}else {
				for(int j = 0;j<json.getJSONObject(i).getJSONArray("values").length();j++) {
					if(!countries.contains(json.getJSONObject(i).getJSONArray("values").getJSONObject(j).getString("country"))) {
						countries.add(json.getJSONObject(i).getJSONArray("values").getJSONObject(j).getString("country"));
					}
				}
			}
		}
		
		if((years.get(years.size()-1) - years.get(0) < aggregationYears))
			return json;
		
		List<Integer> tmpyr = new ArrayList<Integer>();
		int tmpyear = years.get(0);
		
		for(int year : years) {
				if(year-tmpyear >= aggregationYears) {
					tmpyear = year;
					tmpyr.add(tmpyear);
				}
		}
		if(years.get(years.size()-1) > tmpyr.get(tmpyr.size()-1))
			tmpyr.add(years.get(years.size()-1));
		
		if(chartType.equals("scatter")) {
			HashMap<String, List<Float>> tmpHolder = new HashMap<String, List<Float>>();
			
			for(String k : countries) {
				List<Float> fltmp = new ArrayList<>();
				fltmp.add((float) 0);
				fltmp.add((float) 0);
				fltmp.add((float) 0);
				tmpHolder.put(k, fltmp);
			}
			
			for(int i = 0;i<json.length();i++) {
				if(tmpyr.contains(json.getJSONObject(i).getInt("xCoord"))) {
					tmpHolder.values().removeIf(f -> f.get(0) == 0f || f.get(1) == 0f);
					parsedData.add(tmpHolder);
					tmpHolder = new HashMap<String, List<Float>>();
					
					for(String k : countries) {
						List<Float> fltmp = new ArrayList<>();
						fltmp.add((float) 0);
						fltmp.add((float) 0);
						fltmp.add((float) 0);
						tmpHolder.put(k, fltmp);
					}
				}
				for(int j = 0;j<json.getJSONObject(i).getJSONArray("values").length();j++) {
					JSONObject obj = (JSONObject) json.getJSONObject(i).getJSONArray("values").get(j);
					List<Float> fltmp = new ArrayList<>();
					fltmp.add(tmpHolder.get(((JSONObject) obj).getString("country")).get(0) + ((JSONObject) obj).getFloat("stat1"));
					fltmp.add(tmpHolder.get(((JSONObject) obj).getString("country")).get(1) + ((JSONObject) obj).getFloat("stat2"));
					fltmp.add(tmpHolder.get(((JSONObject) obj).getString("country")).get(2)+1);
					tmpHolder.put(((JSONObject) obj).getString("country"), fltmp);
				}
			}

			json = new JSONArray();
			List<Integer> teemp = years;
			
			for(int i = 0;i<tmpyr.size();i++) {
				JSONObject obj = new JSONObject();
				obj.put("xCoord", teemp.get(0) + "-" + tmpyr.get(i));
				teemp = teemp.subList(teemp.indexOf(tmpyr.get(i)), teemp.size());
				
				JSONArray tmpArr = new JSONArray();
				parsedData.get(i).forEach(
			            (key, value)
			                -> {
			                	float total1 = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[0]);
			                	float total2 = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[1]);
			                	float denum = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[2]);
			                	JSONObject tmpObj = new JSONObject();
			                	tmpObj.put("country", key);
								tmpObj.put("stat1", total1/denum);
								tmpObj.put("stat2", total2/denum);
								tmpArr.put(tmpObj);
			                });
				obj.put("values", tmpArr);
				json.put(obj);
			}
		}else {
			HashMap<String, List<Float>> tmpHolder = new HashMap<String, List<Float>>();
			
			for(String k : pairs) {
				List<Float> fltmp = new ArrayList<>();
				fltmp.add((float) 0);
				fltmp.add((float) 0);
				tmpHolder.put(k, fltmp);
			}
			
			for(int i = 0; i<json.length();i++) {
				if(tmpyr.contains(json.getJSONObject(i).getInt("xCoord")) || (json.getJSONObject(i).getInt("xCoord")>tmpyr.get(tmpyr.size()-1) && (i+1) == json.length() )) {
					tmpHolder.values().removeIf(f -> f.get(0) == 0);
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
				
				if(chartType.contentEquals("barchart"))
					obj.put("xCoord", teemp.get(0) + "-" + tmpyr.get(i));
				teemp = teemp.subList(teemp.indexOf(tmpyr.get(i)), teemp.size());
				
				JSONArray tmpArr = new JSONArray();
				parsedData.get(i).forEach(
			            (key, value)
			                -> {
			                	float total = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[0]);
			                	float denum = Float.parseFloat(value.toString().substring(1, value.toString().length()-1).split(", ")[1]);
			                	JSONObject tmpObj = new JSONObject();
			                	tmpObj.put("country", key.toString().split(" ")[0]);
								tmpObj.put("pair", key);
								tmpObj.put("value", total/denum);
								tmpArr.put(tmpObj);
			                });
				obj.put("values", tmpArr);
				json.put(obj);
			}
		}
		return json;
	}
}






