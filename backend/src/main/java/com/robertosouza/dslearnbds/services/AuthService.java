package com.robertosouza.dslearnbds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertosouza.dslearnbds.entities.User;
import com.robertosouza.dslearnbds.repositories.UserRepository;
import com.robertosouza.dslearnbds.services.exceptions.ForbiddenException;
import com.robertosouza.dslearnbds.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public User authenticaded() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid User");
		}
	}
	
	
	public void validateSelfOrAdmin(Long userId) {
		User user = authenticaded();
		
		if(!user.getId().equals(userId) && !user.hasRole("ROLE_ADMIN")) {
			throw new ForbiddenException("Access denied");
		}
	}
}
