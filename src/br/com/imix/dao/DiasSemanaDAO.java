package br.com.imix.dao;

import br.com.imix.jdbc.ConexaoMySQL;
import br.com.imix.model.DiasSemana;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DiasSemanaDAO {
    
    final private Connection conn;
    
    public DiasSemanaDAO(){
        this.conn = new ConexaoMySQL().pegarConexao();
    }
    
    @SuppressWarnings("unchecked")
    public List<DiasSemana> listar(){
        List<DiasSemana> lista = new ArrayList<>();
        String sql = "select * from tb_dias_semana";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                DiasSemana d = new DiasSemana();
                d.setId(rs.getInt("id"));
                d.setDias(rs.getString("dias"));
                lista.add(d);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar lista de dias: "+e);
            return null;
        }
    }
    
    public DiasSemana buscarDias(String descricao){
        String sql = "select * from tb_dias_semana where dias = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            DiasSemana d = new DiasSemana();
            while(rs.next()){
                d.setId(rs.getInt("id"));
                d.setDias(rs.getString("dias"));
            }
            return d;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar dias: "+e);
            return null;
        }
    }
}