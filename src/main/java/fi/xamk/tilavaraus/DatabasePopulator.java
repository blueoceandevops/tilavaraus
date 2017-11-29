package fi.xamk.tilavaraus;

import fi.xamk.tilavaraus.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DatabasePopulator {

	private final AdditionalServiceRepository additionalServiceRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;

	@Autowired
	public DatabasePopulator(AdditionalServiceRepository additionalServiceRepository, UserRepository userRepository, RoomRepository roomRepository) {
		this.additionalServiceRepository = additionalServiceRepository;
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
	}

	private void addAdminUser() {
		String ADMIN_EMAIL = "admin@admin.test";
		if (!userRepository.findByEmail(ADMIN_EMAIL).isPresent()) {
			User user = new User();
			user.setEmail(ADMIN_EMAIL);
			user.setPassword("$2a$10$QcKi3mvVHXrgPX1leqbbqO6s2UQCYKME3aU6KcLjVSjDb3gj1NsNe");
			user.setRole("ROLE_ADMIN");
			user.setAddress("Admin address");
			user.setCity("Admin city");
			user.setZip("12345");
			user.setName("Admin User");
			user.setPhone("123456789");
			userRepository.save(user);
		}
	}

	private Room createRoom(Long id,
	                        String name,
	                        Integer capacity,
	                        Double price,
	                        String thumbnailSrc,
	                        String coverImageSrc,
	                        String description) {
		Room r = new Room();
		r.setId(id);
		r.setName(name);
		r.setCapacity(capacity);
		r.setHourlyPrice(BigDecimal.valueOf(price));
		r.setThumbnailSrc(thumbnailSrc);
		r.setCoverImageSrc(coverImageSrc);
		r.setDescription(description);
		r.setAllowedAdditionalServices(
			id == 1L ? additionalServiceRepository.findAll().stream()
				.filter(as -> as.getId() != 1L).collect(Collectors.toSet()) : additionalServiceRepository.findAll()
		);
		return r;
	}

	private AdditionalService createAdditionalService(Long id, String name, BigDecimal price) {
		AdditionalService as = new AdditionalService();
		as.setId(id);
		as.setName(name);
		as.setPrice(price);
		return as;
	}

	private void populateAdditionalServices() {
			Stream.of(
				createAdditionalService(1L, "foodAndDrink", BigDecimal.valueOf(10.00)),
				createAdditionalService(2L, "itSupport", BigDecimal.valueOf(10.00)),
				createAdditionalService(3L, "janitor", BigDecimal.valueOf(10.00))
			)
				.filter(as -> !additionalServiceRepository.existsById(as.getId()))
				.forEach(additionalServiceRepository::save);
	}

	@Bean
	private CommandLineRunner populateDatabase() {
		return (args) -> {
			addAdminUser();
			populateAdditionalServices();
			populateRooms();
		};
	}

	private void populateRooms() {
		Stream.of(
			createRoom(
				1L,
				"Mikpolisali",
				117,
				160.0,
				"/img/mikpolisali-357w.jpg",
				"/img/mikpolisali.jpg",
				"<ul>\n" +
				"    <li>dataprojektori, 1920 x 1080</li>\n" +
				"    <li>internetyhteys (kiinteä ja WLAN)</li>\n" +
				"    <li>kiinteä tietokone nettiyhteydellä, mahdollisuus myös ulkopuoliselle tietokoneelle</li>\n" +
				"    <li>useita liitäntöjä tietokoneille (VGA, HDMI)</li>\n" +
				"    <li>blu-ray -soitin</li>\n" +
				"    <li>digiTV</li>\n" +
				"    <li>videoneuvottelulaite, 4 pisteen siltayhteydet</li>\n" +
				"    <li>dokumenttikamera</li>\n" +
				"    <li>langattomat mikrofonit, 4 kpl</li>\n" +
				"    <li>kiinteät mikrofonit, 3 kpl</li>\n" +
				"    <li>piano</li>\n" +
					"</ul>\n"
			),
			createRoom(
				2L,
				"Kuitula",
				22,
				200.0,
				"/img/kuitula-357w.jpg",
				"/img/kuitula.jpg",
				"<ul>\n" +
				"    <li>pc, mahdollisuus ulkopuoliselle pc:lle</li>\n" +
				"    <li>internetyhteys (kiinteä + WLAN)</li>\n" +
				"    <li>dataprojektori</li>\n" +
				"    <li>videoneuvottelulaitteisto, 4 pisteen siltayhteydet</li>\n" +
				"    <li>digiTV</li>\n" +
				"    <li>Blu-ray -soitin</li>\n" +
				"    <li>heijastava seinä</li>\n" +
				"    <li>fläppitaulu</li>\n" +
				"</ul>"),
			createRoom(
				3L,
				"Tallin vintti",
				40,
				90.0,
				"/img/vintti-357w.jpg",
				"/img/vintti.jpg",
				"<ul>\n" +
				"    <li>pc</li>\n" +
				"    <li>internetyhteys (WLAN)</li>\n" +
				"    <li>dataprojektori</li>\n" +
				"    <li>valkokangas</li>\n" +
				"    <li>fläppitaulu</li>\n" +
				"</ul>"),
			createRoom(
				4L,
				"MA325",
				9999,
				80.0,
				"/img/ma325-357w.jpg",
				"/img/ma325.jpg",
				"<ul>\n" +
				"    <li>pc</li>\n" +
				"    <li>internetyhteys (kiinteä + WLAN)</li>\n" +
				"    <li>dataprojektori</li>\n" +
				"    <li>valkokangas</li>\n" +
				"    <li>fläppitaulu</li>\n" +
				"</ul>"),
			createRoom(
				5L,
				"MB124",
				14,
				60.0,
				"/img/mb124-357w.jpg",
				"/img/mb124.jpg",
				"<ul>\n" +
				"    <li>pc</li>\n" +
				"    <li>internetyhteys (kiinteä + WLAN)</li>\n" +
				"    <li>kiinteä skype-kamera</li>\n" +
				"    <li>dataprojektori</li>\n" +
				"    <li>valkokangas</li>\n" +
				"    <li>fläppitaulu</li>\n" +
				"</ul>")
		)
			.filter(room -> !roomRepository.existsById(room.getId()))
			.forEach(roomRepository::save);
	}
}
