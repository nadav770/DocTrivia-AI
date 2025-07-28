package com.yourorg.doctrivia.service;

import com.yourorg.doctrivia.model.Document;
import com.yourorg.doctrivia.repository.DocumentRepository;
import com.yourorg.doctrivia.repository.ScoreRepository;
import com.yourorg.doctrivia.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Transactional
    public Document save(Document doc) {
        log.info("Saving document: {}", doc.getTitle());
        return documentRepository.save(doc);
    }

    public Document saveDocument(MultipartFile file, String title) {
        String content;

        try (PDDocument pdfDocument = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(pdfDocument);
            log.info("PDF content extracted successfully, length: {}", content.length());
        } catch (IOException e) {
            log.error("Failed to parse PDF file", e);
            throw new RuntimeException("Failed to parse PDF file", e);
        }

        Document doc = Document.builder()
                .title(title)
                .filename(file.getOriginalFilename())
                .content(content)
                .build();

        return save(doc);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }


    public void deleteDocument(Long id) {
        log.info("Deleting document with id: {}", id);

        // מחיקת ציונים מקושרים למסמך
        scoreRepository.deleteByDocumentId(id);

        // מחיקת שאלות מקושרות למסמך
        questionRepository.deleteByDocumentId(id);

        // לבסוף מחיקת המסמך עצמו
        documentRepository.deleteById(id);
    }
}
