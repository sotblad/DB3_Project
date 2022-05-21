package se_project.DAOTest;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import se_project.dao.CountriesDAO;
import se_project.entity.Country;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class CountriesDAOTest {
	@Autowired 
	CountriesDAO countriesDAO;
		
	@Test
	void testCountriesDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(countriesDAO);
	}
	
	@Test
	void testFindByIdReturnsCountry() {
		Country storedCountry = countriesDAO.findById(10);
		Assertions.assertNotNull(storedCountry);
		Assertions.assertEquals("Greece", storedCountry.getName());
	}
	
	@Test
	void testFindByCodeReturnsCountry() {
		Country storedCountry = countriesDAO.findByCode("GRC");
		Assertions.assertNotNull(storedCountry);
		Assertions.assertEquals("Greece", storedCountry.getName());
	}
}
