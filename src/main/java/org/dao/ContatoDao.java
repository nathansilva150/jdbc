package org.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.db.ConnectionFactory;
import org.model.Contato;

public class ContatoDao {

    public void salvar(Contato contato) throws SQLException {
        String command = """
                INSERT INTO contatos
                (nome,numero)
                VALUES
                (?,?)
                """;
        
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)){

                stmt.setString(1, contato.getNome());
                stmt.setString(2, contato.getNumero());
                stmt.executeUpdate();
        }
    }

    public void editar(int id, String novoNome, String novoNumero) throws SQLException {
        String command = """
                UPDATE contatos
                SET nome = ?, numero = ?
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)) {

                stmt.setString(1, novoNome);
                stmt.setString(2, novoNumero);
                stmt.setInt(3, id);
                stmt.executeUpdate();

        } catch(SQLException e) {
                e.printStackTrace();
        }
    }

    public static List<Contato> listar() {
        String command = """
                SELECT id,nome,numero
                FROM contatos
                """;
        List<Contato> contatos = new ArrayList<>();
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)) {
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String numero = rs.getString("numero");

                var contato = new Contato(id, nome, numero);
                contatos.add(contato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contatos;
    }

    public static void deletar(int id) throws SQLException {
        String command = """
                DELETE FROM contatos
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
        PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contato> BuscarPorNome(String nome) {
        String command = """
                SELECT id, nome, numero
                FROM contatos
                WHERE nome LIKE ?
                """;

        List<Contato> contatosBuscados = new ArrayList<>();

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)) {

                stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            var contato = new Contato();
            contato.setId(rs.getInt("id"));
            contato.setNome(rs.getString("nome"));
            contato.setNumero(rs.getString("numero"));
            contatosBuscados.add(contato);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contatosBuscados;
    }

    public static List<Contato> buscarPorListaID(ArrayList<Integer> ids) {
        String command = """
            SELECT id, nome, numero
            FROM contatos
            WHERE id = ?
            """;

        List<Contato> contatosBuscados = new ArrayList<>();

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)) {

                for (Contato contato : listar()) {
                    for (Integer id : ids) {
                        if (contato.getId() == id) {
                            stmt.setInt(1, id);

                            ResultSet rs = stmt.executeQuery();

                            while(rs.next()) {
                                var contatos = new Contato();
                                contatos.setId(rs.getInt("id"));
                                contato.setNome(rs.getString("nome"));
                                contato.setNumero(rs.getString("numero"));
                                contatosBuscados.add(contato);
                            }
                        }
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contatosBuscados;
    }
}
