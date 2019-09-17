package com.plainprog.duobk_web_service.models;



public class Task {
    public Task() {
    }

    private Integer id;
    private String unprocessed;
    private String status;
    private String result;
    private Integer userId;
    private Integer bookId;
    private Integer entry1_id;
    private Integer entry2_id;
    private String name;
    private String bad;
    private String unprocessed1;
    private String unprocessed2;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnprocessed() {
        return unprocessed;
    }

    public void setUnprocessed(String unprocessed) {
        this.unprocessed = unprocessed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getEntry1_id() {
        return entry1_id;
    }

    public void setEntry1_id(Integer entry1_id) {
        this.entry1_id = entry1_id;
    }

    public Integer getEntry2_id() {
        return entry2_id;
    }

    public void setEntry2_id(Integer entry2_id) {
        this.entry2_id = entry2_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getUnprocessed1() {
        return unprocessed1;
    }

    public void setUnprocessed1(String unprocessed1) {
        this.unprocessed1 = unprocessed1;
    }

    public String getUnprocessed2() {
        return unprocessed2;
    }

    public void setUnprocessed2(String unprocessed2) {
        this.unprocessed2 = unprocessed2;
    }
}
