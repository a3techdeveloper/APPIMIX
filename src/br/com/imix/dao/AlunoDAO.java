package br.com.imix.dao;

import br.com.imix.jdbc.ConexaoMySQL;
import br.com.imix.model.Aluno;
import br.com.imix.model.Curso;
import br.com.imix.model.DiasSemana;
import br.com.imix.model.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlunoDAO {
    
    final private Connection conn;
    
    public AlunoDAO(){
        this.conn = new ConexaoMySQL().pegarConexao();
    }
    
    public void inserir(Aluno a){
        String sql = "insert into tb_alunos(nome, celular, usuario, senha, modulo, id_curso, id_dias_semana, id_horario)values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCelular());
            stmt.setString(3, a.getUsuario());
            stmt.setString(4, a.getSenha());
            stmt.setString(5, a.getModulo());
            stmt.setInt(6, a.getCurso().getId());
            stmt.setInt(7, a.getDias_semana().getId());
            stmt.setInt(8, a.getHorario().getId());
            
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Aluno(a) cadastrado(a) com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar aluno(a)!");
        }
    }
    
    public void editar(Aluno a){
        String sql = "update tb_alunos set nome = ?, celular = ?, usuario = ?, senha = ?, modulo = ?, id_curso = ?, id_dias_semana = ?, id_horario = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCelular());
            stmt.setString(3, a.getUsuario());
            stmt.setString(4, a.getSenha());
            stmt.setString(5, a.getModulo());
            stmt.setInt(6, a.getCurso().getId());
            stmt.setInt(7, a.getDias_semana().getId());
            stmt.setInt(8, a.getHorario().getId());
            stmt.setInt(9, a.getId());
            
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Aluno(a) editado(a) com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar editar aluno(a)!");
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Aluno> listar(){
        List<Aluno> lista = new ArrayList<>();
        String sql = "select a.id, a.nome, a.celular, a.usuario, a.senha, a.modulo, c.curso, d.dias, h.horario from tb_alunos as a inner join tb_cursos as c on(a.id_curso = c.id) inner join tb_dias_semana as d on(a.id_dias_semana = d.id) inner join tb_horarios as h on(a.id_horario = h.id)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Aluno a      = new Aluno();
                Curso c      = new Curso();
                DiasSemana d = new DiasSemana();
                Horario h    = new Horario();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCelular(rs.getString("celular"));
                a.setUsuario(rs.getString("usuario"));
                a.setSenha(rs.getString("senha"));
                a.setModulo(rs.getString("modulo"));
                c.setCurso(rs.getString("curso"));
                a.setCurso(c);
                d.setDias(rs.getString("dias"));
                a.setDias_semana(d);
                h.setHorario(rs.getString("horario"));
                a.setHorario(h);
                lista.add(a);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar lista de alunos: "+e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Aluno> filtrar(String nome){
        List<Aluno> lista = new ArrayList<>();
        String sql = "select tb_alunos.id, nome, celular, usuario, senha, modulo, tb_cursos.curso, tb_dias_semana.dias, tb_horarios.horario from tb_alunos inner join tb_cursos on(tb_alunos.id_curso = tb_cursos.id) inner join tb_dias_semana on(tb_alunos.id_dias_semana = tb_dias_semana.id) inner join tb_horarios on(tb_alunos.id_horario = tb_horarios.id) where nome like ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Aluno a = new Aluno();
                Curso c = new Curso();
                DiasSemana d = new DiasSemana();
                Horario h = new Horario();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCelular(rs.getString("celular"));
                a.setUsuario(rs.getString("usuario"));
                a.setSenha(rs.getString("senha"));
                a.setModulo(rs.getString("modulo"));
                c.setCurso(rs.getString("curso"));
                a.setCurso(c);
                d.setDias(rs.getString("dias"));
                a.setDias_semana(d);
                h.setHorario(rs.getString("horario"));
                a.setHorario(h); 
                lista.add(a);
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista: "+e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Aluno> buscarPorDiaHorario(String dias, String horario){
        List<Aluno> lista = new ArrayList<>();
        String sql = "select tb_alunos.id, nome, celular, usuario, senha, modulo, tb_cursos.curso, tb_dias_semana.dias, tb_horarios.horario from tb_alunos inner join tb_cursos on(tb_alunos.id_curso = tb_cursos.id) inner join tb_dias_semana on(tb_alunos.id_dias_semana = tb_dias_semana.id) inner join tb_horarios on(tb_alunos.id_horario = tb_horarios.id) where tb_dias_semana.dias like ? and tb_horarios.horario like ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, dias);
            stmt.setString(2, horario);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Aluno a = new Aluno();
                Curso c = new Curso();
                DiasSemana d = new DiasSemana();
                Horario h = new Horario();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCelular(rs.getString("celular"));
                a.setUsuario(rs.getString("usuario"));
                a.setSenha(rs.getString("senha"));
                a.setModulo(rs.getString("modulo"));
                c.setCurso(rs.getString("curso"));
                a.setCurso(c);
                d.setDias(rs.getString("dias"));
                a.setDias_semana(d);
                h.setHorario(rs.getString("horario"));
                a.setHorario(h); 
                lista.add(a);
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista: "+e);
            return null;
        }
    }
    
    public String buscarUsuario(int id){
        String usuario = "";
        String sql = "select usuario from tb_alunos where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                usuario = rs.getString("usuario");
            }
            return usuario;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar o usuário: "+e);
            return null;
        }
    }
    
    public String buscarSenha(int id){
        String senha = "";
        String sql = "select senha from tb_alunos where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                senha = rs.getString("senha");
            }
            return senha;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a senha: "+e);
            return null;
        }
    }
    
    public int contarAlunos(){
        int quantidade = 0;
        String sql = "select id from tb_alunos";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                quantidade = rs.getRow();
            }
            return quantidade;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Erro ao buscar a quantidade: "+e);
             return 0;
        }
    }

    public void excluir(Aluno a){
        String sql = "delete from tb_alunos where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, a.getId());
            
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Aluno(a) excluido(a) com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar excluir aluno(a): "+e);
        }
    }    
}