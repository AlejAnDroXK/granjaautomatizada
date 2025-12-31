package granjaautomatizada.interfaz;

import java.util.Scanner;
import granjaautomatizada.negocio.GestorGranja;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GestorGranja gestor = new GestorGranja();

        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE AUTOMATIZACIÓN DE GRANJA ===");
            System.out.println("1. Ingresar terreno y crear parcelas");
            System.out.println("2. Mostrar información de parcelas");
            System.out.println("3. Mostrar información de aspersores");
            System.out.println("4. Mostrar información de sensores");
            System.out.println("5. Añadir aspersor a una parcela");
            System.out.println("6. Añadir sensor a una parcela");
            System.out.println("7. Agregar aspersores al inventario");
            System.out.println("8. Agregar sensores al inventario");
            System.out.println("9. Mostrar lista de cultivos disponibles");
            System.out.println("10. Eliminar una parcela");
            System.out.println("11. Cambiar cultivo de una parcela");
            System.out.println("12. Verificar humedad de las parcelas");
            System.out.println("13. Mostrar lecturas de un sensor");
            System.out.println("14. Mostrar cuándo se prendió un aspersor");
            System.out.println("15. Prender manualmente un aspersor");
            System.out.println("16. Conectar o desconectar un aspersor");
            System.out.println("17. Conectar o desconectar un sensor");
            System.out.println("18. Eliminar un aspersor del programa");
            System.out.println("19. Eliminar un sensor del programa");
            System.out.println("40. Verificar humedad y riego automático");
            System.out.println("20. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1://Añadir más terreno
                    System.out.println("----------------");
                    gestor.getGestorParcelas().crearParcelasDesdeTerreno(scanner);
                    pausa();
                    break;
                case 2://Mostrar información de las parcelas
                    System.out.println("----------------");
                    gestor.getGestorParcelas().mostrarInfoParcelas();
                    pausa();
                    break;
                case 3://Mostrar información de los aspersores
                    System.out.println("----------------");
                    gestor.getGestorAspersores().mostrarInfoAspersores();
                    pausa();
                    break;
                case 4://Mostrar información de los sensores de humedad
                    System.out.println("----------------");
                    gestor.getGestorSensores().mostrarInfoSensores();
                    pausa();
                    break;
                case 5://Añadir más aspersores a una parcela
                    System.out.println("----------------");
                    System.out.print("Ingrese ID de la parcela: ");
                    gestor.getGestorAspersores()
                            .asignarAspersorAParcela(scanner.next());
                    pausa();
                    break;
                case 6://Añadir más sensores de humedad a una parcela
                    System.out.println("----------------");
                    System.out.print("Ingrese ID de la parcela: ");
                    gestor.getGestorSensores()
                            .asignarSensorAParcela(scanner.next());
                    pausa();
                    break;
                case 7://Agregar más aspersores al inventario
                    System.out.println("----------------");
                    System.out.print("¿Cuántos aspersores desea agregar?: ");
                    gestor.getGestorAspersores()
                            .agregarAspersoresAlInventario(scanner.nextInt());
                    pausa();
                    break;
                case 8://Agregar más sensores de humedad al inventario
                    System.out.println("----------------");
                    System.out.print("¿Cuántos sensores desea agregar?: ");
                    gestor.getGestorSensores()
                            .agregarSensoresAlInventario(scanner.nextInt());
                    pausa();
                    break;
                case 9://Mostrar lista de cultivos
                    System.out.println("----------------");
                    gestor.getGestorCultivos().mostrarCultivosDisponibles();
                    pausa();
                    break;
                case 10://Eliminar una parcela
                    System.out.println("----------------");
                    gestor.getGestorParcelas().eliminarParcela(scanner);
                    pausa();
                    break;
                case 11://Cambiar cultivo en una parcela
                    System.out.println("----------------");
                    gestor.getGestorCultivos()
                            .cambiarCultivoParcela(scanner);
                    pausa();
                    break;
                case 12://Verificar humedad de las parcelas
                    System.out.println("----------------");
                    gestor.getGestorSensores()
                            .mostrarHumedadParcelas();
                    pausa();
                    break;
                case 13://Mostrar lecturas de un sensor
                    System.out.println("----------------");
                    gestor.getGestorSensores()
                            .mostrarLecturasSensor(scanner);
                    pausa();
                    break;
                case 14://Mostrar historial de activación de un aspersor
                    System.out.println("----------------");
                    gestor.getGestorAspersores()
                            .mostrarHistorialAspersor(scanner);
                    pausa();
                    break;
                case 15://Prender manualmente un aspersor
                    System.out.println("----------------");
                    gestor.getGestorAspersores()
                            .prenderAspersorManual(scanner);
                    pausa();
                    break;
                case 16://Conectar o desconectar un aspersor
                    System.out.println("----------------");
                    gestor.getGestorAspersores()
                            .cambiarConexionAspersor(scanner);
                    pausa();
                    break;
                case 17://Conectar o desconectar un sensor
                    System.out.println("----------------");
                    gestor.getGestorSensores()
                            .cambiarConexionSensor(scanner);
                    pausa();
                    break;
                case 18://Eliminar un aspersor del sistema
                    System.out.println("----------------");
                    gestor.getGestorAspersores()
                            .eliminarAspersor(scanner);
                    pausa();
                    break;
                case 19://Eliminar un sensor del sistema
                    System.out.println("----------------");
                    gestor.getGestorSensores()
                            .eliminarSensor(scanner);
                    pausa();
                    break;
                case 40://
                    System.out.println("----------------");
                    gestor.getGestorSensores().simularLecturas();
                    gestor.getGestorAspersores().evaluarYRiegoAutomatico();
                    pausa();
                    break;
                case 20://Salir
                    System.out.println("-----------------");
                    System.out.println("Saliendo del sistema...");
                    System.exit(0);
                default:
                    System.out.println("----------------");
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 20);

        scanner.close();
    }

    public static void pausa() {
        System.out.println("__________Presione enter para continuar__________");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}


