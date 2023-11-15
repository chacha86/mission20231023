package com.korea.test.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotePageRepository extends JpaRepository<NotePage, Long>  {
    List<NotePage> findByNotebook(Notebook targetNotebook);
}
