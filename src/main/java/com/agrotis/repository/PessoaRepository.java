package com.agrotis.repository;

import com.agrotis.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	@Query("SELECT l.id AS codigoLaboratorio, UPPER(l.nome) AS nomeLaboratorio, COUNT(p) AS quantidadePessoas " +
		"FROM Pessoa p JOIN p.laboratorio l " +
		"WHERE (:dataInicialInicio IS NULL OR p.dataInicial >= :dataInicialInicio) " +
		"AND (:dataInicialFim IS NULL OR p.dataInicial <= :dataInicialFim) " +
		"AND (:dataFinalInicio IS NULL OR p.dataFinal >= :dataFinalInicio) " +
		"AND (:dataFinalFim IS NULL OR p.dataFinal <= :dataFinalFim) " +
		"AND (:observacoes IS NULL OR UPPER(p.observacoes) LIKE %:observacoes%) " +
		"GROUP BY l.id, l.nome " +
		"HAVING COUNT(p) >= :minPessoas " +
		"ORDER BY COUNT(p) DESC, MIN(p.dataInicial) ASC")
	List<Object[]> findLaboratoriosWithFilters(
		@Param("dataInicialInicio") ZonedDateTime dataInicialInicio,
		@Param("dataInicialFim") ZonedDateTime dataInicialFim,
		@Param("dataFinalInicio") ZonedDateTime dataFinalInicio,
		@Param("dataFinalFim") ZonedDateTime dataFinalFim,
		@Param("observacoes") String observacoes,
		@Param("minPessoas") Long minPessoas);
}