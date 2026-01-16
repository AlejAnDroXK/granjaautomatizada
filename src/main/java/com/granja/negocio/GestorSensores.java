package com.granja.negocio;

import com.granja.modelo.*;
import com.granja.utilitario.*;
import java.time.format.DateTimeFormatter;

public class GestorSensores {
    private GestorGranja gestorGranja;

    public GestorSensores(GestorGranja gestorGranja) {
        this.gestorGranja = gestorGranja;
    }

    public void agregarSensoresInventario(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            String id = gestorGranja.getSiguienteIdSensor();
            SensorHumedad sensor = new SensorHumedad(id);
            gestorGranja.getSensoresInventario().add(sensor);
        }
        System.out.println("Se agregaron " + cantidad + " sensor(es) al inventario.");
    }

    public void mostrarInformacionSensores() {
        System.out.println("\n========== SENSORES EN INVENTARIO ==========");
        if (gestorGranja.getSensoresInventario().isEmpty()) {
            System.out.println("No hay sensores en inventario.");
        } else {
            for (SensorHumedad sensor : gestorGranja.getSensoresInventario()) {
                System.out.println(sensor);
            }
        }

        System.out.println("\n========== SENSORES ASIGNADOS ==========");
        boolean hayAsignados = false;
        for (Parcela parcela : gestorGranja.getParcelas()) {
            for (SensorHumedad sensor : parcela.getSensores()) {
                System.out.println(sensor);
                hayAsignados = true;
            }
        }
        if (!hayAsignados) {
            System.out.println("No hay sensores asignados a parcelas.");
        }
    }

    public void asignarSensorAParcela(String idParcela) throws GranjaException {
        Parcela parcela = gestorGranja.getGestorParcelas().buscarParcela(idParcela);

        if (parcela == null) {
            throw new GranjaException("Parcela no encontrada: " + idParcela);
        }

        if (gestorGranja.getSensoresInventario().isEmpty()) {
            throw new GranjaException("No hay sensores disponibles en el inventario.");
        }

        SensorHumedad sensor = gestorGranja.getSensoresInventario().get(0);
        sensor.setParcela(parcela);
        sensor.setConectado(true);
        parcela.agregarSensor(sensor);
        gestorGranja.getSensoresInventario().remove(sensor);

        System.out.println("Sensor " + sensor.getId() + " asignado a " + idParcela);
    }

    public void simularLecturasTodasParcelas() {
        System.out.println("\n========== SIMULACIÓN DE LECTURAS ==========");
        boolean hayLecturas = false;

        for (Parcela parcela : gestorGranja.getParcelas()) {
            for (SensorHumedad sensor : parcela.getSensores()) {
                if (sensor.isConectado()) {
                    int nuevaHumedad;

                    if (gestorGranja.getGestorArduino().esDispositivoArduino(sensor.getId())) {
                        nuevaHumedad = gestorGranja.getGestorArduino().leerHumedadReal(sensor.getId());
                        if (nuevaHumedad < 0) {
                            nuevaHumedad = Util.generarHumedadProgresiva(sensor.getHumedadActual());
                            System.out.println(sensor.getId() + " (Arduino sin respuesta, usando simulación) en " + parcela.getId() +
                                    " - Nueva humedad: " + nuevaHumedad + "%");
                        } else {
                            System.out.println(sensor.getId() + " (Arduino) en " + parcela.getId() +
                                    " - Humedad real: " + nuevaHumedad + "%");
                        }
                    } else {
                        nuevaHumedad = Util.generarHumedadProgresiva(sensor.getHumedadActual());
                        System.out.println(sensor.getId() + " (Simulado) en " + parcela.getId() +
                                " - Nueva humedad: " + nuevaHumedad + "%");
                    }

                    sensor.setHumedadActual(nuevaHumedad);
                    sensor.realizarLectura();
                    hayLecturas = true;
                }
            }
        }

        if (!hayLecturas) {
            System.out.println("No hay sensores conectados para realizar lecturas.");
        }
    }

    public void mostrarLecturasSensor(String idSensor) throws GranjaException {
        SensorHumedad sensor = buscarSensor(idSensor);

        if (sensor == null) {
            throw new GranjaException("Sensor no encontrado: " + idSensor);
        }

        System.out.println("\n========== LECTURAS DE " + idSensor + " ==========");
        if (sensor.getLecturas().isEmpty()) {
            System.out.println("No hay lecturas registradas.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (LecturaHumedad lectura : sensor.getLecturas()) {
                System.out.println(lectura.getFecha().format(formatter) +
                        " - Humedad: " + lectura.getPorcentajeHumedad() + "%");
            }
        }
    }

    public void mostrarHumedadActualParcelas() {
        System.out.println("\n========== HUMEDAD ACTUAL DE PARCELAS ==========");
        boolean hayDatos = false;

        for (Parcela parcela : gestorGranja.getParcelas()) {
            if (!parcela.getSensores().isEmpty()) {
                SensorHumedad sensor = parcela.getSensores().get(0);
                if (sensor.isConectado()) {
                    String cultivoStr = (parcela.getCultivo() != null) ?
                            parcela.getCultivo().getNombre() : "Sin cultivo";
                    System.out.println(parcela.getId() + " - " + cultivoStr +
                            " - Humedad: " + sensor.getHumedadActual() + "%");
                    hayDatos = true;
                }
            }
        }

        if (!hayDatos) {
            System.out.println("No hay sensores conectados en las parcelas.");
        }
    }

    public void conectarDesconectarSensor(String idSensor) throws GranjaException {
        SensorHumedad sensor = buscarSensor(idSensor);

        if (sensor == null) {
            throw new GranjaException("Sensor no encontrado: " + idSensor);
        }

        if (sensor.isConectado()) {
            sensor.setConectado(false);
            System.out.println("Sensor " + idSensor + " desconectado.");
        } else {
            sensor.setConectado(true);
            System.out.println("Sensor " + idSensor + " conectado.");
        }
    }

    public void eliminarSensor(String idSensor) throws GranjaException {
        SensorHumedad sensor = buscarSensor(idSensor);

        if (sensor == null) {
            throw new GranjaException("Sensor no encontrado: " + idSensor);
        }

        if (!Util.confirmarAccion("¿Está seguro de eliminar el sensor " + idSensor + "?")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (sensor.getParcela() != null) {
            sensor.getParcela().removerSensor(sensor);
        } else {
            gestorGranja.getSensoresInventario().remove(sensor);
        }

        System.out.println("Sensor " + idSensor + " eliminado del sistema.");
    }

    private SensorHumedad buscarSensor(String idSensor) {
        for (SensorHumedad sensor : gestorGranja.getSensoresInventario()) {
            if (sensor.getId().equals(idSensor)) {
                return sensor;
            }
        }

        for (Parcela parcela : gestorGranja.getParcelas()) {
            for (SensorHumedad sensor : parcela.getSensores()) {
                if (sensor.getId().equals(idSensor)) {
                    return sensor;
                }
            }
        }

        return null;
    }
}