package db3.DAOTest;

import org.junit.jupiter.api.Test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import db3.dao.StatisticsDAO;
import db3.entity.Statistic;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class StatisticsDAOTest {
	@Autowired 
	StatisticsDAO statisticsDAO;
		
	@Test
	void testStatisticsDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(statisticsDAO);
	}
	
	@Test
	void testFindByIdReturnsStatistic() {
		Statistic storedStatistic = statisticsDAO.findById(20);
		Assertions.assertNotNull(storedStatistic);
		Assertions.assertEquals("ALB", storedStatistic.getCountry());
		Assertions.assertEquals("SE.PRM.TENR.FE", storedStatistic.getIndicator());
		Assertions.assertEquals("92.2315", storedStatistic.getValue().toString());
	}
	
	@Test
	void testFindByCountryAndIndicatorReturnsStatistics() {
		List<Statistic> storedStatistic = statisticsDAO.findByCountryAndIndicator("ALB", "SE.PRM.TENR.FE");
		Assertions.assertNotNull(storedStatistic);
		Assertions.assertEquals("ALB", storedStatistic.get(0).getCountry());
		Assertions.assertEquals("SE.PRM.TENR.FE", storedStatistic.get(0).getIndicator());
		Assertions.assertEquals("95.9873", storedStatistic.get(0).getValue().toString());
	}
}
