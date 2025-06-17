package com.agrotis.service;

import com.agrotis.exception.ResourceNotFoundException;
import com.agrotis.model.Pessoa;
import com.agrotis.repository.LaboratorioRepository;
import com.agrotis.repository.PessoaRepository;
import com.agrotis.repository.PropriedadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PessoaService {
	private final PessoaRepository pessoaRepository;
	private final PropriedadeRepository propriedadeRepository;
	private final LaboratorioRepository laboratorioRepository;

	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	public Pessoa findById(Long id) {
		return pessoaRepository.findById(id)
							   .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + id));
	}

	public Pessoa save(Pessoa pessoa) {
		validateReferences(pessoa);
		return pessoaRepository.save(pessoa);
	}

	public Pessoa update(Long id, Pessoa pessoa) {
		Pessoa existing = findById(id);
		existing.setNome(pessoa.getNome());
		existing.setDataInicial(pessoa.getDataInicial());
		existing.setDataFinal(pessoa.getDataFinal());
		existing.setInfosPropriedade(pessoa.getInfosPropriedade());
		existing.setLaboratorio(pessoa.getLaboratorio());
		existing.setObservacoes(pessoa.getObservacoes());
		validateReferences(pessoa);
		return pessoaRepository.save(existing);
	}

	public void delete(Long id) {
		Pessoa pessoa = findById(id);
		pessoaRepository.delete(pessoa);
	}

	private void validateReferences(Pessoa pessoa) {
		if (pessoa.getInfosPropriedade() != null && pessoa.getInfosPropriedade().getId() != null) {
			propriedadeRepository.findById(pessoa.getInfosPropriedade().getId())
								 .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com ID: " + pessoa.getInfosPropriedade().getId()));
		}
		if (pessoa.getLaboratorio() != null && pessoa.getLaboratorio().getId() != null) {
			laboratorioRepository.findById(pessoa.getLaboratorio().getId())
								 .orElseThrow(() -> new ResourceNotFoundException("Laboratório não encontrada com ID: " + pessoa.getLaboratorio().getId()));
		}
	}
}