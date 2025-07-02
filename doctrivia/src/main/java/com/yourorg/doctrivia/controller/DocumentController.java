package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;

    @GetMapping
    public List<document> getAll() {
        return documentRepository.findAll();
    }

    @PostMapping
    public document upload(@RequestBody document doc) {
        doc.setStatus("pending");
        return documentRepository.save(doc);
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
