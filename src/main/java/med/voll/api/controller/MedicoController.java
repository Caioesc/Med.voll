package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional //@Valid faz o Spring se integrar com o bin validation e fazer a verificação desse DTO
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){

        var medico = new Medico(dados);

        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhadamentoMedico(medico));
    }

    @GetMapping //Não precisa do @Transactional, pois é uma operação de leitura, não irá alterar registros no banco
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort ={"nome"}) Pageable paginacao){ //Page informa, além da lista, os dados da paginação, mas para listar tudo, usar List
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //Usando page não se faz necessário mais o stream() e o toList()
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional //Pois faz uma alteração de registro no banco de dados
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        //O update no banco é feito automáticamente pela JPA, já que foi criada a variavel médico recebendo uma função do repository e a variável foi alterada.
        return ResponseEntity.ok(new DadosDetalhadamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){ //@PathVariable "avisa" ao Spring que o parâmetro passado se refere ao path, a url, do @DeleteMapping
        //repository.deleteById(id); ---> Essa linha faz a exclusão física do dado no banco, mas para essa aplicação, quero apenas fazer uma exclusão lógica, deixar o médico como inativo.
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhadamentoMedico(medico));
    }

}

