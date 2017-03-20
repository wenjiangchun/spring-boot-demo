package com.haze.springboot.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class QuestionInfo {

    public static final String ROOT_PATH = "/question";

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String data;

    private boolean hasSyn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNodePath() {
        return ROOT_PATH + "/" + id;
    }

    public boolean isHasSyn() {
        return hasSyn;
    }

    public void setHasSyn(boolean hasSyn) {
        this.hasSyn = hasSyn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionInfo that = (QuestionInfo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private Long readCount;

    @Transient
    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                ", hasSyn=" + hasSyn +
                ", title='" + title + '\'' +
                ", readCount=" + readCount +
                '}';
    }
}
