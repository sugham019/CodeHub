package com.example.backend.repository;

import com.example.backend.model.Tag;
import com.example.backend.model.TagType;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT t FROM User u JOIN u.tags t WHERE u.email = :email AND t.tagType = :tagType")
    Set<Tag> findTagsByUserEmailAndTagType(
            @Param("email") String email,
            @Param("tagType") TagType tagType
    );

}