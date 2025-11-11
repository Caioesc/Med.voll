package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional //@Valid faz o Spring se integrar com o bin validation e fazer a verificação desse DTO
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados));
    }

    @GetMapping //Não precisa do @Transactional, pois é uma operação de leitura, não irá alterar registros no banco
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort ={"nome"}) Pageable paginacao){ //Page informa, além da lista, os dados da paginação, mas para listar tudo, usar List
        return repository.findAll(paginacao).map(DadosListagemMedico::new); //Usando page não se faz necessário mais o stream() e o toList()
    }

    @PutMapping
    @Transactional //Pois faz uma alteração de registro no banco de dados
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        //O update no banco é feito automáticamente pela JPA, já que foi criada a variavel médico recebendo uma função do repository e a variável foi alterada.
    }

}

