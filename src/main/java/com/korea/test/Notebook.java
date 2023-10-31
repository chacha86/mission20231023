package com.korea.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Notebook { // 하위노트
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createDate;


    @ManyToOne
    private Notebook parent;

    @OneToMany(mappedBy = "notebook")
    private List<NotePage> notePageList;

    @OneToMany(mappedBy = "parent")
    private List<Notebook> childList;

}
