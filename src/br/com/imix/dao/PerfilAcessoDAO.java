package br.com.imix.dao;

import br.com.imix.jdbc.ConexaoMySQL;
import br.com.imix.model.PerfilAcesso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PerfilAcessoDAO {
    
    final private Connection conn;
    
    public PerfilAcessoDAO(){
        this.conn = new ConexaoMySQL().pegarConexao();
    }
    
    @SuppressWarnings("unchecked")
    public List<PerfilAcesso> listar(){
        List<PerfilAcesso> lista = new ArrayList<>();
        String sql = "select * from tb_perfilAcesso";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                PerfilAcesso pa = new PerfilAcesso();
                pa.setId(rs.getInt("id"));
                pa.setPerfil(rs.getString("perfil"));
                lista.add(pa);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar lista de perfis: "+e);
            return null;
        }
    } 
    
    public PerfilAcesso buscarPerfilAcesso(String descricao){
        String sql = "select * from tb_perfilAcesso where perfil = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            PerfilAcesso p = new PerfilAcesso();
            while(rs.next()){
                p.setId(rs.getInt("id"));
                p.setPerfil(rs.getString("perfil"));
            }
            return p;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar perfil de acesso: "+e);
            return null;
        }
    }
}