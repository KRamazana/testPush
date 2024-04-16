package ru.test.best.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.best.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
