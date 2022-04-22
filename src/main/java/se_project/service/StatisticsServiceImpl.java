package se_project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se_project.dao.StatisticsDAO;
import se_project.entity.Countries;
import se_project.entity.Indicators;
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

	@Override
	public List<Statistics> findByCountriesAndIndicators(List<Countries> countries, List<Indicators> indicators) {
		List<Statistics> allData = new ArrayList<>();
		for(Indicators indicator : indicators) {
			for(Countries country : countries) {
				List<Statistics> statistics = this.findByCountryAndIndicator(country.getCode(), indicator.getCode());
				for(Statistics statistic : statistics) {
					allData.add(statistic);
				}
			}
		}
		
		Collections.sort(allData, new Comparator<Statistics>(){
			public int compare(Statistics o1, Statistics o2){
				return o1.getYear() - o2.getYear();
			}
		});
		
		return allData;
	}

	@Override
	public List<Integer> getYearsList(List<Statistics> stats) {
		List<Integer> years = new ArrayList<>();
		for(Statistics statistic : stats) {
			if(!years.contains(statistic.getYear())) {
				years.add(statistic.getYear());
			}
		}
		Collections.sort(years);
		
		return years;
	}
}






