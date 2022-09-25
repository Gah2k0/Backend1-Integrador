package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.entity.Endereco;
import com.dh.clinicaodontologica.entity.Paciente;
import com.dh.clinicaodontologica.entity.dto.PacienteDto;
import com.dh.clinicaodontologica.exception.EmptyListException;
import com.dh.clinicaodontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class PacienteServiceTest {

    @Autowired
    PacienteService service;

    @Autowired
    static Endereco endereco;
    static Paciente paciente;

    @BeforeEach
    void doBefore() {
        this.paciente = new Paciente();
        this.paciente.setNome("Paciente");
        this.paciente.setSobrenome("Teste");
        this.paciente.setEndereco(this.endereco);
        this.paciente.setRg("1234567");
        this.paciente.setDataCadastro(LocalDate.now());
    }

    @Test
    public void salvarTest() throws ResourceNotFoundException {
        Paciente pacienteSalvo = service.salvar(this.paciente);
        Assertions.assertNotNull(pacienteSalvo.getId());
    }

    @Test
    void buscarTodosTest() throws ResourceNotFoundException, EmptyListException {
        Paciente pacienteSalvo = service.salvar(this.paciente);
        List<PacienteDto> pacienteDtoList = service.buscarTodos();
        Assertions.assertTrue(pacienteDtoList.size() > 0);
    }

    @Test
    void buscarPorIdTest() throws ResourceNotFoundException {
        Paciente pacienteSalvo = service.salvar(this.paciente);
        String nome = "Nome alterado";
        pacienteSalvo.setNome(nome);

        PacienteDto pacienteDto = service.buscarPorId(pacienteSalvo.getId());

        Assertions.assertEquals(nome, pacienteDto.getNome());
    }

    @Test
    void excluirTest() throws ResourceNotFoundException {
        Paciente pacienteAExcluir = service.salvar(this.paciente);
        Long id = pacienteAExcluir.getId();
        service.excluir(id);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(id));
    }

    @Test
    void alterarTest() throws ResourceNotFoundException {
        Paciente pacienteAAlterar = service.salvar(this.paciente);
        LocalDate dataCadastro = LocalDate.of(2020, 03, 19);
        pacienteAAlterar.setDataCadastro(dataCadastro);
        Paciente pacienteAlterado = service.alterar(pacienteAAlterar);
        Assertions.assertEquals(dataCadastro, pacienteAlterado.getDataCadastro());
    }

}