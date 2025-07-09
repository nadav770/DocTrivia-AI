package com.yourorg.doctrivia.model;


import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String filename;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
