package br.com.pointsistemas.testes;

import java.util.List;

import org.junit.Test;

import br.com.pointsistemas.dao.UserPosDAO;
import br.com.pointsistemas.model.Userposjava;

public class TesteBancojdbc {

	@Test
	public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();

		userposjava.setNome("Paulo");
		userposjava.setEmail("novo@email.com");

		userPosDAO.salvar(userposjava);
	}

	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			List<Userposjava> list = dao.listar();

			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("==============================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			Userposjava userposjava = dao.buscar(6L);

			System.out.println(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initAtualizar() {
		try {

			UserPosDAO dao = new UserPosDAO();

			Userposjava userposjava = dao.buscar(28L);
			
			userposjava.setNome("Novo nome para o Paulo");
			
			dao.atualizar(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initDeletar() {
		
		try {
			
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(5L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testeDeleteUserfone() {
		UserPosDAO dao = new UserPosDAO();
		
		dao.deleteFones(1L);
	}
	
}
