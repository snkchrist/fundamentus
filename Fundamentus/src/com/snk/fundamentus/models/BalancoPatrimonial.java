package com.snk.fundamentus.models;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class BalancoPatrimonial {
    @RepresentableOnTable(format = "%1.0f")
    public double adiantamentoParaFuturoAumentoCapital;
    @RepresentableOnTable(format = "%1.0f")
    public double ajustesAcumuladosDeConversao;
    @RepresentableOnTable(format = "%1.0f")
    public double ajustesDeAvaliacaoPatrimonial;
    @RepresentableOnTable(format = "%1.0f")
    public double aplicacoesFinanceiras;
    @RepresentableOnTable(format = "%1.0f")
    public double aplicacoesFinanceirasAvaliadasAValorJusto;
    @RepresentableOnTable(format = "%1.0f")
    public double aplicacoesFinanceirasAvaliadasAoCustoAmortizado;
    @RepresentableOnTable(format = "%1.0f")
    public double ativosBiologicos;
    @RepresentableOnTable(format = "%1.0f")
    public double caixaEEquivalentesDeCaixa;
    @RepresentableOnTable(format = "%1.0f")
    public double capitalSocialRealizado;
    @RepresentableOnTable(format = "%1.0f")
    public double contasAReceber;
    @RepresentableOnTable(format = "%1.0f")
    public double creditosComPartesRelacionadas;
    @RepresentableOnTable(format = "%1.0f")
    public double despesasAntecipadas;
    @RepresentableOnTable(format = "%1.0f")
    public double diferido;
    @RepresentableOnTable(format = "%1.0f")
    public double dividendosEJCPAPagar;
    @RepresentableOnTable(format = "%1.0f")
    public double emprestimosEFinanciamentos;
    @RepresentableOnTable(format = "%1.0f")
    public double estoques;
    @RepresentableOnTable(format = "%1.0f")
    public double fornecedores;
    @RepresentableOnTable(format = "%1.0f")
    public double imobilizado;
    @RepresentableOnTable(format = "%1.0f")
    public double intangivel;
    @RepresentableOnTable(format = "%1.0f")
    public double investimentos;
    @RepresentableOnTable(format = "%1.0f")
    public double lucrosEReceitasAApropriar;
    @RepresentableOnTable(format = "%1.0f")
    public double lucros_PrejuizosAcumulados;
    @RepresentableOnTable(format = "%1.0f")
    public double obrigacoesFiscais;
    @RepresentableOnTable(format = "%1.0f")
    public double obrigacoesSociaisETrabalhistas;
    @RepresentableOnTable(format = "%1.0f")
    public double outros;
    @RepresentableOnTable(format = "%1.0f")
    public double outrosResultadosAbrangentes;
    @RepresentableOnTable(format = "%1.0f")
    public double participacaoDosAcionistasNaoControladores;
    @RepresentableOnTable(format = "%1.0f")
    public double passivosComPartesRelacionadas;
    @RepresentableOnTable(format = "%1.0f")
    public double passivosSobreAtivosNaoCorrentesAVendaEDescontinuados;
    @RepresentableOnTable(format = "%1.0f")
    public double patrimônioLiquido;
    @RepresentableOnTable(format = "%1.0f")
    public double provisoes;
    @RepresentableOnTable(format = "%1.0f")
    public double reservasDeCapital;
    @RepresentableOnTable(format = "%1.0f")
    public double reservasDeLucros;
    @RepresentableOnTable(format = "%1.0f")
    public double reservasDeReavaliacao;
    @RepresentableOnTable(format = "%1.0f")
    public double tributosARecuperar;
    @RepresentableOnTable(format = "%1.0f")
    public double tributosDiferidos;
    @RepresentableOnTable(format = "%1.0f")
    public double ativoTotal;
    @RepresentableOnTable(format = "%1.0f")
    public double ativoCirculante;
    @RepresentableOnTable(format = "%1.0f")
    public double outrosAtivosCirculantes;
    @RepresentableOnTable(format = "%1.0f")
    public double ativoRealizavelALongoPrazo;
    @RepresentableOnTable(format = "%1.0f")
    public double outrosAtivosNaoCirculantes;
    @RepresentableOnTable(format = "%1.0f")
    public double passivoTotal;
    @RepresentableOnTable(format = "%1.0f")
    public double passivoCirculante;
    @RepresentableOnTable(format = "%1.0f")
    public double passivoNaoCirculante;
    @RepresentableOnTable(format = "%1$te/%1$tm/%1$tY")
    public Date dataDoBalanco;

    public double getAdiantamentoParaFuturoAumentoCapital() {
        return adiantamentoParaFuturoAumentoCapital;
    }

    public void setAdiantamentoParaFuturoAumentoCapital(
            final double adiantamentoParaFuturoAumentoCapital) {
        this.adiantamentoParaFuturoAumentoCapital += adiantamentoParaFuturoAumentoCapital;
    }

    public double getAjustesAcumuladosDeConversao() {
        return ajustesAcumuladosDeConversao;
    }

    public void setAjustesAcumuladosDeConversao(final double ajustesAcumuladosDeConversao) {
        this.ajustesAcumuladosDeConversao += ajustesAcumuladosDeConversao;
    }

    public double getAjustesDeAvaliacaoPatrimonial() {
        return ajustesDeAvaliacaoPatrimonial;
    }

    public void setAjustesDeAvaliacaoPatrimonial(final double ajustesDeAvaliacaoPatrimonial) {
        this.ajustesDeAvaliacaoPatrimonial += ajustesDeAvaliacaoPatrimonial;
    }

    public double getAplicacoesFinanceiras() {
        return aplicacoesFinanceiras;
    }

    public void setAplicacoesFinanceiras(final double aplicacoesFinanceiras) {
        this.aplicacoesFinanceiras += aplicacoesFinanceiras;
    }

    public double getAplicacoesFinanceirasAvaliadasAValorJusto() {
        return aplicacoesFinanceirasAvaliadasAValorJusto;
    }

    public void setAplicacoesFinanceirasAvaliadasAValorJusto(
            final double aplicacoesFinanceirasAvaliadasAValorJusto) {
        this.aplicacoesFinanceirasAvaliadasAValorJusto += aplicacoesFinanceirasAvaliadasAValorJusto;
    }

    public double getAplicacoesFinanceirasAvaliadasAoCustoAmortizado() {
        return aplicacoesFinanceirasAvaliadasAoCustoAmortizado;
    }

    public void setAplicacoesFinanceirasAvaliadasAoCustoAmortizado(
            final double aplicacoesFinanceirasAvaliadasAoCustoAmortizado) {
        this.aplicacoesFinanceirasAvaliadasAoCustoAmortizado += aplicacoesFinanceirasAvaliadasAoCustoAmortizado;
    }

    public double getAtivosBiologicos() {
        return ativosBiologicos;
    }

    public void setAtivosBiologicos(final double ativosBiologicos) {
        this.ativosBiologicos += ativosBiologicos;
    }

    public double getCaixaEEquivalentesDeCaixa() {
        return caixaEEquivalentesDeCaixa;
    }

    public void setCaixaEEquivalentesDeCaixa(final double caixaEEquivalentesDeCaixa) {
        this.caixaEEquivalentesDeCaixa += caixaEEquivalentesDeCaixa;
    }

    public double getCapitalSocialRealizado() {
        return capitalSocialRealizado;
    }

    public void setCapitalSocialRealizado(final double capitalSocialRealizado) {
        this.capitalSocialRealizado += capitalSocialRealizado;
    }

    public double getContasAReceber() {
        return contasAReceber;
    }

    public void setContasAReceber(final double contasAReceber) {
        this.contasAReceber += contasAReceber;
    }

    public double getCreditosComPartesRelacionadas() {
        return creditosComPartesRelacionadas;
    }

    public void setCreditosComPartesRelacionadas(final double creditosComPartesRelacionadas) {
        this.creditosComPartesRelacionadas += creditosComPartesRelacionadas;
    }

    public double getDespesasAntecipadas() {
        return despesasAntecipadas;
    }

    public void setDespesasAntecipadas(final double despesasAntecipadas) {
        this.despesasAntecipadas += despesasAntecipadas;
    }

    public double getDiferido() {
        return diferido;
    }

    public void setDiferido(final double diferido) {
        this.diferido += diferido;
    }

    public double getDividendosEJCPAPagar() {
        return dividendosEJCPAPagar;
    }

    public void setDividendosEJCPAPagar(final double dividendosEJCPAPagar) {
        this.dividendosEJCPAPagar += dividendosEJCPAPagar;
    }

    public double getEmprestimosEFinanciamentos() {
        return emprestimosEFinanciamentos;
    }

    public void setEmprestimosEFinanciamentos(final double emprestimosEFinanciamentos) {
        this.emprestimosEFinanciamentos += emprestimosEFinanciamentos;
    }

    public double getEstoques() {
        return estoques;
    }

    public void setEstoques(final double estoques) {
        this.estoques += estoques;
    }

    public double getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(final double fornecedores) {
        this.fornecedores += fornecedores;
    }

    public double getImobilizado() {
        return imobilizado;
    }

    public void setImobilizado(final double imobilizado) {
        this.imobilizado += imobilizado;
    }

    public double getIntangivel() {
        return intangivel;
    }

    public void setIntangivel(final double intangivel) {
        this.intangivel += intangivel;
    }

    public double getInvestimentos() {
        return investimentos;
    }

    public void setInvestimentos(final double investimentos) {
        this.investimentos += investimentos;
    }

    public double getLucrosEReceitasAApropriar() {
        return lucrosEReceitasAApropriar;
    }

    public void setLucrosEReceitasAApropriar(final double lucrosEReceitasAApropriar) {
        this.lucrosEReceitasAApropriar += lucrosEReceitasAApropriar;
    }

    public double getLucros_PrejuizosAcumulados() {
        return lucros_PrejuizosAcumulados;
    }

    public void setLucros_PrejuizosAcumulados(final double lucros_PrejuizosAcumulados) {
        this.lucros_PrejuizosAcumulados += lucros_PrejuizosAcumulados;
    }

    public double getObrigacoesFiscais() {
        return obrigacoesFiscais;
    }

    public void setObrigacoesFiscais(final double obrigacoesFiscais) {
        this.obrigacoesFiscais += obrigacoesFiscais;
    }

    public double getObrigacoesSociaisETrabalhistas() {
        return obrigacoesSociaisETrabalhistas;
    }

    public void setObrigacoesSociaisETrabalhistas(final double obrigacoesSociaisETrabalhistas) {
        this.obrigacoesSociaisETrabalhistas += obrigacoesSociaisETrabalhistas;
    }

    public double getOutros() {
        return outros;
    }

    public void setOutros(final double outros) {
        this.outros += outros;
    }

    public double getOutrosResultadosAbrangentes() {
        return outrosResultadosAbrangentes;
    }

    public void setOutrosResultadosAbrangentes(final double outrosResultadosAbrangentes) {
        this.outrosResultadosAbrangentes += outrosResultadosAbrangentes;
    }

    public double getParticipacaoDosAcionistasNaoControladores() {
        return participacaoDosAcionistasNaoControladores;
    }

    public void setParticipacaoDosAcionistasNaoControladores(
            final double participacaoDosAcionistasNaoControladores) {
        this.participacaoDosAcionistasNaoControladores += participacaoDosAcionistasNaoControladores;
    }

    public double getPassivosComPartesRelacionadas() {
        return passivosComPartesRelacionadas;
    }

    public void setPassivosComPartesRelacionadas(final double passivosComPartesRelacionadas) {
        this.passivosComPartesRelacionadas += passivosComPartesRelacionadas;
    }

    public double getPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados() {
        return passivosSobreAtivosNaoCorrentesAVendaEDescontinuados;
    }

    public void setPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados(
            final double passivosSobreAtivosNaoCorrentesAVendaEDescontinuados) {
        this.passivosSobreAtivosNaoCorrentesAVendaEDescontinuados += passivosSobreAtivosNaoCorrentesAVendaEDescontinuados;
    }

    public double getPatrimonioLiquido() {
        return patrimônioLiquido;
    }

    public void setPatrimônioLiquido(final double patrimônioLiquido) {
        this.patrimônioLiquido += patrimônioLiquido;
    }

    public double getProvisoes() {
        return provisoes;
    }

    public void setProvisoes(final double provisoes) {
        this.provisoes += provisoes;
    }

    public double getReservasDeCapital() {
        return reservasDeCapital;
    }

    public void setReservasDeCapital(final double reservasDeCapital) {
        this.reservasDeCapital += reservasDeCapital;
    }

    public double getReservasDeLucros() {
        return reservasDeLucros;
    }

    public void setReservasDeLucros(final double reservasDeLucros) {
        this.reservasDeLucros += reservasDeLucros;
    }

    public double getReservasDeReavaliacao() {
        return reservasDeReavaliacao;
    }

    public void setReservasDeReavaliacao(final double reservasDeReavaliacao) {
        this.reservasDeReavaliacao += reservasDeReavaliacao;
    }

    public double getTributosARecuperar() {
        return tributosARecuperar;
    }

    public void setTributosARecuperar(final double tributosARecuperar) {
        this.tributosARecuperar += tributosARecuperar;
    }

    public double getTributosDiferidos() {
        return tributosDiferidos;
    }

    public void setTributosDiferidos(final double tributosDiferidos) {
        this.tributosDiferidos += tributosDiferidos;
    }

    public Date getDataDoBalanco() {
        return dataDoBalanco;
    }

    public void setDataDoBalanco(final Date dataDoBalanco) {
        this.dataDoBalanco = dataDoBalanco;
    }

    public double getAtivoTotal() {
        return ativoTotal;
    }

    public void setAtivoTotal(final double ativoTotal) {
        this.ativoTotal += ativoTotal;
    }

    public double getAtivoCirculante() {
        return ativoCirculante;
    }

    public void setAtivoCirculante(final double ativoCirculante) {
        this.ativoCirculante += ativoCirculante;
    }

    public double getOutrosAtivosCirculantes() {
        return outrosAtivosCirculantes;
    }

    public void setOutrosAtivosCirculantes(final double outrosAtivosCirculantes) {
        this.outrosAtivosCirculantes += outrosAtivosCirculantes;
    }

    public double getAtivoRealizavelALongoPrazo() {
        return ativoRealizavelALongoPrazo;
    }

    public void setAtivoRealizavelALongoPrazo(final double ativoRealizavelALongoPrazo) {
        this.ativoRealizavelALongoPrazo += ativoRealizavelALongoPrazo;
    }

    public double getOutrosAtivosNaoCirculantes() {
        return outrosAtivosNaoCirculantes;
    }

    public void setOutrosAtivosNaoCirculantes(final double outrosAtivosNaoCirculantes) {
        this.outrosAtivosNaoCirculantes += outrosAtivosNaoCirculantes;
    }

    public double getPassivoTotal() {
        return passivoTotal;
    }

    public void setPassivoTotal(final double passivoTotal) {
        this.passivoTotal += passivoTotal;
    }

    public double getPassivoCirculante() {
        return passivoCirculante;
    }

    public void setPassivoCirculante(final double passivoCirculante) {
        this.passivoCirculante += passivoCirculante;
    }

    public double getPassivoNaoCirculante() {
        return passivoNaoCirculante;
    }

    public void setPassivoNaoCirculante(final double passivoNaoCirculante) {
        this.passivoNaoCirculante += passivoNaoCirculante;
    }

    @Override
    public String toString() {
        return "Date:" + getDataDoBalanco();

    }

}
