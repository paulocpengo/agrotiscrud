package com.agrotis.controller.api;

import com.agrotis.dto.PessoaRequestDTO;
import com.agrotis.model.Laboratorio;
import com.agrotis.model.Pessoa;
import com.agrotis.model.Propriedade;
import com.agrotis.repository.LaboratorioRepository;
import com.agrotis.repository.PropriedadeRepository;
import com.agrotis.service.PessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoa")
public class PessoaAPIController {
	private final PessoaService pessoaService;
	private final PropriedadeRepository propriedadeRepository;
	private final LaboratorioRepository laboratorioRepository;

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pessoa> createPessoa(@Valid @RequestBody PessoaRequestDTO pessoaDTO) {

		Propriedade propriedade = propriedadeRepository.findById(pessoaDTO.infosPropriedade().id())
													   .orElseThrow(() -> new RuntimeException("Propriedade não encontrada: " + pessoaDTO.infosPropriedade().id()));
		Laboratorio laboratorio = laboratorioRepository.findById(pessoaDTO.laboratorio().id())
													   .orElseThrow(() -> new RuntimeException("Laboratório não encontrado: " + pessoaDTO.laboratorio().id()));

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(pessoaDTO.nome());
		pessoa.setDataInicial(pessoaDTO.dataInicial());
		pessoa.setDataFinal(pessoaDTO.dataFinal());
		pessoa.setPropriedade(propriedade);
		pessoa.setLaboratorio(laboratorio);
		pessoa.setObservacoes(pessoaDTO.observacoes());

		Pessoa savedPessoa = pessoaService.save(pessoa);
		return ResponseEntity.ok(savedPessoa);
	}
}
