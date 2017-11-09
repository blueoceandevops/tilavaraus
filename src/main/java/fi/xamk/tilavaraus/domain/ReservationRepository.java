package fi.xamk.tilavaraus.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findByRoom(Room room);

	List<Reservation> findByUser(String user);
}
