package com.bcaf.tugasakhir.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "MstUser")
public class MstUser {
    @Id
    @Column(name = "UserId", length = 8, nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "AksesId", foreignKey = @ForeignKey(name = "fk-user-to-akses"), nullable = false)
    private MstAkses aksesId;

    @Column(name = "NamaLengkap", unique = true, nullable = false)
    private String namaLengkap;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Password", unique = true, nullable = false)
    private String password;

    @Column(name = "NomorHP", unique = true, nullable = false)
    private String nomorHP;

    @Column(name = "UnitKerja", nullable = false)
    private String unitKerja;

    @Column(name = "CreatedAt", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "ModifiedAt")
    @UpdateTimestamp
    private Date modifiedAt;

    @Column(name = "ModifiedBy", insertable = false)
    private Long modifiedBy;

    public MstUser() {
    }

    public String getUnitKerja() {
        return unitKerja;
    }

    public void setUnitKerja(String unitKerja) {
        this.unitKerja = unitKerja;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MstAkses getAksesId() {
        return aksesId;
    }

    public void setAksesId(MstAkses aksesId) {
        this.aksesId = aksesId;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}

