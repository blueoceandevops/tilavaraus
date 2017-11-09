package fi.xamk.tilavaraus;

import fi.xamk.tilavaraus.domain.Room;
import fi.xamk.tilavaraus.domain.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	private Room createRoom(Long id, String name, Integer capacity, Double price) {
		Room r = new Room();
		r.setId(id);
		r.setName(name);
		r.setCapacity(capacity);
		r.setHourlyPrice(price);
		return r;
	}

	@Bean
	public CommandLineRunner populateRooms(RoomRepository roomRepository) {
		return (args) ->
				roomRepository.saveAll(Arrays.asList(
						createRoom(1L, "Mikpolisali", 100, 160.0),
						createRoom(2L, "Kuitula", 40, 200.0),
						createRoom(3L, "Tallin vintti", 20, 90.0),
						createRoom(4L, "MA325", 50, 80.0),
						createRoom(5L, "MB124", 50, 60.0)
				));
	}

}
