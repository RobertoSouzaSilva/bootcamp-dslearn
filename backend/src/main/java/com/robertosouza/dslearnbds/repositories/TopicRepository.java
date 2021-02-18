package com.robertosouza.dslearnbds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robertosouza.dslearnbds.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{

}
