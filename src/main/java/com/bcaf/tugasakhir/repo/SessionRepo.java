package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepo extends CrudRepository<Session, Long> {
    Optional<Session> findById(Long userId);
    Optional<Session> findBySessionId(String sessionId);

}
