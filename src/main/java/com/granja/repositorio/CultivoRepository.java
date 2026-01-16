package com.granja.repositorio;

import com.granja.entidad.CultivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CultivoRepository extends JpaRepository<CultivoEntity, Integer> {

    Optional<CultivoEntity> findByNombre(String nombre);
}