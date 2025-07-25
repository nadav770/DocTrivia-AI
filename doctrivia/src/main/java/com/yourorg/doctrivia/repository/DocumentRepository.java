package com.yourorg.doctrivia.repository;

import com.yourorg.doctrivia.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {}
