package com.runninghill.assessment.model;

import com.runninghill.assessment.enums.WordType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "words")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Word cannot be blank")
    @Size(min = 2, max = 50, message = "Word must be between 2 and 50 characters")
    @Column(nullable = false, length = 50)
    private String word;

    @NotNull(message = "Word type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WordType type;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public WordModel(String word, WordType type) {
        this.word = word;
        this.type = type;
    }
}
