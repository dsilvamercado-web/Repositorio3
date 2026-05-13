/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muros;

public class CuantificacionMamposteria {
    
    private double largoMuroM;
    private double altoMuroM;
    private double largoBlockCm;
    private double altoBlockCm;
    private double espesorJuntaCm;

    // Constructor
    public CuantificacionMamposteria(double largoMuroM, double altoMuroM, double largoBlockCm, double altoBlockCm, double espesorJuntaCm) {
        this.largoMuroM = largoMuroM;
        this.altoMuroM = altoMuroM;
        this.largoBlockCm = largoBlockCm;
        this.altoBlockCm = altoBlockCm;
        this.espesorJuntaCm = espesorJuntaCm;
    }

    // Método para calcular el área del muro
    public double calcularAreaMuro() {
        return largoMuroM * altoMuroM;
    }

    // Método principal: Cálculo de piezas usando la fórmula C = 1 / ((L+J) * (H+J))
    public int calcularTotalPiezas() {
        // Convertimos las medidas del block y la junta de cm a metros
        double largoM = largoBlockCm / 100.0;
        double altoM = altoBlockCm / 100.0;
        double juntaM = espesorJuntaCm / 100.0;

        // Calculamos cuántas piezas caben en 1 metro cuadrado
        double piezasPorM2 = 1.0 / ((largoM + juntaM) * (altoM + juntaM));
        
        // Multiplicamos por el área total del muro y redondeamos hacia arriba
        double totalPiezas = piezasPorM2 * calcularAreaMuro();
        return (int) Math.ceil(totalPiezas);
    }
    
    // Método para calcular el mortero (aproximación rápida)
    public double calcularVolumenMortero() {
        // Área del muro por un espesor promedio de junta (ej. 0.025 m3 por m2)
        return calcularAreaMuro() * 0.025; 
    }
}