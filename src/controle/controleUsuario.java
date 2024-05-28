package controle;

import java.io.IOException;
import java.sql.SQLException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidade.Usuario;
import persistencia.usuarioDAO;


/**
 * Servlet implementation class controleUsuario
 */
@WebServlet(name = "/controleUsuario", urlPatterns = {"/cadastrarUsuario.html", "/excluirUsuario.html", "/logginUsuario.html", "/sairUsuario.html","/sairAdmin.html"})

public class controleUsuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
   
    public controleUsuario() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String url = request.getServletPath();
				
			if(url.equalsIgnoreCase("/cadastrarUsuario.html")) {
				cadastrar(request, response);
			}else if(url.equalsIgnoreCase("/logginUsuario.html")) {
				logar(request, response);
			}else if(url.equalsIgnoreCase("/sairUsuario.html")) {
				sair(request, response);
			}else {
				throw new Exception("Url nao encontrada!!!");
		
		
		}
		
	}catch (Exception e) {
		response.sendRedirect("index.jsp");
		e.printStackTrace();
	}

}
	
	protected void cadastrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			// Pegando info do formulário 
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
					
			
			//Criando o usuario
			
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setSenha(senha);
	
			
			// Criando um Objeto do tipo UsuarioDAO 
			
			try {
				usuarioDAO dao = new usuarioDAO();
				dao.adicionar(usuario);
				request.getSession().setAttribute("usuario", usuario);
				request.getSession().setAttribute("login", login);	
				request.setAttribute("msg", "<div class='alert alert-success'>Usuario Cadastrado!</div>");
			}catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg","<div> class='alert alert-danger'>Usuario nao cadastrado!</div>" );
			}finally {
				request.getRequestDispatcher("sessaoUsuario.jsp").forward(request, response);
			}			
	}	
	
	public void editar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			
			//Capturar id do user 
			@SuppressWarnings("removal")
			Integer idUsuario = new Integer(request.getParameter("id"));
			Usuario user = new usuarioDAO().buscarPorIdUsuario(idUsuario);
			
			if (user == null) {
				request.setAttribute("msg", "<div class='alert alert-info'>Usuario nao encontrado!</div>");
				request.getRequestDispatcher("buscaUsuaroi.jsp").forward(request, response);
			} else {
				request.setAttribute("user", user);
				request.getRequestDispatcher("editaUsuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Erro: " + e.getMessage() + "</div>");
			request.getRequestDispatcher("buscaUsuario.jsp").forward(request, response);
		}
	}
	
	
	@SuppressWarnings("removal")
	public void confirmar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Pegando os par�metros passados pelo formul�rio
			String idUsuario = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			

			// Instanciando um Objeto do tipo Usuario
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setIdUsuario(new Long(idUsuario));
			
			usuarioDAO pd = new usuarioDAO();
			pd.editar(usuario);
			request.getSession().setAttribute("usuario", usuario);
			request.getSession().setAttribute("login", login);	
			request.setAttribute("msg", "<div class='alert alert-success'>Dados atualizados!</div>");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<div class='alert alert-danger'>Dados nao Atualizados!</div>");
		} finally {
			request.getRequestDispatcher("buscaUsuario.jsp").forward(request, response);
		}
}

	protected void logar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		Usuario usuario = new usuarioDAO().login(login, senha);

		try {
				if (usuario != null) {					
					
					request.getSession().setAttribute("usuario", usuario);
					request.getSession().setAttribute("login", login);					
					response.sendRedirect("sessaoUsuario.jsp");
				} else {
					request.setAttribute("msg", "<div class='alert alert-danger'>Login n�o Encontrado!</div>");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "Erro" + e.getMessage());
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
	}	
	
	
	protected void sair(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Invalidando a sess�o e deslogar do sistema
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}
	
	
}
