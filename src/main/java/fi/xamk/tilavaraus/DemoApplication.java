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

	@Bean
	public CommandLineRunner populateRooms(RoomRepository roomRepository) {
		return (args) -> {
			Room r1 = new Room();
			r1.setId(1L);
			r1.setName("Mikpoli Sali");
			r1.setCapacity(5);
			r1.setPrice(10000.00);
			Room r2 = new Room();
			r2.setName("Vintti");
			r2.setPrice(999.0);
			r2.setId(2L);
			roomRepository.saveAll(Arrays.asList(r1, r2));
		};
	}

}
