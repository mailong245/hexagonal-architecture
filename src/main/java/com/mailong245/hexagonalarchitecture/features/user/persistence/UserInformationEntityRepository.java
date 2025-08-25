package com.mailong245.hexagonalarchitecture.features.user.persistence;


import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.UserEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.UserEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationEntityRepository extends UserEntityRepository {

    // Native SQL for a feature-specific read
    @Query(value = "SELECT u.id AS id, u.email AS email, u.full_name AS fullName FROM users u WHERE u.id = :userId", nativeQuery = true)
    UserEntity searchById(@Param("user_id") String userId);
}
