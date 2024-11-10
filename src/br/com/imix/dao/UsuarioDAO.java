package br.com.imix.dao;

import br.com.imix.jdbc.ConexaoMySQL;
import br.com.imix.model.PerfilAcesso;
import br.com.imix.model.Usuario;
import br.com.imix.view.AreaTrabalho;
import br.com.imix.view.FormularioLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {
    
    final private Connection conn;
    
    public UsuarioDAO(){
        this.conn = new ConexaoMySQL().pegarConexao();
    }
    
    public void inserir(Usuario u){
        String sql = "insert into tb_usuarios(nome,email,senha,id_perfil)values(?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setInt(4, u.getPerfil().getId());
            
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar usuário: "+e);
        }
    }
    
    public void editar(Usuario u){
        String sql = "update tb_usuarios set nome = ?, email = ?, senha = ?, id_perfil = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setInt(4, u.getPerfil().getId());
            stmt.setInt(5, u.getId());
            
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar editar usuário: "+e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Usuario> listar(){
        List<Usuario> lista = new ArrayList<>();
        String sql = "select u.id, u.nome, u.email, p.perfil, u.senha from tb_usuarios as u inner join tb_perfilAcesso as p on(u.id_perfil = p.id)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Usuario u = new Usuario();
                PerfilAcesso p = new PerfilAcesso();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                p.setPerfil(rs.getString("perfil"));
                u.setPerfil(p);
                u.setSenha(rs.getString("senha"));
                lista.add(u);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar lista de usuários: "+e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Usuario> filtrar(String nome){
        List<Usuario> lista = new ArrayList<>();
        String sql = "select tb_usuarios.id, nome, email, senha, perfil, tb_perfilAcesso.id, tb_usuarios.senha from tb_usuarios inner join tb_perfilAcesso on (tb_usuarios.id_perfil = tb_perfilAcesso.id) where tb_usuarios.nome like ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Usuario u = new Usuario();
                PerfilAcesso p = new PerfilAcesso();
                u.setId(rs.getInt("tb_usuarios.id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                p.setPerfil(rs.getString("perfil"));
                u.setPerfil(p);
                u.setSenha(rs.getString("senha"));
                lista.add(u);
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista: "+e);
            return null;
        }
    }
    
    public String buscarSenha(int id){
        String senha = "";
        String sql = "select senha from tb_usuarios where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                senha = rs.getString("senha");
            }
            return senha;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar a senha: "+e);
            return null;
        }
    }
    
    public void excluir(Usuario u){
        String sql = "delete from tb_usuarios where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, u.getId());
            
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Usuário excluido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar excluir usuário: "+e);
        }
    }
    
    public void efetuarLogin(String email, String senha){
        String sql = "select nome, email, senha, perfil from tb_usuarios inner join tb_perfilAcesso on (tb_usuarios.id_perfil = tb_perfilAcesso.id) where email = ? and senha = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                if(rs.getString("perfil").equals("ADMINISTRADOR")){
                    
                    AreaTrabalho at = new AreaTrabalho();
                    at.usuarioLogado = rs.getString("nome");
                    at.perfilLogado = rs.getString("perfil");
                    
                    String msg = "<html>Seja Bem Vindo(a) ao Sistema <b>"+at.usuarioLogado+"</b></html>";
                    JOptionPane.showMessageDialog(null, msg);                    
                    at.setVisible(true);                    
                }else if(rs.getString("perfil").equals("USUÁRIO")){
                    
                    AreaTrabalho at = new AreaTrabalho();
                    at.usuarioLogado = rs.getString("nome");
                    at.perfilLogado = rs.getString("perfil");
                    
                    at.cadUsuario.setEnabled(false);
                    at.cadAluno.setEnabled(false);
                   
                    String msg = "<html>Seja Bem Vindo(a) ao Sistema <b>"+at.usuarioLogado+"</b></html>";
                    JOptionPane.showMessageDialog(null, msg);
                    at.setVisible(true);
                }
            }else{
                FormularioLogin fl = new FormularioLogin();
                JOptionPane.showMessageDialog(null, "Email e/ou Senha Incorreto(s)!");
                fl.setVisible(true);                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }        
    }    
}