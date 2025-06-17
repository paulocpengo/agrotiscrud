package com.agrotis;

import com.agrotis.model.Propriedade;
import com.agrotis.repository.PropriedadeRepository;
import com.agrotis.service.PropriedadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(PropriedadeService.class)
    /**
     * Exemplo de teste sem usar mokito
     */
class PropriedadeServiceTest {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @BeforeEach
    void setUp() {
        propriedadeRepository.deleteAll();
    }

    @Test
    void listarTodas_WithEmptyDatabase_ShouldReturnEmptyList() {
        List<Propriedade> propriedades = propriedadeService.listarTodas();
        
        assertThat(propriedades).isEmpty();
    }

    @Test
    void listarTodas_WithMultiplePropriedades_ShouldReturnAllPropriedades() {
        Propriedade prop1 = new Propriedade();
        prop1.setNome("Fazenda São João");

        Propriedade prop2 = new Propriedade();
        prop2.setNome("Sítio Boa Vista");

        propriedadeRepository.save(prop1);
        propriedadeRepository.save(prop2);

        List<Propriedade> propriedades = propriedadeService.listarTodas();

        assertThat(propriedades)
            .hasSize(2)
            .extracting(Propriedade::getNome)
            .containsExactlyInAnyOrder("Fazenda São João", "Sítio Boa Vista");
    }

    @Test
    void listarTodas_ShouldPreservePropriedadeAttributes() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Fazenda Teste");
        Propriedade savedPropriedade = propriedadeRepository.save(propriedade);

        List<Propriedade> propriedades = propriedadeService.listarTodas();

        assertThat(propriedades)
            .hasSize(1)
            .first()
            .satisfies(prop -> {
                assertThat(prop.getId()).isEqualTo(savedPropriedade.getId());
                assertThat(prop.getNome()).isEqualTo("Fazenda Teste");
            });
    }
}