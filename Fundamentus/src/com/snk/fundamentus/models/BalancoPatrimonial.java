package com.snk.fundamentus.models;

import java.util.Date;

import javax.persistence.Embeddable;

import com.snk.fundamentus.enums.DataType;
import com.snk.fundamentus.enums.ShowOnTable;

@Embeddable
public class BalancoPatrimonial {
    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double adiantamentoParaFuturoAumentoCapital;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ajustesAcumuladosDeConversao;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ajustesDeAvaliacaoPatrimonial;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double aplicacoesFinanceiras;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double aplicacoesFinanceirasAvaliadasAValorJusto;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double aplicacoesFinanceirasAvaliadasAoCustoAmortizado;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ativosBiologicos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double caixaEEquivalentesDeCaixa;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double capitalSocialRealizado;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double contasAReceber;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double creditosComPartesRelacionadas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double despesasAntecipadas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double diferido;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double dividendosEJCPAPagar;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double emprestimosEFinanciamentos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double estoques;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double fornecedores;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double imobilizado;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double intangivel;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double investimentos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double lucrosEReceitasAApropriar;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double lucros_PrejuizosAcumulados;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double obrigacoesFiscais;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double obrigacoesSociaisETrabalhistas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outros;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outrosResultadosAbrangentes;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double participacaoDosAcionistasNaoControladores;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double passivosComPartesRelacionadas;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double passivosSobreAtivosNaoCorrentesAVendaEDescontinuados;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double patrimônioLiquido;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double provisoes;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double reservasDeCapital;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double reservasDeLucros;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double reservasDeReavaliacao;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double tributosARecuperar;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double tributosDiferidos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ativoTotal;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ativoCirculante;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outrosAtivosCirculantes;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ativoRealizavelALongoPrazo;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outrosAtivosNaoCirculantes;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double passivoTotal;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double passivoCirculante;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double passivoNaoCirculante;

    @ShowOnTable(format = "%1$te/%1$tm/%1$tY")
    public Date dataDoBalanco;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double aplicacoesInterfinanceirasLiquidez;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double ativoPermanente;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double captacoesMercadoAberto;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double depositos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double disponibilidades;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double imobilizadoArrendamento;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double imobilizadoUso;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double obrigacoesPorEmprestimos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double obrigacoesPorRepasseExterior;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double obrigacoesPorRepassePais;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double operacoesArrendamentoMercantil;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double operacoesCredito;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outrasObrigacoes;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outrosCreditos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double outrosValoresBens;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double partAcionistasNaoControladores;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double passivoExigivelLongoPrazo;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double recursosAceitesEmissaoTitulos;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double relacoesInterdependencias;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double relacoesInterfinanceiras;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double reservasLucro;

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double resultadosExerciciosFuturos;

    public double getPatrimônioLiquido() {
        return patrimônioLiquido;
    }

    public double getAplicacoesInterfinanceirasLiquidez() {
        return aplicacoesInterfinanceirasLiquidez;
    }

    public double getAtivoPermanente() {
        return ativoPermanente;
    }

    public double getCaptacoesMercadoAberto() {
        return captacoesMercadoAberto;
    }

    public double getDepositos() {
        return depositos;
    }

    public double getDisponibilidades() {
        return disponibilidades;
    }

    public double getImobilizadoArrendamento() {
        return imobilizadoArrendamento;
    }

    public double getImobilizadoUso() {
        return imobilizadoUso;
    }

    public double getObrigacoesPorEmprestimos() {
        return obrigacoesPorEmprestimos;
    }

    public double getObrigacoesPorRepasseExterior() {
        return obrigacoesPorRepasseExterior;
    }

    public double getObrigacoesPorRepassePais() {
        return obrigacoesPorRepassePais;
    }

    public double getOperacoesArrendamentoMercantil() {
        return operacoesArrendamentoMercantil;
    }

    public double getOperacoesCredito() {
        return operacoesCredito;
    }

    public double getOutrasObrigacoes() {
        return outrasObrigacoes;
    }

    public double getOutrosCreditos() {
        return outrosCreditos;
    }

    public double getOutrosValoresBens() {
        return outrosValoresBens;
    }

    public double getPartAcionistasNaoControladores() {
        return partAcionistasNaoControladores;
    }

    public double getPassivoExigivelLongoPrazo() {
        return passivoExigivelLongoPrazo;
    }

    public double getRecursosAceitesEmissaoTitulos() {
        return recursosAceitesEmissaoTitulos;
    }

    public double getRelacoesInterdependencias() {
        return relacoesInterdependencias;
    }

    public double getRelacoesInterfinanceiras() {
        return relacoesInterfinanceiras;
    }

    public double getReservasLucro() {
        return reservasLucro;
    }

    public double getResultadosExerciciosFuturos() {
        return resultadosExerciciosFuturos;
    }

    public double getTitulosValoresMobiliarios() {
        return titulosValoresMobiliarios;
    }

    @ShowOnTable(format = "%1.0f", type = DataType.Currency)
    public double titulosValoresMobiliarios;

    public double getAdiantamentoParaFuturoAumentoCapital() {
        return adiantamentoParaFuturoAumentoCapital;
    }

    public double getAjustesAcumuladosDeConversao() {
        return ajustesAcumuladosDeConversao;
    }

    public double getAjustesDeAvaliacaoPatrimonial() {
        return ajustesDeAvaliacaoPatrimonial;
    }

    public double getAplicacoesFinanceiras() {
        return aplicacoesFinanceiras;
    }

    public double getAplicacoesFinanceirasAvaliadasAValorJusto() {
        return aplicacoesFinanceirasAvaliadasAValorJusto;
    }

    public double getAplicacoesFinanceirasAvaliadasAoCustoAmortizado() {
        return aplicacoesFinanceirasAvaliadasAoCustoAmortizado;
    }

    public double getAtivosBiologicos() {
        return ativosBiologicos;
    }

    public double getCaixaEEquivalentesDeCaixa() {
        return caixaEEquivalentesDeCaixa;
    }

    public double getCapitalSocialRealizado() {
        return capitalSocialRealizado;
    }

    public double getContasAReceber() {
        return contasAReceber;
    }

    public double getCreditosComPartesRelacionadas() {
        return creditosComPartesRelacionadas;
    }

    public double getDespesasAntecipadas() {
        return despesasAntecipadas;
    }

    public double getDiferido() {
        return diferido;
    }

    public double getDividendosEJCPAPagar() {
        return dividendosEJCPAPagar;
    }

    public double getEmprestimosEFinanciamentos() {
        return emprestimosEFinanciamentos;
    }

    public double getEstoques() {
        return estoques;
    }

    public double getFornecedores() {
        return fornecedores;
    }

    public double getImobilizado() {
        return imobilizado;
    }

    public double getIntangivel() {
        return intangivel;
    }

    public double getInvestimentos() {
        return investimentos;
    }

    public double getLucrosEReceitasAApropriar() {
        return lucrosEReceitasAApropriar;
    }

    public double getLucros_PrejuizosAcumulados() {
        return lucros_PrejuizosAcumulados;
    }

    public double getObrigacoesFiscais() {
        return obrigacoesFiscais;
    }

    public double getObrigacoesSociaisETrabalhistas() {
        return obrigacoesSociaisETrabalhistas;
    }

    public double getOutros() {
        return outros;
    }

    public double getOutrosResultadosAbrangentes() {
        return outrosResultadosAbrangentes;
    }

    public double getParticipacaoDosAcionistasNaoControladores() {
        return participacaoDosAcionistasNaoControladores;
    }

    public double getPassivosComPartesRelacionadas() {
        return passivosComPartesRelacionadas;
    }

    public double getPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados() {
        return passivosSobreAtivosNaoCorrentesAVendaEDescontinuados;
    }

    public double getPatrimonioLiquido() {
        return patrimônioLiquido;
    }

    public double getProvisoes() {
        return provisoes;
    }

    public double getReservasDeCapital() {
        return reservasDeCapital;
    }

    public double getReservasDeLucros() {
        return reservasDeLucros;
    }

    public double getReservasDeReavaliacao() {
        return reservasDeReavaliacao;
    }

    public double getTributosARecuperar() {
        return tributosARecuperar;
    }

    public double getTributosDiferidos() {
        return tributosDiferidos;
    }

    public Date getDataDoBalanco() {
        return dataDoBalanco;
    }

    public double getAtivoTotal() {
        return ativoTotal;
    }

    public double getAtivoCirculante() {
        return ativoCirculante;
    }

    public double getOutrosAtivosCirculantes() {
        return outrosAtivosCirculantes;
    }

    public double getAtivoRealizavelALongoPrazo() {
        return ativoRealizavelALongoPrazo;
    }

    public double getOutrosAtivosNaoCirculantes() {
        return outrosAtivosNaoCirculantes;
    }

    public double getPassivoTotal() {
        return passivoTotal;
    }

    public double getPassivoCirculante() {
        return passivoCirculante;
    }

    public double getPassivoNaoCirculante() {
        return passivoNaoCirculante;
    }

    /**
     * SETS
     *
     * @param outrosAtivosNaoCirculantes
     */

    public void setAjustesAcumuladosDeConversao(final double ajustesAcumuladosDeConversao) {
        this.ajustesAcumuladosDeConversao += ajustesAcumuladosDeConversao;
    }

    public void setAdiantamentoParaFuturoAumentoCapital(
            final double adiantamentoParaFuturoAumentoCapital) {
        this.adiantamentoParaFuturoAumentoCapital += adiantamentoParaFuturoAumentoCapital;
    }

    public void setAjustesDeAvaliacaoPatrimonial(final double ajustesDeAvaliacaoPatrimonial) {
        this.ajustesDeAvaliacaoPatrimonial += ajustesDeAvaliacaoPatrimonial;
    }

    public void setAplicacoesFinanceiras(final double aplicacoesFinanceiras) {
        this.aplicacoesFinanceiras += aplicacoesFinanceiras;
    }

    public void setAplicacoesFinanceirasAvaliadasAValorJusto(
            final double aplicacoesFinanceirasAvaliadasAValorJusto) {
        this.aplicacoesFinanceirasAvaliadasAValorJusto += aplicacoesFinanceirasAvaliadasAValorJusto;
    }

    public void setAtivosBiologicos(final double ativosBiologicos) {
        this.ativosBiologicos += ativosBiologicos;
    }

    public void setCaixaEEquivalentesDeCaixa(final double caixaEEquivalentesDeCaixa) {
        this.caixaEEquivalentesDeCaixa += caixaEEquivalentesDeCaixa;
    }

    public void setCapitalSocialRealizado(final double capitalSocialRealizado) {
        this.capitalSocialRealizado += capitalSocialRealizado;
    }

    public void setContasAReceber(final double contasAReceber) {
        this.contasAReceber += contasAReceber;
    }

    public void setCreditosComPartesRelacionadas(final double creditosComPartesRelacionadas) {
        this.creditosComPartesRelacionadas += creditosComPartesRelacionadas;
    }

    public void setDespesasAntecipadas(final double despesasAntecipadas) {
        this.despesasAntecipadas += despesasAntecipadas;
    }

    public void setDiferido(final double diferido) {
        this.diferido += diferido;
    }

    public void setDividendosEJCPAPagar(final double dividendosEJCPAPagar) {
        this.dividendosEJCPAPagar += dividendosEJCPAPagar;
    }

    public void setEmprestimosEFinanciamentos(final double emprestimosEFinanciamentos) {
        this.emprestimosEFinanciamentos += emprestimosEFinanciamentos;
    }

    public void setEstoques(final double estoques) {
        this.estoques += estoques;
    }

    public void setFornecedores(final double fornecedores) {
        this.fornecedores += fornecedores;
    }

    public void setImobilizado(final double imobilizado) {
        this.imobilizado += imobilizado;
    }

    public void setIntangivel(final double intangivel) {
        this.intangivel += intangivel;
    }

    public void setInvestimentos(final double investimentos) {
        this.investimentos += investimentos;
    }

    public void setLucrosEReceitasAApropriar(final double lucrosEReceitasAApropriar) {
        this.lucrosEReceitasAApropriar += lucrosEReceitasAApropriar;
    }

    public void setLucros_PrejuizosAcumulados(final double lucros_PrejuizosAcumulados) {
        this.lucros_PrejuizosAcumulados += lucros_PrejuizosAcumulados;
    }

    public void setObrigacoesFiscais(final double obrigacoesFiscais) {
        this.obrigacoesFiscais += obrigacoesFiscais;
    }

    public void setObrigacoesSociaisETrabalhistas(final double obrigacoesSociaisETrabalhistas) {
        this.obrigacoesSociaisETrabalhistas += obrigacoesSociaisETrabalhistas;
    }

    public void setOutros(final double outros) {
        this.outros += outros;
    }

    public void setOutrosResultadosAbrangentes(final double outrosResultadosAbrangentes) {
        this.outrosResultadosAbrangentes += outrosResultadosAbrangentes;
    }

    public void setParticipacaoDosAcionistasNaoControladores(
            final double participacaoDosAcionistasNaoControladores) {
        this.participacaoDosAcionistasNaoControladores += participacaoDosAcionistasNaoControladores;
    }

    public void setPassivosComPartesRelacionadas(final double passivosComPartesRelacionadas) {
        this.passivosComPartesRelacionadas += passivosComPartesRelacionadas;
    }

    public void setPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados(
            final double passivosSobreAtivosNaoCorrentesAVendaEDescontinuados) {
        this.passivosSobreAtivosNaoCorrentesAVendaEDescontinuados += passivosSobreAtivosNaoCorrentesAVendaEDescontinuados;
    }

    public void setPatrimônioLiquido(final double patrimônioLiquido) {
        this.patrimônioLiquido += patrimônioLiquido;
    }

    public void setProvisoes(final double provisoes) {
        this.provisoes += provisoes;
    }

    public void setReservasDeCapital(final double reservasDeCapital) {
        this.reservasDeCapital += reservasDeCapital;
    }

    public void setReservasDeReavaliacao(final double reservasDeReavaliacao) {
        this.reservasDeReavaliacao += reservasDeReavaliacao;
    }

    public void setTributosARecuperar(final double tributosARecuperar) {
        this.tributosARecuperar += tributosARecuperar;
    }

    public void setTributosDiferidos(final double tributosDiferidos) {
        this.tributosDiferidos += tributosDiferidos;
    }

    public void setDataDoBalanco(final Date dataDoBalanco) {
        this.dataDoBalanco = dataDoBalanco;
    }

    public void setAtivoTotal(final double ativoTotal) {
        this.ativoTotal += ativoTotal;
    }

    public void setAtivoCirculante(final double ativoCirculante) {
        this.ativoCirculante += ativoCirculante;
    }

    public void setOutrosAtivosCirculantes(final double outrosAtivosCirculantes) {
        this.outrosAtivosCirculantes += outrosAtivosCirculantes;
    }

    public void setAtivoRealizavelALongoPrazo(final double ativoRealizavelALongoPrazo) {
        this.ativoRealizavelALongoPrazo += ativoRealizavelALongoPrazo;
    }

    public void setOutrosAtivosNaoCirculantes(final double outrosAtivosNaoCirculantes) {
        this.outrosAtivosNaoCirculantes += outrosAtivosNaoCirculantes;
    }

    public void setPassivoTotal(final double passivoTotal) {
        this.passivoTotal += passivoTotal;
    }

    public void setPassivoCirculante(final double passivoCirculante) {
        this.passivoCirculante += passivoCirculante;
    }

    public void setPassivoNaoCirculante(final double passivoNaoCirculante) {
        this.passivoNaoCirculante += passivoNaoCirculante;
    }

    public void setAplicacoesFinanceirasAvaliadasAoCustoAmortizado(
            final double aplicacoesFinanceirasAvaliadasAoCustoAmortizado) {
        this.aplicacoesFinanceirasAvaliadasAoCustoAmortizado += aplicacoesFinanceirasAvaliadasAoCustoAmortizado;
    }

    public void setAplicacoesInterfinanceirasLiquidez(final double aplicacoesInterfinanceirasLiquidez) {
        this.aplicacoesInterfinanceirasLiquidez += aplicacoesInterfinanceirasLiquidez;
    }

    public void setAtivoPermanente(final double ativoPermanente) {
        this.ativoPermanente += ativoPermanente;
    }

    public void setCaptacoesMercadoAberto(final double captacoesMercadoAberto) {
        this.captacoesMercadoAberto += captacoesMercadoAberto;
    }

    public void setDepositos(final double depositos) {
        this.depositos += depositos;
    }

    public void setDisponibilidades(final double disponibilidades) {
        this.disponibilidades += disponibilidades;
    }

    public void setImobilizadoArrendamento(final double imobilizadoArrendamento) {
        this.imobilizadoArrendamento += imobilizadoArrendamento;
    }

    public void setImobilizadoUso(final double imobilizadoUso) {
        this.imobilizadoUso += imobilizadoUso;
    }

    public void setObrigacoesPorEmprestimos(final double obrigacoesPorEmprestimos) {
        this.obrigacoesPorEmprestimos += obrigacoesPorEmprestimos;
    }

    public void setObrigacoesPorRepasseExterior(final double obrigacoesPorRepasseExterior) {
        this.obrigacoesPorRepasseExterior += obrigacoesPorRepasseExterior;
    }

    public void setObrigacoesPorRepassePais(final double obrigacoesPorRepassePais) {
        this.obrigacoesPorRepassePais += obrigacoesPorRepassePais;
    }

    public void setOperacoesArrendamentoMercantil(final double operacoesArrendamentoMercantil) {
        this.operacoesArrendamentoMercantil += operacoesArrendamentoMercantil;
    }

    public void setOperacoesCredito(final double operacoesCredito) {
        this.operacoesCredito += operacoesCredito;
    }

    public void setOutrasObrigacoes(final double outrasObrigacoes) {
        this.outrasObrigacoes += outrasObrigacoes;
    }

    public void setOutrosCreditos(final double outrosCreditos) {
        this.outrosCreditos += outrosCreditos;
    }

    public void setOutrosValoresBens(final double outrosValoresBens) {
        this.outrosValoresBens += outrosValoresBens;
    }

    public void setPartAcionistasNaoControladores(final double partAcionistasNaoControladores) {
        this.partAcionistasNaoControladores += partAcionistasNaoControladores;
    }

    public void setPassivoExigivelLongoPrazo(final double passivoExigivelLongoPrazo) {
        this.passivoExigivelLongoPrazo += passivoExigivelLongoPrazo;
    }

    public void setRecursosAceitesEmissaoTitulos(final double recursosAceitesEmissaoTitulos) {
        this.recursosAceitesEmissaoTitulos += recursosAceitesEmissaoTitulos;
    }

    public void setRelacoesInterdependencias(final double relacoesInterdependencias) {
        this.relacoesInterdependencias += relacoesInterdependencias;
    }

    public void setRelacoesInterfinanceiras(final double relacoesInterfinanceiras) {
        this.relacoesInterfinanceiras += relacoesInterfinanceiras;
    }

    public void setReservasLucro(final double reservasLucro) {
        this.reservasLucro += reservasLucro;
    }

    public void setResultadosExerciciosFuturos(final double resultadosExerciciosFuturos) {
        this.resultadosExerciciosFuturos += resultadosExerciciosFuturos;
    }

    public void setTitulosValoresMobiliarios(final double titulosValoresMobiliarios) {
        this.titulosValoresMobiliarios += titulosValoresMobiliarios;
    }

    @Override
    public String toString() {
        return "Date:" + getDataDoBalanco();

    }

}
