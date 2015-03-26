package com.snk.fundamentus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.EmpresaDao;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.Empresa;

public class XlsReader {

    private final static Logger logger = Logger.getLogger(XlsReader.class);

    public static void main(final String[] args)
            throws IOException, ParseException, BiffException {

        String path = "C:/Users/ribeifil/Downloads/xls";
        File file = new File(path);
        XlsReader reader = new XlsReader();
        DaoFactory daoFactory = new DaoFactory();
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();

            for (File file2 : listFiles) {
                logger.info("Start reading file [" + file2.getName() + "]");
                List<BalancoPatrimonial> readOldXLSFiles = reader.readOldXLSFiles(file2);
                String acaoOlhar = file2.getName().replaceFirst("[.][^.]+$", "");

                Empresa empresa = empresaDao.findListBySigla(acaoOlhar);

                if (null != empresa) {

                    empresaDao.getEm().getTransaction().begin();
                    empresa.setBalancoList(readOldXLSFiles);
                    empresaDao.getEm().getTransaction().commit();
                }

                logger.info("BalancoPatrimonial created by the filename [" + file2.getPath() + "]");
            }
        }
    }

    private List<BalancoPatrimonial> readOldXLSFiles(final File fileName)
            throws FileNotFoundException, IOException, ParseException, BiffException {

        jxl.Workbook workbook = jxl.Workbook.getWorkbook(fileName);

        jxl.Sheet sheet1 = workbook.getSheet(0);

        int columnsLength = sheet1.getColumns();
        int rowsLength = sheet1.getRows();

        List<BalancoPatrimonial> balancoList = new ArrayList<BalancoPatrimonial>();

        for (int column = 1; column < columnsLength; column++) {
            BalancoPatrimonial balanco = new BalancoPatrimonial();

            logger.info("Starting next column [" + column + "]");

            for (int row = 0; row < rowsLength; row++) {

                jxl.Cell cell2 = sheet1.getCell(0, row);
                String column0 = cell2.getContents();

                jxl.Cell cell = sheet1.getCell(column, row);
                String value = cell.getContents().replaceAll("[.,]", "");

                if (null != value && false == value.isEmpty()) {
                    setBalancoAttributeValue(balanco, column0, value);
                }
                else {
                    logger.info("Value [" + value + "] is empty or null.");
                }

            }

            balancoList.add(balanco);
        }

        //jxl.Cell cell = sheet1.getCell(0, 0);
        workbook.close();

        return balancoList;
    }

    private void setBalancoAttributeValue(
            final BalancoPatrimonial balanco,
            final String tituloPrimeiraColuna,
            final String valorDaColunaStr) {

        logger.info("checking if the element [" + tituloPrimeiraColuna + "] exists");

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

        else if (false == StringUtils.isNumeric(valorDaColunaStr)) {
            return;
        }

        double valorDaColuna = Double.parseDouble(valorDaColunaStr) * 1000;

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
            balanco.setReservasDeLucros(valorDaColuna);
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
    }
}
