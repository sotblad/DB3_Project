package db3.DAOTest;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import db3.dao.IndicatorsDAO;
import db3.entity.Indicator;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class IndicatorsDAOTest {
	@Autowired 
	IndicatorsDAO indicatorsDAO;
		
	@Test
	void testIndicatorsDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(indicatorsDAO);
	}
	
	@Test
	void testFindByIdReturnsIndicator() {
		Indicator storedIndicator = indicatorsDAO.findById(20);
		Assertions.assertNotNull(storedIndicator);
		Assertions.assertEquals("Adjusted savings: energy depletion (% of GNI)", storedIndicator.getName());
	}
	
	@Test
	void testFindByCodeReturnsIndicator() {
		Indicator storedIndicator = indicatorsDAO.findByCode("NY.ADJ.DNGY.GN.ZS");
		Assertions.assertNotNull(storedIndicator);
		Assertions.assertEquals("Adjusted savings: energy depletion (% of GNI)", storedIndicator.getName());
	}
}
