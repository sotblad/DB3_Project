package db3.entity;

import java.util.List;

public class StatisticOption {
	private List<String> statisticOption;
	
	public StatisticOption() {
	}
	
	public StatisticOption(List<String> statisticOption) {
		super();
		this.statisticOption = statisticOption;
	}

	public List<String> getStatisticOption() {
	    return statisticOption;
	}
	
	public void setStatisticOption(List<String> statisticOption) {
	    this.statisticOption = statisticOption;
	}
}