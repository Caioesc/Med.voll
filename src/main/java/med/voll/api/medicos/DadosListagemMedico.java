package med.voll.api.medicos;

import med.voll.api.medico.Especialidade;
import med.voll.api.medico.Medico;


//DTO criado para devolver apenas os dados necessários para listagem
public record DadosListagemMedico(String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemMedico(Medico medico){
        this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
