package com.example.finalproject.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "current_multicriteria")
public class CurrentMulticriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int mmruId;

    @Column(name = "directory")
    private String directory;

    public CurrentMulticriteria() {

    }

    public int getMmruId() {
        return mmruId;
    }

    public void setMmruId(int mmruId) {
        this.mmruId = mmruId;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
