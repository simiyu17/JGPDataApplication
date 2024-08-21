package com.jgp.bmo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BMOClientDataRepository extends JpaRepository<BMOClientData, Long> {
}
