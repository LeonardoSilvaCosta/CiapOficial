package com.br.ciapoficial.validation;

import android.os.Build;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;

import com.br.ciapoficial.helper.DellayAction;
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
import com.br.ciapoficial.model.in_servico.Acesso;
import com.br.ciapoficial.model.in_servico.CondicaoLaboral;
import com.br.ciapoficial.model.in_servico.DemandaEspecifica;
import com.br.ciapoficial.model.in_servico.DemandaGeral;
import com.br.ciapoficial.model.in_servico.Modalidade;
import com.br.ciapoficial.model.in_servico.Procedimento;
import com.br.ciapoficial.model.in_servico.Programa;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

public class FieldValidator {

    public static boolean isFieldEmptyOrNull(TextInputEditText campoString, String nomeDoCampo)
    {
        String valorDoCampo = Objects.requireNonNull(campoString.getText()).toString().trim();
        if(valorDoCampo.isEmpty()) {
            campoString.setError("O campo " + nomeDoCampo + "é obrigatório.");
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

    public static boolean validarUF(AutoCompleteTextView campoUf, List<Estado> listaDeEstados)
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

    public static boolean validarModalidade(AutoCompleteTextView campoModalidadeSelecionada,
                                            List<Modalidade> listaDeModalidades)
    {
        String valorDoCampo = campoModalidadeSelecionada.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoModalidadeSelecionada.setError("O campo MODALIDADE é obrigatório.");
            campoModalidadeSelecionada.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoModalidadeSelecionada);
            return false;
        }else
        {
            if(listaDeModalidades != null) {
                boolean valueExists = false;
                for(Modalidade modalidadeSelecionada : listaDeModalidades)
                {
                    if(modalidadeSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoModalidadeSelecionada.setError(null);
                    return true;
                }else
                {
                    campoModalidadeSelecionada.setError("Insira uma opção de MODALIDADE válida.");
                    campoModalidadeSelecionada.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoModalidadeSelecionada);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarAcessoAtendimento(AutoCompleteTextView campoAcessoAtendimentoSelecionado,
                                                   List<Acesso> listaDeAcessos)
    {
        String valorDoCampo = campoAcessoAtendimentoSelecionado.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoAcessoAtendimentoSelecionado.setError("O campo ACESSO é obrigatório.");
            campoAcessoAtendimentoSelecionado.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoAcessoAtendimentoSelecionado);
            return false;
        }else
        {
            if(listaDeAcessos != null) {
                boolean valueExists = false;
                for(Acesso acessoSelecionada : listaDeAcessos)
                {
                    if(acessoSelecionada.toString().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoAcessoAtendimentoSelecionado.setError(null);
                    return true;
                }else
                {
                    campoAcessoAtendimentoSelecionado.setError("Insira uma opção de ACESSO válida.");
                    campoAcessoAtendimentoSelecionado.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoAcessoAtendimentoSelecionado);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarTipoDeServico(AutoCompleteTextView campoTipoServicoSelecionado,
                                               List<String> listaDeServicos)
    {
        String valorDoCampo = campoTipoServicoSelecionado.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoTipoServicoSelecionado.setError("O campo TIPO DE SERVIÇO é obrigatório.");
            campoTipoServicoSelecionado.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoTipoServicoSelecionado);
            return false;
        }else
        {
            if(listaDeServicos != null) {
                boolean valueExists = false;
                for(String servicoSelecionado : listaDeServicos)
                {
                    if(servicoSelecionado.equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoTipoServicoSelecionado.setError(null);
                    return true;

                }else
                {
                    campoTipoServicoSelecionado.setError("Insira uma opção de SERVIÇO válida.");
                    campoTipoServicoSelecionado.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoTipoServicoSelecionado);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarPrograma(AutoCompleteTextView campoProgramaSelecionado,
                                          List<Programa> listaDeProgramas)
    {
        String valorDoCampo = campoProgramaSelecionado.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoProgramaSelecionado.setError("O campo PROGRAMA é obrigatório.");
            campoProgramaSelecionado.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoProgramaSelecionado);
            return false;
        }else
        {
            if(listaDeProgramas != null) {
                boolean valueExists = false;
                for(Programa programaSelecionado : listaDeProgramas)
                {
                    if(programaSelecionado.getNome().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoProgramaSelecionado.setError(null);
                    return true;
                }else
                {
                    campoProgramaSelecionado.setError("Insira uma opção de PROGRAMA válida.");
                    campoProgramaSelecionado.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoProgramaSelecionado);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarDemandaGeral(AutoCompleteTextView campoDemandaGeralSelecionada,
                                              List<DemandaGeral> listaDeDemandasGerais)
    {
        String valorDoCampo = campoDemandaGeralSelecionada.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoDemandaGeralSelecionada.setError("O campo DEMANDA GERAL é obrigatório.");
            campoDemandaGeralSelecionada.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoDemandaGeralSelecionada);
            return false;
        }else
        {
            if(listaDeDemandasGerais != null) {
                boolean valueExists = false;
                for(DemandaGeral demandaGeralSelecionada : listaDeDemandasGerais)
                {
                    if(demandaGeralSelecionada.getNome().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoDemandaGeralSelecionada.setError(null);
                    return true;
                }else
                {
                    campoDemandaGeralSelecionada.setError("Insira uma opção de DEMANDA GERAL válida.");
                    campoDemandaGeralSelecionada.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoDemandaGeralSelecionada);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarProcedimento(AutoCompleteTextView campoProcedimentoSelecionado,
                                              List<Procedimento> listaDeProcedimentos)
    {
        String valorDoCampo = campoProcedimentoSelecionado.getText().toString().trim();

        if(valorDoCampo.isEmpty())
        {
            campoProcedimentoSelecionado.setError("O campo PROCEDIMENTO é obrigatório.");
            campoProcedimentoSelecionado.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campoProcedimentoSelecionado);
            return false;
        }else
        {
            if(listaDeProcedimentos != null) {
                boolean valueExists = false;
                for(Procedimento procedimentoSelecionado : listaDeProcedimentos)
                {
                    if(procedimentoSelecionado.toString().equals(valorDoCampo))
                    { valueExists = true; break; }
                }

                if (valueExists)
                {
                    campoProcedimentoSelecionado.setError(null);
                    return true;
                }else
                {
                    campoProcedimentoSelecionado.setError("Insira uma opção de PROCEDIMENTO válida.");
                    campoProcedimentoSelecionado.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campoProcedimentoSelecionado);
                    return false;
                }
            }
        }
        return true;
    }

}
