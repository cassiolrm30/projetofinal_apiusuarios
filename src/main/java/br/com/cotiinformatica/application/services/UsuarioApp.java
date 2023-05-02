package br.com.cotiinformatica.application.services;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.cotiinformatica.application.dtos.RequestLoginDTO;
import br.com.cotiinformatica.application.dtos.ResponseLoginDTO;
import br.com.cotiinformatica.application.dtos.RequestCreateDTO;
import br.com.cotiinformatica.application.dtos.ResponseCreateDTO;
import br.com.cotiinformatica.application.dtos.EmailMessageDTO;
import br.com.cotiinformatica.application.dtos.RequestPasswordRecoverDTO;
import br.com.cotiinformatica.application.dtos.ResponsePasswordRecoverDTO;
import br.com.cotiinformatica.application.interfaces.IUsuarioApp;
import br.com.cotiinformatica.domain.models.Usuario;
import br.com.cotiinformatica.domain.services.UsuarioDomain;
import br.com.cotiinformatica.infrastructure.producers.MessageProducer;

@Service
public class UsuarioApp implements IUsuarioApp
{
	@Autowired
	private UsuarioDomain usuarioDomainService;

	@Autowired
	private MessageProducer messageProducer;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public ResponseCreateDTO criarConta(RequestCreateDTO dto)
	{
		ModelMapper modelMapper = new ModelMapper();	
		Usuario usuario = modelMapper.map(dto, Usuario.class);		
		usuarioDomainService.criarConta(usuario);
		
		ResponseCreateDTO response = modelMapper.map(usuario, ResponseCreateDTO.class);
		response.setMensagem("Conta de usuário criada com sucesso.");
		
		//escrevendo a mensagem para incluir na fila
		EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
		emailMessageDTO.setTo(usuario.getEmail());
		emailMessageDTO.setSubject("Parabéns " + usuario.getNome() + ", sua conta foi criada com sucesso!");
		emailMessageDTO.setBody("Olá, sua conta de usuário foi criada com sucesso em nosso sistema!<br/>Att,<br/>API Usuários");
		try
		{
			//serializando a mensagem e enviando para a fila
			messageProducer.send(objectMapper.writeValueAsString(emailMessageDTO));
		}
		catch(JsonProcessingException ex)
		{
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseLoginDTO autenticar(RequestLoginDTO dto)
	{
		ModelMapper modelMapper = new ModelMapper();		
		Usuario usuario = usuarioDomainService.autenticar(dto.getEmail(), dto.getSenha());
		ResponseLoginDTO response = modelMapper.map(usuario, ResponseLoginDTO.class);		
		response.setMensagem("Usuário autenticado com sucesso.");	
		return response;
	}

	@Override
	public ResponsePasswordRecoverDTO recuperarSenha(RequestPasswordRecoverDTO dto)
	{
		ModelMapper modelMapper = new ModelMapper();
		Usuario usuario = usuarioDomainService.recuperarSenha(dto.getEmail());
		ResponsePasswordRecoverDTO response = modelMapper.map(usuario, ResponsePasswordRecoverDTO.class);
		response.setMensagem("Recuperação de senha realizada com sucesso.");
		EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
		emailMessageDTO.setTo(usuario.getEmail());
		emailMessageDTO.setSubject("Recuperação de senha realizada com sucesso!");
		emailMessageDTO.setBody("Olá, " + usuario.getNome() + ". Acesse o sistema com a senha: " + usuario.getNovaSenha() + "<br/>Att,<br/>API Usuários");
		try
		{
			messageProducer.send(objectMapper.writeValueAsString(emailMessageDTO));
		}
		catch(JsonProcessingException ex)
		{
			ex.printStackTrace();
		}
		return response;
	}
}