package fi.xamk.tilavaraus.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ReservationTest {

	@Test
	public void getTotalPrice() {
		Reservation reservation = new Reservation();
		LocalTime min = LocalTime.MIN;
		reservation.setStartTime(min);
		reservation.setEndTime(min.plus(90, ChronoUnit.MINUTES));
		Room room = new Room();
		room.setHourlyPrice(BigDecimal.valueOf(15.0));
		reservation.setRoom(room);
		assertEquals(BigDecimal.valueOf(22.5), reservation.getTotalPrice());
	}
}