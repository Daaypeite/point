package br.com.pointsistemas.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.pointsistemas.dao.UserPosDAO;
import br.com.pointsistemas.model.Userposjava;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Userposjava> users;
	private Userposjava newUser;
	private Userposjava selectedUser; // Para armazenar o usuário selecionado na tabela

	// Construtor
	public UsuarioBean() {
		carregarUsuarios();
		newUser = new Userposjava();
	}

	// Getter e Setter para users
	public List<Userposjava> getUsers() {
		return users;
	}

	public void setUsers(List<Userposjava> users) {
		this.users = users;
	}

	// Getter e Setter para newUser
	public Userposjava getNewUser() {
		return newUser;
	}

	public void setNewUser(Userposjava newUser) {
		this.newUser = newUser;
	}

	// Getter e Setter para selectedUser
	public Userposjava getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Userposjava selectedUser) {
		this.selectedUser = selectedUser;
	}

	// Método para carregar a lista de usuários
	private void carregarUsuarios() {
		try {
			UserPosDAO userPosDAO = new UserPosDAO();
			users = userPosDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para salvar um novo usuário
	/*
	 * public void salvar() { try { UserPosDAO userPosDAO = new UserPosDAO();
	 * userPosDAO.salvar(newUser);
	 * 
	 * // Após salvar, recarrega a lista de usuários carregarUsuarios();
	 * 
	 * // Limpa o formulário newUser = new Userposjava(); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	public void selecionarUsuario(Userposjava user) {
		selectedUser = user;
		System.out.println(selectedUser);
	}

	public void salvar() {
		try {
			UserPosDAO userPosDAO = new UserPosDAO();

			if (newUser.getNome() == null && newUser.getEmail() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe os dados do usuário", null));
			} else {
				if (newUser.getId() == null) {
					// Se o ID for nulo, é um novo usuário
					userPosDAO.salvar(newUser);
				} else {
					// Se o ID não for nulo, é uma atualização
					userPosDAO.atualizar(newUser);
				}

				carregarUsuarios();
				newUser = new Userposjava();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para editar um usuário
	public void editarUsuario(Userposjava user) {
		if (user != null) {
			try {
				UserPosDAO userPosDAO = new UserPosDAO();
				newUser = userPosDAO.buscar(user.getId());
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar usuário", null));
			}
		}
	}

	public void atualizarUsuario(Userposjava user) {
	    try {
	        // Faça a lógica de atualização utilizando o DAO
	        UserPosDAO userPosDAO = new UserPosDAO();
	        userPosDAO.atualizar(user);

	        // Imprimir informações para debug (remova após solução)
	        System.out.println("Usuário Atualizado: " + user);

	        // Após atualizar, recarrega a lista de usuários
	        carregarUsuarios();

	        // Exibe mensagem de sucesso
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado com sucesso", null));
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Exibe mensagem de erro
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar", null));
	    }
	}


	// Método para deletar um usuário
	public void deletarUsuario(Userposjava user) {
		try {
			UserPosDAO userPosDAO = new UserPosDAO();
			userPosDAO.deletar(user.getId());

			// Após deletar, recarrega a lista de usuários
			carregarUsuarios();

			// Exibe mensagem de sucesso
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Deletado com sucesso", null));
		} catch (Exception e) {
			e.printStackTrace();
			// Exibe mensagem de erro
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar", null));
		}
	}

}
