package se_project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se_project.dao.IndicatorsDAO;
import se_project.dao.StatisticsDAO;
import se_project.entity.Countries;
import se_project.entity.Indicators;
import se_project.entity.Statistics;

@Service
public class IndicatorsServiceImpl implements IndicatorsService {

	@Autowired
	private IndicatorsDAO indicatorsRepository;
	
	public IndicatorsServiceImpl() {
		super();
	}

	@Autowired
	public IndicatorsServiceImpl(IndicatorsDAO theIndicatorsRepository) {
		indicatorsRepository = theIndicatorsRepository;
	}
	
	@Override
	@Transactional
	public List<Indicators> findAll() {
		return indicatorsRepository.findAll();
	}

	@Override
	@Transactional
	public Indicators findById(int theId) {
		Indicators result = indicatorsRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the Course
			throw new RuntimeException("Did not find indicator id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public Indicators findByCode(String theCode) {
		Indicators result = indicatorsRepository.findByCode(theCode);
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the Course
			throw new RuntimeException("Did not find indicator code - " + theCode);
		}
	}

	@Override
	public List<Indicators> getIndicatorsByStrings(List<String> indicators) {
		List<Indicators> indicatorsList = new ArrayList<>();
		
		for(String indicator : indicators) {
			indicatorsList.add(this.findByCode(indicator));
		}
		return indicatorsList;
	}
}






