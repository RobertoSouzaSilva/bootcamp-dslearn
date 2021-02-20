package com.robertosouza.dslearnbds.services;

import java.time.Instant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertosouza.dslearnbds.dto.NotificationDTO;
import com.robertosouza.dslearnbds.entities.Deliver;
import com.robertosouza.dslearnbds.entities.Notification;
import com.robertosouza.dslearnbds.entities.User;
import com.robertosouza.dslearnbds.observers.DeliverRevisionObserver;
import com.robertosouza.dslearnbds.repositories.NotificationRepository;

@Service
public class NotificationService implements DeliverRevisionObserver {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private DeliverService deliverService;
	
	@PostConstruct
	private void initialize() {
		deliverService.subscribeDeliverRevisionObserver(this);
	}
	
	@Transactional(readOnly = true)
	public Page<NotificationDTO> notificationsForCurrentUser(Pageable pageable, boolean unreadOnly){
		User user = authService.authenticaded();
		Page<Notification> page = notificationRepository.find(user, unreadOnly, pageable);
		return page.map(notification -> new NotificationDTO(notification));
	}
	
	@Transactional
	public void saveDeliverNotification(Deliver deliver) {
		
		Long sectionId = deliver.getLesson().getSection().getId();
		Long resourceId = deliver.getLesson().getSection().getResource().getId();
		Long offerId = deliver.getLesson().getSection().getResource().getOffer().getId();
		
		String route = "/offers/" + offerId + "/resources/" + resourceId + "/sections/" + sectionId;
		String text = deliver.getFeedback();
		Instant moment = Instant.now();
		User user = deliver.getEnrollment().getStudent();
		
		Notification notification = new Notification(null, text, moment, false, route, user);
		notificationRepository.save(notification);
		
	}

	@Override
	public void onSaveRevision(Deliver deliver) {
		saveDeliverNotification(deliver);
		
	}
}
