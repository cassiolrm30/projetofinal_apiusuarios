package br.com.cotiinformatica.application.dtos;

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
public class ResponseLoginDTO
{
	private String id;
	private String nome;
	private String email;
	private String accessToken;
	private String mensagem;
}