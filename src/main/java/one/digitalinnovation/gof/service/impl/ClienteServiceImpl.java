package one.digitalinnovation.gof.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	public ClienteRepository clienteRepository;

	@Autowired
	public EnderecoRepository enderecoRepository;

	@Autowired
	public ViaCepService viaCepService;

	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) throws NotFoundException {
		return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);

	}

	@Override
	public void atualizar(Long id, Cliente cliente) throws NotFoundException {
		Cliente clienteBd = buscarPorId(id);
		salvarClienteComCep(clienteBd);
	}

	@Override
	public void deletar(Long id) throws NotFoundException {
		buscarPorId(id);
		clienteRepository.deleteById(id);

	}

	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}
}
