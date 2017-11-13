package fi.xamk.tilavaraus;

import fi.xamk.tilavaraus.domain.Room;
import fi.xamk.tilavaraus.domain.RoomRepository;
import fi.xamk.tilavaraus.domain.User;
import fi.xamk.tilavaraus.domain.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
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
		r.setHourlyPrice(BigDecimal.valueOf(price));
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
