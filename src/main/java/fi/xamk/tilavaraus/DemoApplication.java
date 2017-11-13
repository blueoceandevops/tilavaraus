package fi.xamk.tilavaraus;

import fi.xamk.tilavaraus.domain.Room;
import fi.xamk.tilavaraus.domain.RoomRepository;
import fi.xamk.tilavaraus.domain.User;
import fi.xamk.tilavaraus.domain.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	private Room createRoom(Long id, String name, Integer capacity, Double price, String thumbnailSrc) {
		Room r = new Room();
		r.setId(id);
		r.setName(name);
		r.setCapacity(capacity);
		r.setHourlyPrice(BigDecimal.valueOf(price));
		r.setThumbnailSrc(thumbnailSrc);
		return r;
	}

	@Bean
	public CommandLineRunner populateRooms(RoomRepository roomRepository) {
		return (args) ->
				roomRepository.saveAll(Arrays.asList(
						createRoom(1L, "Mikpolisali", 117, 160.0, "/img/mikpolisali.jpg"),
						createRoom(2L, "Kuitula", 22, 200.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/Kuitula_ylakuva-2-1920x725.jpg"),
						createRoom(3L, "Tallin vintti", 40, 90.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/talli_ylakerta-1920x725.jpg"),
						createRoom(4L, "MA325", 9999, 80.0, "https://via.placeholder.com/350x150"),
						createRoom(5L, "MB124", 9999, 60.0, "https://via.placeholder.com/350x150")
				));
	}

	@Bean
	public CommandLineRunner addAdminUser(UserRepository userRepository) {
		return (args) -> {
			User user = new User();
			user.setEmail("admin@admin");
			user.setPassword("$2a$10$QcKi3mvVHXrgPX1leqbbqO6s2UQCYKME3aU6KcLjVSjDb3gj1NsNe");
			user.setRole("ROLE_ADMIN");
			userRepository.save(user);
		};
	}

}
