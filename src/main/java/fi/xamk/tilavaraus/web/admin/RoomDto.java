package fi.xamk.tilavaraus.web.admin;

import fi.xamk.tilavaraus.domain.Room;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
class RoomDto {
	private Room room;
	private MultipartFile coverImage;
	private MultipartFile thumbnailImage;
}
