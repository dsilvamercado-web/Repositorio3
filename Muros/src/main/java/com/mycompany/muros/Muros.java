/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.muros;

/**
 *
 * @author HP
 */
public class Muros {


    public static void main(String[] args) {
        
        
        System.out.println("--- INICIANDO PRUEBA FINAL DE INTEGRACIÓN ---");

        // 1. Instanciar la clase de matemáticas
        System.out.println("\nCalculando materiales para el muro...");
        CuantificacionMamposteria calculo = new CuantificacionMamposteria(5.0, 2.5, 40.0, 20.0, 1.5);
        
        // 2. Extraer los resultados en variables
        double area = calculo.calcularAreaMuro();
        int piezas = calculo.calcularTotalPiezas();
        double mortero = calculo.calcularVolumenMortero();
        
        // Imprimir resultados en consola para verificar
        System.out.println("Área del muro: " + area + " m2");
        System.out.println("Total de piezas: " + piezas + " blocks");
        System.out.println("Volumen de mortero: " + mortero + " m3");

        // 3. Guardar directamente en la base de datos
        System.out.println("\nIntentando guardar en MariaDB...");
        ConexionBD.guardarCalculoMuro(5.0, 2.5, area, piezas, mortero);
        
        System.out.println("\n--- FIN DE LA PRUEBA ---");
    }
}