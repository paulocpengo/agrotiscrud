package com.agrotis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotNull(message = "Data inicial é obrigatória")
	private ZonedDateTime dataInicial;

	private ZonedDateTime dataFinal;

	@NotNull(message = "Propriedade é obrigatória")
	@ManyToOne
	@JoinColumn(name = "propriedade_id")
	private Propriedade infosPropriedade;

	@NotNull(message = "Laboratório é obrigatório")
	@ManyToOne
	@JoinColumn(name = "laboratorio_id")
	private Laboratorio laboratorio;

	private String observacoes;
}
