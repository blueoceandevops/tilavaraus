package fi.xamk.tilavaraus.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservationService {
	private final Optional<JavaMailSender> mailSender;
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationService(Optional<JavaMailSender> mailSender, ReservationRepository reservationRepository) {
		this.mailSender = mailSender;
		this.reservationRepository = reservationRepository;
	}

	@Transactional(rollbackOn = Exception.class)
	public void save(Reservation reservation) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {

		reservationRepository.save(reservation);

		Stripe.apiKey = "sk_test_xtMoyTT9zw4LeTuY9sWRtfSH";
		Map<String, Object> params = new HashMap<>();
		params.put("amount", reservation.getTotalPrice().multiply(BigDecimal.valueOf(100)).intValue());
		params.put("currency", "eur");
		params.put("description", reservation.getUser().getEmail());
		params.put("source", reservation.getStripeToken());
		Map<String, Object> initialMetadata = new HashMap<>();
		initialMetadata.put("reservation_id", reservation.getId());
		params.put("metadata", initialMetadata);
		Charge charge = Charge.create(params);

		mailSender.ifPresent(javaMailSender -> new Thread(() -> {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("Varausvahvistus");
			mailMessage.setTo(reservation.getUser().getEmail());
			mailMessage.setText("Tila " + reservation.getRoom().getName() + " varattu " + reservation.getPersonCount() + " henkilölle ajalle " +
				reservation.getStartTime().toString() + " - " + reservation.getEndTime().toString() + ".");
			javaMailSender.send(mailMessage);
		}).start());
	}

}
