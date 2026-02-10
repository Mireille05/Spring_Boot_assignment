package com.example.userProfileMgt.repository;

import com.example.userProfileMgt.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    List<UserProfile> findByUsernameContainingIgnoreCase(String username);

    List<UserProfile> findByCountryIgnoreCase(String country);

    // Using query for range
    @Query("SELECT u FROM UserProfile u WHERE u.age >= :minAge AND u.age <= :maxAge")
    List<UserProfile> findByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);
}
