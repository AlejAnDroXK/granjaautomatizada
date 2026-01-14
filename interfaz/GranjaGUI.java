package granjaautomatizada.interfaz;

import granjaautomatizada.negocio.*;
import granjaautomatizada.modelo.*;
import granjaautomatizada.utilitario.GranjaException;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class GranjaGUI extends JFrame {
    private GestorGranja gestor;
    private JTextArea txtSalida;

    public GranjaGUI() {
        gestor = new GestorGranja();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("SISTEMA DE GESTIÓN DE GRANJA AUTOMATIZADA");
        setSize(1100, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // PANEL DE BOTONES (Organizado por grupos)
        JPanel panelIzquierdo = new JPanel(new GridLayout(11, 1, 5, 5));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Gestión de Parcelas"));
        panelIzquierdo.add(crearBoton("Crear Terreno y Parcelas", 1));
        panelIzquierdo.add(crearBoton("Info de Parcelas", 2));
        panelIzquierdo.add(crearBoton("Lista de Cultivos", 9));
        panelIzquierdo.add(crearBoton("Eliminar una Parcela", 10));
        panelIzquierdo.add(crearBoton("Cambiar Cultivo", 11));
        panelIzquierdo.add(crearBoton("Evaluar Riego Auto", 12));

        JPanel panelDerecho = new JPanel(new GridLayout(11, 1, 5, 5));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Inventario y Dispositivos"));
        panelDerecho.add(crearBoton("Cargar Aspersores Inv.", 7));
        panelDerecho.add(crearBoton("Cargar Sensores Inv.", 8));
        panelDerecho.add(crearBoton("Asignar Aspersor a Parcela", 5));
        panelDerecho.add(crearBoton("Asignar Sensor a Parcela", 6));
        panelDerecho.add(crearBoton("Info de Aspersores", 3));
        panelDerecho.add(crearBoton("Info de Sensores", 4));

        JPanel panelCentroAbajo = new JPanel(new GridLayout(2, 4, 5, 5));
        panelCentroAbajo.setBorder(BorderFactory.createTitledBorder("Control de Dispositivos"));
        panelCentroAbajo.add(crearBoton("Historial Sensor", 13));
        panelCentroAbajo.add(crearBoton("Historial Aspersor", 14));
        panelCentroAbajo.add(crearBoton("Riego Manual", 15));
        panelCentroAbajo.add(crearBoton("On/Off Aspersor", 16));
        panelCentroAbajo.add(crearBoton("On/Off Sensor", 17));
        panelCentroAbajo.add(crearBoton("Eliminar Aspersor", 18));
        panelCentroAbajo.add(crearBoton("Eliminar Sensor", 19));

        JButton btn20 = new JButton("SALIR");
        btn20.setBackground(new Color(200, 50, 50));
        btn20.setForeground(Color.WHITE);
        btn20.addActionListener(e -> System.exit(0));
        panelCentroAbajo.add(btn20);

        // AREA DE SALIDA
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        txtSalida.setBackground(new Color(40, 44, 52));
        txtSalida.setForeground(new Color(171, 178, 191));
        txtSalida.setFont(new Font("Monospaced", Font.BOLD, 13));
        JScrollPane scroll = new JScrollPane(txtSalida);

        // Agregando Paneles
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2));
        panelSuperior.add(panelIzquierdo);
        panelSuperior.add(panelDerecho);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelCentroAbajo, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, int opcion) {
        JButton btn = new JButton(texto);
        btn.addActionListener(e -> ejecutarOpcion(opcion));
        return btn;
    }

    private void ejecutarOpcion(int opcion) {
        String id;
        try {
            switch (opcion) {
                case 1: // Crear Terreno y Parcelas (VERSIÓN FINAL PROTEGIDA)
                    try {
                        // 1. VALIDAR TERRENO TOTAL
                        double terrenoTotal = 0;
                        while (terrenoTotal <= 0) {
                            String tStr = JOptionPane.showInputDialog("Ingrese la cantidad total de terreno en m²:");
                            if (tStr == null) return; // Si presiona cancelar, sale de la función
                            try {
                                terrenoTotal = Double.parseDouble(tStr);
                                if (terrenoTotal <= 0) {
                                    JOptionPane.showMessageDialog(this, "El terreno debe ser un número mayor a 0.");
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Error: Ingrese un número válido (ej: 100 o 150.5).");
                            }
                        }

                        // Lógica de creación automática de parcelas
                        double terrenoRestante = terrenoTotal;
                        gestor.getParcelas().clear();
                        int contador = 0;
                        while (terrenoRestante > 0) {
                            double tamanio = (terrenoRestante >= 50) ? 50 : terrenoRestante;
                            contador++;
                            gestor.getParcelas().add(new Parcela("PARCELA_" + contador, tamanio));
                            terrenoRestante -= tamanio;
                        }
                        txtSalida.append("\n[SISTEMA] Terreno de " + terrenoTotal + "m² dividido en " + gestor.getParcelas().size() + " parcelas.\n");

                        // 2. VALIDAR CANTIDAD DE DISPOSITIVOS
                        int cantAsp = -1, cantSen = -1;
                        while (cantAsp < 0) {
                            String s = JOptionPane.showInputDialog("¿Cuántos aspersores nuevos hay en inventario?\n(Ingrese 0 o más)");
                            if (s == null) break;
                            try {
                                cantAsp = Integer.parseInt(s);
                                if (cantAsp < 0) JOptionPane.showMessageDialog(this, "No puede ingresar números negativos.");
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Error: Ingrese un número entero.");
                            }
                        }
                        while (cantSen < 0) {
                            String s = JOptionPane.showInputDialog("¿Cuántos sensores nuevos hay en inventario?\n(Ingrese 0 o más)");
                            if (s == null) break;
                            try {
                                cantSen = Integer.parseInt(s);
                                if (cantSen < 0) JOptionPane.showMessageDialog(this, "No puede ingresar números negativos.");
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Error: Ingrese un número entero.");
                            }
                        }

                        if(cantAsp > 0) gestor.getGestorAspersores().agregarAspersoresAlInventario(cantAsp);
                        if(cantSen > 0) gestor.getGestorSensores().agregarSensoresAlInventario(cantSen);

                        // Asignación automática de dispositivos
                        int iA = 0, iS = 0;
                        for (Parcela p : gestor.getParcelas()) {
                            if (iA < gestor.getAspersoresInventario().size()) {
                                Aspersor a = gestor.getAspersoresInventario().get(iA++);
                                a.setParcela(p); p.getAspersores().add(a);
                            }
                            if (iS < gestor.getSensoresInventario().size()) {
                                SensorHumedad s = gestor.getSensoresInventario().get(iS++);
                                s.setParcela(p); p.getSensores().add(s);
                            }
                        }
                        txtSalida.append("[SISTEMA] Dispositivos de inventario instalados automáticamente.\n");

                        // 3. VALIDAR REGISTRO DE CULTIVOS
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea registrar cultivos para las nuevas parcelas?", "Cultivos", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            for (Parcela p : gestor.getParcelas()) {
                                String nom = "";

                                while (nom.trim().isEmpty()) {
                                    nom = JOptionPane.showInputDialog("Nombre del cultivo para " + p.getId() + ":\n(Escriba 'SALTAR' para no poner nada)");
                                    if (nom == null) break;
                                    if (nom.trim().isEmpty()) JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                                }

                                if (nom == null || nom.equalsIgnoreCase("SALTAR")) {
                                    txtSalida.append(" - " + p.getId() + " se dejó sin cultivo.\n");
                                    continue;
                                }


                                int min = -1;
                                while (min < 0 || min > 100) {
                                    String s = JOptionPane.showInputDialog("Humedad mínima para " + nom + " (0-100):");
                                    if (s == null) break;
                                    try {
                                        min = Integer.parseInt(s);
                                        if (min < 0 || min > 100) JOptionPane.showMessageDialog(this, "La humedad debe estar entre 0 y 100.");
                                    } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Ingrese un número entero."); }
                                }


                                int max = -1;
                                while (max <= min || max > 100) {
                                    String s = JOptionPane.showInputDialog("Humedad máxima para " + nom + " (" + (min + 1) + "-100):");
                                    if (s == null) break;
                                    try {
                                        max = Integer.parseInt(s);
                                        if (max <= min) JOptionPane.showMessageDialog(this, "La humedad máxima debe ser mayor a la mínima (" + min + ").");
                                        else if (max > 100) JOptionPane.showMessageDialog(this, "La humedad máxima no puede pasar de 100.");
                                    } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Ingrese un número entero."); }
                                }


                                int freq = 0;
                                while (freq <= 0) {
                                    String s = JOptionPane.showInputDialog("Frecuencia de riego para " + nom + " (Horas - mayor a 0):");
                                    if (s == null) break;
                                    try {
                                        freq = Integer.parseInt(s);
                                        if (freq <= 0) JOptionPane.showMessageDialog(this, "La frecuencia debe ser al menos 1 hora.");
                                    } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Ingrese un número entero."); }
                                }


                                p.setCultivo(new Cultivo(nom, min, max, freq));
                                txtSalida.append(" - Cultivo " + nom + " registrado en " + p.getId() + " (Riego cada " + freq + "h).\n");
                            }
                        }
                        txtSalida.append("[ÉXITO] Proceso de configuración terminado.\n");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage());
                    }
                    break;
                case 2:
                    txtSalida.append("\n--- INFO PARCELAS ---");
                    for (Parcela p : gestor.getParcelas()) {
                        txtSalida.append("\nID: " + p.getId() + " | Área: " + p.getMetrosCuadrados() + "m2 | Cultivo: " + (p.getCultivo() != null ? p.getCultivo().getNombre() : "SIN CULTIVO"));
                    }
                    break;
                case 3:
                    txtSalida.append("\n--- INFO ASPERSORES ---\n");
                    for (Aspersor a : gestor.getAspersoresInventario()) {
                        txtSalida.append(a.getId() + " | Parcela: " + (a.getParcela() != null ? a.getParcela().getId() : "INV") + " | Conectado: " + a.isConectado() + " | Encendido: " + a.isEncendido() + "\n");
                    }
                    break;
                case 4:
                    txtSalida.append("\n--- INFO SENSORES ---\n");
                    for (SensorHumedad s : gestor.getSensoresInventario()) {
                        txtSalida.append(s.getId() + " | Parcela: " + (s.getParcela() != null ? s.getParcela().getId() : "INV") + " | Conectado: " + s.isConectado() + "\n");
                    }
                    break;
                case 5:
                    id = JOptionPane.showInputDialog("Ingrese ID Parcela:");
                    if(id != null) gestor.getGestorAspersores().asignarAspersorAParcela(id);
                    break;
                case 6:
                    id = JOptionPane.showInputDialog("Ingrese ID Parcela:");
                    if(id != null) gestor.getGestorSensores().asignarSensorAParcela(id);
                    break;
                case 7:
                    String cA = JOptionPane.showInputDialog("¿Cuántos Aspersores al inventario?");
                    if(cA != null) gestor.getGestorAspersores().agregarAspersoresAlInventario(Integer.parseInt(cA));
                    break;
                case 8:
                    String cS = JOptionPane.showInputDialog("¿Cuántos Sensores al inventario?");
                    if(cS != null) gestor.getGestorSensores().agregarSensoresAlInventario(Integer.parseInt(cS));
                    break;
                case 9:
                    txtSalida.append("\n--- CULTIVOS REGISTRADOS ---\n");
                    for(Parcela p : gestor.getParcelas()){
                        if(p.getCultivo() != null) txtSalida.append("Cultivo: " + p.getCultivo().getNombre() + " en " + p.getId() + "\n");
                    }
                    break;
                case 10:
                    id = JOptionPane.showInputDialog("ID Parcela a ELIMINAR:");
                    if(id != null) {
                        gestor.getGestorParcelas().eliminarParcela(new java.util.Scanner(id + "\n" + id));
                        txtSalida.append("\nParcela " + id + " eliminada.\n");
                    }
                    break;
                case 11:
                    try {
                        txtSalida.append("\n--- PARCELAS CON CULTIVO ACTUAL ---\n");
                        boolean hayCultivos = false;
                        for (Parcela p : gestor.getParcelas()) {
                            if (p.getCultivo() != null) {
                                txtSalida.append("ID: " + p.getId() + " | Cultivo: " + p.getCultivo().getNombre() + "\n");
                                hayCultivos = true;
                            }
                        }
                        if (!hayCultivos) {
                            JOptionPane.showMessageDialog(this, "No hay parcelas con cultivos registrados.");
                            break;
                        }
                        String idBuscado = JOptionPane.showInputDialog("Ingrese el ID de la parcela para cambiar el cultivo:");
                        if (idBuscado == null) break;
                        Parcela parcelaSeleccionada = null;
                        for (Parcela p : gestor.getParcelas()) {
                            if (p.getId().equalsIgnoreCase(idBuscado)) {
                                parcelaSeleccionada = p;
                                break;
                            }
                        }
                        if (parcelaSeleccionada == null) {
                            JOptionPane.showMessageDialog(this, "La parcela '" + idBuscado + "' no existe.");
                            break;
                        }
                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre del cultivo:");
                        if (nuevoNombre == null || nuevoNombre.isEmpty()) break;
                        int nuevoMin = Integer.parseInt(JOptionPane.showInputDialog("Nueva Humedad Mínima (%):"));
                        int nuevoMax = Integer.parseInt(JOptionPane.showInputDialog("Nueva Humedad Máxima (%):"));
                        int nuevaFreq = Integer.parseInt(JOptionPane.showInputDialog("Nueva Frecuencia de Riego (horas):"));
                        Cultivo nuevoCultivo = new Cultivo(nuevoNombre, nuevoMin, nuevoMax, nuevaFreq);
                        parcelaSeleccionada.setCultivo(nuevoCultivo);
                        txtSalida.append("¡ÉXITO! El cultivo de " + parcelaSeleccionada.getId() + " ahora es: " + nuevoNombre + "\n");
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(this, "Error: Ingrese solo números enteros para humedad y frecuencia.");
                    }
                    break;
                case 12: // Evaluar Riego Automático y mostrar en GUI
                    txtSalida.append("\n--- EJECUTANDO EVALUACIÓN DE RIEGO ---\n");

                    // 1. Llamamos al método original para que cambie los estados (on/off) internamente
                    gestor.getGestorAspersores().evaluarYRiegoAutomatico();

                    // 2. Ahora recorremos las parcelas para mostrar los resultados en la INTERFAZ
                    if (gestor.getParcelas().isEmpty()) {
                        txtSalida.append("No hay parcelas para evaluar.\n");
                    } else {
                        for (Parcela p : gestor.getParcelas()) {
                            txtSalida.append("\nParcela: " + p.getId());

                            // Verificar cultivo
                            if (p.getCultivo() == null) {
                                txtSalida.append(" -> Sin cultivo registrado.\n");
                                continue;
                            }

                            // Verificar sensor y humedad
                            if (p.getSensores().isEmpty()) {
                                txtSalida.append(" -> ERROR: No tiene sensor.\n");
                            } else {
                                SensorHumedad sensor = p.getSensores().get(0);
                                LecturaHumedad lectura = sensor.leerHumedad();

                                if (lectura == null) {
                                    txtSalida.append(" -> ERROR: Sensor desconectado.\n");
                                } else {
                                    int hum = lectura.getPorcentajeHumedad();
                                    int min = p.getCultivo().getHumedadMinima();
                                    txtSalida.append("\n  - Humedad actual: " + hum + "% (Mínima: " + min + "%)");

                                    // Mostrar estado del riego
                                    if (hum < min) {
                                        txtSalida.append("\n  - ESTADO: [RIEGO ACTIVADO] Falta humedad.\n");
                                    } else {
                                        txtSalida.append("\n  - ESTADO: [RIEGO APAGADO] Humedad correcta.\n");
                                    }
                                }
                            }
                        }
                    }
                    txtSalida.append("--------------------------------------\n");
                    break;
                case 13:
                    id = JOptionPane.showInputDialog("ID del Sensor para ver lecturas:");
                    if(id != null) {
                        for(SensorHumedad s : gestor.getSensoresInventario()) {
                            if(s.getId().equals(id)) {
                                txtSalida.append("\nLecturas de " + id + ":\n");
                                for(LecturaHumedad l : s.getLecturas()) txtSalida.append(l.getFecha() + " | " + l.getPorcentajeHumedad() + "%\n");
                            }
                        }
                    }
                    break;
                case 14:
                    id = JOptionPane.showInputDialog("ID del Aspersor para ver historial:");
                    if(id != null) {
                        for(Aspersor a : gestor.getAspersoresInventario()) {
                            if(a.getId().equals(id)) {
                                txtSalida.append("\nHistorial " + id + ":\n");
                                for(LocalDateTime f : a.getHistorialEncendidos()) txtSalida.append("Prendido el: " + f + "\n");
                            }
                        }
                    }
                    break;
                case 15:
                    id = JOptionPane.showInputDialog("ID Aspersor a prender MANUAL:");
                    if(id != null) {
                        for(Aspersor a : gestor.getAspersoresInventario()) if(a.getId().equals(id)) a.encender();
                        txtSalida.append("\nAspersor " + id + " encendido manualmente.\n");
                    }
                    break;
                case 16:
                    id = JOptionPane.showInputDialog("ID del Aspersor para cambiar conexión:");
                    if(id != null) {
                        for(Aspersor a : gestor.getAspersoresInventario()) {
                            if(a.getId().equals(id)) {
                                a.setConectado(!a.isConectado());
                                txtSalida.append("\nAspersor " + id + " ahora está " + (a.isConectado()?"CONECTADO":"DESCONECTADO") + "\n");
                            }
                        }
                    }
                    break;
                case 17:
                    id = JOptionPane.showInputDialog("ID del Sensor para cambiar conexión:");
                    if(id != null) {
                        for(SensorHumedad s : gestor.getSensoresInventario()) {
                            if(s.getId().equals(id)) {
                                s.setConectado(!s.isConectado());
                                txtSalida.append("\nSensor " + id + " ahora está " + (s.isConectado()?"CONECTADO":"DESCONECTADO") + "\n");
                            }
                        }
                    }
                    break;
                case 18:
                    id = JOptionPane.showInputDialog("ID del Aspersor a ELIMINAR:");
                    if(id != null) {
                        gestor.getGestorAspersores().eliminarAspersor(new java.util.Scanner(id));
                        txtSalida.append("\nAspersor " + id + " eliminado del sistema.\n");
                    }
                    break;
                case 19:
                    id = JOptionPane.showInputDialog("ID del Sensor a ELIMINAR:");
                    if(id != null) {
                        gestor.getGestorSensores().eliminarSensor(new java.util.Scanner(id));
                        txtSalida.append("\nSensor " + id + " eliminado del sistema.\n");
                    }
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GranjaGUI().setVisible(true));
    }
}