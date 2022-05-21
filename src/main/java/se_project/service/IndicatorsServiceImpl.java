package se_project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se_project.dao.IndicatorsDAO;
import se_project.entity.Indicator;

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
	public List<Indicator> findAll() {
		return indicatorsRepository.findAll();
	}

	@Override
	@Transactional
	public Indicator findById(int theId) {
		Indicator result = indicatorsRepository.findById(theId);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find indicator id - " + theId);
		}
	}
	
	@Override
	@Transactional
	public Indicator findByCode(String theCode) {
		Indicator result = indicatorsRepository.findByCode(theCode);
				
		if (result != null ) {
			return result;
		}
		else {
			throw new RuntimeException("Did not find indicator code - " + theCode);
		}
	}

	@Override
	public List<Indicator> getIndicatorsByStrings(List<String> indicators) {
		List<Indicator> indicatorsList = new ArrayList<>();
		
		for(String indicator : indicators) {
			indicatorsList.add(this.findByCode(indicator));
		}
		return indicatorsList;
	}
}






