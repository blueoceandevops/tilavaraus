package fi.xamk.tilavaraus.web.admin;

import fi.xamk.tilavaraus.domain.*;
import fi.xamk.tilavaraus.web.FileController;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.Optional;
import java.util.function.Function;

@RequestMapping("/admin/rooms")
@Controller
@Secured("ROLE_ADMIN")
public class RoomController {
	private final RoomRepository roomRepository;
	private final AdditionalServiceRepository additionalServiceRepository;
	private final UploadedFileRepository uploadedFileRepository;

	@Autowired
	public RoomController(RoomRepository roomRepository, AdditionalServiceRepository additionalServiceRepository, UploadedFileRepository uploadedFileRepository) {
		this.roomRepository = roomRepository;
		this.additionalServiceRepository = additionalServiceRepository;
		this.uploadedFileRepository = uploadedFileRepository;
	}

	@GetMapping("/")
	public String listRooms(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "admin/listrooms";
	}

	private static String getFullPath(UploadedFile uploadedFile) {
		UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(
			FileController.class,
			"getFile",
			uploadedFile.getPath()
		).build();
		return uriComponents.getPath() + "?" + uriComponents.getQuery();
	}

	private static Function<MultipartFile, UploadedFile> toUploadedFile(Room room) {
		//noinspection Convert2Lambda
		return new Function<MultipartFile, UploadedFile>() {
			@Override
			@SneakyThrows
			public UploadedFile apply(MultipartFile multipartFile) {
				UploadedFile uploadedFile = new UploadedFile();
				uploadedFile.setPath(room.getName() + "/" + multipartFile.getOriginalFilename());
				uploadedFile.setData(multipartFile.getBytes());
				return uploadedFile;
			}
		};
	}

	@GetMapping("/{id}/delete")
	public String deleteRoom(@PathVariable("id") Room room) {
		roomRepository.delete(room);
		return "redirect:/admin/rooms/";
	}

	@GetMapping("/new")
	public String newRoom(Model model) {
		RoomDto roomDto = new RoomDto();
		roomDto.setRoom(new Room());
		model.addAttribute(roomDto);
		model.addAttribute("additionalServices", additionalServiceRepository.findAll());
		return "admin/editroom";
	}

	@GetMapping("/{id}")
	public String editRoom(@PathVariable("id") Room room, Model model) {
		RoomDto roomDto = new RoomDto();
		roomDto.setRoom(room);
		model.addAttribute(roomDto);
		model.addAttribute("additionalServices", additionalServiceRepository.findAll());
		return "admin/editroom";
	}

	@PostMapping("/")
	public String saveRoom(@ModelAttribute("roomDto") @Valid RoomDto roomDto,
	                       BindingResult bindingResult,
	                       Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("additionalServices", additionalServiceRepository.findAll());
			return "admin/editroom";
		}

		toPath(roomDto.getRoom(), roomDto.getThumbnailImage()).ifPresent(roomDto.getRoom()::setThumbnailSrc);
		toPath(roomDto.getRoom(), roomDto.getCoverImage()).ifPresent(roomDto.getRoom()::setCoverImageSrc);

		roomRepository.save(roomDto.getRoom());
		return "redirect:/admin/rooms/";
	}

	private Optional<String> toPath(Room room, MultipartFile multipartFile) {
		return Optional.ofNullable(multipartFile)
			.filter(mf -> !mf.isEmpty())
			.map(toUploadedFile(room))
			.map(uploadedFileRepository::save)
			.map(RoomController::getFullPath);
	}

}
