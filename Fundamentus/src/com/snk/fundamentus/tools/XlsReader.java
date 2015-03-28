package com.snk.fundamentus.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.EmpresaDao;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;


public class XlsReader {

    private final static Logger logger = Logger.getLogger(XlsReader.class);

    public static void main(final String[] args)
        throws IOException, ParseException, BiffException {
        /*
         * XlsReader reader = new XlsReader(); reader.importXLSDataIntoDb();
         */

        DaoFactory dao = new DaoFactory();
        EmpresaDao empresaDao = dao.getEmpresaDao();
        List<Empresa> listAllElements = empresaDao.listAllElements();

        for (Empresa empresa : listAllElements) {
            List<BalancoPatrimonial> balancoList = empresa.getBalancoList();

            List<BalancoPatrimonial> listaBalancoAtualizada = new ArrayList<BalancoPatrimonial>();

            for (BalancoPatrimonial balancoPatrimonial : balancoList) {
                if (null != balancoPatrimonial.getDataDoBalanco()) {
                    listaBalancoAtualizada.add(balancoPatrimonial);
                }
            }

            empresaDao.beginTransaction();
            empresa.setBalancoList(listaBalancoAtualizada);
            empresaDao.commitTransaction();
        }

    }

    private void importXLSDataIntoDb()
        throws FileNotFoundException, IOException, ParseException, BiffException {
        String path = "C:/xls";
        File file = new File(path);
        XlsReader reader = new XlsReader();
        DaoFactory daoFactory = new DaoFactory();
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();

            for (File file2 : listFiles) {
                logger.info("Start reading file [" + file2.getName() + "]");
                List<DemonstrativoResultado> readOldXLSFiles = reader.readOldXLSFiles(file2);
                String acaoOlhar = file2.getName().replaceFirst("[.][^.]+$", "");

                Empresa empresa = empresaDao.findListBySigla(acaoOlhar);

                if (null != empresa) {

                    empresaDao.getEm().getTransaction().begin();
                    empresa.setDemonstrativoList(readOldXLSFiles);
                    empresaDao.getEm().getTransaction().commit();
                }

                logger.info("BalancoPatrimonial created by the filename ["
                    + file2.getPath() + "]");
            }
        }
    }

    private List<DemonstrativoResultado> readOldXLSFiles(final File fileName)
        throws FileNotFoundException, IOException, ParseException, BiffException {

        jxl.Workbook workbook = jxl.Workbook.getWorkbook(fileName);

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
                    setDemonstrativoResultado(demonstrativo, column0, value);
                }
                else {
                    logger.info("Value [" + value + "] is empty or null.");
                }

            }

            if (null != demonstrativo.getDataDemonstrativo())
                demonstratitoList.add(demonstrativo);
        }

        // jxl.Cell cell = sheet1.getCell(0, 0);
        workbook.close();

        return demonstratitoList;
    }

    private void setDemonstrativoResultado(
        final DemonstrativoResultado demons,
        final String tituloPrimeiraColuna,
        final String valorDaColunaStr) {

        if (tituloPrimeiraColuna.trim().isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parse;
            try {
                parse = format.parse(valorDaColunaStr);
                demons.setDataDemonstrativo(parse);
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
            logger.error("NumberFormatException: not able to parse [" + valorDaColunaStr
                + "]", exp);
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
        default:
            break;
        }

    }

    private void setBalancoAttributeValue(
        final BalancoPatrimonial balanco,
        final String tituloPrimeiraColuna,
        final String valorDaColunaStr) {

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
            logger.error("NumberFormatException: not able to parse [" + valorDaColunaStr
                + "]", exp);
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
