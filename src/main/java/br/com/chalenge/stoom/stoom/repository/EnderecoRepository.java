package br.com.chalenge.stoom.stoom.repository;

import br.com.chalenge.stoom.stoom.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Endereco findByStreetName(String streetName);
}
