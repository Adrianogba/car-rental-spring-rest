package io.github.adrianogba.carrent.repository;

import io.github.adrianogba.carrent.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findById(long id);

}
