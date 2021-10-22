package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelUsuario;

public class DAOUsuarioRepository {

	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	
	public ModelUsuario gravarUsuario(ModelUsuario objeto) throws Exception{
		
		if(objeto.isNovo()) {
		
			String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?);";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, objeto.getLogin());
			preparedStatement.setString(2, objeto.getSenha()); 
			preparedStatement.setString(3, objeto.getNome());
			preparedStatement.setString(4, objeto.getEmail());
		
			preparedStatement.execute();
			
			connection.commit();
		
		} else {
			
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, objeto.getLogin());
			preparedStatement.setString(2, objeto.getSenha()); 
			preparedStatement.setString(3, objeto.getNome());
			preparedStatement.setString(4, objeto.getEmail());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		}
		
		return this.consultaUsuario(objeto.getLogin());
	}
	
	
	public List<ModelUsuario> consultarUsuarioList(String nome) throws Exception{
		
		List<ModelUsuario> retorno = new ArrayList<>();
		
		String sql = "SELECT * FROM model_login WHERE upper(nome) like upper(?)";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "%" + nome + "%");
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelUsuario modelUsuario =  new ModelUsuario();
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setLogin(resultSet.getString("login"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
			
			retorno.add(modelUsuario);
		}
		
		return retorno;
		
	}
	
	
	public ModelUsuario consultaUsuario(String login) throws Exception {
		
		ModelUsuario modelUsuario =  new ModelUsuario();
		
		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('"+login+"')";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
		}
		
		return modelUsuario;
		
	}
	
	public boolean validarLogin(String login) throws Exception {
		
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE upper(login) = upper('"+login+"')";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next(); //PARA ENTRAR NOS RESULTADOS DO SQL
		return resultSet.getBoolean("existe");
	}
	
	public void deletaUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ?;";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, Long.parseLong(idUser));
		
		preparedStatement.executeUpdate();
		
		connection.commit();
	}
	
}
