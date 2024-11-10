package br.com.imix.jdbc;

public class TestarConexao {

    public static void main(String[] args) {
        
        ConexaoMySQL conecta = new ConexaoMySQL();
        conecta.pegarConexao();
        
    }
    
}
