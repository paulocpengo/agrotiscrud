package com.agrotis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record PessoaRequestDTO(
	@NotBlank(message = "Nome é obrigatório") String nome,
	@NotNull(message = "Data Inicial é obrigatória") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") ZonedDateTime dataInicial,
	@NotNull(message = "Data Final é obrigatória") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") ZonedDateTime dataFinal,
	@NotNull(message = "Propriedade é obrigatória") PropriedadeDTO infosPropriedade,
	@NotNull(message = "Laboratório é obrigatório") LaboratorioDTO laboratorio,
	String observacoes) {

	public record PropriedadeDTO(
		@NotNull(message = "ID da Propriedade é obrigatório") Long id,
		@NotBlank(message = "Nome da Propriedade é obrigatório") String nome) {}

	public record LaboratorioDTO(
		@NotNull(message = "ID do Laboratório é obrigatório") Long id,
		@NotBlank(message = "Nome do Laboratório é obrigatório") String nome) {}
}
