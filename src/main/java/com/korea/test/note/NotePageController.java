package com.korea.test.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotePageController {

    private final NotePageService notePageService;
    private final NotebookService notebookService;

    @RequestMapping("/test")
    @ResponseBody public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Notebook> notebookList = notebookService.getParentNotebookList();

        if(notebookList.isEmpty()) {
            notebookService.saveDefaultNotebook();
            return "redirect:/";
        }

        Notebook targetNotebook = notebookList.get(0);
        List<NotePage> notePageList = notePageService.getNotePageListByNotebook(targetNotebook);

        if(notePageList.isEmpty()) {
            notePageService.saveDefaultNotePage(targetNotebook);
            return "redirect:/";
        }

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("notePageList", notePageList);
        model.addAttribute("targetPost", notePageList.get(0));
        model.addAttribute("targetNotebook", targetNotebook);
        model.addAttribute("notebookList", notebookList);

        return "main";
    }

    @PostMapping("/write")
    public String write(Long notebookId) {
        Notebook notebook = notebookService.getNotebookById(notebookId);
        notePageService.saveDefaultNotePage(notebook);
        return "redirect:/notebook/" + notebookId;
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        NotePage notePage = notePageService.getNotePageById(id);
        List<NotePage> notePageList = notePageService.getNotePageListByNotebook(notePage.getNotebook());
        List<Notebook> notebookList = notebookService.getParentNotebookList();

        model.addAttribute("targetPost", notePage);
        model.addAttribute("notePageList", notePageList);
        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", notePage.getNotebook());

        return "main";
    }
    @PostMapping("/update")
    public String update(Long id, String title, String content) {
        NotePage notePage = notePageService.getNotePageById(id);

        if(title.trim().length() == 0) {
            title = "제목 없음";
        }

        notePage.setTitle(title);
        notePage.setContent(content);

        notePageService.save(notePage);
        return "redirect:/detail/" + id;
    }

    @PostMapping("/delete")
    public String delete(Long id) {
        notePageService.deleteById(id);
        return "redirect:/";
    }

}
