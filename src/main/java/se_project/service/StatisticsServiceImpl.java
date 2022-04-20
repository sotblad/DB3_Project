package se_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se_project.dao.StatisticsDAO;
import se_project.entity.Statistics;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsDAO statisticsRepository;
	
	public StatisticsServiceImpl() {
		super();
	}

	@Autowired
	public StatisticsServiceImpl(StatisticsDAO theStatisticsRepository) {
		statisticsRepository = theStatisticsRepository;
	}
	
	@Override
	@Transactional
	public List<Statistics> findAll() {
		return statisticsRepository.findAll();
	}

	@Override
	@Transactional
	public Statistics findById(int theId) {
		Statistics result = statisticsRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the Course
			throw new RuntimeException("Did not find stat id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public List<Statistics> findByCountryAndIndicator(String Country, String Indicator) {
		List<Statistics> result = statisticsRepository.findByCountryAndIndicator(Country, Indicator);
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the Course
			throw new RuntimeException("Did not find.");
		}
	}
}






