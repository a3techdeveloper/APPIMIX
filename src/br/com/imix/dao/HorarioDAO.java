package br.com.imix.dao;

import br.com.imix.jdbc.ConexaoMySQL;
import br.com.imix.model.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class HorarioDAO {
    
    final private Connection conn;
    
    public HorarioDAO(){
        this.conn = new ConexaoMySQL().pegarConexao();
    }
    
    @SuppressWarnings("unchecked")
    public List<Horario> listar(){
        List<Horario> lista = new ArrayList<>();
        String sql = "select * from tb_horarios";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Horario h = new Horario();
                h.setId(rs.getInt("id"));
                h.setHorario(rs.getString("horario"));
                lista.add(h);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar lista de horários: "+e);
            return null;
        }
    }
    
    public Horario buscarHorários(String descricao){
        String sql = "select * from tb_horarios where horario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            Horario h = new Horario();
            while(rs.next()){
                h.setId(rs.getInt("id"));
                h.setHorario(rs.getString("horario"));
            }
            return h;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar horários: "+e);
            return null;
        }
    }
}