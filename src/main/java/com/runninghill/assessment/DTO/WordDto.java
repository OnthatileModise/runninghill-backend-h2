package com.runninghill.assessment.DTO;

import com.runninghill.assessment.enums.WordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private Long id;

    @NotBlank(message = "Word cannot be blank")
    @Size(min = 2, max = 50, message = "Word must be between 2 and 50 characters")
    private String word;

    @NotNull(message = "Word type is required")
    private WordType type;
}
