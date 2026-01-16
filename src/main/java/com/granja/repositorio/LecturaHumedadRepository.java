package com.granja.repositorio;

import com.granja.entidad.LecturaHumedadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LecturaHumedadRepository extends JpaRepository<LecturaHumedadEntity, Integer> {

    List<LecturaHumedadEntity> findBySensorIdOrderByFechaDesc(String sensorId);
}