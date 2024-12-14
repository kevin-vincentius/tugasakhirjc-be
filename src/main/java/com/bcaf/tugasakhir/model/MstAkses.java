package com.bcaf.tugasakhir.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "MstAkses")
public class MstAkses  {
    @Id
    @Column(name = "AksesId", nullable = false)
    private Short aksesId;

    @Column(name = "AksesName", nullable = false, length = 50)
    private String aksesName;

    public Short getAksesId() {
        return aksesId;
    }

    public void setAksesId(Short aksesId) {
        this.aksesId = aksesId;
    }

    public String getAksesName() {
        return aksesName;
    }

    public void setAksesName(String aksesName) {
        this.aksesName = aksesName;
    }
}