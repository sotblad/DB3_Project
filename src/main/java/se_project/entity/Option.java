package se_project.entity;

import java.util.List;

public class Option {
	private CountryOption countries;
	private StatisticOption stats;
	
	public CountryOption getCountries() {
		return countries;
	}
	public void setCountries(CountryOption countries) {
		this.countries = countries;
	}
	public StatisticOption getStats() {
		return stats;
	}
	public void setStats(StatisticOption stats) {
		this.stats = stats;
	}
}