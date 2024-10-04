package com.backend.sahmeto.authentication.repositories;

import com.backend.sahmeto.authentication.models.ERole;
import com.backend.sahmeto.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
