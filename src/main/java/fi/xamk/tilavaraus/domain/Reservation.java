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
import java.util.Optional;
import java.util.Set;

@Entity
public class Reservation {
	private static final int PREPARATION_DAYS = 7;
	private static final Duration PREPARATION_DURATION = Duration.ofDays(PREPARATION_DAYS);
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Room room;
	@NotNull
	@Min(0)
	private Integer personCount;
	private String notes;
	private String stripeToken;
	private String chargeToken;
	@ManyToOne
	private User user;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<AdditionalService> additionalServices;
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
	private PaymentMethod paymentMethod;

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public enum PaymentMethod {
		CARD,
		BILL
	}

	public Set<AdditionalService> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(Set<AdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
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

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

	public BigDecimal getTotalPrice() {
		BigDecimal additionalServicesPrice = Optional.ofNullable(getAdditionalServices())
			.map(Set::stream)
			.map(stream -> stream.map(AdditionalService::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
			.orElse(BigDecimal.ZERO);

		return getRoom().getHourlyPrice()
			.multiply(BigDecimal.valueOf(getDuration().toMinutes()))
			.divide(BigDecimal.valueOf(60), BigDecimal.ROUND_HALF_UP)
			.add(additionalServicesPrice);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCancellable() {
		return this.startTime.isAfter(LocalDateTime.now().plus(PREPARATION_DURATION));
	}

	public String getChargeToken() {
		return chargeToken;
	}

	public void setChargeToken(String chargeToken) {
		this.chargeToken = chargeToken;
	}
}
