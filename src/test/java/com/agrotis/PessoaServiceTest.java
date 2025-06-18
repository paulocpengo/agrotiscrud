package com.agrotis;

import com.agrotis.exception.ResourceNotFoundException;
import com.agrotis.model.Laboratorio;
import com.agrotis.model.Pessoa;
import com.agrotis.model.Propriedade;
import com.agrotis.repository.LaboratorioRepository;
import com.agrotis.repository.PessoaRepository;
import com.agrotis.repository.PropriedadeRepository;
import com.agrotis.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Exemplo de teste com Mockito
 */
@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa pessoa;
    private Propriedade propriedade;
    private Laboratorio laboratorio;

    @BeforeEach
    void setUp() {
        propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Test Propriedade");

        laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Test Laboratorio");

        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Test Person");
        pessoa.setDataInicial(ZonedDateTime.now());
        pessoa.setDataFinal(ZonedDateTime.now().plusDays(1));
        pessoa.setPropriedade(propriedade);
        pessoa.setLaboratorio(laboratorio);
        pessoa.setObservacoes("Test observações");
    }

    @Test
    void findAll_ShouldReturnAllPessoas() {
        when(pessoaRepository.findAll()).thenReturn(Arrays.asList(pessoa));

        List<Pessoa> result = pessoaService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(pessoa);
    }

    @Test
    void findById_WhenExists_ShouldReturnPessoa() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.findById(1L);

        assertThat(result).isEqualTo(pessoa);
    }

    @Test
    void findById_WhenNotExists_ShouldThrowException() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.findById(1L));
    }

    @Test
    void save_WithValidReferences_ShouldSavePessoa() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa result = pessoaService.save(pessoa);

        assertThat(result).isEqualTo(pessoa);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void save_WithInvalidPropriedade_ShouldThrowException() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.save(pessoa));
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void save_WithInvalidLaboratorio_ShouldThrowException() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.save(pessoa));
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void update_WithValidData_ShouldUpdatePessoa() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa updated = pessoaService.update(1L, pessoa);

        assertThat(updated).isEqualTo(pessoa);
        verify(pessoaRepository).save(any(Pessoa.class));
    }

    @Test
    void delete_WhenExists_ShouldDeletePessoa() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        pessoaService.delete(1L);

        verify(pessoaRepository).delete(pessoa);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowException() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.delete(1L));
        verify(pessoaRepository, never()).delete(any());
    }
}