package one.digitalinnovation.gof.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.service.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<Iterable<Cliente>> buscarTodos() {
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(clienteService.buscarPorId(id));
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping()
	public ResponseEntity<Cliente> inserir(@RequestBody @Valid Cliente cliente) {
		clienteService.inserir(cliente);
		return ResponseEntity.ok(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {

		try {
			clienteService.atualizar(id, cliente);
			return ResponseEntity.ok(cliente);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> deletar(@PathVariable Long id) {
		try {
			clienteService.deletar(id);
			return ResponseEntity.ok().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
