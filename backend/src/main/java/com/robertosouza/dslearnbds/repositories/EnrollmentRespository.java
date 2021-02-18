package com.robertosouza.dslearnbds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robertosouza.dslearnbds.entities.Enrollment;
import com.robertosouza.dslearnbds.entities.pk.EnrollmentPK;

@Repository
public interface EnrollmentRespository extends JpaRepository<Enrollment, EnrollmentPK> {

}
