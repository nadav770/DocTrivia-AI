package com.yourorg.doctrivia.repository;

import com.yourorg.doctrivia.model.document;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DocumentRepository extends JpaRepository<document, Long> {
}
