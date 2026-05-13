/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muros;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL = "jdbc:mariadb://localhost:3306/scea_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // Sin contraseña por defecto en XAMPP

    // Método 1: Conectar a la base de datos
    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a MariaDB.");
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    // Método 2: Guardar los cálculos
    public static void guardarCalculoMuro(double largo, double alto, double area, int piezas, double mortero) {
        String sql = "INSERT INTO cuantificacion_muros (id_proyecto, largo_muro_m, alto_muro_m, area_neta_m2, total_piezas, volumen_mortero_m3) VALUES (1, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conectar(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, largo);
            pstmt.setDouble(2, alto);
            pstmt.setDouble(3, area);
            pstmt.setInt(4, piezas);
            pstmt.setDouble(5, mortero);
            
            pstmt.executeUpdate();
            System.out.println("Cálculo guardado exitosamente en la base de datos.");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar en la BD: " + e.getMessage());
        }
    }
}