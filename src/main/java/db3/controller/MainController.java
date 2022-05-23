package db3.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import db3.entity.Country;
import db3.entity.CountryOption;
import db3.entity.Indicator;
import db3.entity.Option;
import db3.entity.StatisticOption;
import db3.entity.Statistic;
import db3.service.CountriesService;
import db3.service.IndicatorsService;
import db3.service.StatisticsService;

@Controller
public class MainController {

	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private IndicatorsService indicatorsService;
	
	@Autowired
	private CountriesService countriesService;
	
	public MainController(StatisticsService theStatisticsService, IndicatorsService theIndicatorsService, CountriesService theCountriesService) {
		statisticsService = theStatisticsService;
		indicatorsService = theIndicatorsService;
		countriesService= theCountriesService;
	}
	
	@GetMapping("")
	public String getHome(Model model) {
		String[] charts = {"barchart", "trendline", "scatter"};
		List<Indicator> indicators = indicatorsService.findAll();

		List<Country> countries = countriesService.findAll();

		model.addAttribute("countryOptionsObj", new CountryOption());
		model.addAttribute("countryOptions", countries);
		
		model.addAttribute("indOptionsObj", new StatisticOption());
		model.addAttribute("indOptions", indicators);
		
		model.addAttribute("form", new Option());
		model.addAttribute("charts", charts);

	    return "dashboard";
	}
	
	@PostMapping("chart")
	public String getChart(@ModelAttribute("aggbyyears")String aggregation, @ModelAttribute("chartType")String chartType, @ModelAttribute("form")Option options, Model model) {
		List<Country> countriesList = countriesService.getCountriesByStrings(
				options.getCountries().getCountryOption()
		);
		List<Indicator> indicatorsList = indicatorsService.getIndicatorsByStrings(
				options.getStats().getStatisticOption()
		);
		
		JSONArray json = new JSONArray();
		int aggregationYears = 1;
		
		if(!aggregation.equals("")) {
			aggregationYears = Integer.parseInt(aggregation);
		}
		if(chartType.contentEquals("scatter") && indicatorsList.size() != 2) {
			return "error";
		}
		
		List<Statistic> allData = statisticsService.findByCountriesAndIndicators(countriesList, indicatorsList);
		List<Integer> years = statisticsService.getYearsList(allData);

		List<Integer> selectionYears = new ArrayList<>();
		if(chartType.contentEquals("scatter")) {
			for(int year : years) {
				HashMap<String, List<Statistic>> tmps = new HashMap<String, List<Statistic>>();
				JSONArray tmpArr = new JSONArray();
				JSONObject obj = new JSONObject();
				obj.put("xCoord", year);
				obj.put("indicator1", indicatorsList.get(0).getName());
				obj.put("indicator2", indicatorsList.get(1).getName());
				
				for(Statistic stat : allData) {
						if(stat.getYear() == year) {
							tmps.computeIfAbsent(stat.getCountry(), k -> new ArrayList<>()).add(stat);
						}
				}
				for (Entry<String, List<Statistic>> set : tmps.entrySet()) {
					if(set.getValue().size() == indicatorsList.size()) {
						JSONObject tmpObj = new JSONObject();
						int cnt = 1;
						
						for(Statistic stat : set.getValue()) {
							if(!selectionYears.contains(year)) {
								selectionYears.add(year);
							}
							
							tmpObj.put("country", set.getKey());
							tmpObj.put("stat" + cnt, stat.getValue());
							cnt++;
						}
						
						tmpArr.put(tmpObj);
					}
				}
				obj.put("values", tmpArr);
				if(tmpArr.length() != 0)
					json.put(obj);
			}
		}else {	
			for(int year : years) {
				JSONObject obj = new JSONObject();
				obj.put("xCoord", year);
				
				JSONArray tmpArr = new JSONArray();
				for(Statistic stat : allData) {
					if(stat.getYear() == year) {
						JSONObject tmpObj = new JSONObject();
						tmpObj.put("country", stat.getCountry());
						tmpObj.put("pair", stat.getCountry() + " " + stat.getIndicator());
						tmpObj.put("value", stat.getValue());
						tmpArr.put(tmpObj);
					}
				}
				
				obj.put("values", tmpArr);
				json.put(obj);
			}
			selectionYears = years;
		}
		if(aggregationYears != 1) {
			json = statisticsService.getAggregatedByYear(json, aggregationYears, chartType);
		}

		model.addAttribute("countries", countriesList);
		model.addAttribute("stats", indicatorsList);
		model.addAttribute("listYears", selectionYears);
		model.addAttribute("dataGiven", json);
		
		return chartType;
	}
	
}






