package com.agrotis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Propriedade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome da propriedade é obrigatório")
	@Size(max = 100, message = "O nome da propriedade não pode exceder 100 caracteres")
	@Column(length = 100)
	private String nome;
}
