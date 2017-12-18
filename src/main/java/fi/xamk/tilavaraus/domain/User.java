package fi.xamk.tilavaraus.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@Email
	@NotNull
	private String email;
	@NotNull
	@Length(min = 6)
	private String password;
	@NotNull
	@Length(min = 1)
	private String name;
	private String role;
	@NotNull
	@Length(min = 1)
	private String address;
	@NotNull
	@Length(min = 1)
	private String phone;
	@NotNull
	@Length(min = 1)
	private String city;
	@NotNull
	@Length(min = 5, max = 5)
	private String zip;

}
