package com.project.team9.repo;


import com.project.team9.model.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface UserRepository  {
    Optional<User> findByEmail(String email);
}
