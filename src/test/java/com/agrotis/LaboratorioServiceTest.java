package com.agrotis;

import com.agrotis.repository.PessoaRepository;
import com.agrotis.service.LaboratorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Exemplo de teste com Mockito
 */
@ExtendWith(MockitoExtension.class)
class LaboratorioServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private LaboratorioService laboratorioService;

    private ZonedDateTime dataInicialInicio;
    private ZonedDateTime dataInicialFim;
    private ZonedDateTime dataFinalInicio;
    private ZonedDateTime dataFinalFim;

    @BeforeEach
    void setUp() {
        dataInicialInicio = ZonedDateTime.now().minusDays(10);
        dataInicialFim = ZonedDateTime.now();
        dataFinalInicio = ZonedDateTime.now();
        dataFinalFim = ZonedDateTime.now().plusDays(10);
    }

    @Test
    void findLaboratoriosWithFilters_ShouldReturnFilteredResults() {
        Object[] mockRow = new Object[]{1L, "Test Lab", 5L};
        List<Object[]> mockResults = Arrays.asList(new Object[][]{mockRow});

        when(pessoaRepository.findLaboratoriosWithFilters(
            dataInicialInicio,
            dataInicialFim,
            dataFinalInicio,
            dataFinalFim,
            "TEST",
            3L
        )).thenReturn(mockResults);

        List<LaboratorioService.LaboratorioSummary> result = laboratorioService.findLaboratoriosWithFilters(
            dataInicialInicio,
            dataInicialFim,
            dataFinalInicio,
            dataFinalFim,
            "test",
            3L
        );

        assertThat(result).hasSize(1);
        LaboratorioService.LaboratorioSummary summary = result.get(0);
        assertThat(summary.codigoLaboratorio()).isEqualTo(1L);
        assertThat(summary.nomeLaboratorio()).isEqualTo("Test Lab");
        assertThat(summary.quantidadePessoas()).isEqualTo(5L);
    }

    @Test
    void findLaboratoriosWithFilters_WithNullObservacoes_ShouldReturnResults() {
        Object[] mockRow = new Object[]{1L, "Test Lab", 5L};
        List<Object[]> mockResults = Arrays.asList(new Object[][]{mockRow});

        when(pessoaRepository.findLaboratoriosWithFilters(
            dataInicialInicio,
            dataInicialFim,
            dataFinalInicio,
            dataFinalFim,
            null,
            3L
        )).thenReturn(mockResults);

        List<LaboratorioService.LaboratorioSummary> result = laboratorioService.findLaboratoriosWithFilters(
            dataInicialInicio,
            dataInicialFim,
            dataFinalInicio,
            dataFinalFim,
            null,
            3L
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).codigoLaboratorio()).isEqualTo(1L);
    }

    @Test
    void findLaboratoriosWithFilters_WithEmptyResults_ShouldReturnEmptyList() {
        when(pessoaRepository.findLaboratoriosWithFilters(
            dataInicialInicio,
            dataInicialFim,
            dataFinalInicio,
            dataFinalFim,
            "TEST",
            3L
        )).thenReturn(Arrays.asList());

        List<LaboratorioService.LaboratorioSummary> result = laboratorioService.findLaboratoriosWithFilters(
            dataInicialInicio,
            dataInicialFim,
            dataFinalInicio,
            dataFinalFim,
            "test",
            3L
        );

        assertThat(result).isEmpty();
    }
}