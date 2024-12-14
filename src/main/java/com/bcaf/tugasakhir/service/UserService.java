package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.dto.ReqChangePasswordDTO;
import com.bcaf.tugasakhir.dto.ReqLoginDTO;
import com.bcaf.tugasakhir.dto.ReqRegisterDTO;
import com.bcaf.tugasakhir.dto.RespLoginDTO;
import com.bcaf.tugasakhir.model.Bookings;
import com.bcaf.tugasakhir.model.MstAkses;
import com.bcaf.tugasakhir.model.MstUser;
import com.bcaf.tugasakhir.model.Session;
import com.bcaf.tugasakhir.repo.AksesRepo;
import com.bcaf.tugasakhir.repo.LogUserRepo;
import com.bcaf.tugasakhir.repo.SessionRepo;
import com.bcaf.tugasakhir.repo.UserRepo;
import com.bcaf.tugasakhir.security.BcryptImpl;
import com.bcaf.tugasakhir.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AksesRepo aksesRepo;

    @Autowired
    private LogUserRepo logUserRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private HttpSession session;

    private ModelMapper modelMapper = new ModelMapper();

    // Login
//    @Scheduled(fixedRate = 60000)
    public ResponseEntity<Object> login(ReqLoginDTO loginDTO) {
        try {
            Optional<MstUser> userOptional = userRepo.findById(loginDTO.getUserId());
            if (userOptional.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.NOT_FOUND, "User ID tidak ditemukan");
            }

            MstUser user = userOptional.get();

            RespLoginDTO respLoginDTO = new RespLoginDTO();

            String combinedPassword = loginDTO.getUserId() + loginDTO.getPassword();
            boolean passwordMatches = BcryptImpl.verifyHash(combinedPassword, user.getPassword());

            if (!passwordMatches) {
                return GlobalFunction.requestFailed(null, HttpStatus.UNAUTHORIZED, "Password salah");
            }

            String sessionId = UUID.randomUUID().toString();
            // simpan sessionId di header/ authorization

            Optional<Session> existingSession = sessionRepo.findById(user.getUserId());

            if (existingSession.isPresent()) {
                Session session = existingSession.get();
                session.setSessionId(sessionId);
                session.setModifiedAt(new Date());
                sessionRepo.save(session);
            } else {
                Session newSession = new Session();
                newSession.setSessionId(sessionId);
                newSession.setUserId(user.getUserId());
                newSession.setModifiedAt(new Date());
                sessionRepo.save(newSession);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Session", sessionId);

            respLoginDTO.setUserId(user.getUserId());
            respLoginDTO.setAksesId(user.getAksesId());
            respLoginDTO.setNamaLengkap(user.getNamaLengkap());
            respLoginDTO.setEmail(user.getEmail());
            respLoginDTO.setCreatedAt(user.getCreatedAt());
            respLoginDTO.setCreatedBy(user.getCreatedBy());
            respLoginDTO.setModifiedAt(user.getModifiedAt());
            respLoginDTO.setModifiedBy(user.getModifiedBy());

            return GlobalFunction.requestSuccess(respLoginDTO, headers, HttpStatus.OK, "Login berhasil!");
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Login gagal");
        }
    }

    // Register
    public ResponseEntity<Object> register(ReqRegisterDTO registerDTO, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Optional<MstUser> existingUser = userRepo.findById(registerDTO.getUserId());

            if (existingUser.isPresent()) {
                return GlobalFunction.requestFailed(null, HttpStatus.CONFLICT, "User ID sudah ada");
            }

            MstUser newUser = modelMapper.map(registerDTO, MstUser.class);
            Optional<MstAkses> akses = aksesRepo.findById(registerDTO.getAksesId());

            newUser.setAksesId(akses.get());

            String combinedPassword = registerDTO.getUserId() + registerDTO.getPassword();
            newUser.setPassword(BcryptImpl.hash(combinedPassword));
            newUser.setCreatedBy(existingSession.get().getUserId());
            newUser.setModifiedBy(existingSession.get().getUserId());

            userRepo.save(newUser);
            return GlobalFunction.requestSuccess(null, null, HttpStatus.CREATED, "Register berhasil!");
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Register gagal");
        }
    }

    public ResponseEntity<Object> getAllUsers(HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            List<MstUser> users = userRepo.findAll();

            return GlobalFunction.requestSuccess(users, null, HttpStatus.OK, "Get list user berhasil!");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Get list user gagal");
        }
    }

    public ResponseEntity<Object> getUserDetail(Long userId, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);

            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Optional<MstUser> user = userRepo.findById(userId);

            return GlobalFunction.requestSuccess(user, null, HttpStatus.OK, "Get user detail berhasil!");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Get user detail gagal");
        }
    }

    public ResponseEntity<Object> changePassword(Long userId, ReqChangePasswordDTO reqChangePasswordDTO, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Optional<MstUser> optionalUser = userRepo.findById(userId);
            if (optionalUser.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.NOT_FOUND, "User ID tidak ada");
            }

            MstUser user = optionalUser.get();

            if (!BcryptImpl.verifyHash(userId + reqChangePasswordDTO.getOldPassword(), user.getPassword())) {
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Password tidak sama");
            }

            String combinedPassword = userId + reqChangePasswordDTO.getNewPassword();
            user.setPassword(BcryptImpl.hash(combinedPassword));
            user.setModifiedBy(userId);

            return GlobalFunction.requestSuccess(null, null, HttpStatus.OK, "Update password untuk user " + userId + " berhasil!");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Change password gagal");
        }
    }

    public ResponseEntity<Object> delete(Long userId, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Optional<MstUser> optionalUser = userRepo.findById(userId);

            if (optionalUser.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.NOT_FOUND, "User tidak ditemukan");
            }
            userRepo.deleteById(userId);
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Delete user gagal");
        }
        return GlobalFunction.requestSuccess(null, null, HttpStatus.OK, "Delete user " + userId + "berhasil!");
    }


}

//1. admin add user
//2. account user terbuat dengan password default
//3. user login dan reset password