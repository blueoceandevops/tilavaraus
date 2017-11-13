package fi.xamk.tilavaraus.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Entity
public class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Room room;
	private Integer personCount;
	@NotNull
	private Instant startTime;
	@NotNull
	private Instant endTime;
	@ManyToOne
	private User user;
	@ElementCollection
	private List<String> additionalServices;

	public BigDecimal getTotalPrice() {
		return getRoom().getHourlyPrice().multiply(BigDecimal.valueOf(getDuration().toHours()));
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Duration getDuration() {
		return Duration.between(startTime, endTime);
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}

	public List<String> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<String> additionalServices) {
		this.additionalServices = additionalServices;
	}
}
