package com.agrotis.service;

import com.agrotis.model.Propriedade;
import com.agrotis.repository.PropriedadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropriedadeService {

	private final PropriedadeRepository propriedadeRepository;

	public PropriedadeService(PropriedadeRepository propriedadeRepository) {
		this.propriedadeRepository = propriedadeRepository;
	}

	public List<Propriedade> listarTodas() {
		return propriedadeRepository.findAll();
	}
}
