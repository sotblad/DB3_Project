package db3.entity;

public class Option {
	private CountryOption countries;
	private StatisticOption stats;
	
	public Option() {
	}
	
	public Option(CountryOption countries, StatisticOption stats) {
		super();
		this.countries = countries;
		this.stats = stats;
	}
	
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