package fi.xamk.tilavaraus.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ReservationTest {

	@Test
	public void getTotalPrice() {
		Reservation reservation = new Reservation();
		Instant epoch = Instant.EPOCH;
		reservation.setStartTime(epoch);
		reservation.setEndTime(epoch.plus(2, ChronoUnit.HOURS));
		Room room = new Room();
		room.setHourlyPrice(15.0);
		reservation.setRoom(room);
		assertEquals(30.0, reservation.getTotalPrice(), 0.001);
	}
}