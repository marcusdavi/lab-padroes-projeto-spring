package one.digitalinnovation.gof.service;

import javassist.NotFoundException;
import one.digitalinnovation.gof.model.Cliente;

public interface ClienteService {

	Iterable<Cliente> buscarTodos();

	Cliente buscarPorId(Long id) throws NotFoundException;

	void inserir(Cliente cliente);

	void atualizar(Long id, Cliente cliente) throws NotFoundException;

	void deletar(Long id) throws NotFoundException;

}
