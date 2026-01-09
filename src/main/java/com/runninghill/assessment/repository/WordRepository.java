package com.runninghill.assessment.repository;

import com.runninghill.assessment.model.WordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<WordModel, Long> {

    Optional<WordModel> findByWordIgnoreCase(String word);

    boolean existsByWordIgnoreCase(String word);

    @Query("SELECT w FROM WordModel w WHERE LOWER(w.word) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<WordModel> searchWords(String search);

}
