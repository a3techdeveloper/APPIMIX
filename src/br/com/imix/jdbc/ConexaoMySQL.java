package br.com.imix.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoMySQL {
    
    private Connection conn;
    final private String url = "jdbc:mysql://localhost:3306/db_imix?characterEncoding=utf-8";
    final private String user = "root";
    final private String password = "";
    
    public Connection pegarConexao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);            
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao se conectar com banco de dados: "+e);
        }
        return null;
    }    
}