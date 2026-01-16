package com.granja.repositorio;

import com.granja.entidad.HistorialEncendidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialEncendidoRepository extends JpaRepository<HistorialEncendidoEntity, Integer> {

    List<HistorialEncendidoEntity> findByAspersorIdOrderByFechaDesc(String aspersorId);
}