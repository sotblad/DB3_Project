package se_project.controller;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
//@SessionAttributes("employees")
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
		String[] charts = {"barchart", "trendline"};
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
	
	@PostMapping("viewLineea")
	public String getViewLine(@ModelAttribute("form")Option options, Model model) {
		List<Countries> countriesList = new ArrayList<>();
		List<Indicators> indList = new ArrayList<>();
		
		List<String> countries = options.getCountries().getCountryOption();
		List<String> stats = options.getStats().getStatisticOption();
		
		String tsv = "date\t";
		for(String country : countries) {
			Countries tmpCountry = countriesService.findByCode(country);
			countriesList.add(tmpCountry);
			tsv += country + "\t";
		} // ftiaxnei ta columns me ta country codes
		
		for(String stat : stats) {
			Indicators tmpIndicator = indicatorsService.findByCode(stat);
			indList.add(tmpIndicator);
		} // vriskei ta indicator objects
		
		List<Integer> years = new ArrayList<>();
		List<Statistics> finalStats = new ArrayList<>();
		for(int i = 0;i<countries.size();i++) {
			for(int j = 0;j<stats.size();j++) {
				List<Statistics> statistics = statisticsService.findByCountryAndIndicator(countries.get(i), stats.get(j));
				for(Statistics stat : statistics) {
					finalStats.add(stat);
					if(!years.contains(stat.getYear())) {
						years.add(stat.getYear());
					}
				}
			}
		} // apothikevei ola ta statistika apo oles tis xwres gia oles tis xronologies
		
		Collections.sort(finalStats, new Comparator<Statistics>(){
			   public int compare(Statistics o1, Statistics o2){
			      return o1.getYear() - o2.getYear();
			   }
			});
		
		Collections.sort(years); // apothikevei ta years sorted
		tsv += "\n";
		System.out.println(finalStats);
		
		model.addAttribute("countries", countriesList);
		model.addAttribute("stats", indList);
		model.addAttribute("data", tsv);
		
		
		
		HashMap<Integer, HashMap<String, Float>> values = new HashMap<Integer, HashMap<String, Float>>();
		for(int year : years) {
			HashMap<String, Float> in = new HashMap<String, Float>();
			for(Statistics stat : finalStats) {
				if(stat.getYear() == year) {
					in.put(stat.getCountry(), stat.getValue());
				}
			}
			values.put(year, in);
		}
		
		//System.out.println(values);

        List<Integer> sortedKeys=new ArrayList<Integer>(values.keySet());
        Collections.sort(sortedKeys);
        
        JSONArray json = new JSONArray();
        
        for(Integer i: sortedKeys) {
        	JSONObject obj=new JSONObject();
        	HashMap<String, Float> v = values.get(i);
        	
 	       obj.put("xCoord", i);
 	       
        	JSONArray ja = new JSONArray();
        	for(String k : v.keySet()) {
        		JSONObject jObjd=new JSONObject();
        	       jObjd.put("country", k);
        	       jObjd.put("value", v.get(k));
        	       ja.put(jObjd);
        	}
        	obj.put("values", ja);
        	json.put(obj);
       }
        System.out.println(json);
        model.addAttribute("dataGiven", json);
		
	    return "viewLine";
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

		for(int year : years) {
			JSONObject obj=new JSONObject();
			obj.put("xCoord", year);
			
			JSONArray tmpArr = new JSONArray();
			for(Statistics stat : allData) {
				if(stat.getYear() == year) {
					JSONObject tmpObj=new JSONObject();
					tmpObj.put("pair", stat.getCountry() + " " + stat.getIndicator());
					tmpObj.put("value", stat.getValue());
					tmpArr.put(tmpObj);
				}
			}
			obj.put("values", tmpArr);
			json.put(obj);
		}
		
		System.out.println(json);
		model.addAttribute("dataGiven", json);
		
		return chartType;
	}
	
}






