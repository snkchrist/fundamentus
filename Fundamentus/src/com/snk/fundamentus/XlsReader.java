package com.snk.fundamentus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.snk.fundamentus.models.BalancoPatrimonial;


public class XlsReader {

    public static void main(String[] args) throws IOException, ParseException {
        FileInputStream file = new FileInputStream(new File("H:\\balanco.xlsx"));

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();

        int maximumNumberOfCells = 32;

        for (int i = 1; i <= maximumNumberOfCells; i++) {
            BalancoPatrimonial balanco = new BalancoPatrimonial();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // row.getPhysicalNumberOfCells();
                if (row.getRowNum() < 1)
                    continue;

                Cell cell = row.getCell(0);

                if (null != cell) {
                    String stringValueFromCell = cell.getStringCellValue();
                    double numericValueFromCell = 0.0;
                    try {
                        String stringFromCell = row.getCell(i).getStringCellValue().replace(".", "");
                        double parseDouble = Double.parseDouble(stringFromCell);
                        numericValueFromCell = parseDouble;
                    }
                    catch (Exception e) {
                        System.out.println("An error occured");
                    }

                    if (stringValueFromCell.equals("Ativo Total"))
                        balanco.setAtivoTotal(numericValueFromCell);

                    if (stringValueFromCell.equals("Adiantamento para Futuro Aumento Capital"))
                        balanco.setAdiantamentoParaFuturoAumentoCapital(numericValueFromCell);

                    if (stringValueFromCell.equals("Ajustes Acumulados de Conversão"))
                        balanco.setAjustesAcumuladosDeConversao(numericValueFromCell);

                    if (stringValueFromCell.equals("Ajustes de Avaliação Patrimonial"))
                        balanco.setAjustesDeAvaliacaoPatrimonial(numericValueFromCell);

                    if (stringValueFromCell.equals("Aplicações Financeiras"))
                        balanco.setAplicacoesFinanceiras(numericValueFromCell);

                    if (stringValueFromCell.equals("Aplicações Financeiras Avaliadas a Valor Justo"))
                        balanco.setAplicacoesFinanceirasAvaliadasAValorJusto(numericValueFromCell);

                    if (stringValueFromCell.equals("Aplicações Financeiras Avaliadas ao Custo Amortizado"))
                        balanco.setAplicacoesFinanceirasAvaliadasAoCustoAmortizado(numericValueFromCell);

                    if (stringValueFromCell.equals("Ativo Circulante"))
                        balanco.setAtivoCirculante(numericValueFromCell);

                    if (stringValueFromCell.equals("Ativo Realizável a Longo Prazo"))
                        balanco.setAtivoRealizavelALongoPrazo(numericValueFromCell);

                    if (stringValueFromCell.equals("Ativos Biológicos"))
                        balanco.setAtivosBiologicos(numericValueFromCell);

                    if (stringValueFromCell.equals("Caixa e Equivalentes de Caixa"))
                        balanco.setCaixaEEquivalentesDeCaixa(numericValueFromCell);

                    if (stringValueFromCell.equals("Capital Social Realizado"))
                        balanco.setCapitalSocialRealizado(numericValueFromCell);

                    if (stringValueFromCell.equals("Contas a Receber"))
                        balanco.setContasAReceber(numericValueFromCell);

                    if (stringValueFromCell.equals("Créditos com Partes Relacionadas"))
                        balanco.setCreditosComPartesRelacionadas(numericValueFromCell);

                    if (stringValueFromCell.equals("Despesas Antecipadas"))
                        balanco.setDespesasAntecipadas(numericValueFromCell);

                    if (stringValueFromCell.equals("Diferido"))
                        balanco.setDiferido(numericValueFromCell);

                    if (stringValueFromCell.equals("Dividendos e JCP a Pagar"))
                        balanco.setDividendosEJCPAPagar(numericValueFromCell);

                    if (stringValueFromCell.equals("Empréstimos e Financiamentos"))
                        balanco.setEmprestimosEFinanciamentos(numericValueFromCell);

                    if (stringValueFromCell.equals("Estoques"))
                        balanco.setEstoques(numericValueFromCell);

                    if (stringValueFromCell.equals("Fornecedores"))
                        balanco.setFornecedores(numericValueFromCell);

                    if (stringValueFromCell.equals("Imobilizado"))
                        balanco.setImobilizado(numericValueFromCell);

                    if (stringValueFromCell.equals("Intangível"))
                        balanco.setIntangivel(numericValueFromCell);

                    if (stringValueFromCell.equals("Investimentos"))
                        balanco.setInvestimentos(numericValueFromCell);

                    if (stringValueFromCell.equals("Lucros e Receitas a Apropriar"))
                        balanco.setLucrosEReceitasAApropriar(numericValueFromCell);

                    if (stringValueFromCell.equals("Lucros/Prejuízos Acumulados"))
                        balanco.setLucros_PrejuizosAcumulados(numericValueFromCell);

                    if (stringValueFromCell.equals("Obrigações Fiscais"))
                        balanco.setObrigacoesFiscais(numericValueFromCell);

                    if (stringValueFromCell.equals("Obrigações Sociais e Trabalhistas"))
                        balanco.setObrigacoesSociaisETrabalhistas(numericValueFromCell);

                    if (stringValueFromCell.equals("Outros"))
                        balanco.setOutros(numericValueFromCell);

                    if (stringValueFromCell.equals("Outros Ativos Circulantes"))
                        balanco.setOutrosAtivosCirculantes(numericValueFromCell);

                    if (stringValueFromCell.equals("Outros Ativos Não Circulantes"))
                        balanco.setOutrosAtivosNaoCirculantes(numericValueFromCell);

                    if (stringValueFromCell.equals("Outros Resultados Abrangentes"))
                        balanco.setOutrosResultadosAbrangentes(numericValueFromCell);

                    if (stringValueFromCell.equals("Participação dos Acionistas Não Controladores"))
                        balanco.setParticipacaoDosAcionistasNaoControladores(numericValueFromCell);

                    if (stringValueFromCell.equals("Passivo Circulante"))
                        balanco.setPassivoCirculante(numericValueFromCell);

                    if (stringValueFromCell.equals("Passivo Não Circulante"))
                        balanco.setPassivoNaoCirculante(numericValueFromCell);

                    if (stringValueFromCell.equals("Passivo Total"))
                        balanco.setPassivoTotal(numericValueFromCell);

                    if (stringValueFromCell.equals("Passivos com Partes Relacionadas"))
                        balanco.setPassivosComPartesRelacionadas(numericValueFromCell);

                    if (stringValueFromCell.equals("Passivos sobre Ativos Não-Correntes a Venda e Descontinuados"))
                        balanco.setPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados(numericValueFromCell);

                    if (stringValueFromCell.equals("Patrimônio Líquido"))
                        balanco.setPatrimônioLiquido(numericValueFromCell);

                    if (stringValueFromCell.equals("Provisões"))
                        balanco.setProvisoes(numericValueFromCell);

                    if (stringValueFromCell.equals("Reservas de Capital"))
                        balanco.setReservasDeCapital(numericValueFromCell);

                    if (stringValueFromCell.equals("Reservas de Lucros"))
                        balanco.setReservasDeLucros(numericValueFromCell);

                    if (stringValueFromCell.equals("Reservas de Reavaliação"))
                        balanco.setReservasDeReavaliacao(numericValueFromCell);

                    if (stringValueFromCell.equals("Tributos a Recuperar"))
                        balanco.setTributosARecuperar(numericValueFromCell);

                    if (stringValueFromCell.equals("Tributos Diferidos"))
                        balanco.setTributosDiferidos(numericValueFromCell);

                }
                else {
                    // int physicalNumberOfCells =
                    // row.getPhysicalNumberOfCells();
                    String stringCellValue = row.getCell(i).getStringCellValue();

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date parse = format.parse(stringCellValue);

                    balanco.setDataDoBalanco(parse);

                }

                // case Cell.CELL_TYPE_STRING:
                // case Cell.CELL_TYPE_NUMERIC:

            }

            System.out.println("Done with " + balanco.getDataDoBalanco());
        }

        file.close();
    }
}
