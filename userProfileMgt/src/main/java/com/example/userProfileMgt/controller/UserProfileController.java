package com.example.userProfileMgt.controller;

import com.example.userProfileMgt.model.UserProfile;
import com.example.userProfileMgt.param.ApiResponse;
import com.example.userProfileMgt.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUser(@RequestBody UserProfile userProfile) {
        UserProfile savedUser = userProfileRepository.save(userProfile);
        return ResponseEntity.ok(new ApiResponse<>(true, "User profile created successfully", savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfile>> getUser(@PathVariable Long id) {
        Optional<UserProfile> user = userProfileRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile found", user.get()));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        List<UserProfile> users = userProfileRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "All user profiles retrieved", users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUser(@PathVariable Long id, @RequestBody UserProfile userDetails) {
        Optional<UserProfile> userOptional = userProfileRepository.findById(id);
        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setFullName(userDetails.getFullName());
            user.setAge(userDetails.getAge());
            user.setCountry(userDetails.getCountry());
            user.setBio(userDetails.getBio());
            user.setActive(userDetails.isActive());
            
            UserProfile updatedUser = userProfileRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile updated successfully", updatedUser));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        if (userProfileRepository.existsById(id)) {
            userProfileRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User profile not found", null));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        
        List<UserProfile> results;
        
        if (username != null) {
            results = userProfileRepository.findByUsernameContainingIgnoreCase(username);
        } else if (country != null) {
            results = userProfileRepository.findByCountryIgnoreCase(country);
        } else if (minAge != null && maxAge != null) {
            results = userProfileRepository.findByAgeRange(minAge, maxAge);
        } else {
            results = userProfileRepository.findAll();
        }
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", results));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserProfile>> updateUserStatus(@PathVariable Long id, @RequestParam boolean active) {
        Optional<UserProfile> userOptional = userProfileRepository.findById(id);
        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            user.setActive(active);
            UserProfile updatedUser = userProfileRepository.save(user);
            String status = active ? "activated" : "deactivated";
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile " + status + " successfully", updatedUser));
        } else {
             return ResponseEntity.status(404).body(new ApiResponse<>(false, "User profile not found", null));
        }
    }
}
