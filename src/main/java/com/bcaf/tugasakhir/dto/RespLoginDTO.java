package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.MstAkses;

import java.util.Date;

public class RespLoginDTO {
    private Long userId;
    private String namaLengkap;
    private String email;
    private Date createdAt;
    private Long createdBy;
    private Date modifiedAt;
    private Long modifiedBy;
    private MstAkses aksesId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public MstAkses getAksesId() {
        return aksesId;
    }

    public void setAksesId(MstAkses aksesId) {
        this.aksesId = aksesId;
    }
}
