package com.backend.sahmeto.authentication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64, min = 5)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Size(max = 11, min = 11)
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotBlank
    @Size(max = 128, min = 5)
    @Column(name = "password")
    private String password;

    @Size(max = 128, min = 7)
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 128, min = 7)
    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Setter
    @Getter
    @Column(name = "point")
    private int point;

    @Setter
    @Getter
    @Column(name = "level")
    private int level;

    @Setter
    @Getter
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Setter
    @Getter
    @Column(name = "date_joined")
    private LocalDateTime dateJoined;

    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String phoneNumber, String password, String fullName, String profileImagePath, int point, int level, LocalDateTime lastLogin, LocalDateTime dateJoined, Set<Role> roles) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fullName = fullName;
        this.profileImagePath = profileImagePath;
        this.point = point;
        this.level = level;
        this.lastLogin = lastLogin;
        this.dateJoined = dateJoined;
        this.roles = roles;
    }

    public User(String phoneNumber, String phoneNumber1, String encode, Object o, Object o1, int i, int i1, LocalDateTime now, LocalDateTime now1) {
    }

    public @NotBlank @Size(max = 64, min = 5) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(max = 64, min = 5) String username) {
        this.username = username;
    }

    public @NotBlank @Size(max = 11, min = 11) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank @Size(max = 11, min = 11) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotBlank @Size(max = 128, min = 5) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(max = 128, min = 5) String password) {
        this.password = password;
    }

    public @Size(max = 128, min = 7) String getFullName() {
        return fullName;
    }

    public void setFullName(@Size(max = 128, min = 7) String fullName) {
        this.fullName = fullName;
    }

    public @Size(max = 128, min = 7) String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(@Size(max = 128, min = 7) String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

}
