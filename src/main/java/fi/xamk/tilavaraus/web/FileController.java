package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/files")
@Controller
public class FileController {
	private final UploadedFileRepository uploadedFileRepository;

	@Autowired
	public FileController(UploadedFileRepository uploadedFileRepository) {
		this.uploadedFileRepository = uploadedFileRepository;
	}

	@GetMapping("/")
	@ResponseBody
	public byte[] getFile(@RequestParam("path") String path) {
		return uploadedFileRepository.findById(path).get().getData();
	}

}
