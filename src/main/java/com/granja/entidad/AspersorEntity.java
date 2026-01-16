package com.granja.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "aspersor")
public class AspersorEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "conectado")
    private boolean conectado;

    @Column(name = "encendido")
    private boolean encendido;

    @ManyToOne
    @JoinColumn(name = "parcela_id")
    private ParcelaEntity parcela;

    public AspersorEntity() {
    }

    public AspersorEntity(String id) {
        this.id = id;
        this.conectado = false;
        this.encendido = false;
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

    public boolean isEncendido() {
        return encendido;
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    public ParcelaEntity getParcela() {
        return parcela;
    }

    public void setParcela(ParcelaEntity parcela) {
        this.parcela = parcela;
    }
}