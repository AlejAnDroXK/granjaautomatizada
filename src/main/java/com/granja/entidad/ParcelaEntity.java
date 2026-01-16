package com.granja.entidad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcela")
public class ParcelaEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "metros_cuadrados", nullable = false)
    private double metrosCuadrados;

    @ManyToOne
    @JoinColumn(name = "cultivo_id")
    private CultivoEntidad cultivo;

    @ManyToOne
    @JoinColumn(name = "usuario_creador_id")
    private UsuarioEntidad usuarioCreador;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    public ParcelaEntity() {
    }

    public ParcelaEntity(String id, double metrosCuadrados) {
        this.id = id;
        this.metrosCuadrados = metrosCuadrados;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public CultivoEntidad getCultivo() {
        return cultivo;
    }

    public void setCultivo(CultivoEntidad cultivo) {
        this.cultivo = cultivo;
    }

    public UsuarioEntidad getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(UsuarioEntidad usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}