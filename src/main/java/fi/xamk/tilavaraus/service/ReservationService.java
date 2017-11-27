package fi.xamk.tilavaraus.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class ReservationService {
	private final Optional<JavaMailSender> mailSender;
	private final ReservationRepository reservationRepository;
	private final String stripeSecret;
	private final Executor executor;

	@Autowired
	public ReservationService(Optional<JavaMailSender> mailSender,
	                          ReservationRepository reservationRepository,
	                          @Value("${stripe.secret:sk_test_xtMoyTT9zw4LeTuY9sWRtfSH}") String stripeSecret, Executor executor) {
		this.mailSender = mailSender;
		this.reservationRepository = reservationRepository;
		this.stripeSecret = stripeSecret;
		this.executor = executor;
	}

	private void chargeCard(Reservation reservation) {
		reservationRepository.save(reservation); // Save reservation to get an ID
		Stripe.apiKey = stripeSecret;
		Map<String, Object> params = new HashMap<>();
		params.put("amount", reservation.getTotalPrice().multiply(BigDecimal.valueOf(100)).intValue());
		params.put("currency", "eur");
		params.put("description", reservation.getUser().getEmail());
		params.put("source", reservation.getStripeToken());
		Map<String, Object> initialMetadata = new HashMap<>();
		initialMetadata.put("reservation_id", reservation.getId());
		initialMetadata.put("authentication_name", SecurityContextHolder.getContext().getAuthentication().getName());
		params.put("metadata", initialMetadata);
		try {
			Charge charge = Charge.create(params);
			reservation.setChargeToken(charge.getId());
			reservationRepository.save(reservation);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void save(Reservation reservation) {

		switch (reservation.getPaymentMethod()) {
			case CARD:
				chargeCard(reservation);
				break;
			case BILL:
				sendBill(reservation);
				break;
			default:
				throw new IllegalStateException("Unimplemented payment method!");
		}

		mailSender.ifPresent(javaMailSender -> CompletableFuture.runAsync(() -> {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("Varausvahvistus");
			mailMessage.setTo(reservation.getUser().getEmail());
			mailMessage.setText("Tila " + reservation.getRoom().getName() + " varattu " + reservation.getPersonCount() + " henkilölle ajalle " +
				reservation.getStartTime().toString() + " - " + reservation.getEndTime().toString() + ".");
			javaMailSender.send(mailMessage);
		}, executor));
	}

	private void sendBill(Reservation reservation) {
		System.out.println("Sending bill to " + reservation.getUser().getAddress());
	}

}
