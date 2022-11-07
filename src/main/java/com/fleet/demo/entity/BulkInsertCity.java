package com.fleet.demo.entity;

import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class BulkInsertCity extends City implements Persistable<String>, Serializable {

    @Override
    public String getId() {
        return getFips();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
