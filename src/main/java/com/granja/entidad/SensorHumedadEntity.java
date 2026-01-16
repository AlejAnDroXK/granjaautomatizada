package com.granja.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "sensor_humedad")
public class SensorHumedadEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "conectado")
    private boolean conectado;

    @Column(name = "humedad_actual")
    private int humedadActual;

    @ManyToOne
    @JoinColumn(name = "parcela_id")
    private ParcelaEntity parcela;

    public SensorHumedadEntity() {
    }

    public SensorHumedadEntity(String id) {
        this.id = id;
        this.conectado = false;
        this.humedadActual = 50;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public int getHumedadActual() {
        return humedadActual;
    }

    public void setHumedadActual(int humedadActual) {
        this.humedadActual = humedadActual;
    }

    public ParcelaEntity getParcela() {
        return parcela;
    }

    public void setParcela(ParcelaEntity parcela) {
        this.parcela = parcela;
    }
}