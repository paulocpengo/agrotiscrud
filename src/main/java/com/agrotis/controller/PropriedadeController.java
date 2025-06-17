package com.agrotis.controller;

import com.agrotis.model.Propriedade;
import com.agrotis.repository.PropriedadeRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/propriedades")
public class PropriedadeController {
	private final PropriedadeRepository propriedadeRepository;

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("propriedade", new Propriedade());
		return "propriedade/create";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Propriedade propriedade, BindingResult result) {
		if (result.hasErrors()) {
			return "propriedade/create";
		}
		propriedadeRepository.save(propriedade);
		return "redirect:/propriedades";
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("propriedades", propriedadeRepository.findAll());
		return "propriedade/list";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("propriedade", propriedadeRepository.findById(id)
															   .orElseThrow(() -> new RuntimeException("Propriedade não encontrada")));
		return "propriedade/view";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("propriedade", propriedadeRepository.findById(id)
															   .orElseThrow(() -> new RuntimeException("Propriedade não encontrada")));
		return "propriedade/edit";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute Propriedade propriedade, BindingResult result) {
		if (result.hasErrors()) {
			return "propriedade/edit";
		}
		propriedade.setId(id);
		propriedadeRepository.save(propriedade);
		return "redirect:/propriedades";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		propriedadeRepository.deleteById(id);
		return "redirect:/propriedades";
	}
}