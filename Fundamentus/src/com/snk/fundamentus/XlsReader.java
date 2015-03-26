package com.snk.fundamentus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.snk.fundamentus.models.BalancoPatrimonial;

public class XlsReader {

    private final Logger logger = Logger.getLogger(XlsReader.class);

    public Logger getLogger() {
        return logger;
    }

    public static void main(final String[] args)
            throws IOException, ParseException, BiffException {

        String path = "C:/Users/ribeifil/Downloads/xls";
        File file = new File(path);
        XlsReader reader = new XlsReader();

        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();

            for (File file2 : listFiles) {
                BalancoPatrimonial balanco = reader.readOldXLSFiles(file2);
                reader.getLogger().info("BalancoPatrimonial created by the filename [" + file2.getPath() + "]");
            }
        }
    }

    private BalancoPatrimonial readOldXLSFiles(final File fileName)
            throws FileNotFoundException, IOException, ParseException, BiffException {

        BalancoPatrimonial balanco = new BalancoPatrimonial();

        jxl.Workbook workbook = jxl.Workbook.getWorkbook(fileName);

        jxl.Sheet sheet1 = workbook.getSheet(0);

        int columnsLength = sheet1.getColumns();
        int rowsLength = sheet1.getRows();
        //jxl.Cell cell = sheet1.getCell(0, 0);
        //String str_colArow1 = cell.getContents();

        return balanco;
    }

    private BalancoPatrimonial readXLSXFiles(final File fileName)
            throws FileNotFoundException, IOException, ParseException {

        FileInputStream file = new FileInputStream(fileName);

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        BalancoPatrimonial balanco = excelRowsToBalanco(sheet);
        file.close();

        return balanco;
    }

    private BalancoPatrimonial readXLSFiles(final File fileName)
            throws FileNotFoundException, IOException, ParseException {

        FileInputStream file = new FileInputStream(fileName);

        HSSFWorkbook workbook = new HSSFWorkbook(file);
        // Get first/desired sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);
        BalancoPatrimonial balanco = excelRowsToBalanco(sheet);

        file.close();

        return balanco;
    }

    private BalancoPatrimonial excelRowsToBalanco(final Sheet sheet)
            throws ParseException {

        getLogger().info("Starting to read the sheet.");
        BalancoPatrimonial balanco = new BalancoPatrimonial();
        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();

        int maximumNumberOfCells = 32;

        for (int i = 1; i <= maximumNumberOfCells; i++) {
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // row.getPhysicalNumberOfCells();
                if (row.getRowNum() < 1) {
                    continue;
                }

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
                        getLogger().error("Error during the parse of a element from the sheet", e);
                    }

                    setBalancoAttributeValue(balanco, stringValueFromCell, numericValueFromCell);
                }
                else {
                    getLogger().info("Cell is null. Checking if is the date heading.");
                    // int physicalNumberOfCells =
                    // row.getPhysicalNumberOfCells();
                    String stringCellValue = row.getCell(i).getStringCellValue();

                    getLogger().info("Parsing data header.");
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date parse = format.parse(stringCellValue);

                    balanco.setDataDoBalanco(parse);
                    getLogger().info("Date is [" + parse + "]");

                }
            }
        }

        return balanco;
    }

    private void setBalancoAttributeValue(
            final BalancoPatrimonial balanco,
            final String tituloPrimeiraColuna,
            final double valorDaColuna) {

        getLogger().info("checking if the element [" + tituloPrimeiraColuna + "] exists");

        if (tituloPrimeiraColuna.equals("Ativo Total")) {
            balanco.setAtivoTotal(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Adiantamento para Futuro Aumento Capital")) {
            balanco.setAdiantamentoParaFuturoAumentoCapital(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Ajustes Acumulados de Conversão")) {
            balanco.setAjustesAcumuladosDeConversao(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Ajustes de Avaliação Patrimonial")) {
            balanco.setAjustesDeAvaliacaoPatrimonial(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Aplicações Financeiras")) {
            balanco.setAplicacoesFinanceiras(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Aplicações Financeiras Avaliadas a Valor Justo")) {
            balanco.setAplicacoesFinanceirasAvaliadasAValorJusto(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Aplicações Financeiras Avaliadas ao Custo Amortizado")) {
            balanco.setAplicacoesFinanceirasAvaliadasAoCustoAmortizado(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Ativo Circulante")) {
            balanco.setAtivoCirculante(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Ativo Realizável a Longo Prazo")) {
            balanco.setAtivoRealizavelALongoPrazo(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Ativos Biológicos")) {
            balanco.setAtivosBiologicos(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Caixa e Equivalentes de Caixa")) {
            balanco.setCaixaEEquivalentesDeCaixa(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Capital Social Realizado")) {
            balanco.setCapitalSocialRealizado(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Contas a Receber")) {
            balanco.setContasAReceber(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Créditos com Partes Relacionadas")) {
            balanco.setCreditosComPartesRelacionadas(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Despesas Antecipadas")) {
            balanco.setDespesasAntecipadas(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Diferido")) {
            balanco.setDiferido(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Dividendos e JCP a Pagar")) {
            balanco.setDividendosEJCPAPagar(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Empréstimos e Financiamentos")) {
            balanco.setEmprestimosEFinanciamentos(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Estoques")) {
            balanco.setEstoques(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Fornecedores")) {
            balanco.setFornecedores(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Imobilizado")) {
            balanco.setImobilizado(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Intangível")) {
            balanco.setIntangivel(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Investimentos")) {
            balanco.setInvestimentos(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Lucros e Receitas a Apropriar")) {
            balanco.setLucrosEReceitasAApropriar(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Lucros/Prejuízos Acumulados")) {
            balanco.setLucros_PrejuizosAcumulados(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Obrigações Fiscais")) {
            balanco.setObrigacoesFiscais(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Obrigações Sociais e Trabalhistas")) {
            balanco.setObrigacoesSociaisETrabalhistas(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Outros")) {
            balanco.setOutros(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Outros Ativos Circulantes")) {
            balanco.setOutrosAtivosCirculantes(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Outros Ativos Não Circulantes")) {
            balanco.setOutrosAtivosNaoCirculantes(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Outros Resultados Abrangentes")) {
            balanco.setOutrosResultadosAbrangentes(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Participação dos Acionistas Não Controladores")) {
            balanco.setParticipacaoDosAcionistasNaoControladores(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Passivo Circulante")) {
            balanco.setPassivoCirculante(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Passivo Não Circulante")) {
            balanco.setPassivoNaoCirculante(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Passivo Total")) {
            balanco.setPassivoTotal(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Passivos com Partes Relacionadas")) {
            balanco.setPassivosComPartesRelacionadas(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Passivos sobre Ativos Não-Correntes a Venda e Descontinuados")) {
            balanco.setPassivosSobreAtivosNaoCorrentesAVendaEDescontinuados(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Patrimônio Líquido")) {
            balanco.setPatrimônioLiquido(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Provisões")) {
            balanco.setProvisoes(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Reservas de Capital")) {
            balanco.setReservasDeCapital(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Reservas de Lucros")) {
            balanco.setReservasDeLucros(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Reservas de Reavaliação")) {
            balanco.setReservasDeReavaliacao(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Tributos a Recuperar")) {
            balanco.setTributosARecuperar(valorDaColuna);
        }

        if (tituloPrimeiraColuna.equals("Tributos Diferidos")) {
            balanco.setTributosDiferidos(valorDaColuna);
        }
    }
}
