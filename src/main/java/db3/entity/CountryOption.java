package db3.entity;

import java.util.List;

public class CountryOption {
	private List<String> countryOption;
	
	public CountryOption() {
	}
	
	public CountryOption(List<String> countryOption) {
		super();
		this.countryOption = countryOption;
	}

	public List<String> getCountryOption() {
	    return countryOption;
	}
	
	public void setCountryOption(List<String> countryOption) {
	    this.countryOption = countryOption;
	}
}