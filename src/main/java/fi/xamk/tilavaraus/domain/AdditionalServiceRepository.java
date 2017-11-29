package fi.xamk.tilavaraus.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AdditionalServiceRepository extends CrudRepository<AdditionalService, Long> {
	@Override
	Set<AdditionalService> findAll();
}
