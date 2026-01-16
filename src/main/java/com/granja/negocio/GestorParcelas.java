package com.granja.negocio;

import com.granja.modelo.*;
import com.granja.utilitario.*;

public class GestorParcelas {
    private GestorGranja gestorGranja;
    private int contadorParcelas;

    public GestorParcelas(GestorGranja gestorGranja) {
        this.gestorGranja = gestorGranja;
        this.contadorParcelas = 1;
    }

    public void crearParcelas(double terrenoTotal) throws GranjaException {
        if (terrenoTotal <= 0) {
            throw new GranjaException("El terreno debe ser mayor a 0 m²");
        }

        Usuario usuarioActual = gestorGranja.getGestorUsuarios().getUsuarioActual();
        if (usuarioActual == null) {
            throw new GranjaException("Debe seleccionar un usuario antes de crear parcelas");
        }

        double tamañoParcela = 50.0;
        int cantidadParcelas = (int) (terrenoTotal / tamañoParcela);
        double terrenoRestante = terrenoTotal % tamañoParcela;

        for (int i = 0; i < cantidadParcelas; i++) {
            String idParcela = "PARCELA_" + contadorParcelas;
            Parcela parcela = new Parcela(idParcela, tamañoParcela);
            parcela.setUsuarioCreador(usuarioActual);
            gestorGranja.getParcelas().add(parcela);
            contadorParcelas++;
        }

        if (terrenoRestante > 0) {
            String idParcela = "PARCELA_" + contadorParcelas;
            Parcela parcela = new Parcela(idParcela, terrenoRestante);
            parcela.setUsuarioCreador(usuarioActual);
            gestorGranja.getParcelas().add(parcela);
            contadorParcelas++;
        }

        System.out.println("Se crearon " + gestorGranja.getParcelas().size() + " parcela(s) exitosamente.");
        System.out.println("Creado por: " + usuarioActual.getNombreCompleto());
        asignarDispositivosAutomaticamente();
    }

    private void asignarDispositivosAutomaticamente() {
        for (Parcela parcela : gestorGranja.getParcelas()) {
            if (!gestorGranja.getAspersoresInventario().isEmpty()) {
                Aspersor aspersor = gestorGranja.getAspersoresInventario().get(0);
                aspersor.setParcela(parcela);
                aspersor.setConectado(true);
                parcela.agregarAspersor(aspersor);
                gestorGranja.getAspersoresInventario().remove(aspersor);
            }

            if (!gestorGranja.getSensoresInventario().isEmpty()) {
                SensorHumedad sensor = gestorGranja.getSensoresInventario().get(0);
                sensor.setParcela(parcela);
                sensor.setConectado(true);
                parcela.agregarSensor(sensor);
                gestorGranja.getSensoresInventario().remove(sensor);
            }
        }
    }

    public void mostrarInformacionParcelas() {
        if (gestorGranja.getParcelas().isEmpty()) {
            System.out.println("No hay parcelas registradas en el sistema.");
            return;
        }

        System.out.println("\n========== INFORMACIÓN DE PARCELAS ==========");
        for (Parcela parcela : gestorGranja.getParcelas()) {
            System.out.println(parcela);
        }
    }

    public void eliminarParcela(String idParcela) throws GranjaException {
        Parcela parcela = buscarParcela(idParcela);

        if (parcela == null) {
            throw new GranjaException("Parcela no encontrada: " + idParcela);
        }

        if (!Util.confirmarAccion("¿Está seguro de eliminar la parcela " + idParcela + "?")) {
            System.out.println("Operación cancelada.");
            return;
        }

        for (Aspersor aspersor : parcela.getAspersores()) {
            aspersor.setParcela(null);
            aspersor.setConectado(false);
            aspersor.setEncendido(false);
            gestorGranja.getAspersoresInventario().add(aspersor);
        }

        for (SensorHumedad sensor : parcela.getSensores()) {
            sensor.setParcela(null);
            sensor.setConectado(false);
            gestorGranja.getSensoresInventario().add(sensor);
        }

        gestorGranja.getParcelas().remove(parcela);
        System.out.println("Parcela " + idParcela + " eliminada exitosamente.");
    }

    public Parcela buscarParcela(String idParcela) {
        for (Parcela parcela : gestorGranja.getParcelas()) {
            if (parcela.getId().equals(idParcela)) {
                return parcela;
            }
        }
        return null;
    }
}