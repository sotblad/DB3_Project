package db3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Statistics")
public class Statistic {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="Country")
	private String country;
	
	@Column(name="Year")
	private Integer year;
	
	@Column(name="Indicator")
	private String indicator;
	
	@Column(name="Value")
	private Float value;

	public Statistic() {
		
	}

	public Statistic(int id, String country, Integer year, String indicator, Float value) {
		super();
		this.country = country;
		this.year = year;
		this.indicator = indicator;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Statistics [country=" + country + ", year=" + year + ", indicator=" + indicator + ", value=" + value
				+ "]";
	}
		
}











