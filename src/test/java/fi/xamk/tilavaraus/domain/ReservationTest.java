package fi.xamk.tilavaraus.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ReservationTest {

	@Test
	public void getTotalPrice() {
		Reservation reservation = new Reservation();
		LocalDateTime epoch = LocalDateTime.MIN;
		reservation.setStartTime(epoch);
		reservation.setEndTime(epoch.plus(2, ChronoUnit.HOURS));
		Room room = new Room();
		room.setHourlyPrice(BigDecimal.valueOf(15.0));
		reservation.setRoom(room);
		assertEquals(BigDecimal.valueOf(30.0), reservation.getTotalPrice());
	}
}