package com.granja.repositorio;

import com.granja.entidad.ParcelaEntity;
import com.granja.entidad.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<ParcelaEntity, String> {

    List<ParcelaEntity> findByUsuarioCreador(UsuarioEntity usuarioCreador);
}

