package com.agrotis.controller;

import com.agrotis.model.Laboratorio;
import com.agrotis.repository.LaboratorioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/laboratorios")
public class LaboratorioController {
	private final LaboratorioRepository laboratorioRepository;

	public LaboratorioController(LaboratorioRepository laboratorioRepository) {
		this.laboratorioRepository = laboratorioRepository;
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("laboratorio", new Laboratorio());
		return "laboratorio/create";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Laboratorio laboratorio, BindingResult result) {
		if (result.hasErrors()) {
			return "laboratorio/create";
		}
		laboratorioRepository.save(laboratorio);
		return "redirect:/laboratorios";
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("laboratorios", laboratorioRepository.findAll());
		return "laboratorio/list";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("laboratorio", laboratorioRepository.findById(id)
															   .orElseThrow(() -> new RuntimeException("Laboratório não encontrado")));
		return "laboratorio/view";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("laboratorio", laboratorioRepository.findById(id)
															   .orElseThrow(() -> new RuntimeException("Laboratório não encontrado")));
		return "laboratorio/edit";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute Laboratorio laboratorio, BindingResult result) {
		if (result.hasErrors()) {
			return "laboratorio/edit";
		}
		laboratorio.setId(id);
		laboratorioRepository.save(laboratorio);
		return "redirect:/laboratorios";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		laboratorioRepository.deleteById(id);
		return "redirect:/laboratorios";
	}
}