package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "status_type")
public class StatusType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    @NotNull
    private int statusId;

    @Column(name = "status_name")
    @NotNull
    private String status;

    public StatusType() {

    }

    public StatusType(int statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
