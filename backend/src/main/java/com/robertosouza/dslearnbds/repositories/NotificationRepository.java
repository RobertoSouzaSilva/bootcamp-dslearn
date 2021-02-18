package com.robertosouza.dslearnbds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robertosouza.dslearnbds.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

}
