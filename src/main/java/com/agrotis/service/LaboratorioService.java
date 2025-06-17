package com.agrotis.service;

import com.agrotis.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaboratorioService {
	private final PessoaRepository pessoaRepository;

	public LaboratorioService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	public List<LaboratorioSummary> findLaboratoriosWithFilters(
		ZonedDateTime dataInicialInicio,
		ZonedDateTime dataInicialFim,
		ZonedDateTime dataFinalInicio,
		ZonedDateTime dataFinalFim,
		String observacoes,
		Long minPessoas
	) {
		String observacoesUpper = observacoes != null ? observacoes.toUpperCase() : null;
		List<Object[]> results = pessoaRepository.findLaboratoriosWithFilters(
			dataInicialInicio, dataInicialFim, dataFinalInicio, dataFinalFim, observacoesUpper, minPessoas);
		return results.stream()
					  .map(row -> new LaboratorioSummary(
						  ((Number) row[0]).longValue(),
						  (String) row[1],
						  ((Number) row[2]).longValue()
					  ))
					  .collect(Collectors.toList());
	}

	public record LaboratorioSummary(Long codigoLaboratorio, String nomeLaboratorio, Long quantidadePessoas) {
	}
}