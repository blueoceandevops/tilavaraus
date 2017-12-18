package fi.xamk.tilavaraus.domain;

import fi.xamk.tilavaraus.domain.validation.Future;
import fi.xamk.tilavaraus.domain.validation.TimeWindow;
import lombok.Data;
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

@Data
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

	public boolean isOld() {
		return this.date.isBefore(LocalDate.now());
	}

	public Duration getDuration() {
		return Duration.between(startTime, endTime);
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

	public boolean isCancellable() {
		return this.date.isAfter(LocalDate.now().plus(PREPARATION_DAYS - 1, ChronoUnit.DAYS));
	}

	public enum PaymentMethod {
		CARD,
		BILL
	}
}
