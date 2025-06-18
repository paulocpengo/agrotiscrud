package com.agrotis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@Size(max = 100, message = "O nome não pode exceder 100 caracteres")
	@Column(length = 100)
	private String nome;

	@NotNull(message = "Data inicial é obrigatória")
	private ZonedDateTime dataInicial;

	@NotNull(message = "Data final é obrigatória")
	private ZonedDateTime dataFinal;

	@NotNull(message = "Propriedade é obrigatória")
	@ManyToOne
	@JoinColumn(name = "propriedade_id")
	private Propriedade propriedade;

	@NotNull(message = "Laboratório é obrigatório")
	@ManyToOne
	@JoinColumn(name = "laboratorio_id")
	private Laboratorio laboratorio;

	@Size(max = 100, message = "A observação não pode exceder 100 caracteres")
	@Column(length = 100)
	private String observacoes;
}
