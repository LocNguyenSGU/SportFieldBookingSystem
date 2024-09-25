package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u JOIN FETCH u.userRoleList ur JOIN FETCH ur.role WHERE u.userCode = :userCode")
    Optional<User> findUserWithRolesByUserCode(@Param("userCode") String userCode);

    @Query("SELECT u FROM User u JOIN FETCH u.userRoleList ur JOIN FETCH ur.role")
    Page<User> findAllUsersWithRoles(Pageable pageable);

    boolean existsUserByUsername(String userName);
    boolean existsUserByEmail(String userName);


}
