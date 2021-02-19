package com.robertosouza.dslearnbds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.robertosouza.dslearnbds.dto.NotificationDTO;
import com.robertosouza.dslearnbds.entities.Notification;
import com.robertosouza.dslearnbds.entities.User;
import com.robertosouza.dslearnbds.repositories.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private AuthService authService;
	
	public Page<NotificationDTO> notificationsForCurrentUser(Pageable pageable, boolean unreadOnly){
		User user = authService.authenticaded();
		Page<Notification> page = notificationRepository.find(user, unreadOnly, pageable);
		return page.map(notification -> new NotificationDTO(notification));
	}
}
