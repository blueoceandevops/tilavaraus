package fi.xamk.tilavaraus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Room {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String name;
	private String address;
	private Integer capacity;
	private BigDecimal hourlyPrice;

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getHourlyPrice() {
		return hourlyPrice;
	}

	public void setHourlyPrice(BigDecimal hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}
}
