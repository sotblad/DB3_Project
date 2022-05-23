package db3.ServicesTest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import db3.entity.Country;
import db3.entity.Indicator;
import db3.entity.Statistic;
import db3.service.CountriesService;
import db3.service.IndicatorsService;
import db3.service.StatisticsService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class StatisticsServiceTest {
	@Autowired 
	StatisticsService statisticsService;
	
	@Autowired 
	CountriesService countriesService;
	
	@Autowired 
	IndicatorsService indicatorsService;
		
	@Test
	void testStatisticsDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(statisticsService);
	}
	
	@Test
	void testFindAllReturnsStatistics() {
		List<Statistic> storedIndicators = statisticsService.findAll();
		Assertions.assertNotNull(storedIndicators);
		Assertions.assertEquals("SE.PRM.TENR", storedIndicators.get(9).getIndicator());
		Assertions.assertEquals("89.532", storedIndicators.get(9).getValue().toString());
	}
	
	@Test
	void testFindByIdReturnsStatistic() {
		Statistic storedStatistic = statisticsService.findById(20);
		Assertions.assertNotNull(storedStatistic);
		Assertions.assertEquals("ALB", storedStatistic.getCountry());
		Assertions.assertEquals("SE.PRM.TENR.FE", storedStatistic.getIndicator());
		Assertions.assertEquals("92.2315", storedStatistic.getValue().toString());
	}
	
	@Test
	void testFindByCountryAndIndicatorReturnsStatistics() {
		List<Statistic> storedStatistic = statisticsService.findByCountryAndIndicator("ALB", "SE.PRM.TENR.FE");
		Assertions.assertNotNull(storedStatistic);
		Assertions.assertEquals("ALB", storedStatistic.get(0).getCountry());
		Assertions.assertEquals("SE.PRM.TENR.FE", storedStatistic.get(0).getIndicator());
		Assertions.assertEquals("95.9873", storedStatistic.get(0).getValue().toString());
	}
	
	@Test
	void testFindByCountriesAndIndicatorsReturnsStatistics() {
		List<Country> countries = new ArrayList<>();
		countries.add(countriesService.findById(1));
		countries.add(countriesService.findById(2));
		
		List<Indicator> indicators = new ArrayList<>();
		indicators.add(indicatorsService.findById(1));
		indicators.add(indicatorsService.findById(2));
		
		List<Statistic> storedStatistics = statisticsService.findByCountriesAndIndicators(countries, indicators);
		Assertions.assertNotNull(storedStatistics);
		Assertions.assertEquals("AUT", storedStatistics.get(0).getCountry());
		Assertions.assertEquals("SE.PRM.TENR", storedStatistics.get(0).getIndicator());
		Assertions.assertEquals("90.9558", storedStatistics.get(0).getValue().toString());
	}
	
	@Test
	void testGetYearsListReturnsYears() {
		List<Statistic> storedStatistic = statisticsService.findByCountryAndIndicator("ALB", "SE.PRM.TENR.FE");
		List<Integer> years = statisticsService.getYearsList(storedStatistic);
		Assertions.assertNotNull(years);
	}
}
