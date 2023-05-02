package br.com.cotiinformatica.domain.models;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "usuarios")
public class Usuario
{
	@Id
	private String id;

	@Indexed(unique = true)
	private String email;

	private String nome;
	private String senha;
	private String telefone;
	private Instant dataHoraCriacao;

	@Transient
	private String accessToken;

	@Transient
	private String novaSenha;
}