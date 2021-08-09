package com.br.ciapoficial.helper;

import android.os.Build;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;

import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Especialidade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.FuncaoAdministrativa;
import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.SituacaoFuncional;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Unidade;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Vinculo;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class FieldValidator {

    public static boolean validarNomeCompleto(TextInputEditText campoNomeCompleto)
    {
        String valorDoCampo = campoNomeCompleto.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoNomeCompleto.setError("O campo NOME COMPLETO é obrigatório.");
            campoNomeCompleto.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoNomeCompleto);
            return false;
        }else { campoNomeCompleto.setError(null);return true; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validarDataDeNascimento(TextInputEditText campoData)
    {
        String valorDoCampo = campoData.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoData.setError("O campo DATA DE NASCIMENTO é obrigatório.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else if (!DateValidator.isDateValid(valorDoCampo)) {
            campoData.setError("Digite uma DATA válida.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else{ campoData.setError(null); return true; }
    }

    public static boolean validarCpf(TextInputEditText cpf)
    {
        String valorDoCampo = cpf.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            cpf.setError("O campo CPF é obrigatório.");
            return false;
        }else if(!CPFValidator.validarCPF(valorDoCampo)) {
            cpf.setError("CPF Inválido.");
            return false;
        }else{ cpf.setError(null); return true; }
    }

    public static boolean validarSexo(RadioGroup radioGroupSexo, RadioButton rbtnMasculino) {
        if (radioGroupSexo.getCheckedRadioButtonId() == -1) {
            rbtnMasculino.setError("Selecione uma opção em SEXO");
            rbtnMasculino.requestFocus();
            rbtnMasculino.requestFocusFromTouch();
            DellayAction.clearErrorAfter2Seconds(rbtnMasculino);
            return false;
        } else { rbtnMasculino.setError(null); return true; }
    }

    public static boolean validarUf(AutoCompleteTextView campoUf, List<Estado> listaDeEstados)
    {
        String valorDoCampo = campoUf.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoUf.setError("O campo UF é obrigatório.");
            campoUf.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoUf);
            return false;
        }else
        {
            if(listaDeEstados != null)
            {
                boolean valueExists = false;
                for(Estado estadoSelecionado : listaDeEstados)
                {
                    if(estadoSelecionado.toString().equals(valorDoCampo))
                    {
                        valueExists = true;
                        break;
                    }
                }

                if(valueExists)
                { campoUf.setError(null); return true; }
                else
                {
                    campoUf.setError("Insira uma opção de UF válida.");
                    campoUf.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoUf);
                    return false;
                }
            }
        }
        return true;

    }

    public static boolean validarCidade(AutoCompleteTextView campoCidade, List<Cidade> listaDeCidades)
    {
        String valorDoCampo = campoCidade.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoCidade.setError("O campo CIDADE é obrigatório.");
            campoCidade.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoCidade);
            return false;
        }else
        {
            if(listaDeCidades != null) {
                boolean valueExists = false;
                for(Cidade cidadeSelecionada : listaDeCidades)
                {
                    if(cidadeSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if(valueExists)
                {
                    campoCidade.setError(null);
                    return true;
                }else
                {
                    campoCidade.setError("Insira uma opção de CIDADE válida.");
                    campoCidade.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoCidade);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarEstadoCivil(AutoCompleteTextView campoEstadoCivil, List<EstadoCivil> listaDeEstadosCivis)
    {
        String valorDoCampo = campoEstadoCivil.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoEstadoCivil.setError("O campo ESTADO CIVIL é obrigatório.");
            campoEstadoCivil.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoEstadoCivil);
            return false;
        }else
        {
            if(listaDeEstadosCivis != null) {
                boolean valueExists = false;
                for(EstadoCivil estadoCivilSelecionado : listaDeEstadosCivis)
                {
                    if(estadoCivilSelecionado.toString().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoEstadoCivil.setError(null);
                    return true;
                }else
                {
                    campoEstadoCivil.setError("Insira uma opção de ESTADO CIVIL válida.");
                    campoEstadoCivil.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoEstadoCivil);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarNumeroDeFilhos(TextInputEditText numeroDeFilhos)
    {
        String valorDoCampo = numeroDeFilhos.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            numeroDeFilhos.setError("O campo NÚMERO DE FILHOS é obrigatório.");
            numeroDeFilhos.requestFocus();
            DellayAction.clearErrorAfter2Seconds(numeroDeFilhos);
            return false;
        }else { numeroDeFilhos.setError(null);return true; }
    }

    public static boolean validarEscolaridade(AutoCompleteTextView campoEscolaridade,
                                              List<Escolaridade> listaDeEscolaridades)
    {
        String valorDoCampo = campoEscolaridade.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoEscolaridade.setError("O campo ESCOLARIDADE é obrigatório.");
            campoEscolaridade.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoEscolaridade);
            return false;
        }else
        {
            if(listaDeEscolaridades != null) {
                boolean valueExists = false;
                for(Escolaridade escolaridadeSelecionada : listaDeEscolaridades)
                {
                    if(escolaridadeSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoEscolaridade.setError(null);
                    return true;
                }
                else
                {
                    campoEscolaridade.setError("Insira uma opção de ESCOLARIDADE válida.");
                    campoEscolaridade.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoEscolaridade);
                    return false;
                }
            }
        }
        return true;
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

    public static boolean validarEmail(TextInputEditText email)
    {
        String valorDoCampo = email.getText().toString().trim();
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
        String valorDoCampo = cep.getText().toString().trim();
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

    public static boolean validarBairro (TextInputEditText bairro)
    {
        String valorDoCampo = bairro.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            bairro.setError("O campo BAIRRO é obrigatório.");
            bairro.requestFocus();
            DellayAction.clearErrorAfter2Seconds(bairro);
            return false;
        }else { bairro.setError(null);return true; }
    }

    public static boolean validarLogradouro (TextInputEditText logradouro)
    {
        String valorDoCampo = logradouro.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            logradouro.setError("O campo LOGRADOURO é obrigatório.");
            logradouro.requestFocus();
            DellayAction.clearErrorAfter2Seconds(logradouro);
            return false;
        }else { logradouro.setError(null);return true; }
    }

    public static boolean validarNumero (TextInputEditText numero)
    {
        String valorDoCampo = numero.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            numero.setError("O campo NÚMERO é obrigatório.");
            numero.requestFocus();
            DellayAction.clearErrorAfter2Seconds(numero);
            return false;
        }else { numero.setError(null);return true; }
    }

    public static boolean validarPostoGradCat(AutoCompleteTextView campoPostoGradCat,
                                              List<PostoGradCat> listaDePostoGradCats)
    {
        String valorDoCampo = campoPostoGradCat.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoPostoGradCat.setError("O campo POSTO/GRADUAÇÃO/CATEGORIA é obrigatório.");
            campoPostoGradCat.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoPostoGradCat);
            return false;
        }else
        {
            if(listaDePostoGradCats != null) {
                boolean valueExists = false;
                for(PostoGradCat postoGradCatSelecionada : listaDePostoGradCats)
                {
                    if(postoGradCatSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoPostoGradCat.setError(null);
                    return true;
                }
                else
                {
                    campoPostoGradCat.setError("Insira uma opção de POSTO/GRADUAÇÃO/CATEGORIA válida.");
                    campoPostoGradCat.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoPostoGradCat);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarQuadro(AutoCompleteTextView campoQuadro,
                                        List<Quadro> listaDeQuadros)
    {
        String valorDoCampo = campoQuadro.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoQuadro.setError("O campo QUADRO é obrigatório.");
            campoQuadro.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoQuadro);
            return false;
        }else
        {
            if(listaDeQuadros != null) {
                boolean valueExists = false;
                for(Quadro quadroSelecionado : listaDeQuadros)
                {
                    if(quadroSelecionado.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoQuadro.setError(null);
                    return true;
                }
                else
                {
                    campoQuadro.setError("Insira uma opção de QUADRO válida.");
                    campoQuadro.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoQuadro);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarRgMilitar (TextInputEditText campoRgMilitar)
    {
        String valorDoCampo = campoRgMilitar.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoRgMilitar.setError("O campo RG é obrigatório.");
            campoRgMilitar.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoRgMilitar);
            return false;
        }else { campoRgMilitar.setError(null);return true; }
    }

    public static boolean validarNomeGuerra (TextInputEditText campoNomeGuerra)
    {
        String valorDoCampo = campoNomeGuerra.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoNomeGuerra.setError("O campo NOME GUERRA é obrigatório.");
            campoNomeGuerra.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoNomeGuerra);
            return false;
        }else { campoNomeGuerra.setError(null);return true; }
    }

    public static boolean validarUnidade(AutoCompleteTextView campoUnidade,
                                         List<Unidade> listaDeUnidades)
    {
        String valorDoCampo = campoUnidade.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoUnidade.setError("O campo UNIDADE é obrigatório.");
            campoUnidade.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoUnidade);
            return false;
        }else
        {
            if(listaDeUnidades != null) {
                boolean valueExists = false;
                for(Unidade unidadeSelecionada : listaDeUnidades)
                {
                    if(unidadeSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoUnidade.setError(null);
                    return true;
                }
                else
                {
                    campoUnidade.setError("Insira uma opção de UNIDADE válida.");
                    campoUnidade.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoUnidade);
                    return false;
                }
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validarDataDeInclusao(TextInputEditText campoData)
    {
        String valorDoCampo = campoData.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoData.setError("O campo DATA DE INCLUSÃO é obrigatório.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else if (!DateValidator.isDateValid(valorDoCampo)) {
            campoData.setError("Digite uma DATA válida.");
            campoData.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoData);
            return false;
        }else{ campoData.setError(null); return true; }
    }

    public static boolean validarFuncaoAdministrativa(AutoCompleteTextView campoFuncaoAdministrativa,
                                                      List<FuncaoAdministrativa> listaDeFuncoesAdministrativas)
    {
        String valorDoCampo = campoFuncaoAdministrativa.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoFuncaoAdministrativa.setError("O campo FUNÇÃO ADMINISTRATIVA é obrigatório.");
            campoFuncaoAdministrativa.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoFuncaoAdministrativa);
            return false;
        }else
        {
            if(listaDeFuncoesAdministrativas != null) {
                boolean valueExists = false;
                for(FuncaoAdministrativa funcaoAdministrativaSelecionada : listaDeFuncoesAdministrativas)
                {
                    if(funcaoAdministrativaSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoFuncaoAdministrativa.setError(null);
                    return true;
                }
                else
                {
                    campoFuncaoAdministrativa.setError("Insira uma opção de FUNÇÃO ADMINISTRATIVA válida.");
                    campoFuncaoAdministrativa.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoFuncaoAdministrativa);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarSituacaoFuncional(AutoCompleteTextView campoSituacaoFuncional,
                                                   List<SituacaoFuncional> listaDeSituacoesFuncionais)
    {
        String valorDoCampo = campoSituacaoFuncional.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoSituacaoFuncional.setError("O campo SITUAÇÃO FUNCIONAL é obrigatório.");
            campoSituacaoFuncional.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoSituacaoFuncional);
            return false;
        }else
        {
            if(listaDeSituacoesFuncionais != null) {
                boolean valueExists = false;
                for(SituacaoFuncional situacaoFuncionalSelecionada : listaDeSituacoesFuncionais)
                {
                    if(situacaoFuncionalSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoSituacaoFuncional.setError(null);
                    return true;
                }
                else
                {
                    campoSituacaoFuncional.setError("Insira uma opção de SITUAÇÃO FUCIONAL válida.");
                    campoSituacaoFuncional.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoSituacaoFuncional);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarEspecialidade(AutoCompleteTextView campoEspecialidade,
                                               List<Especialidade> listaDeEspecialidades)
    {
        String valorDoCampo = campoEspecialidade.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoEspecialidade.setError("O campo ESPECIALISTA é obrigatório.");
            campoEspecialidade.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoEspecialidade);
            return false;
        }else
        {
            if(listaDeEspecialidades != null) {
                boolean valueExists = false;
                for(Especialidade especialidadeSelecionada : listaDeEspecialidades)
                {
                    if(especialidadeSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; }
                }

                if(valueExists) {
                    campoEspecialidade.setError(null);
                    return true;
                }
                else
                {
                    campoEspecialidade.setError("Insira uma opção de ESPECIALIDADE válida.");
                    campoEspecialidade.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoEspecialidade);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarRegistroConselho (TextInputEditText campoRegistroConselho)
    {
        String valorDoCampo = campoRegistroConselho.getText().toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoRegistroConselho.setError("O campo REGISTRO NO CONSELHO é obrigatório.");
            campoRegistroConselho.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoRegistroConselho);
            return false;
        }else { campoRegistroConselho.setError(null);return true; }
    }

    public static boolean validarSenhaUpdate(TextInputEditText campoSenha, boolean desejaAtualizarSenha) {

        String senha = campoSenha.getText().toString();

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
        String valorDoCampo = campoSenha.getText().toString().trim();

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
//        }

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
