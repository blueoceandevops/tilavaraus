package fi.xamk.tilavaraus.web.admin;

import fi.xamk.tilavaraus.domain.AdditionalServiceRepository;
import fi.xamk.tilavaraus.domain.Room;
import fi.xamk.tilavaraus.domain.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/admin/rooms")
@Controller
@Secured("ROLE_ADMIN")
public class RoomController {
	private final RoomRepository roomRepository;
	private final AdditionalServiceRepository additionalServiceRepository;

	@Autowired
	public RoomController(RoomRepository roomRepository, AdditionalServiceRepository additionalServiceRepository) {
		this.roomRepository = roomRepository;
		this.additionalServiceRepository = additionalServiceRepository;
	}

	@GetMapping("/")
	public String listRooms(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "admin/listrooms";
	}

	@GetMapping("/new")
	public String newRoom(Model model) {
		model.addAttribute("room", new Room());
		model.addAttribute("additionalServices", additionalServiceRepository.findAll());
		return "admin/editroom";
	}

	@GetMapping("/{id}")
	public String editRoom(@PathVariable("id") Room room, Model model) {
		model.addAttribute(room);
		model.addAttribute("additionalServices", additionalServiceRepository.findAll());
		return "admin/editroom";
	}

	@GetMapping("/{id}/delete")
	public String deleteRoom(@PathVariable("id") Room room) {
		roomRepository.delete(room);
		return "redirect:/admin/rooms/";
	}

	@PostMapping("/")
	public String saveRoom(@ModelAttribute("room") @Valid Room room,
						   BindingResult bindingResult,
						   Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("additionalServices", additionalServiceRepository.findAll());
			return "admin/editroom";
		}
		roomRepository.save(room);
		return "redirect:/admin/rooms/";
	}

}
