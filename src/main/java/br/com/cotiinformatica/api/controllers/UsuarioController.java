package br.com.cotiinformatica.api.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.com.cotiinformatica.application.dtos.RequestCreateDTO;
import br.com.cotiinformatica.application.dtos.RequestLoginDTO;
import br.com.cotiinformatica.application.dtos.RequestPasswordRecoverDTO;
import br.com.cotiinformatica.application.dtos.ResponseCreateDTO;
import br.com.cotiinformatica.application.dtos.ResponseLoginDTO;
import br.com.cotiinformatica.application.dtos.ResponsePasswordRecoverDTO;
import br.com.cotiinformatica.application.interfaces.IUsuarioApp;
import jakarta.validation.Valid;

public class UsuarioController
{
	private IUsuarioApp usuarioAppService;

	@PostMapping("/api/usuarios/login")
	public ResponseEntity<ResponseLoginDTO> post(@Valid @RequestBody RequestLoginDTO dto)
	{
		ResponseLoginDTO response = usuarioAppService.autenticar(dto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/api/usuarios/create")
	public ResponseEntity<ResponseCreateDTO> post(@Valid @RequestBody RequestCreateDTO dto)
	{
		ResponseCreateDTO response = usuarioAppService.criarConta(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/api/usuarios/password-recover")
	public ResponseEntity<ResponsePasswordRecoverDTO> post(@Valid @RequestBody RequestPasswordRecoverDTO dto)
	{
		ResponsePasswordRecoverDTO response = usuarioAppService.recuperarSenha(dto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}