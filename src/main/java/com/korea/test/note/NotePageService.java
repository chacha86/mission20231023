package com.korea.test.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotePageService {

    private final NotePageRepository notePageRepository;
    public void saveDefaultNotePage(Notebook notebook) {
        NotePage notePage = new NotePage();
        notePage.setTitle("new title..");
        notePage.setContent("");
        notePage.setCreateDate(LocalDateTime.now());
        notePage.setNotebook(notebook);

        notePageRepository.save(notePage);
    }

    public List<NotePage> getNotePageList() {
        return notePageRepository.findAll();
    }

    public NotePage getNotePageById(Long id) {
        Optional<NotePage> notePageOptional = notePageRepository.findById(id);
        if(notePageOptional.isPresent()) {
            return notePageOptional.get();
        }

        throw new IllegalArgumentException("해당 게시물은 존재하지 않습니다.");
    }

    public void save(NotePage notePage) {
        notePageRepository.save(notePage);
    }

    public void deleteById(Long id) {
        notePageRepository.deleteById(id);
    }

    public List<NotePage> getNotePageListByNotebook(Notebook targetNotebook) {
        return notePageRepository.findByNotebook(targetNotebook);
    }
}
