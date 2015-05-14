package com.snk.fundamentus.utils.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;

import org.apache.log4j.Logger;

import com.snk.fundamentus.interfaces.IXslReaderBalanco;
import com.snk.fundamentus.models.BalancoPatrimonial;

public class BalancoXlsTranslator implements IXslReaderBalanco<BalancoPatrimonial> {
    private final static Logger logger = Logger.getLogger(BalancoXlsTranslator.class);

    @Override
    public List<BalancoPatrimonial> decodeXsl(final Workbook workbook) {
        jxl.Sheet sheet1 = workbook.getSheet(0);

        int columnsLength = sheet1.getColumns();
        int rowsLength = sheet1.getRows();

        List<BalancoPatrimonial> balancoList = new ArrayList<BalancoPatrimonial>();

        for (int column = 1; column < columnsLength; column++) {
            BalancoPatrimonial balancoPatrimonial = new BalancoPatrimonial();

            for (int row = 0; row < rowsLength; row++) {

                jxl.Cell cell2 = sheet1.getCell(0, row);
                String column0 = cell2.getContents();

                jxl.Cell cell = sheet1.getCell(column, row);
                String value = cell.getContents().replaceAll("[.,]", "");

                if (null != value && false == value.isEmpty()) {
                    translateCell(balancoPatrimonial, column0, value);
                }
            }

            if (null != balancoPatrimonial.getDataDoBalanco()) {
                balancoList.add(balancoPatrimonial);
            }
        }

        return balancoList;

    }

    @Override
    public void translateCell(final BalancoPatrimonial balanco, final String tituloPrimeiraColuna, final String valorDaColunaStr) {
        if (tituloPrimeiraColuna.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parse;
            try {
                parse = format.parse(valorDaColunaStr);
                balanco.setDataDoBalanco(parse);
                return;
            }
            catch (ParseException e) {
                logger.error("An error was throwed", e);
            }
        }

        double valorDaColuna;
        try {
            valorDaColuna = Double.parseDouble(valorDaColunaStr) * 1000;
        }
        catch (NumberFormatException exp) {
            return;
        }

        if (tituloPrimeiraColuna.equals("Ativo Total")) {
            balanco.setAtivoTotal(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Adiantamento para Futuro Aumento Capital")) {
            balanco.setAdiantamentoParaFuturoAumentoCapital(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Ajustes Acumulados de Conversão")) {
            balanco.setAjustesAcumuladosDeConversao(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Ajustes de Avaliação Patrimonial")) {
            balanco.setAjustesDeAvaliacaoPatrimonial(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Aplicações Financeiras")) {
            balanco.setAplicacoesFinanceiras(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Aplicações Financeiras Avaliadas a Valor Justo")) {
            balanco.setAplicacoesFinanceirasAvaliadasAValorJusto(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Aplicações Financeiras Avaliadas ao Custo Amortizado")) {
            balanco.setAplicacoesFinanceirasAvaliadasAoCustoAmortizado(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Ativo Circulante")) {
            balanco.setAtivoCirculante(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Ativo Realizável a Longo Prazo")) {
            balanco.setAtivoRealizavelALongoPrazo(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Ativos Biológicos")) {
            balanco.setAtivosBiologicos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Caixa e Equivalentes de Caixa")) {
            balanco.setCaixaEEquivalentesDeCaixa(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Capital Social Realizado")) {
            balanco.setCapitalSocialRealizado(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Contas a Receber")) {
            balanco.setContasAReceber(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Créditos com Partes Relacionadas")) {
            balanco.setCreditosComPartesRelacionadas(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Despesas Antecipadas")) {
            balanco.setDespesasAntecipadas(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Diferido")) {
            balanco.setDiferido(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Dividendos e JCP a Pagar")) {
            balanco.setDividendosEJCPAPagar(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Empréstimos e Financiamentos")) {
            balanco.setEmprestimosEFinanciamentos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Estoques")) {
            balanco.setEstoques(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Fornecedores")) {
            balanco.setFornecedores(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Imobilizado")) {
            balanco.setImobilizado(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Intangível")) {
            balanco.setIntangivel(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Investimentos")) {
            balanco.setInvestimentos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Lucros e Receitas a Apropriar")) {
            balanco.setLucrosEReceitasAApropriar(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Lucros/Prejuízos Acumulados")) {
            balanco.setLucros_PrejuizosAcumulados(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Obrigações Fiscais")) {
            balanco.setObrigacoesFiscais(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Obrigações Sociais e Trabalhistas")) {
            balanco.setObrigacoesSociaisETrabalhistas(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outros")) {
            balanco.setOutros(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outros Ativos Circulantes")) {
            balanco.setOutrosAtivosCirculantes(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outros Ativos Não Circulantes")) {
            balanco.setOutrosAtivosNaoCirculantes(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outros Resultados Abrangentes")) {
            balanco.setOutrosResultadosAbrangentes(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Participação dos Acionistas Não Controladores")) {
            balanco.setParticipacaoDosAcionistasNaoControladores(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Passivo Circulante")) {
            balanco.setPassivoCirculante(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Passivo Não Circulante")) {
            balanco.setPassivoNaoCirculante(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Passivo Total")) {
            balanco.setPassivoTotal(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Passivos com Partes Relacionadas")) {
            balanco.setPassivosComPartesRelacionadas(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Passivos sobre Ativos Não-Correntes a Venda e Descontinuados")) {
            balanco.setPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Patrimônio Líquido")) {
            balanco.setPatrimônioLiquido(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Provisões")) {
            balanco.setProvisoes(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Reservas de Capital")) {
            balanco.setReservasDeCapital(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Reservas de Lucros")) {
            balanco.setReservasLucro(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Reservas de Reavaliação")) {
            balanco.setReservasDeReavaliacao(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Tributos a Recuperar")) {
            balanco.setTributosARecuperar(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Tributos Diferidos")) {
            balanco.setTributosDiferidos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Aplicações Interfinanceiras de Liquidez")) {
            balanco.setAplicacoesInterfinanceirasLiquidez(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Ativo Permanente")) {
            balanco.setAtivoPermanente(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Captações no Mercado Aberto")) {
            balanco.setCaptacoesMercadoAberto(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Depósitos")) {
            balanco.setDepositos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Disponibilidades")) {
            balanco.setDisponibilidades(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Imobilizado de Arrendamento")) {
            balanco.setImobilizadoArrendamento(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Imobilizado de Uso")) {
            balanco.setImobilizadoUso(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Obrigações por Empréstimos")) {
            balanco.setObrigacoesPorEmprestimos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Obrigações por Repasse do Exterior")) {
            balanco.setObrigacoesPorRepasseExterior(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Obrigações por Repasse do País")) {
            balanco.setObrigacoesPorRepassePais(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Operações de Arrendamento Mercantil")) {
            balanco.setOperacoesArrendamentoMercantil(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Operações de Crédito")) {
            balanco.setOperacoesCredito(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outras Obrigações")) {
            balanco.setOutrasObrigacoes(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outros Créditos")) {
            balanco.setOutrosCreditos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Outros Valores e Bens")) {
            balanco.setOutrosValoresBens(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Part. de Acionistas Não Controladores")) {
            balanco.setPartAcionistasNaoControladores(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Passivo Exigível a Longo Prazo")) {
            balanco.setPassivoExigivelLongoPrazo(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Recursos de Aceites e Emissão de Títulos")) {
            balanco.setRecursosAceitesEmissaoTitulos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Relações Interdependências")) {
            balanco.setRelacoesInterdependencias(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Relações Interfinanceiras")) {
            balanco.setRelacoesInterfinanceiras(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Reservas de Lucro")) {
            balanco.setReservasLucro(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Resultados de Exercícios Futuros")) {
            balanco.setResultadosExerciciosFuturos(valorDaColuna);
        }

        else if (tituloPrimeiraColuna.equals("Títulos e Valores Mobiliários")) {
            balanco.setTitulosValoresMobiliarios(valorDaColuna);
        }

    }
}
