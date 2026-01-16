package com.granja.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "cultivo")
public class CultivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "humedad_minima", nullable = false)
    private int humedadMinima;

    @Column(name = "humedad_maxima", nullable = false)
    private int humedadMaxima;

    @Column(name = "frecuencia_riego_horas", nullable = false)
    private int frecuenciaRiegoHoras;

    public CultivoEntity() {
    }

    public CultivoEntity(String nombre, int humedadMinima, int humedadMaxima, int frecuenciaRiegoHoras) {
        this.nombre = nombre;
        this.humedadMinima = humedadMinima;
        this.humedadMaxima = humedadMaxima;
        this.frecuenciaRiegoHoras = frecuenciaRiegoHoras;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHumedadMinima() {
        return humedadMinima;
    }

    public void setHumedadMinima(int humedadMinima) {
        this.humedadMinima = humedadMinima;
    }

    public int getHumedadMaxima() {
        return humedadMaxima;
    }

    public void setHumedadMaxima(int humedadMaxima) {
        this.humedadMaxima = humedadMaxima;
    }

    public int getFrecuenciaRiegoHoras() {
        return frecuenciaRiegoHoras;
    }

    public void setFrecuenciaRiegoHoras(int frecuenciaRiegoHoras) {
        this.frecuenciaRiegoHoras = frecuenciaRiegoHoras;
    }
}