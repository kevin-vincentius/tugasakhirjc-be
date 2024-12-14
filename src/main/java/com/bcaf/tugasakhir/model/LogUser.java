package com.bcaf.tugasakhir.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "LogUser")
public class LogUser  {
    @Id
    @Column(name = "LogUserId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logUserId;

    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Column(name = "AksesId", nullable = false)
    private Short AksesId;

    @Column(name = "NamaLengkap", nullable = false)
    private String namaLengkap;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "CreatedAt", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private Integer createdBy;

}
