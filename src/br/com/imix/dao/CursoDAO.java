package br.com.imix.dao;

import br.com.imix.jdbc.ConexaoMySQL;
import br.com.imix.model.Curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CursoDAO {
    
    final private Connection conn;
    
    public CursoDAO(){
        this.conn = new ConexaoMySQL().pegarConexao();
    }
    
    @SuppressWarnings("unchecked")
    public List<Curso> listar(){
        List<Curso> lista = new ArrayList<>();
        String sql = "select * from tb_cursos";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Curso c = new Curso();
                c.setId(rs.getInt("id"));
                c.setCurso(rs.getString("curso"));
                lista.add(c);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar lista de cursos: "+e);
            return null;
        }
    }
    
    public Curso buscarCurso(String descricao){
        String sql = "select * from tb_cursos where curso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            Curso c = new Curso();
            while(rs.next()){
                c.setId(rs.getInt("id"));
                c.setCurso(rs.getString("curso"));
            }
            return c;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar curso: "+e);
            return null;
        }
    }
}