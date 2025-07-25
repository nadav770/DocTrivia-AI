package com.yourorg.doctrivia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String filename;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;  // הטקסט מתוך ה-PDF, כמו עכשיו

    private String s3Key;

    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
