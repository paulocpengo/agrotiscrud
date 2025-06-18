package com.agrotis.controller;

import com.agrotis.model.Laboratorio;
import com.agrotis.repository.LaboratorioRepository;
import com.agrotis.repository.PessoaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/laboratorios")
public class LaboratorioController {
	private final LaboratorioRepository laboratorioRepository;
	private final PessoaRepository pessoaRepository;

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
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Laboratorio laboratorio = laboratorioRepository.findById(id)
													   .orElseThrow(() -> new RuntimeException("Laboratório não encontrado"));

		boolean isInUse = pessoaRepository.findAll().stream()
										  .anyMatch(pessoa -> pessoa.getLaboratorio() != null && pessoa.getLaboratorio().getId().equals(id));

		if (isInUse) {
			redirectAttributes.addFlashAttribute("errorMessage", "Não é possível excluir o laboratório, pois ele está sendo usado.");
			return "redirect:/laboratorios";
		}

		laboratorioRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Laboratório excluído com sucesso.");
		return "redirect:/laboratorios";
	}
}