package br.com.cotiinformatica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import br.com.cotiinformatica.application.dtos.RequestLoginDTO;
import br.com.cotiinformatica.application.dtos.ResponseLoginDTO;
import br.com.cotiinformatica.application.dtos.RequestCreateDTO;
import br.com.cotiinformatica.application.dtos.RequestPasswordRecoverDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjetofinalApiusuariosApplicationTests
{
	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper objectMapper;

	private static String id;
	private static String email;
	private static String senha;
	private static String accessToken;

	@Test
	@Order(1)
	public void criarContaTest() throws Exception
	{
		RequestCreateDTO dto = new RequestCreateDTO();
		Faker faker = new Faker();
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSenha("@Teste1234");
		mock.perform(post("/api/usuarios/criar-conta")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status()
				.isCreated());
		email = dto.getEmail();
		senha = dto.getSenha();
	}

	@Test
	@Order(2)
	public void autenticarTest() throws Exception
	{	
		RequestLoginDTO dto = new RequestLoginDTO();
		dto.setEmail(email);
		dto.setSenha(senha);
		MvcResult result = mock.perform(post("/api/usuarios/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status()
				.isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		ResponseLoginDTO response = objectMapper.readValue(content, ResponseLoginDTO.class);
		id = response.getId();
		accessToken = response.getAccessToken();
	}

	@Test
	@Order(4)
	public void recuperarSenhaTest() throws Exception
	{	
		RequestPasswordRecoverDTO dto = new RequestPasswordRecoverDTO();	
		dto.setEmail(email);	
		mock.perform(post("/api/usuarios/recuperar-senha")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status()
				.isOk());		
	}
}