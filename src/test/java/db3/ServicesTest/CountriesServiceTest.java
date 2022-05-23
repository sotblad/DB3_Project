package db3.ServicesTest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import db3.entity.Country;
import db3.service.CountriesService;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class CountriesServiceTest {
	@Autowired 
	CountriesService countriesService;
		
	@Test
	void testCountriesDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(countriesService);
	}
	
	@Test
	void testFindAllReturnsCountries() {
		List<Country> storedCountries = countriesService.findAll();
		Assertions.assertNotNull(storedCountries);
		Assertions.assertEquals(1, storedCountries.get(0).getId());
	}
	
	@Test
	void testFindByIdReturnsCountry() {
		Country storedCountry = countriesService.findById(10);
		Assertions.assertNotNull(storedCountry);
		Assertions.assertEquals("Greece", storedCountry.getName());
	}
	
	@Test
	void testFindByCodeReturnsCountry() {
		Country storedCountry = countriesService.findByCode("GRC");
		Assertions.assertNotNull(storedCountry);
		Assertions.assertEquals("Greece", storedCountry.getName());
	}
	
	@Test
	void testGetCountriesByStringsReturnsCountries() {
		List<String> countries = new ArrayList<>();
		countries.add("GRC");
		countries.add("ALB");
		List<Country> storedCountries = countriesService.getCountriesByStrings(countries);
		Assertions.assertNotNull(storedCountries);
		Assertions.assertEquals("Greece", storedCountries.get(0).getName());
		Assertions.assertEquals("Albania", storedCountries.get(1).getName());
	}
}
