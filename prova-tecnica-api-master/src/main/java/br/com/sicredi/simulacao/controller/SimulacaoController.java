package br.com.sicredi.simulacao.controller;

import br.com.sicredi.simulacao.dto.MessageDTO;
import br.com.sicredi.simulacao.dto.SimulacaoDTO;
import br.com.sicredi.simulacao.dto.ValidacaoDTO;
import br.com.sicredi.simulacao.entity.Simulacao;
import br.com.sicredi.simulacao.exception.SimulacaoException;
import br.com.sicredi.simulacao.repository.SimulacaoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


@RestController
@Api(value = "/simulacoes", tags = "Simulações")
public class SimulacaoController {

    private final SimulacaoRepository repository;

    public SimulacaoController(SimulacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/v1/simulacoes")
    @ApiOperation(value = "Retorna todas as simulações existentes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulações encontradas", response = SimulacaoDTO.class, responseContainer = "List")
    })
    List<Simulacao> getSimulacao() {
        return repository.findAll();
    }

    @GetMapping("/api/v1/simulacoes/{cpf}")
    @ApiOperation(value = "Retorna uma simulação através do CPF")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulação encontrada", response = SimulacaoDTO.class),
            @ApiResponse(code = 404, message = "Simulação não encontrada", response = MessageDTO.class)
    })
    ResponseEntity<SimulacaoDTO> one(@PathVariable String cpf) {
        return repository.findByCpf(cpf).
                map(simulacao -> ResponseEntity.ok().body(new ModelMapper().map(simulacao, SimulacaoDTO.class))).
                orElseThrow(() -> new SimulacaoException(MessageFormat.format("CPF {0} não encontrado", cpf)));
    }

    @PostMapping("/api/v1/simulacoes")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Insere uma nova simulação", code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Simulação criada com sucesso", response = SimulacaoDTO.class),
            @ApiResponse(code = 400, message = "Falta de informações", response = ValidacaoDTO.class),
            @ApiResponse(code = 409, message = "CPF já existente")
    })
    Simulacao novaSimulacao(@Valid @RequestBody SimulacaoDTO simulacao) {
        return repository.save(new ModelMapper().map(simulacao, Simulacao.class));
    }

    @PutMapping("/api/v1/simulacoes/{cpf}")
    @ApiOperation(value = "Atualiza uma simulação existente através do CPF")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulação alterada com sucesso", response = SimulacaoDTO.class),
            @ApiResponse(code = 404, message = "Simulação não encontrada", response = MessageDTO.class),
            @ApiResponse(code = 409, message = "CPF já existente")
    })
    Simulacao atualizaSimulacao(@RequestBody SimulacaoDTO simulacao, @PathVariable String cpf) {

        return update(new ModelMapper().
                map(simulacao, Simulacao.class), cpf).
                orElseThrow(() -> new SimulacaoException(MessageFormat.format("CPF {0} não encontrado", cpf)));
    }

    @DeleteMapping("/api/v1/simulacoes/{id}")
    @ApiOperation(value = "Remove uma simulação existente através do CPF")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulação removida com sucesso")
    })
    ResponseEntity<String> delete(@PathVariable Long id) {
        if (repository.findById(id).isPresent()) repository.deleteById(id);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    private Optional<Simulacao> update(Simulacao novaSimulacao, String cpf) {
        return repository.findByCpf(cpf).map(simulacao -> {
            setIfNotNull(simulacao::setId, novaSimulacao.getId());
            setIfNotNull(simulacao::setNome, novaSimulacao.getNome());
            setIfNotNull(simulacao::setCpf, novaSimulacao.getCpf());
            setIfNotNull(simulacao::setEmail, novaSimulacao.getEmail());
            setIfNotNull(simulacao::setParcelas, novaSimulacao.getParcelas());
            setIfNotNull(simulacao::setSeguro, novaSimulacao.getSeguro());

            return repository.save(simulacao);
        });
    }

    private <T> void setIfNotNull(final Consumer<T> setter, final T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
