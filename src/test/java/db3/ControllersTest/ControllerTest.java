package db3.ControllersTest;
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

import db3.controller.MainController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	MainController controller;

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
	void testGetChartBarchartReturnsPage() throws Exception {
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("aggbyyears", Integer.toString(1));
	    multiValueMap.add("chartType", "barchart");
	    multiValueMap.add("countries.countryOption", "GBR");
	    multiValueMap.add("countries.countryOption", "ALB");
	    multiValueMap.add("countries.countryOption", "GRC");
	    multiValueMap.add("stats.statisticOption", "NY.ADJ.NNTY.CD");
	    multiValueMap.add("stats.statisticOption", "NY.ADJ.NNTY.PC.KD.ZG");
	    	    
		mockMvc.perform(
			post("/chart").
			params(multiValueMap)).
		
			andExpect(status().isOk()).
			andExpect(model().attributeExists("countries")).
			andExpect(model().attributeExists("stats")).
			andExpect(model().attributeExists("listYears")).
			andExpect(model().attributeExists("dataGiven")).
			andExpect(view().name("barchart")
		);
	}
	
	@Test 
	void testGetChartTrendlineReturnsPage() throws Exception {		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("aggbyyears", Integer.toString(1));
	    multiValueMap.add("chartType", "trendline");
	    multiValueMap.add("countries.countryOption", "GBR");
	    multiValueMap.add("countries.countryOption", "ALB");
	    multiValueMap.add("countries.countryOption", "GRC");
	    multiValueMap.add("stats.statisticOption", "NY.ADJ.NNTY.CD");
	    multiValueMap.add("stats.statisticOption", "NY.ADJ.NNTY.PC.KD.ZG");
	    	    
		mockMvc.perform(
			post("/chart").
			params(multiValueMap)).
			andExpect(status().isOk()).
			andExpect(model().attributeExists("countries")).
			andExpect(model().attributeExists("stats")).
			andExpect(model().attributeExists("listYears")).
			andExpect(model().attributeExists("dataGiven")).
			andExpect(view().name("trendline")
		);
	}
	
	@Test 
	void testGetChartScatterReturnsPage() throws Exception {		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("aggbyyears", Integer.toString(1));
	    multiValueMap.add("chartType", "scatter");
	    multiValueMap.add("countries.countryOption", "GBR");
	    multiValueMap.add("countries.countryOption", "ALB");
	    multiValueMap.add("countries.countryOption", "GRC");
	    multiValueMap.add("stats.statisticOption", "NY.ADJ.NNTY.CD");
	    multiValueMap.add("stats.statisticOption", "NY.ADJ.NNTY.PC.KD.ZG");
	    	    
		mockMvc.perform(
			post("/chart").
			params(multiValueMap)).
			andExpect(status().isOk()).
			andExpect(model().attributeExists("countries")).
			andExpect(model().attributeExists("stats")).
			andExpect(model().attributeExists("listYears")).
			andExpect(model().attributeExists("dataGiven")).
			andExpect(view().name("scatter")
		);
	}
}