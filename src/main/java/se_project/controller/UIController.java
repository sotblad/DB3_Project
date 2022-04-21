package se_project.controller;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import se_project.entity.Countries;
import se_project.entity.CountryOption;
import se_project.entity.Indicators;
import se_project.entity.Option;
import se_project.entity.StatisticOption;
import se_project.entity.Statistics;
import se_project.service.CountriesService;
import se_project.service.IndicatorsService;
import se_project.service.StatisticsService;

@Controller
@RequestMapping("/dashboard")
public class UIController {

	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private IndicatorsService indicatorsService;
	
	@Autowired
	private CountriesService countriesService;
	
	public UIController(StatisticsService theStatisticsService, IndicatorsService theIndicatorsService, CountriesService theCountriesService) {
		statisticsService = theStatisticsService;
		indicatorsService = theIndicatorsService;
		countriesService= theCountriesService;
	}
	
	@GetMapping("")
	public String dashboard(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
	    model.addAttribute("user", authentication.getName());

	    return "dashboard/dashboard";
	}
	
	@GetMapping("test")
	public String getTest(Model model) {
		String[] charts = {"barchart", "trendline", "scatter"};
		List<String> indNames = new ArrayList<>();
		List<Indicators> indicators = indicatorsService.findAll();

		List<String> countryNames = new ArrayList<>();
		List<Countries> countries = countriesService.findAll();

		model.addAttribute("countryOptionsObj", new CountryOption());
		model.addAttribute("countryOptions", countries);
		
		model.addAttribute("indOptionsObj", new StatisticOption());
		model.addAttribute("indOptions", indicators);
		
		model.addAttribute("form", new Option());
		model.addAttribute("charts", charts);

	    return "test";
	}
	
	@PostMapping("chart")
	public String getChart(@ModelAttribute("chartType")String chartType, @ModelAttribute("form")Option options, Model model) {
		JSONArray json = new JSONArray();
		List<Countries> countriesList = new ArrayList<>();
		List<Indicators> indList = new ArrayList<>();
		
		List<String> countries = options.getCountries().getCountryOption();
		List<String> stats = options.getStats().getStatisticOption();
		
		for(String country : countries) {
			Countries tmpCountry = countriesService.findByCode(country);
			countriesList.add(tmpCountry);
		} // vriskei ta country objects pou epilexthikan
		
		for(String stat : stats) {
			Indicators tmpIndicator = indicatorsService.findByCode(stat);
			indList.add(tmpIndicator);
		} // vriskei ta indicator objects pou epilexthikan
		
		if(chartType.contentEquals("scatter") && stats.size() != 2) {
			return "error";
		}
		
		model.addAttribute("countries", countriesList);
		model.addAttribute("stats", indList);
		
		List<Statistics> allData = new ArrayList<>();
		List<Integer> years = new ArrayList<>();
		for(String stat : stats) {
			for(String country : countries) {
				List<Statistics> statistics = statisticsService.findByCountryAndIndicator(country, stat);
				for(Statistics statistic : statistics) {
					if(!years.contains(statistic.getYear())) {
						years.add(statistic.getYear());
					}
					allData.add(statistic);
				}
			}
		}
		
		Collections.sort(years);
		Collections.sort(allData, new Comparator<Statistics>(){
			   public int compare(Statistics o1, Statistics o2){
			      return o1.getYear() - o2.getYear();
			   }
			});
		
		List<Integer> scatterYears = new ArrayList<>();
		if(chartType.contentEquals("scatter")) {
			for(int year : years) {
				JSONObject obj=new JSONObject();
				obj.put("xCoord", year);
				obj.put("indicator1", stats.get(0));
				obj.put("indicator2", stats.get(1));
				
				HashMap<String, List<Statistics>> tmps = new HashMap<String, List<Statistics>>();
				JSONArray tmpArr = new JSONArray();
				for(Statistics stat : allData) {
						if(stat.getYear() == year) {
							tmps.computeIfAbsent(stat.getCountry(), k -> new ArrayList<>()).add(stat);
						}
				}
			//	System.out.println(year + " " + tmps);
				for (Entry<String, List<Statistics>> set : tmps.entrySet()) {
					if(set.getValue().size() == stats.size()) {
						JSONObject tmpObj=new JSONObject();
						int cnt = 1;
						for(Statistics stat : set.getValue()) {
							if(!scatterYears.contains(year)) {
								scatterYears.add(year);
							}
							System.out.println(stat);
							//tmpObj.put("indicator" + cnt, stat.getIndicator());
							tmpObj.put("country", set.getKey());
							tmpObj.put("stat" + cnt, stat.getValue());
							cnt += 1;
						}
						
						
						tmpArr.put(tmpObj);
					}
				}
				obj.put("values", tmpArr);
				json.put(obj);
			}
		}else {
			for(int year : years) {
				JSONObject obj=new JSONObject();
				obj.put("xCoord", year);
				
				JSONArray tmpArr = new JSONArray();
				for(Statistics stat : allData) {
					if(stat.getYear() == year) {
						JSONObject tmpObj=new JSONObject();
						tmpObj.put("country", stat.getCountry());
						tmpObj.put("pair", stat.getCountry() + " " + stat.getIndicator());
						tmpObj.put("value", stat.getValue());
						tmpArr.put(tmpObj);
					}
				}
				obj.put("values", tmpArr);
				json.put(obj);
			}
		}
		System.out.println(scatterYears);
		model.addAttribute("listYears", scatterYears);
		model.addAttribute("dataGiven", json);
		
		return chartType;
	}
	
}






