package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.repository.DocumentRepository;
import com.yourorg.doctrivia.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping
    public List<document> getAll() {
        return documentRepository.findAll();
    }

    @PostMapping
    public document upload(@RequestBody document doc) {
        document saved = documentRepository.save(doc);
        kafkaProducerService.sendMessage("doc-events", "New doc uploaded: " + saved.getFilename());
        return saved; // מחזיר את המסמך עם ה־id שנוצר
    }

    @GetMapping("/{id}")
    public document getById(@PathVariable Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        documentRepository.deleteById(id);
    }
}
