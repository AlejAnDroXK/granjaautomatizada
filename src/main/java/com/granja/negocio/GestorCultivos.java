package com.granja.negocio;

import com.granja.modelo.*;
import com.granja.utilitario.*;
import java.util.ArrayList;

public class GestorCultivos {
    private GestorGranja gestorGranja;
    private ArrayList<Cultivo> cultivosRegistrados;

    public GestorCultivos(GestorGranja gestorGranja) {
        this.gestorGranja = gestorGranja;
        this.cultivosRegistrados = new ArrayList<>();
        inicializarCultivos();
    }

    private void inicializarCultivos() {
        cultivosRegistrados.add(new Cultivo("Tomate", 50, 70, 72));
        cultivosRegistrados.add(new Cultivo("Lechuga", 40, 60, 48));
        cultivosRegistrados.add(new Cultivo("Maíz", 45, 65, 96));
        cultivosRegistrados.add(new Cultivo("Zanahoria", 35, 55, 120));
        cultivosRegistrados.add(new Cultivo("Fresa", 55, 75, 60));
        cultivosRegistrados.add(new Cultivo("Pepino", 50, 70, 72));
        cultivosRegistrados.add(new Cultivo("Cebolla", 30, 50, 144));
    }

    public void mostrarCultivosDisponibles() {
        System.out.println("\n========== CULTIVOS DISPONIBLES ==========");
        for (int i = 0; i < cultivosRegistrados.size(); i++) {
            System.out.println((i + 1) + ". " + cultivosRegistrados.get(i));
        }
    }

    public void registrarCultivoEnParcela(String idParcela, int indiceCultivo) throws GranjaException {
        Parcela parcela = gestorGranja.getGestorParcelas().buscarParcela(idParcela);

        if (parcela == null) {
            throw new GranjaException("Parcela no encontrada: " + idParcela);
        }

        if (indiceCultivo < 1 || indiceCultivo > cultivosRegistrados.size()) {
            throw new GranjaException("Índice de cultivo inválido.");
        }

        Cultivo cultivo = cultivosRegistrados.get(indiceCultivo - 1);
        parcela.setCultivo(cultivo);
        System.out.println("Cultivo '" + cultivo.getNombre() + "' registrado en " + idParcela);
    }

    public void cambiarCultivoParcela(String idParcela) throws GranjaException {
        Parcela parcela = gestorGranja.getGestorParcelas().buscarParcela(idParcela);

        if (parcela == null) {
            throw new GranjaException("Parcela no encontrada: " + idParcela);
        }

        if (parcela.getCultivo() == null) {
            throw new GranjaException("La parcela no tiene cultivo asignado.");
        }

        mostrarCultivosDisponibles();
        int opcion = Util.leerEntero("\nSeleccione el nuevo cultivo: ");

        if (opcion < 1 || opcion > cultivosRegistrados.size()) {
            throw new GranjaException("Opción inválida.");
        }

        Cultivo nuevoCultivo = cultivosRegistrados.get(opcion - 1);
        parcela.setCultivo(nuevoCultivo);
        System.out.println("Cultivo cambiado a '" + nuevoCultivo.getNombre() + "' en " + idParcela);
    }

    public ArrayList<Cultivo> getCultivosRegistrados() {
        return cultivosRegistrados;
    }
}