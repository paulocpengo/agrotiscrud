package com.agrotis.controller;

import com.agrotis.model.Pessoa;
import com.agrotis.repository.LaboratorioRepository;
import com.agrotis.repository.PropriedadeRepository;
import com.agrotis.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {
	private final PessoaService pessoaService;
	private final PropriedadeRepository propriedadeRepository;
	private final LaboratorioRepository laboratorioRepository;

	public PessoaController(PessoaService pessoaService,
		PropriedadeRepository propriedadeRepository,
		LaboratorioRepository laboratorioRepository) {
		this.pessoaService = pessoaService;
		this.propriedadeRepository = propriedadeRepository;
		this.laboratorioRepository = laboratorioRepository;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(ZonedDateTime.class, new PropertyEditorSupport() {
			private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			@Override
			public void setAsText(String text) throws java.lang.IllegalArgumentException {
				if (text == null || text.isEmpty()) {
					setValue(null);
				} else {
					LocalDate localDate = LocalDate.parse(text, formatter);
					ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of("America/Sao_Paulo"));
					setValue(zonedDateTime);
				}
			}

			@Override
			public String getAsText() {
				ZonedDateTime zonedDateTime = (ZonedDateTime) getValue();
				return (zonedDateTime != null) ? zonedDateTime.format(formatter) : "";
			}
		});
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("pessoas", pessoaService.findAll());
		return "pessoa/list";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("pessoa", new Pessoa());
		model.addAttribute("propriedades", propriedadeRepository.findAll());
		model.addAttribute("laboratorios", laboratorioRepository.findAll());
		return "pessoa/create";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Pessoa pessoa, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("propriedades", propriedadeRepository.findAll());
			model.addAttribute("laboratorios", laboratorioRepository.findAll());
			return "pessoa/create";
		}
		pessoaService.save(pessoa);
		return "redirect:/pessoas";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("pessoa", pessoaService.findById(id));
		model.addAttribute("propriedades", propriedadeRepository.findAll());
		model.addAttribute("laboratorios", laboratorioRepository.findAll());
		return "pessoa/edit";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute Pessoa pessoa, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("propriedades", propriedadeRepository.findAll());
			model.addAttribute("laboratorios", laboratorioRepository.findAll());
			return "pessoa/edit";
		}
		pessoaService.update(id, pessoa);
		return "redirect:/pessoas";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		pessoaService.delete(id);
		return "redirect:/pessoas";
	}
}