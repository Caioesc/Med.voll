package med.voll.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

        @NotBlank
        String logradouro,

        @NotBlank
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{8}") //Deve ser somente 8 dígitos
        String cep,

        @NotBlank
        String cidade,

        @NotBlank
        String uf,

        //Campo opcionais, por isso estão sem validação

        String complemento,

        String numero) {
}
