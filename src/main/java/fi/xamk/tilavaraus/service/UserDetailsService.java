package fi.xamk.tilavaraus.service;

import fi.xamk.tilavaraus.domain.MyUserDetails;
import fi.xamk.tilavaraus.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.map(MyUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(""));
	}
}
