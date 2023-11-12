package br.com.pointsistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.pointsistemas.conexaojdbc.SingleConnection;
import br.com.pointsistemas.model.Userposjava;

public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Userposjava userposjava) { /* Recebe a classe para salvar */
		try {
			String sql = "insert into userposjava (nome, email) values (?, ?)"; /* manda a instrução */
			PreparedStatement insert = connection.prepareStatement(sql); /* Finaliza o comando */
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());

			insert.execute();

			connection.commit(); /* Salva no banco */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Userposjava> listar() throws Exception {
		List<Userposjava> list = new ArrayList<Userposjava>();

		String sql = "select * from userposjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();

			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));

			list.add(userposjava);
		}

		return list;
	}

	public Userposjava buscar(Long id) throws Exception {
		Userposjava retorno = new Userposjava();

		String sql = "select * from userposjava where id = " + id;

		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultado = statement.executeQuery()) {

			while (resultado.next()) {
				retorno.setId(resultado.getLong("id"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setEmail(resultado.getString("email"));
			}
		}

		return retorno;
	}

	public void atualizar(Userposjava userposjava) {
	    try {
	        String sql = "update userposjava set nome = ? where id = ?";

	        PreparedStatement statement = connection.prepareStatement(sql);

	        statement.setString(1, userposjava.getNome());
	        statement.setLong(2, userposjava.getId());

	        statement.execute();
	        connection.commit();

	    } catch (Exception e) {
	        try {
	            connection.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    }
	}

	public void deletar(Long id) {
		try {
			String sql = "delete from userposjava where id = " + id;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void deleteFones(Long idUser) {
		try {
			String sqlFone = "delete from telefoneuser where usuariopessoa =" + idUser;
			String sqlUser = "delete from userposjava where id =" + idUser;

			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);

			preparedStatement.executeUpdate();
			connection.commit();

			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
