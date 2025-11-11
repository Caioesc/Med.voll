package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull //NotBlank é apenas para String
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereco) {
}
