package fi.xamk.tilavaraus.domain;

import org.springframework.data.repository.CrudRepository;

public interface UploadedFileRepository extends CrudRepository<UploadedFile, String> {
}
