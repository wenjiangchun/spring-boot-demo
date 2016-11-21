package com.haze.springboot.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Sofar on 2016/11/14.
 */
@Entity
public class QuestionInfo {

    public static final String ROOT_PATH = "/question";

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid.hex")
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
}
