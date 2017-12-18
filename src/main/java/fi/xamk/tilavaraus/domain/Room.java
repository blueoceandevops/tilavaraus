package fi.xamk.tilavaraus.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min = 1)
	private String name;
	private String address;
	@NotNull
	private Integer capacity;
	@NotNull
	private BigDecimal hourlyPrice;
	private String thumbnailSrc;
	private String coverImageSrc;
	@Column(columnDefinition = "TEXT")
	private String description;
	@ManyToMany
	private List<AdditionalService> allowedAdditionalServices;

}
