package com.runninghill.assessment.service;

import com.runninghill.assessment.DTO.WordDto;
import com.runninghill.assessment.enums.WordType;
import com.runninghill.assessment.exception.DuplicateResourceException;
import com.runninghill.assessment.exception.ResourceNotFoundException;
import com.runninghill.assessment.model.WordModel;
import com.runninghill.assessment.repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<WordDto> getAllWords() {
        log.debug("Fetching all words");
        return wordRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public List<WordDto> searchWords(String search) {
        log.debug("Searching words with term: {}", search);
        return wordRepository.searchWords(search).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public WordDto createWord(WordDto wordDto) {
        log.debug("Creating new word: {}", wordDto.getWord());

        // Check for duplicates
        if (wordRepository.existsByWordIgnoreCase(wordDto.getWord())) {
            throw new DuplicateResourceException("Word already exists: " + wordDto.getWord());
        }

        WordModel word = new WordModel();
        word.setWord(wordDto.getWord().trim());
        word.setType(wordDto.getType());

        WordModel savedWord = wordRepository.save(word);
        log.info("Created word with id: {}", savedWord.getId());

        return convertToDto(savedWord);
    }

    @Transactional
    public WordDto updateWord(Long id, WordDto wordDto) {
        log.debug("Updating word with id: {}", id);

        WordModel word = wordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found with id: " + id));

        // Check if the new word already exists (excluding current word)
        wordRepository.findByWordIgnoreCase(wordDto.getWord())
                .ifPresent(existingWord -> {
                    if (!existingWord.getId().equals(id)) {
                        throw new DuplicateResourceException("Word already exists: " + wordDto.getWord());
                    }
                });

        word.setWord(wordDto.getWord().trim());
        word.setType(wordDto.getType());

        WordModel updatedWord = wordRepository.save(word);
        log.info("Updated word with id: {}", id);

        return convertToDto(updatedWord);
    }

    @Transactional
    public String deleteWord(Long id) {
        log.debug("Deleting word with id: {}", id);

        if (!wordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Word not found with id: " + id);
        }

        wordRepository.deleteById(id);
        log.info("Deleted word with id: {}", id);
        return ("Deleted word with id: " + id);
    }

    private WordDto convertToDto(WordModel word) {
        WordDto dto = new WordDto();
        dto.setId(word.getId());
        dto.setWord(word.getWord());
        dto.setType(word.getType());
        return dto;
    }
}
