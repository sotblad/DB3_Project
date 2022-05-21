package se_project.ControllersTest;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import se_project.controller.UIController;
import se_project.entity.CountryOption;
import se_project.entity.Option;
import se_project.entity.StatisticOption;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestCourseController {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	UIController controller;

	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testControllerIsNotNull() {
		Assertions.assertNotNull(controller);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}	
	
	@Test 
	void testHomeReturnsPage() throws Exception {
		mockMvc.perform(
			get("/")).
			andExpect(status().isOk()).
			andExpect(model().attributeExists("countryOptionsObj")).
			andExpect(model().attributeExists("countryOptions")).
			andExpect(model().attributeExists("indOptionsObj")).
			andExpect(model().attributeExists("indOptions")).
			andExpect(model().attributeExists("form")).
			andExpect(model().attributeExists("charts")).
			andExpect(view().name("dashboard")
		);		
	}
	
	@Test 
	void testGetChartReturnsPage() throws Exception {
		List<String> countryList = new ArrayList<>();
		countryList.add("GRE");
		countryList.add("ALB");
		List<String> statsList = new ArrayList<>();
		statsList.add("SE.PRM.TENR.MA");
		statsList.add("NY.ADJ.NNTY.PC.KD.ZG");
		CountryOption countries = new CountryOption(countryList);
		StatisticOption stats = new StatisticOption(statsList);
		Option options = new Option(new CountryOption(countryList), new StatisticOption(statsList));
		System.out.println(options);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("aggbyyears", Integer.toString(1));
	    multiValueMap.add("chartType", "barchart");
	    multiValueMap.add("form", String.valueOf(options));
	  //  multiValueMap.add("form", String.valueOf(stats));
	    	    
		mockMvc.perform(
			post("/chart").
			params(multiValueMap)).
		
			andExpect(status().isFound()).
			andExpect(model().attributeExists("countries")).
			andExpect(model().attributeExists("stats")).
			andExpect(model().attributeExists("listYears")).
			andExpect(model().attributeExists("dataGiven")).
			andExpect(view().name("barchart")
		);
	}
}