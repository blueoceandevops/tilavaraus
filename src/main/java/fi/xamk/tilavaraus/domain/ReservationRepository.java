package fi.xamk.tilavaraus.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findByRoom(Room room);
	List<Reservation> findByUser(String user);

	@Query("select r from Reservation r where " +
			"(r.startTime between ?1 and ?2 or r.endTime between ?1 and ?2) or " +
			"(r.startTime < ?1 and r.endTime > ?2) or " +
			"(r.startTime > ?1 and r.endTime < ?2) and " +
			"(r.room = ?3)")
	List<Reservation> findOverlapping(Instant start, Instant end, Room room);
}
