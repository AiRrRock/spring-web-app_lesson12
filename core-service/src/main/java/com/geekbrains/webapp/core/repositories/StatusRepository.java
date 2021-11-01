package com.geekbrains.webapp.core.repositories;

import com.geekbrains.webapp.core.model.Order;
import com.geekbrains.webapp.core.model.Product;
import com.geekbrains.webapp.core.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByStatus(String status);

}
