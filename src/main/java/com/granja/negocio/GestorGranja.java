package com.granja.negocio;

import com.granja.modelo.*;
import com.granja.hardware.GestorArduino;
import java.util.ArrayList;

public class GestorGranja {
    private ArrayList<Parcela> parcelas;
    private ArrayList<Aspersor> aspersoresInventario;
    private ArrayList<SensorHumedad> sensoresInventario;
    private GestorParcelas gestorParcelas;
    private GestorAspersores gestorAspersores;
    private GestorSensores gestorSensores;
    private GestorCultivos gestorCultivos;
    private GestorArduino gestorArduino;
    private GestorUsuarios gestorUsuarios;
    private int contadorIdAspersores;
    private int contadorIdSensores;

    public GestorGranja() {
        this.parcelas = new ArrayList<>();
        this.aspersoresInventario = new ArrayList<>();
        this.sensoresInventario = new ArrayList<>();
        this.contadorIdAspersores = 1;
        this.contadorIdSensores = 1;

        this.gestorUsuarios = new GestorUsuarios(this);
        this.gestorParcelas = new GestorParcelas(this);
        this.gestorAspersores = new GestorAspersores(this);
        this.gestorSensores = new GestorSensores(this);
        this.gestorCultivos = new GestorCultivos(this);
        this.gestorArduino = new GestorArduino(this);
    }

    public ArrayList<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(ArrayList<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public ArrayList<Aspersor> getAspersoresInventario() {
        return aspersoresInventario;
    }

    public void setAspersoresInventario(ArrayList<Aspersor> aspersoresInventario) {
        this.aspersoresInventario = aspersoresInventario;
    }

    public ArrayList<SensorHumedad> getSensoresInventario() {
        return sensoresInventario;
    }

    public void setSensoresInventario(ArrayList<SensorHumedad> sensoresInventario) {
        this.sensoresInventario = sensoresInventario;
    }

    public GestorParcelas getGestorParcelas() {
        return gestorParcelas;
    }

    public GestorAspersores getGestorAspersores() {
        return gestorAspersores;
    }

    public GestorSensores getGestorSensores() {
        return gestorSensores;
    }

    public GestorCultivos getGestorCultivos() {
        return gestorCultivos;
    }

    public GestorArduino getGestorArduino() {
        return gestorArduino;
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public int getContadorIdAspersores() {
        return contadorIdAspersores;
    }

    public void setContadorIdAspersores(int contadorIdAspersores) {
        this.contadorIdAspersores = contadorIdAspersores;
    }

    public int getContadorIdSensores() {
        return contadorIdSensores;
    }

    public void setContadorIdSensores(int contadorIdSensores) {
        this.contadorIdSensores = contadorIdSensores;
    }

    public String getSiguienteIdAspersor() {
        String id = "ASPERSOR_" + contadorIdAspersores;
        contadorIdAspersores++;
        return id;
    }

    public String getSiguienteIdSensor() {
        String id = "SENSOR_" + contadorIdSensores;
        contadorIdSensores++;
        return id;
    }
}