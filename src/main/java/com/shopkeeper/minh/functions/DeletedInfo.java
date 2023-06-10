package com.shopkeeper.minh.functions;

import java.time.LocalDate;

public class DeletedInfo {
    private long id;
    private LocalDate time;

    public DeletedInfo(long id, LocalDate time){
        this.id = id;
        this.time = time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public LocalDate getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
