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

import se_project.entity.Indicators;
import se_project.entity.Option;
import se_project.entity.Statistics;
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
	
	public UIController(StatisticsService theCourseService, IndicatorsService theIndicatorsService) {
		courseService = theCourseService;
		indicatorsService = theIndicatorsService;
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
		for(int i = 0;i < indicators.size(); i++) {
			indNames.add(indicators.get(i).getName());
		}
		Option options = new Option();
		options.setOption(indNames);
		model.addAttribute("optionsObj", new Option());
		model.addAttribute("options", indNames);

	    return "test";
	}
	
	@PostMapping("viewLine")
	public String getViewLine(@ModelAttribute("optionsObj")Option option, Model model) {
		model.addAttribute("options", option.getOption());

	    return "viewLine";
	}
	
	
}






