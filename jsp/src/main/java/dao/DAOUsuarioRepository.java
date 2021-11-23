package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelUsuario;

public class DAOUsuarioRepository {

	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	
	public ModelUsuario gravarUsuario(ModelUsuario objeto, Long userLogado) throws Exception{
		
		if(objeto.isNovo()) {
		
			String sql = "INSERT INTO model_login (login, senha, nome, email, usuario_id, sexo, perfil, cep, logradouro, bairro, localidade, uf, numero) "
					                    + "VALUES (?,      ?,     ?,     ?,      ?,        ?,     ?,     ?,       ?,       ?,        ?,       ?,    ?  );";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, objeto.getLogin());
			preparedStatement.setString(2, objeto.getSenha()); 
			preparedStatement.setString(3, objeto.getNome());
			preparedStatement.setString(4, objeto.getEmail());
			preparedStatement.setLong(5, userLogado);
			preparedStatement.setString(6, objeto.getSexo());
			preparedStatement.setString(7, objeto.getPerfil());
			preparedStatement.setString(8, objeto.getCep());
			preparedStatement.setString(9, objeto.getLogradouro());
			preparedStatement.setString(10, objeto.getBairro());
			preparedStatement.setString(11, objeto.getLocalidade());
			preparedStatement.setString(12, objeto.getUf());
			preparedStatement.setString(13, objeto.getNumero());
			
		
			preparedStatement.execute();
			
			connection.commit();
			
				if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
					sql = "UPDATE model_login SET fotoUser =?, extensaoFotoUser=? WHERE login=?";
					
					preparedStatement = connection.prepareStatement(sql);
					
					preparedStatement.setString(1, objeto.getFotoUser());
					preparedStatement.setString(2, objeto.getExtensaoFotoUser());
					preparedStatement.setString(3, objeto.getLogin());
					
					preparedStatement.execute();
					connection.commit();
				}
		
		} else {
			
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?  WHERE id = "+objeto.getId()+";";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, objeto.getLogin());
			preparedStatement.setString(2, objeto.getSenha()); 
			preparedStatement.setString(3, objeto.getNome());
			preparedStatement.setString(4, objeto.getEmail());
			preparedStatement.setString(5, objeto.getPerfil());
			preparedStatement.setString(6, objeto.getSexo());
			preparedStatement.setString(7, objeto.getCep());
			preparedStatement.setString(8, objeto.getLogradouro());
			preparedStatement.setString(9, objeto.getBairro());
			preparedStatement.setString(10, objeto.getLocalidade());
			preparedStatement.setString(11, objeto.getUf());
			preparedStatement.setString(12, objeto.getNumero());

			preparedStatement.executeUpdate();
			
			connection.commit();
			
			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "UPDATE model_login SET fotoUser =?, extensaoFotoUser=? WHERE id=?";
				
				preparedStatement = connection.prepareStatement(sql);
				
				preparedStatement.setString(1, objeto.getFotoUser());
				preparedStatement.setString(2, objeto.getExtensaoFotoUser());
				preparedStatement.setLong(3, objeto.getId());
				
				preparedStatement.execute();
				connection.commit();
			}
			
		}
		
		return this.consultaUsuario(objeto.getLogin(), userLogado);
	}
	
	
	
	public List<ModelUsuario> consultarUsuarioList(Long userLogado) throws Exception{
		
		List<ModelUsuario> retorno = new ArrayList<>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin is false and usuario_id = " + userLogado;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelUsuario modelUsuario =  new ModelUsuario();
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setLogin(resultSet.getString("login"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
			modelUsuario.setPerfil(resultSet.getString("perfil"));
			modelUsuario.setSexo(resultSet.getString("sexo"));
			modelUsuario.setFotoUser(resultSet.getString("fotoUser"));
			
			
			retorno.add(modelUsuario);
		}
		
		return retorno;
		
	}
	
	
	
	
	
	public List<ModelUsuario> consultarUsuarioList(String nome, Long userLogado) throws Exception{
		
		List<ModelUsuario> retorno = new ArrayList<>();
		
		String sql = "SELECT * FROM model_login WHERE upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "%" + nome + "%");
		preparedStatement.setLong(2, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelUsuario modelUsuario =  new ModelUsuario();
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setLogin(resultSet.getString("login"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
			modelUsuario.setPerfil(resultSet.getString("perfil"));
			modelUsuario.setSexo(resultSet.getString("sexo"));
			modelUsuario.setFotoUser(resultSet.getString("fotoUser"));

			retorno.add(modelUsuario);
		}
		
		return retorno;
		
	}
	
	
	
public ModelUsuario consultaUsuario(String login, Long userLogado) throws Exception {
		
		ModelUsuario modelUsuario =  new ModelUsuario();
		
		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('"+login+"')  and useradmin is false and usuario_id = " + userLogado;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
			modelUsuario.setPerfil(resultSet.getString("perfil"));
			modelUsuario.setSexo(resultSet.getString("sexo"));
			modelUsuario.setFotoUser(resultSet.getString("fotoUser"));
			modelUsuario.setCep(resultSet.getString("cep"));
			modelUsuario.setLogradouro(resultSet.getString("logradouro"));
			modelUsuario.setBairro(resultSet.getString("bairro"));
			modelUsuario.setLocalidade(resultSet.getString("localidade"));
			modelUsuario.setUf(resultSet.getString("uf"));
			modelUsuario.setNumero(resultSet.getString("numero"));
		}
		
		return modelUsuario;
		
	}
	
	
	
	public ModelUsuario consultaUsuario(String login) throws Exception {
		
		ModelUsuario modelUsuario =  new ModelUsuario();
		
		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('"+login+"')  and useradmin is false";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
			modelUsuario.setPerfil(resultSet.getString("perfil"));
			modelUsuario.setSexo(resultSet.getString("sexo"));
			modelUsuario.setFotoUser(resultSet.getString("fotoUser"));

		}
		
		return modelUsuario;
		
	}
	
	
	
	public ModelUsuario consultaUsuarioLogado(String login) throws Exception {
		
		ModelUsuario modelUsuario =  new ModelUsuario();
		
		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('"+login+"')";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			modelUsuario.setId(resultSet.getLong("id"));
			modelUsuario.setEmail(resultSet.getString("email"));
			modelUsuario.setLogin(resultSet.getString("login"));
			modelUsuario.setNome(resultSet.getString("nome"));
			modelUsuario.setSenha(resultSet.getString("senha"));
			modelUsuario.setUseradmin(resultSet.getBoolean("useradmin"));
			modelUsuario.setPerfil(resultSet.getString("perfil"));
			modelUsuario.setSexo(resultSet.getString("sexo"));
			modelUsuario.setFotoUser(resultSet.getString("fotoUser"));
			modelUsuario.setCep(resultSet.getString("cep"));
			modelUsuario.setLogradouro(resultSet.getString("logradouro"));
			modelUsuario.setBairro(resultSet.getString("bairro"));
			modelUsuario.setLocalidade(resultSet.getString("localidade"));
			modelUsuario.setUf(resultSet.getString("uf"));
			modelUsuario.setNumero(resultSet.getString("numero"));


		}
		
		return modelUsuario;
		
	}
	
	
	
	public ModelUsuario consultaUsuarioId(String id, Long userLogado) throws Exception {
			
			ModelUsuario modelUsuario =  new ModelUsuario();
			
			String sql = "SELECT * FROM model_login WHERE id = ?  and useradmin is false and usuario_id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, Long.parseLong(id));
			preparedStatement.setLong(2, userLogado);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				modelUsuario.setId(resultSet.getLong("id"));
				modelUsuario.setEmail(resultSet.getString("email"));
				modelUsuario.setLogin(resultSet.getString("login"));
				modelUsuario.setNome(resultSet.getString("nome"));
				modelUsuario.setSenha(resultSet.getString("senha"));
				modelUsuario.setPerfil(resultSet.getString("perfil"));
				modelUsuario.setSexo(resultSet.getString("sexo"));
				modelUsuario.setFotoUser(resultSet.getString("fotoUser"));
				modelUsuario.setExtensaoFotoUser(resultSet.getString("extensaoFotoUser"));
				modelUsuario.setCep(resultSet.getString("cep"));
				modelUsuario.setLogradouro(resultSet.getString("logradouro"));
				modelUsuario.setBairro(resultSet.getString("bairro"));
				modelUsuario.setLocalidade(resultSet.getString("localidade"));
				modelUsuario.setUf(resultSet.getString("uf"));
				modelUsuario.setNumero(resultSet.getString("numero"));
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
		String sql = "DELETE FROM model_login WHERE id = ?  and useradmin is false;";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, Long.parseLong(idUser));
		
		preparedStatement.executeUpdate();
		
		connection.commit();
	}
	
}
