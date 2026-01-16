package com.granja.repositorio;

import com.granja.entidad.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {

    Optional<UsuarioEntity> findByEmail(String email);

    List<UsuarioEntity> findByActivo(boolean activo);

    List<UsuarioEntity> findByRol(String rol);
}