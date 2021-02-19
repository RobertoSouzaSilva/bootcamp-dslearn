package com.robertosouza.dslearnbds.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertosouza.dslearnbds.dto.UserDTO;
import com.robertosouza.dslearnbds.entities.User;
import com.robertosouza.dslearnbds.repositories.UserRepository;
import com.robertosouza.dslearnbds.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		authService.validateSelfOrAdmin(id);
		Optional<User> userOptional = userRepository.findById(id);
		User userEntity = userOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		return new UserDTO(userEntity);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username);
		if (user == null) {
			logger.error("usuario nao encontrado " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("usuario encontrado " + username);
		return user;

	}

}
