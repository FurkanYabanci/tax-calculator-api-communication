package com.yabanci.ayrotekproductuser.repository;

import com.yabanci.ayrotekproductuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
