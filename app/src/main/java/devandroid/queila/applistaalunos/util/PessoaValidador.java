package devandroid.queila.applistaalunos.util;

import devandroid.queila.applistaalunos.model.Aluno;

// PessoaValidator.java
    public class PessoaValidador {

        public static boolean validarCamposObrigatorios(Aluno pessoa) {
            return !(pessoa.getPrimeiroNome().isEmpty() ||
                    pessoa.getSobrenome().isEmpty() ||
                    pessoa.getCurso().isEmpty() ||
                    pessoa.getTelefone().isEmpty());
        }

        public static boolean telefoneValido(String telefoneFormatado) {
            String limpo = TelefoneMascara.limpar(telefoneFormatado);
            return limpo.matches("\\d{10,11}");
        }
    }

