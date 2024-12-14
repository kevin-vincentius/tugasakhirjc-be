package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.MstUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<MstUser, Long> {

}
