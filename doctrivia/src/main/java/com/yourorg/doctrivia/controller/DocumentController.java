package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.service.DocumentService;
import com.yourorg.doctrivia.service.PdfExtractor;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final PdfExtractor pdfExtractor;

    @Operation(summary = "Upload PDF Document", description = "Upload PDF file and extract content")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<document> uploadDocument(@RequestParam("file") MultipartFile file) {

        String content = pdfExtractor.extractTextFromPdf(file); // שליפת טקסט מה-PDF

        document doc = document.builder()
                .title(file.getOriginalFilename())
                .filename(file.getOriginalFilename())
                .content(content)
                .build();
        document savedDoc = documentService.save(doc);
        return ResponseEntity.ok(savedDoc);
    }

    @GetMapping
    public ResponseEntity<List<document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<document> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
