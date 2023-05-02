package br.com.cotiinformatica.domain.services;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;
import br.com.cotiinformatica.domain.interfaces.IUsuarioDomain;
import br.com.cotiinformatica.domain.models.Usuario;
import br.com.cotiinformatica.infrastructure.components.MD5Component;
import br.com.cotiinformatica.infrastructure.repositories.UsuarioRepository;
import br.com.cotiinformatica.infrastructure.security.TokenCreator;

@Service
public class UsuarioDomain implements IUsuarioDomain
{
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MD5Component md5Component;
	
	@Autowired
	private TokenCreator tokenCreator;

	@Override
	public void criarConta(Usuario usuario)
	{
		Optional<Usuario> optional = usuarioRepository.findByEmail(usuario.getEmail());
		if (optional.isPresent())
			throw new IllegalArgumentException("O e-mail informado já está cadastrado.");
		usuario.setSenha(md5Component.encrypt(usuario.getSenha()));
		usuario.setDataHoraCriacao(Instant.now());
		usuarioRepository.save(usuario);
	}

	@Override
	public Usuario autenticar(String email, String senha)
	{
		Optional<Usuario> optional = usuarioRepository.findByEmailAndSenha(email, md5Component.encrypt(senha));
		if (optional.isEmpty())
			throw new IllegalArgumentException("Acesso negado. Usuário não encontrado.");
		Usuario usuario = optional.get();
		usuario.setAccessToken(tokenCreator.generateToken(usuario.getEmail()));
		return usuario;
	}

	@Override
	public Usuario recuperarSenha(String email)
	{
		Optional<Usuario> optional = usuarioRepository.findByEmail(email);
		if (optional.isEmpty())
			throw new IllegalArgumentException("Usuário inválido. Verifique o e-mail informado.");
		Usuario usuario = optional.get();
		Faker faker = new Faker();
		usuario.setNovaSenha(faker.internet().password(8, 10, true, true, true));
		usuario.setSenha(md5Component.encrypt(usuario.getNovaSenha()));
		usuarioRepository.save(usuario);
		return usuario;
	}
}