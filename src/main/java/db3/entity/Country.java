package db3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Countries")
public class Country {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Code")
	private String code;
	
	@Column(name="LongName")
	private String longName;
	
	@Column(name="Region")
	private String region;
	
	@Column(name="Currency")
	private String currency;
	
	public Country() {
		
	}

	public Country(String name, String code, String longName, String region, String currency) {
		super();
		this.name = name;
		this.code = code;
		this.longName = longName;
		this.region = region;
		this.currency = currency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Countries [name=" + name + ", code=" + code + ", longName=" + longName + ", region=" + region
				+ ", currency=" + currency + "]";
	}
}
