package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.Document;
import com.yourorg.doctrivia.repository.DocumentRepository;
import com.yourorg.doctrivia.service.AWSService;
import com.yourorg.doctrivia.service.DocumentService;
import com.yourorg.doctrivia.service.PdfExtractor;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final PdfExtractor pdfExtractor;
    private final DocumentRepository documentRepository;
    private final AWSService awsService;

    @Operation(summary = "Upload PDF Document", description = "Upload PDF file to S3 and extract content")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile file) {
        String content = pdfExtractor.extractTextFromPdf(file);

        // צור מזהה ייחודי לקובץ ב־S3
        String s3Key = "pdfs/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        awsService.putInBucket(file, s3Key);

        Document doc = Document.builder()
                .title(file.getOriginalFilename())
                .filename(file.getOriginalFilename())
                .content(content)
                .s3Key(s3Key)
                .build();

        Document savedDoc = documentService.save(doc);
        return ResponseEntity.ok(savedDoc);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // ✨ חדש: קבלת לינק זמני לצפייה ב־PDF מ־S3
    @GetMapping("/{id}/view-link")
    public ResponseEntity<String> getDocumentViewLink(@PathVariable Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        String url = awsService.generateLink(doc.getS3Key());
        return ResponseEntity.ok(url);
    }
}
