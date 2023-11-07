package com.korea.test.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notebook")
@RequiredArgsConstructor
public class NotebookController {

    private final NotePageService notePageService;
    private final NotebookService notebookService;

    @RequestMapping("/{notebookId}")
    public String select(@PathVariable("notebookId") Long notebookId, Model model) {

        Notebook notebook = notebookService.getNotebookById(notebookId);
        List<Notebook> notebookList = notebookService.getParentNotebookList();

        List<Notebook> notCheckableList = notebookService.getNotCheckableNotebookList(notebook, new ArrayList<>());

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("notePageList", notebook.getNotePageList());
        model.addAttribute("targetPost", notebook.getNotePageList().get(0));
        model.addAttribute("targetNotebook", notebook);
        model.addAttribute("notCheckableList", notCheckableList);

        return "main";

    }
    @RequestMapping("/")
    public String main(Model model) {

        List<NotePage> notePageList = notePageService.getNotePageList();
        List<Notebook> notebookList = notebookService.getParentNotebookList();
        System.out.println(notebookList.size());
        if(notebookList.isEmpty()) {
            return "redirect:/";
        }

        if(notePageList.isEmpty()) {
            notePageService.saveDefaultNotePage(notebookList.get(0));
            return "redirect:/";
        }

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("notePageList", notePageList);
        model.addAttribute("targetPost", notePageList.get(0));
        model.addAttribute("targetNotebook", notebookList.get(0));

        return "main";
    }

    @PostMapping("/write")
    public String write() {
        Notebook notebook = notebookService.saveDefaultNotebook();
        notePageService.saveDefaultNotePage(notebook);
        return "redirect:/notebook/" + notebook.getId();
    }

    @PostMapping("/add-group")
    public String addGroup(Long notebookId) {
        Notebook notebook = notebookService.saveGroupNotebook(notebookId);
        notePageService.saveDefaultNotePage(notebook);
        return "redirect:/notebook/" + notebook.getId();
    }

    @PostMapping("/delete")
    public String delete(Long notebookId) {
        notebookService.deleteById(notebookId);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(Long notebookId, String notebookName) {
        notebookService.updateName(notebookId, notebookName);
        return "redirect:/";
    }

    @PostMapping("/move")
    public String move(Long moveNotebookId, Long destinationId) {
        notebookService.moveNotebookTo(moveNotebookId, destinationId);
        return "redirect:/notebook/" + moveNotebookId;

    }
}
