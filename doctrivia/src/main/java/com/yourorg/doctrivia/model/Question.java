package com.yourorg.doctrivia.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode =Schema.AccessMode.READ_ONLY)
    private Long id;

    private String text;
    private String answer;

    @Column(name = "document_id")
    private Long documentId;
}
