package com.snk.fundamentus.models;

import java.util.Date;

import javax.persistence.Embeddable;

import com.snk.fundamentus.enums.DataType;
import com.snk.fundamentus.enums.ShowOnTable;

@Embeddable
public class DemonstrativoResultado {

    private Date dataDemonstrativo;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Receita Bruta de Vendas e/ou Servi�os")
    private double receitaBrutaVendasServicos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Dedu��es da Receita Bruta")
    private double deducoesReceitaBruta;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Receita L�quida de Vendas e/ou Servi�os")
    private double receitaLiquidaVendasServicos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Custo de Bens e/ou Servi�os Vendidos")
    private double custoBensServicosVendidos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Resultado Bruto")
    private double resultadoBruto;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Despesas Com Vendas")
    private double despesasComVendas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Despesas Gerais e Administrativas")
    private double despesasGeraisAdministrativas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Perdas pela N�o Recuperabilidade de Ativos")
    private double perdasPelaNaoRecuperabilidadeAtivos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Outras Receitas Operacionais")
    private double outrasReceitasOperacionais;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Outras Despesas Operacionais")
    private double outrasDespesasOperacionais;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Resultado da Equival�ncia Patrimonial")
    private double resultadoEquivalenciaPatrimonial;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Financeiras")
    private double financeiras;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Receitas Financeiras")
    private double receitasFinanceiras;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Despesas Financeiras")
    private double despesasFinanceiras;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Resultado N�o Operacional")
    private double resultadoNaoOperacional;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Receitas")
    private double receitas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Despesas")
    private double despesas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Resultado Antes Tributa��o/Participa��es")
    private double resultadoAntesTributacao_Participacoes;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Provis�o para IR e Contribui��o Social")
    private double provisaoParaIRContribuicaoSocial;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "IR Diferido")
    private double iRDiferido;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Participa��es/Contribui��es Estatut�rias")
    private double participacoes_ContribuicoesEstatutarias;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Revers�o dos Juros sobre Capital Pr�prio")
    private double reversaoJurosSobreCapitalProprio;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Participa��o de Acionistas N�o Controladores")
    private double partAcionistasNaoControladores;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency, name = "Lucro/Preju�zo do Per�odo")
    private double lucro_PrejuizoPeriodo;

    public Date getDataDemonstrativo() {
        return dataDemonstrativo;
    }

    public void setDataDemonstrativo(final Date dataDemonstrativo) {
        this.dataDemonstrativo = dataDemonstrativo;
    }

    public double getReceitaBrutaVendasServicos() {
        return receitaBrutaVendasServicos;
    }

    public void setReceitaBrutaVendasServicos(final double receitaBrutaVendasServicos) {
        this.receitaBrutaVendasServicos += receitaBrutaVendasServicos;
    }

    public double getDeducoesReceitaBruta() {
        return deducoesReceitaBruta;
    }

    public void setDeducoesReceitaBruta(final double deducoesReceitaBruta) {
        this.deducoesReceitaBruta += deducoesReceitaBruta;
    }

    public double getReceitaLiquidaVendasServicos() {
        return receitaLiquidaVendasServicos;
    }

    public void setReceitaLiquidaVendasServicos(final double receitaLiquidaVendasServicos) {
        this.receitaLiquidaVendasServicos += receitaLiquidaVendasServicos;
    }

    public double getCustoBensServicosVendidos() {
        return custoBensServicosVendidos;
    }

    public void setCustoBensServicosVendidos(final double custoBensServicosVendidos) {
        this.custoBensServicosVendidos += custoBensServicosVendidos;
    }

    public double getResultadoBruto() {
        return resultadoBruto;
    }

    public void setResultadoBruto(final double resultadoBruto) {
        this.resultadoBruto += resultadoBruto;
    }

    public double getDespesasComVendas() {
        return despesasComVendas;
    }

    public void setDespesasComVendas(final double despesasComVendas) {
        this.despesasComVendas += despesasComVendas;
    }

    public double getDespesasGeraisAdministrativas() {
        return despesasGeraisAdministrativas;
    }

    public void setDespesasGeraisAdministrativas(
            final double despesasGeraisAdministrativas) {
        this.despesasGeraisAdministrativas += despesasGeraisAdministrativas;
    }

    public double getPerdasPelaNaoRecuperabilidadeAtivos() {
        return perdasPelaNaoRecuperabilidadeAtivos;
    }

    public void setPerdasPelaNaoRecuperabilidadeAtivos(
            final double perdasPelaNaoRecuperabilidadeAtivos) {
        this.perdasPelaNaoRecuperabilidadeAtivos += perdasPelaNaoRecuperabilidadeAtivos;
    }

    public double getOutrasReceitasOperacionais() {
        return outrasReceitasOperacionais;
    }

    public void setOutrasReceitasOperacionais(final double outrasReceitasOperacionais) {
        this.outrasReceitasOperacionais += outrasReceitasOperacionais;
    }

    public double getOutrasDespesasOperacionais() {
        return outrasDespesasOperacionais;
    }

    public void setOutrasDespesasOperacionais(final double outrasDespesasOperacionais) {
        this.outrasDespesasOperacionais += outrasDespesasOperacionais;
    }

    public double getResultadoEquivalenciaPatrimonial() {
        return resultadoEquivalenciaPatrimonial;
    }

    public void setResultadoEquivalenciaPatrimonial(
            final double resultadoEquivalenciaPatrimonial) {
        this.resultadoEquivalenciaPatrimonial += resultadoEquivalenciaPatrimonial;
    }

    public double getFinanceiras() {
        return financeiras;
    }

    public void setFinanceiras(final double financeiras) {
        this.financeiras += financeiras;
    }

    public double getReceitasFinanceiras() {
        return receitasFinanceiras;
    }

    public void setReceitasFinanceiras(final double receitasFinanceiras) {
        this.receitasFinanceiras += receitasFinanceiras;
    }

    public double getDespesasFinanceiras() {
        return despesasFinanceiras;
    }

    public void setDespesasFinanceiras(final double despesasFinanceiras) {
        this.despesasFinanceiras += despesasFinanceiras;
    }

    public double getResultadoNaoOperacional() {
        return resultadoNaoOperacional;
    }

    public void setResultadoNaoOperacional(final double resultadoNaoOperacional) {
        this.resultadoNaoOperacional += resultadoNaoOperacional;
    }

    public double getReceitas() {
        return receitas;
    }

    public void setReceitas(final double receitas) {
        this.receitas += receitas;
    }

    public double getDespesas() {
        return despesas;
    }

    public void setDespesas(final double despesas) {
        this.despesas += despesas;
    }

    public double getResultadoAntesTributacao_Participacoes() {
        return resultadoAntesTributacao_Participacoes;
    }

    public void setResultadoAntesTributacao_Participacoes(
            final double resultadoAntesTributacao_Participacoes) {
        this.resultadoAntesTributacao_Participacoes += resultadoAntesTributacao_Participacoes;
    }

    public double getProvisaoParaIRContribuicaoSocial() {
        return provisaoParaIRContribuicaoSocial;
    }

    public void setProvisaoParaIRContribuicaoSocial(
            final double provisaoParaIRContribuicaoSocial) {
        this.provisaoParaIRContribuicaoSocial += provisaoParaIRContribuicaoSocial;
    }

    public double getiRDiferido() {
        return iRDiferido;
    }

    public void setIRDiferido(final double iRDiferido) {
        this.iRDiferido += iRDiferido;
    }

    public double getParticipacoes_ContribuicoesEstatutarias() {
        return participacoes_ContribuicoesEstatutarias;
    }

    public void setParticipacoes_ContribuicoesEstatutarias(
            final double participacoes_ContribuicoesEstatutarias) {
        this.participacoes_ContribuicoesEstatutarias += participacoes_ContribuicoesEstatutarias;
    }

    public double getReversaoJurosSobreCapitalProprio() {
        return reversaoJurosSobreCapitalProprio;
    }

    public void setReversaoJurosSobreCapitalProprio(
            final double reversaoJurosSobreCapitalProprio) {
        this.reversaoJurosSobreCapitalProprio += reversaoJurosSobreCapitalProprio;
    }

    public double getPartAcionistasNaoControladores() {
        return partAcionistasNaoControladores;
    }

    public void setPartAcionistasNaoControladores(
            final double partAcionistasNaoControladores) {
        this.partAcionistasNaoControladores += partAcionistasNaoControladores;
    }

    public double getLucro_PrejuizoPeriodo() {
        return lucro_PrejuizoPeriodo;
    }

    public void setLucro_PrejuizoPeriodo(final double lucro_PrejuizoPeriodo) {
        this.lucro_PrejuizoPeriodo += lucro_PrejuizoPeriodo;
    }

}
