package persistencia;

import java.sql.SQLException;
import entidade.Usuario;



public class usuarioDAO extends Dao {

	public Usuario login(String login, String senha) throws SQLException, ClassNotFoundException {
		open();
		String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
		stmt = con.prepareStatement(sql);

		stmt.setString(1, login);
		stmt.setString(2, senha);

		rs = stmt.executeQuery();
		Usuario usuario = null;

		if (rs.next()) {
			usuario = new Usuario(rs.getString(1), rs.getString(2));
		}
		close();
		return usuario;
	}
	
	public void adicionar(Usuario usuario) throws ClassNotFoundException {
		String sql = "insert into usuario " + "(nome,email,login,senha,)" + " values (?,?,?,?)";

		try {
			// prepared statement para inser��o
			open();
			stmt = con.prepareStatement(sql);

			// seta os valores
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getLogin());
			stmt.setString(4, usuario.getSenha());
			

			// executa
			stmt.execute();
			close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
}
	
	public void editar(Usuario usuario) throws ClassNotFoundException {
		String sql = "update usuario set nome=?, email=?, login=?, senha=?, where idUsuario=?";

		try {
			open();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getLogin());
			stmt.setString(4, usuario.getSenha());
			stmt.setLong(6, usuario.getIdUsuario());

			stmt.execute();
			close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

		
		
		public Usuario buscarUsuario(String login) throws SQLException, ClassNotFoundException {

			open();
			String sql = "SELECT * FROM usuario WHERE login  = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, login);
			Usuario usuario = null;
			rs = stmt.executeQuery();

			if (rs.next()) {
				usuario = new Usuario(rs.getLong("idUsuario"), rs.getString("nome"), rs.getString("email"),
						rs.getString("login"), rs.getString("senha"));
			}
			close();
			return usuario;

		}

		public Usuario buscarPorIdUsuario(int idUsuario) throws ClassNotFoundException, SQLException{
		
			open();
			String sql = "select * from usuario where idUsuario =?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idUsuario);
			
			Usuario usuario = null;
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				usuario = new Usuario(rs.getLong("idUsuario"), rs.getString("nome"), rs.getString("email"),
						rs.getString("login"), rs.getString("senha"));
			}
			close();
			return usuario;

		}
}
