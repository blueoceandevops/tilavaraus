package fi.xamk.tilavaraus.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class UploadedFile {
	@Id
	private String path;
	@Lob
	private byte[] data;
}
