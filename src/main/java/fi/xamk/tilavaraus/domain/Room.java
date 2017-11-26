package fi.xamk.tilavaraus.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;
	private String address;
	private Integer capacity;
	private BigDecimal hourlyPrice;
	private String thumbnailSrc;
	@Column(columnDefinition = "LONGVARCHAR")
	private String description;

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getHourlyPrice() {
		return hourlyPrice;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHourlyPrice(BigDecimal hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnailSrc() {
		return thumbnailSrc;
	}

	public void setThumbnailSrc(String thumbnailSrc) {
		this.thumbnailSrc = thumbnailSrc;
	}
}
