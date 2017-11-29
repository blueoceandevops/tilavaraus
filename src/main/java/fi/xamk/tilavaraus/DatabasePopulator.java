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

	private Room createRoom(Long id, String name, Integer capacity, Double price, String thumbnailSrc, String description) {
		Room r = new Room();
		r.setId(id);
		r.setName(name);
		r.setCapacity(capacity);
		r.setHourlyPrice(BigDecimal.valueOf(price));
		r.setThumbnailSrc(thumbnailSrc);
		r.setDescription(description);
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
				createRoom(1L, "Mikpolisali", 117, 160.0, "/img/mikpolisali.jpg", "<ul>\n" +
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
					"</ul>\n"),
				createRoom(2L, "Kuitula", 22, 200.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/Kuitula_ylakuva-2-1920x725.jpg", "<ul>\n" +
					"    <li>pc, mahdollisuus ulkopuoliselle pc:lle</li>\n" +
					"    <li>internetyhteys (kiinteä + WLAN)</li>\n" +
					"    <li>dataprojektori</li>\n" +
					"    <li>videoneuvottelulaitteisto, 4 pisteen siltayhteydet</li>\n" +
					"    <li>digiTV</li>\n" +
					"    <li>Blu-ray -soitin</li>\n" +
					"    <li>heijastava seinä</li>\n" +
					"    <li>fläppitaulu</li>\n" +
					"</ul>"),
				createRoom(3L, "Tallin vintti", 40, 90.0, "https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/talli_ylakerta-1920x725.jpg", "<ul>\n" +
					"    <li>pc</li>\n" +
					"    <li>internetyhteys (WLAN)</li>\n" +
					"    <li>dataprojektori</li>\n" +
					"    <li>valkokangas</li>\n" +
					"    <li>fläppitaulu</li>\n" +
					"</ul>"),
				createRoom(4L, "MA325", 9999, 80.0, "/img/ma325.jpg", "<ul>\n" +
					"    <li>pc</li>\n" +
					"    <li>internetyhteys (kiinteä + WLAN)</li>\n" +
					"    <li>dataprojektori</li>\n" +
					"    <li>valkokangas</li>\n" +
					"    <li>fläppitaulu</li>\n" +
					"</ul>"),
				createRoom(5L, "MB124", 14, 60.0, "/img/mb124.jpg", "<ul>\n" +
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
