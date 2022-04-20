package se_project.controller;



import java.util.ArrayList;
import java.util.List;


import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

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
	private StatisticsService courseService;
	
	@Autowired
	private IndicatorsService indicatorsService;
	
	@Autowired
	private CountriesService countriesService;
	
	public UIController(StatisticsService theCourseService, IndicatorsService theIndicatorsService, CountriesService theCountriesService) {
		courseService = theCourseService;
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
		List<String> indNames = new ArrayList<>();
		List<Indicators> indicators = indicatorsService.findAll();

		List<String> countryNames = new ArrayList<>();
		List<Countries> countries = countriesService.findAll();

		model.addAttribute("countryOptionsObj", new CountryOption());
		model.addAttribute("countryOptions", countries);
		
		model.addAttribute("indOptionsObj", new StatisticOption());
		model.addAttribute("indOptions", indicators);
		
		model.addAttribute("form", new Option());

	    return "test";
	}
	
	@PostMapping("viewLine")
	public String getViewLine(@ModelAttribute("form")Option options, Model model) {
		List<Countries> countriesList = new ArrayList<>();
		List<Indicators> indList = new ArrayList<>();
		
		List<String> countries = options.getCountries().getCountryOption();
		List<String> stats = options.getStats().getStatisticOption();
		
		for(String country : countries) {
			Countries tmpCountry = countriesService.findByCode(country);
			countriesList.add(tmpCountry);
		}
		
		for(String stat : stats) {
			Indicators tmpIndicator = indicatorsService.findByCode(stat);
			indList.add(tmpIndicator);
		}
		
		model.addAttribute("countries", countriesList);
		model.addAttribute("stats", indList);
		
	    return "viewLine";
	}
	
	
}






