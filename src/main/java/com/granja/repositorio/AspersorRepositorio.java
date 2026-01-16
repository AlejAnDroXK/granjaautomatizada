package com.granja.repositorio;

import com.granja.entidad.AspersorEntidad;
import com.granja.entidad.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AspersorRepositorio extends JpaRepository<AspersorEntidad, String> {

    List<AspersorEntidad> findByParcela(ParcelaEntity parcela);

    List<AspersorEntidad> findByParcelaIsNull();
}