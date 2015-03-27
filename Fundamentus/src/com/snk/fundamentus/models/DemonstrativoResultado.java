package com.snk.fundamentus.models;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class DemonstrativoResultado {
    private Date dataDemonstrativo;
    private double receitaBrutaVendasServicos;
    private double deducoesReceitaBruta;
    private double receitaLiquidaVendasServicos;
    private double custoBensServicosVendidos;
    private double resultadoBruto;
    private double despesasComVendas;
    private double despesasGeraisAdministrativas;
    private double perdasPelaNaoRecuperabilidadeAtivos;
    private double outrasReceitasOperacionais;
    private double outrasDespesasOperacionais;
    private double resultadoEquivalenciaPatrimonial;
    private double financeiras;
    private double receitasFinanceiras;
    private double despesasFinanceiras;
    private double resultadoNaoOperacional;
    private double receitas;
    private double despesas;
    private double resultadoAntesTributacao_Participacoes;
    private double provisaoParaIRContribuicaoSocial;
    private double iRDiferido;
    private double participacoes_ContribuicoesEstatutarias;
    private double reversaoJurosSobreCapitalProprio;
    private double partAcionistasNaoControladores;
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
        this.receitaBrutaVendasServicos = receitaBrutaVendasServicos;
    }

    public double getDeducoesReceitaBruta() {
        return deducoesReceitaBruta;
    }

    public void setDeducoesReceitaBruta(final double deducoesReceitaBruta) {
        this.deducoesReceitaBruta = deducoesReceitaBruta;
    }

    public double getReceitaLiquidaVendasServicos() {
        return receitaLiquidaVendasServicos;
    }

    public void setReceitaLiquidaVendasServicos(final double receitaLiquidaVendasServicos) {
        this.receitaLiquidaVendasServicos = receitaLiquidaVendasServicos;
    }

    public double getCustoBensServicosVendidos() {
        return custoBensServicosVendidos;
    }

    public void setCustoBensServicosVendidos(final double custoBensServicosVendidos) {
        this.custoBensServicosVendidos = custoBensServicosVendidos;
    }

    public double getResultadoBruto() {
        return resultadoBruto;
    }

    public void setResultadoBruto(final double resultadoBruto) {
        this.resultadoBruto = resultadoBruto;
    }

    public double getDespesasComVendas() {
        return despesasComVendas;
    }

    public void setDespesasComVendas(final double despesasComVendas) {
        this.despesasComVendas = despesasComVendas;
    }

    public double getDespesasGeraisAdministrativas() {
        return despesasGeraisAdministrativas;
    }

    public void setDespesasGeraisAdministrativas(final double despesasGeraisAdministrativas) {
        this.despesasGeraisAdministrativas = despesasGeraisAdministrativas;
    }

    public double getPerdasPelaNaoRecuperabilidadeAtivos() {
        return perdasPelaNaoRecuperabilidadeAtivos;
    }

    public void setPerdasPelaNaoRecuperabilidadeAtivos(final double perdasPelaNaoRecuperabilidadeAtivos) {
        this.perdasPelaNaoRecuperabilidadeAtivos = perdasPelaNaoRecuperabilidadeAtivos;
    }

    public double getOutrasReceitasOperacionais() {
        return outrasReceitasOperacionais;
    }

    public void setOutrasReceitasOperacionais(final double outrasReceitasOperacionais) {
        this.outrasReceitasOperacionais = outrasReceitasOperacionais;
    }

    public double getOutrasDespesasOperacionais() {
        return outrasDespesasOperacionais;
    }

    public void setOutrasDespesasOperacionais(final double outrasDespesasOperacionais) {
        this.outrasDespesasOperacionais = outrasDespesasOperacionais;
    }

    public double getResultadoEquivalenciaPatrimonial() {
        return resultadoEquivalenciaPatrimonial;
    }

    public void setResultadoEquivalenciaPatrimonial(final double resultadoEquivalenciaPatrimonial) {
        this.resultadoEquivalenciaPatrimonial = resultadoEquivalenciaPatrimonial;
    }

    public double getFinanceiras() {
        return financeiras;
    }

    public void setFinanceiras(final double financeiras) {
        this.financeiras = financeiras;
    }

    public double getReceitasFinanceiras() {
        return receitasFinanceiras;
    }

    public void setReceitasFinanceiras(final double receitasFinanceiras) {
        this.receitasFinanceiras = receitasFinanceiras;
    }

    public double getDespesasFinanceiras() {
        return despesasFinanceiras;
    }

    public void setDespesasFinanceiras(final double despesasFinanceiras) {
        this.despesasFinanceiras = despesasFinanceiras;
    }

    public double getResultadoNaoOperacional() {
        return resultadoNaoOperacional;
    }

    public void setResultadoNaoOperacional(final double resultadoNaoOperacional) {
        this.resultadoNaoOperacional = resultadoNaoOperacional;
    }

    public double getReceitas() {
        return receitas;
    }

    public void setReceitas(final double receitas) {
        this.receitas = receitas;
    }

    public double getDespesas() {
        return despesas;
    }

    public void setDespesas(final double despesas) {
        this.despesas = despesas;
    }

    public double getResultadoAntesTributacao_Participacoes() {
        return resultadoAntesTributacao_Participacoes;
    }

    public void setResultadoAntesTributacao_Participacoes(final double resultadoAntesTributacao_Participacoes) {
        this.resultadoAntesTributacao_Participacoes = resultadoAntesTributacao_Participacoes;
    }

    public double getProvisaoParaIRContribuicaoSocial() {
        return provisaoParaIRContribuicaoSocial;
    }

    public void setProvisaoParaIRContribuicaoSocial(final double provisaoParaIRContribuicaoSocial) {
        this.provisaoParaIRContribuicaoSocial = provisaoParaIRContribuicaoSocial;
    }

    public double getiRDiferido() {
        return iRDiferido;
    }

    public void setiRDiferido(final double iRDiferido) {
        this.iRDiferido = iRDiferido;
    }

    public double getParticipacoes_ContribuicoesEstatutarias() {
        return participacoes_ContribuicoesEstatutarias;
    }

    public void setParticipacoes_ContribuicoesEstatutarias(final double participacoes_ContribuicoesEstatutarias) {
        this.participacoes_ContribuicoesEstatutarias = participacoes_ContribuicoesEstatutarias;
    }

    public double getReversaoJurosSobreCapitalProprio() {
        return reversaoJurosSobreCapitalProprio;
    }

    public void setReversaoJurosSobreCapitalProprio(final double reversaoJurosSobreCapitalProprio) {
        this.reversaoJurosSobreCapitalProprio = reversaoJurosSobreCapitalProprio;
    }

    public double getPartAcionistasNaoControladores() {
        return partAcionistasNaoControladores;
    }

    public void setPartAcionistasNaoControladores(final double partAcionistasNaoControladores) {
        this.partAcionistasNaoControladores = partAcionistasNaoControladores;
    }

    public double getLucro_PrejuizoPeriodo() {
        return lucro_PrejuizoPeriodo;
    }

    public void setLucro_PrejuizoPeriodo(final double lucro_PrejuizoPeriodo) {
        this.lucro_PrejuizoPeriodo = lucro_PrejuizoPeriodo;
    }

}
