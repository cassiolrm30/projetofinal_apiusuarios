package br.com.cotiinformatica.application.interfaces;
import br.com.cotiinformatica.application.dtos.RequestLoginDTO;
import br.com.cotiinformatica.application.dtos.ResponseLoginDTO;
import br.com.cotiinformatica.application.dtos.RequestCreateDTO;
import br.com.cotiinformatica.application.dtos.ResponseCreateDTO;
import br.com.cotiinformatica.application.dtos.RequestPasswordRecoverDTO;
import br.com.cotiinformatica.application.dtos.ResponsePasswordRecoverDTO;

public interface IUsuarioApp
{
	ResponseCreateDTO criarConta(RequestCreateDTO dto);
	ResponseLoginDTO autenticar(RequestLoginDTO dto);
	ResponsePasswordRecoverDTO recuperarSenha(RequestPasswordRecoverDTO dto);
	//ResponseAtualizarDadosDTO atualizarDados(AtualizarDadosDTO dto);
}