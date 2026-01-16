package com.granja.repositorio;

import com.granja.entidad.AspersorEntity;
import com.granja.entidad.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AspersorRepository extends JpaRepository<AspersorEntity, String> {

    List<AspersorEntity> findByParcela(ParcelaEntity parcela);

    List<AspersorEntity> findByParcelaIsNull();
}