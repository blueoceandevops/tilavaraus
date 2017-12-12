package fi.xamk.tilavaraus.domain;

import fi.xamk.tilavaraus.domain.validation.Future;
import fi.xamk.tilavaraus.domain.validation.TimeWindow;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Entity
public class Reservation {
	private static final int PREPARATION_DAYS = 7;
	private static final Duration PREPARATION_DURATION = Duration.ofDays(PREPARATION_DAYS);
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@NotNull
	private Room room;
	@NotNull
	@Min(0)
	private Integer personCount;
	@Length(max = 1000)
	private String notes;
	private String stripeToken;
	private String chargeToken;
	@ManyToOne
	@NotNull
	private User user;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<AdditionalService> additionalServices;

	@Future(days = PREPARATION_DAYS - 1)
	@DateTimeFormat(iso = ISO.DATE)
	@NotNull
	private LocalDate date;
	@NotNull
	@TimeWindow(from = 8, to = 20)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	@TimeWindow(from = 8, to = 20)
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	private PaymentMethod paymentMethod;

	public List<AdditionalService> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<AdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public boolean isOld() {
		return this.date.isBefore(LocalDate.now());
	}

	public String getChargeToken() {
		return chargeToken;
	}

	public void setChargeToken(String chargeToken) {
		this.chargeToken = chargeToken;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Duration getDuration() {
		return Duration.between(startTime, endTime);
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
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

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
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

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
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
			.map(List::stream)
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
		return this.date.isAfter(LocalDate.now().plus(PREPARATION_DAYS - 1, ChronoUnit.DAYS));
	}

	public enum PaymentMethod {
		CARD,
		BILL
	}
}
