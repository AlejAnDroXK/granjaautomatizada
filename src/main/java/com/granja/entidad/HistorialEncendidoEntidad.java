package com.granja.entidad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_encendido")
public class HistorialEncendidoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "aspersor_id", nullable = false, length = 50)
    private String aspersorId;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    public HistorialEncendidoEntidad() {
    }

    public HistorialEncendidoEntidad(String aspersorId, LocalDateTime fecha) {
        this.aspersorId = aspersorId;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAspersorId() {
        return aspersorId;
    }

    public void setAspersorId(String aspersorId) {
        this.aspersorId = aspersorId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}