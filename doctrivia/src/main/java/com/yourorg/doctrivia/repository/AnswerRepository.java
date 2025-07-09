package com.yourorg.doctrivia.repository;

import com.yourorg.doctrivia.model.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {
    // כל התשובות של משתמש
    List<Answers> findByUserId(Long userId);

    // כל התשובות של משתמש למסמך מסוים
    List<Answers> findByUserIdAndDocumentId(Long userId, Long documentId);

    // כל התשובות של משתמש לשאלה מסוימת (רשות)
    List<Answers> findByUserIdAndQuestionId(Long userId, Long questionId);
}
