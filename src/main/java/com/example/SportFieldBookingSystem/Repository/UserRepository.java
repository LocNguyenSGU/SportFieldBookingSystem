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

    @Query("SELECT u FROM User u JOIN FETCH u.userRoleList ur JOIN FETCH ur.role WHERE u.email = :email")
    Optional<User> findUserWithRolesByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u JOIN FETCH u.userRoleList ur JOIN FETCH ur.role")
    Page<User> findAllUsersWithRoles(Pageable pageable);

    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);


    @Query("SELECT u FROM User u " +
            "JOIN u.userRoleList ur " +
            "JOIN ur.role r " +
            "WHERE (:email IS NULL OR u.email LIKE %:email%) " +
            "AND (:phone IS NULL OR u.phone LIKE %:phone%) " +
            "AND (:roleName IS NULL OR r.roleName LIKE %:roleName%)")
    Page<User> findByEmailPhoneAndRole(
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("roleName") String roleName,
            Pageable pageable
    );


}
