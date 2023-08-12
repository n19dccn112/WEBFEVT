package com.n19dccn112.repository;

import com.n19dccn112.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    @Query(value = "Select * from users_detail where user_id = ?1 and address_default = 1", nativeQuery = true)
    UserDetail findAllByUserUserIdDefault(Long userId);

    @Modifying
    @Query(value = "Delete from users_detail where user_id = ?1", nativeQuery = true)
    void deleteUserDetailsByUserUserId(Long userId);
}
