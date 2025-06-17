package com.agrotis.controller.api;

import com.agrotis.service.LaboratorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/laboratorios")
public class RelatorioAPIController {
	private final LaboratorioService laboratorioService;

	public RelatorioAPIController(LaboratorioService laboratorioService) {
		this.laboratorioService = laboratorioService;
	}

	@GetMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LaboratorioService.LaboratorioSummary> getLaboratoriosWithFilters(
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dataInicialInicio,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dataInicialFim,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dataFinalInicio,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dataFinalFim,
		@RequestParam(required = false) String observacoes,
		@RequestParam Long minPessoas
	) {
		return laboratorioService.findLaboratoriosWithFilters(dataInicialInicio, dataInicialFim, dataFinalInicio, dataFinalFim, observacoes, minPessoas);
	}
}