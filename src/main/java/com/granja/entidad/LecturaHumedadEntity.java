package com.granja.entidad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lectura_humedad")
public class LecturaHumedadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sensor_id", nullable = false, length = 50)
    private String sensorId;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "porcentaje_humedad", nullable = false)
    private int porcentajeHumedad;

    public LecturaHumedadEntity() {
    }

    public LecturaHumedadEntity(String sensorId, LocalDateTime fecha, int porcentajeHumedad) {
        this.sensorId = sensorId;
        this.fecha = fecha;
        this.porcentajeHumedad = porcentajeHumedad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getPorcentajeHumedad() {
        return porcentajeHumedad;
    }

    public void setPorcentajeHumedad(int porcentajeHumedad) {
        this.porcentajeHumedad = porcentajeHumedad;
    }
}