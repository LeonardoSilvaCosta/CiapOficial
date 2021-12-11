package com.br.ciapoficial.validation;

import android.os.Build;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;

import com.br.ciapoficial.helper.DellayAction;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Vinculo;
import com.br.ciapoficial.model.in_servico.CondicaoLaboral;
import com.br.ciapoficial.model.in_servico.DemandaEspecifica;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

public class FieldValidator {

    public static boolean isFieldEmptyOrNull(TextInputEditText campoString, String nomeDoCampo)
    {
        String valorDoCampo = Objects.requireNonNull(campoString.getText()).toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoString.setError("O campo " + nomeDoCampo + " é obrigatório.");
            campoString.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoString);
            return false;
        }else { campoString.setError(null);return true; }
    }

    public static boolean isListEmptyOrNull(AutoCompleteTextView campo,
                                            List<?> lista, String nomeDoCampo) {
        if (lista.isEmpty()) {
            campo.setError("Selecione ao menos uma opção de " + nomeDoCampo + ".");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
            return false;
        } else {
            campo.setError(null);
            return true;
        }
    }

    public static boolean validarRadioGroup(RadioGroup radioGroup, RadioButton rbtnDefault, String nomeDoCampo) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            rbtnDefault.setError("Selecione uma opção em " + nomeDoCampo + ".");
            rbtnDefault.requestFocus();
            rbtnDefault.requestFocusFromTouch();
            DellayAction.clearErrorAfter2Seconds(rbtnDefault);
            return false;
        } else { rbtnDefault.setError(null); return true; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validarData(TextInputEditText campoData, String nomeDoCampo)
    {
        String valorDoCampo = Objects.requireNonNull(campoData.getText()).toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoData.setError("O campo " + nomeDoCampo + " é obrigatório.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else if (!DateValidator.isDateValid(valorDoCampo)) {
            campoData.setError("Digite uma " + nomeDoCampo + " válida.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else{ campoData.setError(null); return true; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validarDataDoServico(AutoCompleteTextView campoData)
    {
        String valorDoCampo = campoData.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoData.setError("O campo DATA DO SERVIÇO é obrigatório.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else if (!DateValidator.isDateValid(valorDoCampo)) {
            campoData.setError("Digite uma DATA DE SERVIÇO válida.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else{ campoData.setError(null); return true; }
    }

    public static boolean validarCpf(TextInputEditText campoCpf)
    {
        String valorDoCampo = Objects.requireNonNull(campoCpf.getText()).toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoCpf.setError("O campo CPF é obrigatório.");
            return false;
        }else if(!CPFValidator.validarCPF(valorDoCampo)) {
            campoCpf.setError("Digite um CPF válido.");
            return false;
        }else{ campoCpf.setError(null); return true; }
    }

    public static boolean validarTelefones(TextInputEditText autoCompleteTextViewTelefones,
                                           List<Telefone> telefones)
    {
        if(telefones.isEmpty()) {
            autoCompleteTextViewTelefones.setError("É necessário inserir ao menos um telefone.");
            autoCompleteTextViewTelefones.requestFocus();
            DellayAction.clearErrorAfter2Seconds(autoCompleteTextViewTelefones);
            return false;
        }else { autoCompleteTextViewTelefones.setError(null); return true; }
    }

    public static boolean validarCondicaoLaboral(AutoCompleteTextView campoCondicaoLaboralSelecionada, List<CondicaoLaboral> listaDeCondicoesLaborais,
                                                 List<DemandaEspecifica> listaDeDemandasEspecificasSelecionadasNaoValidadas)
    {
        String valorDoCampo = campoCondicaoLaboralSelecionada.getText().toString().trim();
        boolean containsObitoMilitar = listaDeDemandasEspecificasSelecionadasNaoValidadas.toString().contains("Óbito(militar)");

        if(containsObitoMilitar) {
            if(valorDoCampo.isEmpty())
            {
                campoCondicaoLaboralSelecionada.setError("O campo CONDIÇÃO LABORAL é obrigatório.");
                campoCondicaoLaboralSelecionada.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campoCondicaoLaboralSelecionada);
                return false;
            }else
            {
                if(listaDeCondicoesLaborais != null) {
                    boolean valueExists = false;
                    for(CondicaoLaboral condicaoLaboralSelecionada : listaDeCondicoesLaborais)
                    {
                        if(condicaoLaboralSelecionada.toString().equals(valorDoCampo))
                        { valueExists = true; break; }
                    }

                    if (valueExists)
                    {
                        campoCondicaoLaboralSelecionada.setError(null);
                        return true;
                    }else
                    {
                        campoCondicaoLaboralSelecionada.setError("Insira uma opção de CONDIÇÃO LABORAL válida.");
                        campoCondicaoLaboralSelecionada.requestFocus();
                        DellayAction.clearErrorAfter2Seconds(campoCondicaoLaboralSelecionada);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean validarSenhaUpdate(TextInputEditText campoSenha, boolean desejaAtualizarSenha) {

        String senha = Objects.requireNonNull(campoSenha.getText()).toString();

        if(desejaAtualizarSenha) {
            if (!campoSenha.getText().toString().isEmpty()) {
                if (senha.length() < 6) {
                    campoSenha.setError("Sua SENHA deve conter pelo menos 6 caracteres.");
                    campoSenha.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoSenha);
                    return false;
                } else {
                    campoSenha.setError(null);
                    return true;
                }
            }else{
                campoSenha.setError("O campo SENHA não pode ficar em branco.");
                campoSenha.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campoSenha);
                return false;
            }
        }
        campoSenha.setError(null);
        return true;
    }

    public static boolean validarConfirmacaoDeSenha(TextInputEditText campoSenha, String senhaAtual) {
        String valorDoCampo = Objects.requireNonNull(campoSenha.getText()).toString().trim();

        if(valorDoCampo.isEmpty()) {
            campoSenha.setError("O campo SENHA é obrigatório.");
            campoSenha.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoSenha);
            return false;
        }else {
            if(!BCrypt.checkpw(valorDoCampo, senhaAtual)) {
                campoSenha.setError("A senha informada não conferece com a senha cadastrada.");
                campoSenha.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campoSenha);
                return false;
            }else{
                campoSenha.setError(null);
                return true;
            }
        }
    }

    public static boolean validarAutoCompleteTextView(EditText campoRecebido, List<?> listaRecebida,
                                                      String mensagemParaCampoVazio, String mensagemInvalida)
    {
        String valorDoCampo = campoRecebido.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoRecebido.setError(mensagemParaCampoVazio);
            campoRecebido.requestFocus();
            DellayAction.clearErrorAfter2Seconds((AutoCompleteTextView) campoRecebido);
            return false;
        }else
        {
            if(listaRecebida != null)
            {
                boolean valueExists = false;
                for(Object estadoSelecionado : listaRecebida)
                {
                    if(estadoSelecionado.toString().equals(valorDoCampo))
                    {
                        valueExists = true;
                        break;
                    }
                }

                if(valueExists)
                { campoRecebido.setError(null); return true; }
                else
                {
                    campoRecebido.setError(mensagemInvalida);
                    campoRecebido.requestFocus();
                    DellayAction.clearErrorAfter2Seconds((AutoCompleteTextView) campoRecebido);
                    return false;
                }
            }
        }
        return true;

    }

    public static boolean validarEmail(TextInputEditText email)
    {
        String valorDoCampo = Objects.requireNonNull(email.getText()).toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (valorDoCampo.isEmpty()) {
            email.setError("O campo EMAIL é obrigatório.");
            email.requestFocus();
            DellayAction.clearErrorAfter2Seconds(email);
            return false;
        } else if (!valorDoCampo.matches(checkEmail)) {
            email.setError("Digite um EMAIL válido.");
            email.requestFocus();
            DellayAction.clearErrorAfter2Seconds(email);
            return false;
        } else { email.setError(null); return true; }
    }

    public static boolean validarCep (TextInputEditText cep)
    {
        String valorDoCampo = Objects.requireNonNull(cep.getText()).toString().trim();
        String checkCep = "[0-9]{5}-[0-9]{3}";
        if(valorDoCampo.isEmpty()) {
            cep.setError("O campo CEP é obrigatório.");
            cep.requestFocus();
            DellayAction.clearErrorAfter2Seconds(cep);
            return false;
        } else if (!valorDoCampo.matches(checkCep)) {
            cep.setError("Digite um CEP válido.");
            cep.requestFocus();
            DellayAction.clearErrorAfter2Seconds(cep);
            return false;
        } else {
            cep.setError(null);return true; }
    }

//
//            boolean achouNumero = false;
//            boolean achouMaiuscula = false;
//            boolean achouMinuscula = false;
//            boolean achouSimbolo = false;
//            for (char c : senha.toCharArray()) {
//                if (c >= '0' && c <= '9') {
//                    achouNumero = true;
//                } else if (c >= 'A' && c <= 'Z') {
//                    achouMaiuscula = true;
//                } else if (c >= 'a' && c <= 'z') {
//                    achouMinuscula = true;
//                } else {
//                    achouSimbolo = true;
//                }
//            }
//
//            return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;

    public static boolean validarTitular(AutoCompleteTextView campoTitular,
                                         List<Usuario> listaDeTitulares, RadioButton rbtnDependente)
    {

        if(rbtnDependente.isChecked()) {

            String valorDoCampo = campoTitular.getText().toString().trim();

            if(valorDoCampo.isEmpty())
            {
                campoTitular.setError("O campo TITULAR é obrigatório.");
                campoTitular.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campoTitular);
                return false;
            }else
            {
                if(listaDeTitulares != null) {
                    boolean valueExists = false;
                    for(Usuario titularSelecionado : listaDeTitulares)
                    {
                        if(titularSelecionado.toString().equals(valorDoCampo))
                        { valueExists = true; }
                    }

                    if(valueExists) {
                        campoTitular.setError(null);
                        return true;
                    }
                    else
                    {
                        campoTitular.setError("Insira uma opção de Titular válida.");
                        campoTitular.requestFocus();
                        DellayAction.clearErrorAfter2Seconds(campoTitular);
                        return false;
                    }
                }
            }

        }

        return true;
    }

    public static boolean validarVinculo(AutoCompleteTextView campoVinculo,
                                         List<Vinculo> listaDeVinculos, RadioButton rbtnDependente)
    {

        if(rbtnDependente.isChecked()) {

            String valorDoCampo = campoVinculo.getText().toString().trim();

            if(valorDoCampo.isEmpty())
            {
                campoVinculo.setError("O campo VÍNCULO é obrigatório.");
                campoVinculo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campoVinculo);
                return false;
            }else
            {
                if(listaDeVinculos != null) {
                    boolean valueExists = false;
                    for(Vinculo vinculoSelecionado : listaDeVinculos)
                    {
                        if(vinculoSelecionado.toString().equals(valorDoCampo))
                        { valueExists = true; }
                    }

                    if(valueExists) {
                        campoVinculo.setError(null);
                        return true;
                    }
                    else
                    {
                        campoVinculo.setError("Insira uma opção de VÍNCULO válida.");
                        campoVinculo.requestFocus();
                        DellayAction.clearErrorAfter2Seconds(campoVinculo);
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
