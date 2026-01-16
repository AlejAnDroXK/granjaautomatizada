package com.granja.servicio;

import com.granja.entidad.*;
import com.granja.modelo.*;
import com.granja.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersistenciaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private AspersorRepository aspersorRepository;

    @Autowired
    private SensorHumedadRepository sensorRepository;

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private LecturaHumedadRepository lecturaRepository;

    @Autowired
    private HistorialEncendidoRepository historialRepository;

    public void guardarUsuario(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRol()
        );
        entity.setFechaRegistro(usuario.getFechaRegistro());
        entity.setActivo(usuario.isActivo());
        usuarioRepository.save(entity);
    }

    public List<Usuario> cargarUsuarios() {
        List<UsuarioEntity> entities = usuarioRepository.findAll();
        List<Usuario> usuarios = new ArrayList<>();
        for (UsuarioEntity entity : entities) {
            Usuario usuario = new Usuario(
                    entity.getId(),
                    entity.getNombre(),
                    entity.getApellido(),
                    entity.getEmail(),
                    entity.getTelefono(),
                    entity.getRol()
            );
            usuario.setFechaRegistro(entity.getFechaRegistro());
            usuario.setActivo(entity.isActivo());
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public void guardarParcela(Parcela parcela, Usuario usuario) {
        ParcelaEntity entity = new ParcelaEntity(parcela.getId(), parcela.getMetrosCuadrados());
        entity.setFechaCreacion(parcela.getFechaCreacion());

        if (usuario != null) {
            Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuario.getId());
            usuarioEntity.ifPresent(entity::setUsuarioCreador);
        }

        if (parcela.getCultivo() != null) {
            Optional<CultivoEntity> cultivoEntity = cultivoRepository.findByNombre(parcela.getCultivo().getNombre());
            cultivoEntity.ifPresent(entity::setCultivo);
        }

        parcelaRepository.save(entity);
    }

    public void eliminarParcela(String idParcela) {
        parcelaRepository.deleteById(idParcela);
    }

    public void guardarAspersor(Aspersor aspersor) {
        AspersorEntity entity = new AspersorEntity(aspersor.getId());
        entity.setConectado(aspersor.isConectado());
        entity.setEncendido(aspersor.isEncendido());

        if (aspersor.getParcela() != null) {
            Optional<ParcelaEntity> parcelaEntity = parcelaRepository.findById(aspersor.getParcela().getId());
            parcelaEntity.ifPresent(entity::setParcela);
        }

        aspersorRepository.save(entity);
    }

    public void eliminarAspersor(String idAspersor) {
        aspersorRepository.deleteById(idAspersor);
    }

    public void guardarSensor(SensorHumedad sensor) {
        SensorHumedadEntity entity = new SensorHumedadEntity(sensor.getId());
        entity.setConectado(sensor.isConectado());
        entity.setHumedadActual(sensor.getHumedadActual());

        if (sensor.getParcela() != null) {
            Optional<ParcelaEntity> parcelaEntity = parcelaRepository.findById(sensor.getParcela().getId());
            parcelaEntity.ifPresent(entity::setParcela);
        }

        sensorRepository.save(entity);
    }

    public void eliminarSensor(String idSensor) {
        sensorRepository.deleteById(idSensor);
    }

    public void guardarLecturaHumedad(String sensorId, LecturaHumedad lectura) {
        LecturaHumedadEntity entity = new LecturaHumedadEntity(
                sensorId,
                lectura.getFecha(),
                lectura.getPorcentajeHumedad()
        );
        lecturaRepository.save(entity);
    }

    public void guardarHistorialEncendido(String aspersorId, java.time.LocalDateTime fecha) {
        HistorialEncendidoEntity entity = new HistorialEncendidoEntity(aspersorId, fecha);
        historialRepository.save(entity);
    }

    public void inicializarCultivos() {
        if (cultivoRepository.count() == 0) {
            cultivoRepository.save(new CultivoEntity("Tomate", 50, 70, 72));
            cultivoRepository.save(new CultivoEntity("Lechuga", 40, 60, 48));
            cultivoRepository.save(new CultivoEntity("Maíz", 45, 65, 96));
            cultivoRepository.save(new CultivoEntity("Zanahoria", 35, 55, 120));
            cultivoRepository.save(new CultivoEntity("Fresa", 55, 75, 60));
            cultivoRepository.save(new CultivoEntity("Pepino", 50, 70, 72));
            cultivoRepository.save(new CultivoEntity("Cebolla", 30, 50, 144));
        }
    }

    public void actualizarCultivoParcela(String idParcela, String nombreCultivo) {
        Optional<ParcelaEntity> parcelaOpt = parcelaRepository.findById(idParcela);
        Optional<CultivoEntity> cultivoOpt = cultivoRepository.findByNombre(nombreCultivo);

        if (parcelaOpt.isPresent() && cultivoOpt.isPresent()) {
            ParcelaEntity parcela = parcelaOpt.get();
            parcela.setCultivo(cultivoOpt.get());
            parcelaRepository.save(parcela);
        }
    }
}