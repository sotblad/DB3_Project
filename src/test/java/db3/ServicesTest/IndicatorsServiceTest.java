package db3.ServicesTest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import db3.entity.Indicator;
import db3.service.IndicatorsService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class IndicatorsServiceTest {
	@Autowired 
	IndicatorsService indicatorsService;
		
	@Test
	void testIndicatorsDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(indicatorsService);
	}
	
	@Test
	void testFindAllReturnsCountries() {
		List<Indicator> storedIndicators = indicatorsService.findAll();
		Assertions.assertNotNull(storedIndicators);
		Assertions.assertEquals("Adjusted net savings, excluding particulate emission damage (% of GNI)", storedIndicators.get(9).getName());
	}
	
	@Test
	void testFindByIdReturnsIndicator() {
		Indicator storedIndicator = indicatorsService.findById(10);
		Assertions.assertNotNull(storedIndicator);
		Assertions.assertEquals("Adjusted net savings, excluding particulate emission damage (% of GNI)", storedIndicator.getName());
		Assertions.assertEquals("NY.ADJ.SVNX.GN.ZS", storedIndicator.getCode());
	}
	
	@Test
	void testFindByCodeReturnsIndicator() {
		Indicator storedCountry = indicatorsService.findByCode("NY.ADJ.SVNX.GN.ZS");
		Assertions.assertNotNull(storedCountry);
		Assertions.assertEquals(10, storedCountry.getId());
	}
	
	@Test
	void testGetIndicatorsByStringsReturnsIndicators() {
		List<String> indicators = new ArrayList<>();
		indicators.add("NY.ADJ.NNTY.PC.KD.ZG");
		indicators.add("NY.ADJ.NNTY.PC.KD");
		List<Indicator> storedIndicators = indicatorsService.getIndicatorsByStrings(indicators);
		Assertions.assertNotNull(storedIndicators);
		Assertions.assertEquals("Adjusted net national income per capita (annual % growth)", storedIndicators.get(0).getName());
		Assertions.assertEquals("Adjusted net national income per capita (constant 2015 US$)", storedIndicators.get(1).getName());
	}
}
