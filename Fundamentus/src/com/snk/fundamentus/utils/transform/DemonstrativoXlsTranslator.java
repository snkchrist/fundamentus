package com.snk.fundamentus.utils.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;

import org.apache.log4j.Logger;

import com.snk.fundamentus.interfaces.IXslReaderBalanco;
import com.snk.fundamentus.models.DemonstrativoResultado;

public class DemonstrativoXlsTranslator implements IXslReaderBalanco<DemonstrativoResultado> {

    private final Logger logger = Logger.getLogger(DemonstrativoXlsTranslator.class);

    @Override
    public List<DemonstrativoResultado> decodeXsl(final Workbook workbook) {
        jxl.Sheet sheet1 = workbook.getSheet(1);

        int columnsLength = sheet1.getColumns();
        int rowsLength = sheet1.getRows();

        List<DemonstrativoResultado> demonstratitoList = new ArrayList<DemonstrativoResultado>();

        for (int column = 1; column < columnsLength; column++) {
            DemonstrativoResultado demonstrativo = new DemonstrativoResultado();

            for (int row = 0; row < rowsLength; row++) {

                jxl.Cell cell2 = sheet1.getCell(0, row);
                String column0 = cell2.getContents();

                jxl.Cell cell = sheet1.getCell(column, row);
                String value = cell.getContents().replaceAll("[.,]", "");

                if (null != value && false == value.isEmpty()) {
                    translateCell(demonstrativo, column0, value);
                }
            }

            if (null != demonstrativo.getDataDemonstrativo()) {
                demonstratitoList.add(demonstrativo);
            }
        }

        return demonstratitoList;
    }

    @Override
    public void translateCell(final DemonstrativoResultado demons, final String tituloPrimeiraColuna, final String valorDaColunaStr) {
        if (tituloPrimeiraColuna.trim().isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parse;
            try {
                parse = format.parse(valorDaColunaStr);
                demons.setDataDemonstrativo(parse);
                return;
            }
            catch (ParseException e) {
            }
        }

        double valorDaColuna;
        try {
            valorDaColuna = Double.parseDouble(valorDaColunaStr) * 1000;
        }
        catch (NumberFormatException exp) {
            logger.info(tituloPrimeiraColuna);
            return;
        }

        switch (tituloPrimeiraColuna) {
            case "Receita Bruta de Vendas e/ou Serviços":
                demons.setReceitaBrutaVendasServicos(valorDaColuna);
                break;
            case "Deduções da Receita Bruta":
                demons.setDeducoesReceitaBruta(valorDaColuna);
                break;
            case "Receita Líquida de Vendas e/ou Serviços":
                demons.setReceitaLiquidaVendasServicos(valorDaColuna);
                break;
            case "Custo de Bens e/ou Serviços Vendidos":
                demons.setCustoBensServicosVendidos(valorDaColuna);
                break;
            case "Resultado Bruto":
                demons.setResultadoBruto(valorDaColuna);
                break;
            case "Despesas Com Vendas":
                demons.setDespesasComVendas(valorDaColuna);
                break;
            case "Despesas Gerais e Administrativas":
                demons.setDespesasGeraisAdministrativas(valorDaColuna);
                break;
            case "Perdas pela Não Recuperabilidade de Ativos":
                demons.setPerdasPelaNaoRecuperabilidadeAtivos(valorDaColuna);
                break;
            case "Outras Receitas Operacionais":
                demons.setOutrasReceitasOperacionais(valorDaColuna);
                break;
            case "Outras Despesas Operacionais":
                demons.setOutrasDespesasOperacionais(valorDaColuna);
                break;
            case "Resultado da Equivalência Patrimonial":
                demons.setResultadoEquivalenciaPatrimonial(valorDaColuna);
                break;
            case "Financeiras":
                demons.setFinanceiras(valorDaColuna);
                break;
            case "Receitas Financeiras":
                demons.setReceitasFinanceiras(valorDaColuna);
                break;
            case "Despesas Financeiras":
                demons.setDespesasFinanceiras(valorDaColuna);
                break;
            case "Resultado Não Operacional":
                demons.setResultadoNaoOperacional(valorDaColuna);
                break;
            case "Receitas":
                demons.setReceitas(valorDaColuna);
                break;
            case "Despesas":
                demons.setDespesas(valorDaColuna);
                break;
            case "Resultado Antes Tributação/Participações":
                demons.setResultadoAntesTributacao_Participacoes(valorDaColuna);
                break;
            case "Provisão para IR e Contribuição Social":
                demons.setProvisaoParaIRContribuicaoSocial(valorDaColuna);
                break;
            case "IR Diferido":
                demons.setIRDiferido(valorDaColuna);
                break;
            case "Participações/Contribuições Estatutárias":
                demons.setParticipacoes_ContribuicoesEstatutarias(valorDaColuna);
                break;
            case "Reversão dos Juros sobre Capital Próprio":
                demons.setReversaoJurosSobreCapitalProprio(valorDaColuna);
                break;
            case "Part. de Acionistas Não Controladores":
                demons.setPartAcionistasNaoControladores(valorDaColuna);
                break;
            case "Lucro/Prejuízo do Período":
                demons.setLucro_PrejuizoPeriodo(valorDaColuna);
                break;
            case "Despesas da Intermediação Financeira":
                demons.setDespesasIntermediaçaoFinanceira(valorDaColuna);
                break;
            case "Despesas de Pessoal":
                demons.setDespesasPessoal(valorDaColuna);
                break;
            case "Despesas Tributárias":
                demons.setDespesasTributarias(valorDaColuna);
                break;
            case "Outras Despesas Administrativas":
                demons.setOutrasDespesasAdministrativas(valorDaColuna);
                break;
            case "Outras Despesas/Receitas Operacionais":
                demons.setOutrasDespesasReceitasOperacionais(valorDaColuna);
                break;
            case "Receitas da Intermediação Financeira":
                demons.setReceitasIntermediaçaoFinanceira(valorDaColuna);
                break;
            case "Receitas de Prestação de Serviços":
                demons.setReceitasPrestaçaoServicos(valorDaColuna);
                break;
            case "Resultado Bruto Intermediação Financeira":
                demons.setResultadoBrutoIntermediacaoFinanceira(valorDaColuna);
                break;
            case "Resultado Operacional":
                demons.setResultadoOperacional(valorDaColuna);
                break;
            default:
                break;
        }

    }
}
