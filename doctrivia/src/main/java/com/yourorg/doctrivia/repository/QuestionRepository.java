package com.yourorg.doctrivia.repository;

import com.yourorg.doctrivia.model.Question;
import com.yourorg.doctrivia.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDocument(Document document);
}


