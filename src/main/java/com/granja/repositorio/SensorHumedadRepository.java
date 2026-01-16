package com.granja.repositorio;

import com.granja.entidad.SensorHumedadEntity;
import com.granja.entidad.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SensorHumedadRepository extends JpaRepository<SensorHumedadEntity, String> {

    List<SensorHumedadEntity> findByParcela(ParcelaEntity parcela);

    List<SensorHumedadEntity> findByParcelaIsNull();
}