package br.com.cotiinformatica.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.models.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>
{
	@Query("{email : ?0}")
	Optional<Usuario> findByEmail(String email);

	@Query("{email : ?0, senha : ?1}")
	Optional<Usuario> findByEmailAndSenha(String email, String senha);
}