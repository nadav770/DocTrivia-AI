package com.yourorg.doctrivia.service;

import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.repository.DocumentRepository;
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

    // שמירה ישירה של אובייקט document (CRUD בסיסי)
    public document save(document doc) {
        log.info("Saving document: {}", doc.getTitle());
        return documentRepository.save(doc);
    }

    // העלאת PDF, שליפת טקסט, ושמירה
    public document saveDocument(MultipartFile file, String title) {
        String content;

        try (PDDocument pdfDocument = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(pdfDocument);
            log.info("PDF content extracted successfully, length: {}", content.length());
        } catch (IOException e) {
            log.error("Failed to parse PDF file", e);
            throw new RuntimeException("Failed to parse PDF file", e);
        }

        document doc = document.builder()
                .title(title)
                .filename(file.getOriginalFilename())
                .content(content)
                .build();

        return save(doc); // שימוש בפונקציה החדשה!
    }

    // שליפת כל המסמכים
    public List<document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // שליפת מסמך בודד
    public document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    // מחיקת מסמך
    public void deleteDocument(Long id) {
        log.info("Deleting document with id: {}", id);
        documentRepository.deleteById(id);
    }
}
