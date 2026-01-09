package com.runninghill.assessment.controller;

import com.runninghill.assessment.DTO.WordDto;
import com.runninghill.assessment.enums.WordType;
import com.runninghill.assessment.service.WordService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/words")
@Slf4j

public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/getAllWords")
    public ResponseEntity<?> getAllWords() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(wordService.getAllWords());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/getAllWordTypes")
    public ResponseEntity<?> getAllWordTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(WordType.values()));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/addWord")
    public ResponseEntity<?> addWord(@Valid @RequestBody WordDto wordDto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(wordService.createWord(wordDto));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PutMapping("/updateWordById/{wordId}")
    public ResponseEntity<?> updateWord(@PathVariable Long wordId, @Valid @RequestBody WordDto wordDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(wordService.updateWord(wordId, wordDto));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @DeleteMapping("/deleteWordById/{wordId}")
    public ResponseEntity<?> deleteWordById(@PathVariable Long wordId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(wordService.deleteWord(wordId));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
