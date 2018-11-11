package io.github.adrianogba.carrent.repository;

import io.github.adrianogba.carrent.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Veiculo findById(long id);

}
