package com.example.finalproject.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "multicriteria_updates")
public class MulticriteriaHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multicriteria_update_id")
    private int multicriteriaUpdateId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column(name = "date_of_update")
    private LocalDate dateOfUpdate;

    @Column(name = "directory")
    private String directory;

    public MulticriteriaHistory() {

    }

    public int getMulticriteriaUpdateId() {
        return multicriteriaUpdateId;
    }

    public void setMulticriteriaUpdateId(int multicriteriaUpdateId) {
        this.multicriteriaUpdateId = multicriteriaUpdateId;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public LocalDate getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(LocalDate dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
