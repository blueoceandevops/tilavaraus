package fi.xamk.tilavaraus.domain;

import fi.xamk.tilavaraus.domain.validation.Future;
import fi.xamk.tilavaraus.domain.validation.TimeWindow;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Room room;
	@NotNull
	@Min(0)
	private Integer personCount;
	public static final int PREPARATION_DAYS = 7;
	public static final Duration PREPARATION_DURATION = Duration.ofDays(PREPARATION_DAYS);
	@ManyToOne
	private User user;
	@ElementCollection
	private List<String> additionalServices;
	@NotNull
	@Future(days = PREPARATION_DAYS)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@TimeWindow(from = 8, to = 20)
	private LocalDateTime startTime;
	@NotNull
	@Future(days = PREPARATION_DAYS, hours = 1)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@TimeWindow(from = 8, to = 20)
	private LocalDateTime endTime;

	public BigDecimal getTotalPrice() {
		return getRoom().getHourlyPrice().multiply(BigDecimal.valueOf(getDuration().toHours()));
	}

	public boolean isCancellable() {
		return this.startTime.isAfter(LocalDateTime.now().plus(PREPARATION_DURATION));
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Duration getDuration() {
		return Duration.between(startTime, endTime);
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
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
