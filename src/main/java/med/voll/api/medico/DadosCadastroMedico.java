package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(

        @NotBlank //Verifica se não é nulo e nem vazio
        String nome,

        @NotBlank
        @Email //Verifica se está no formato de email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //Verifica se é um dígito e tem que ter de 4 a 6 dígitos
        String crm,

        @NotNull //NotBlank é apenas para campos String
        Especialidade especialidade,

        //Valid é para verificar o DTO DadosEndereco que está dentro do DTO DadosCadastroMedico
        @NotNull @Valid DadosEndereco endereco) {

}
