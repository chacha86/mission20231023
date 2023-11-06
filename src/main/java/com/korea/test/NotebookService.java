package com.korea.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotebookService {
     private final NotebookRepository notebookRepository;

    public List<Notebook> getNotebookList() {
        return notebookRepository.findAll();
    }

    public Notebook getNotebookById(Long id) {
        Optional<Notebook> notebookOptional = notebookRepository.findById(id);
        if(notebookOptional.isPresent()) {
            return notebookOptional.get();
        }

        throw new IllegalArgumentException("해당 노트북은 존재하지 않습니다.");
    }
    public Notebook getDefaultNotebook() {
        Notebook notebook = new Notebook();
        notebook.setName("새노트");
        notebook.setCreateDate(LocalDateTime.now());
        return notebook;
    }
    public Notebook saveDefaultNotebook() {
        Notebook notebook = getDefaultNotebook();
        return notebookRepository.save(notebook);
    }
    public Notebook saveGroupNotebook(Long parentId) {

        Notebook parentNotebook = getNotebookById(parentId);
        Notebook childNotebook = getDefaultNotebook();
        childNotebook.setParent(parentNotebook);
        return notebookRepository.save(childNotebook);
    }

    public List<Notebook> getParentNotebookList() {
        return notebookRepository.findByParentId(null);
    }

    public void deleteById(Long notebookId) {
        notebookRepository.deleteById(notebookId);
    }
}
