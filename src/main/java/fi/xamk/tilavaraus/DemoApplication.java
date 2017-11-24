package fi.xamk.tilavaraus;

import fi.xamk.tilavaraus.domain.*;
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

	@Bean
	public CommandLineRunner addAdminUser(UserRepository userRepository) {
		return (args) -> {
			if (!userRepository.findByEmail("admin@admin").isPresent()) {
				User user = new User();
				user.setEmail("admin@admin");
				user.setPassword("$2a$10$QcKi3mvVHXrgPX1leqbbqO6s2UQCYKME3aU6KcLjVSjDb3gj1NsNe");
				user.setRole("ROLE_ADMIN");
				user.setAddress("Admin address");
				user.setCity("Admin city");
				user.setZip("12345");
				user.setName("Admin User");
				user.setPhone("123456789");
				userRepository.save(user);
			}
		};
	}

	private AdditionalService createAdditionalService(String name, BigDecimal price) {
		AdditionalService as = new AdditionalService();
		as.setName(name);
		as.setPrice(price);
		return as;
	}

	private Room createRoom(String name, Integer capacity, Double price, String thumbnailSrc) {
		Room r = new Room();
		r.setName(name);
		r.setCapacity(capacity);
		r.setHourlyPrice(BigDecimal.valueOf(price));
		r.setThumbnailSrc(thumbnailSrc);
		return r;
	}

	@Bean
	public CommandLineRunner populateAdditionalServices(AdditionalServiceRepository additionalServiceRepository) {
		return (args) -> {
			if (additionalServiceRepository.count() == 0) {
				additionalServiceRepository.saveAll(Arrays.asList(
					createAdditionalService("foodAndDrink", BigDecimal.valueOf(10.00)),
					createAdditionalService("itSupport", BigDecimal.valueOf(10.00)),
					createAdditionalService("janitor", BigDecimal.valueOf(10.00))
				));
			}
		};
	}

	@Bean
	public CommandLineRunner populateRooms(RoomRepository roomRepository) {
		return (args) ->
		{
			if (roomRepository.count() == 0) {
				roomRepository.saveAll(Arrays.asList(
					createRoom("Mikpolisali", 117, 160.0, "/img/mikpolisali.jpg"),
					createRoom("Kuitula", 22, 200.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/Kuitula_ylakuva-2-1920x725.jpg"),
					createRoom("Tallin vintti", 40, 90.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/talli_ylakerta-1920x725.jpg"),
					createRoom("MA325", 9999, 80.0, "https://via.placeholder.com/350x150"),
					createRoom("MB124", 9999, 60.0, "/img/mb124.jpg")
				));
			}
		};
	}

}
