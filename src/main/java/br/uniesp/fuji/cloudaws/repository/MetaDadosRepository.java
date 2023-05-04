package br.uniesp.fuji.cloudaws.repository;


import br.uniesp.fuji.cloudaws.model.MetaDados;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDadosRepository extends CrudRepository<MetaDados, Integer> {
}