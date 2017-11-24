package fi.xamk.tilavaraus;

import fi.xamk.tilavaraus.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Component
public class DatabasePopulator {

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

	private AdditionalService createAdditionalService(Long id, String name, BigDecimal price) {
		AdditionalService as = new AdditionalService();
		as.setId(id);
		as.setName(name);
		as.setPrice(price);
		return as;
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
	public CommandLineRunner populateAdditionalServices(AdditionalServiceRepository additionalServiceRepository) {
		return (args) ->
			Stream.of(
				createAdditionalService(1L, "foodAndDrink", BigDecimal.valueOf(10.00)),
				createAdditionalService(2L, "itSupport", BigDecimal.valueOf(10.00)),
				createAdditionalService(3L, "janitor", BigDecimal.valueOf(10.00))
			)
				.filter(as -> !additionalServiceRepository.existsById(as.getId()))
				.forEach(additionalServiceRepository::save);
	}

	@Bean
	public CommandLineRunner populateRooms(RoomRepository roomRepository) {
		return (args) ->
			Stream.of(
				createRoom(1L, "Mikpolisali", 117, 160.0, "/img/mikpolisali.jpg"),
				createRoom(2L, "Kuitula", 22, 200.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/Kuitula_ylakuva-2-1920x725.jpg"),
				createRoom(3L, "Tallin vintti", 40, 90.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/talli_ylakerta-1920x725.jpg"),
				createRoom(4L, "MA325", 9999, 80.0, "https://via.placeholder.com/350x150"),
				createRoom(5L, "MB124", 9999, 60.0, "/img/mb124.jpg")
			)
				.filter(room -> !roomRepository.existsById(room.getId()))
				.forEach(roomRepository::save);
	}

}
