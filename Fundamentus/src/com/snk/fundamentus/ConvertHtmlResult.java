package com.snk.fundamentus;

import java.net.UnknownHostException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import com.snk.fundamentus.database.Database;
import com.snk.fundamentus.models.Balanco;
import com.snk.fundamentus.models.Demonstrativo12Meses;
import com.snk.fundamentus.models.Demonstrativo3Meses;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.models.Oscilacoes;


public class ConvertHtmlResult {

    private String html;

    public ConvertHtmlResult(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public static void main(String[] args)
        throws ParserConfigurationException, XPathExpressionException,
        UnknownHostException {

    }

    public Empresa getEmpresa()
        throws XPathExpressionException, ParserConfigurationException {

        Empresa empresa = htmlToEmpresaObject();
        Balanco htmlToBalancoObject = htmlToBalancoObject();
        Oscilacoes htmlToOscilacoes = htmlToOscilacoes();
        Demonstrativo3Meses htmlToDemonstrativo3Meses = htmlToDemonstrativo3Meses();
        Demonstrativo12Meses htmlToDemonstrativo12Meses = htmlToDemonstrativo12Meses();

        empresa.setBalanco(htmlToBalancoObject);
        empresa.setOscilacoes(htmlToOscilacoes);
        empresa.setDemonstracao3meses(htmlToDemonstrativo3Meses);
        empresa.setDemonstracao12meses(htmlToDemonstrativo12Meses);

        return empresa;

    }

    private Empresa htmlToEmpresaObject()
        throws ParserConfigurationException, XPathExpressionException {

        Empresa empresa = new Empresa();

        empresa.setSigla(RegExClasses.getFieldFromXpath(html, RegExClasses.NAME));
        empresa.setTipo(RegExClasses.getFieldFromXpath(html, RegExClasses.TIPO));

        empresa.setNome(RegExClasses.getFieldFromXpath(html, RegExClasses.NOME_EMPRESA));
        empresa.setSetor(RegExClasses.getFieldFromXpath(html, RegExClasses.SETOR));

        empresa.setSubSetor(RegExClasses.getFieldFromXpath(html, RegExClasses.SUB_SETOR));
        empresa.setCotacao(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.COTACAO));
        empresa.setDataUltimaCotacao(RegExClasses.getFieldFromXpath(html, RegExClasses.DATA_ULTIMA_COTACAO));
        empresa.setMinimo52Semanas(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.MIN_52_SEMANAS));
        empresa.setMaximo52Semanas(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.MAX_52_SEMANAS));
        empresa.setVolumeMedio2Meses(RegExClasses.getFieldFromXpathLong(html, RegExClasses.VOLUME_MEDIO_ULTIMOS_2_MESES));
        empresa.setUltimoBalancoProcessado(RegExClasses.getFieldFromXpath(html, RegExClasses.ULTIMO_BALANCO_PROCESSADO));
        empresa.setNumeroDeAcoes(RegExClasses.getFieldFromXpathLong(html, RegExClasses.NUMERO_ACOES));
        empresa.setValorMercado(RegExClasses.getFieldFromXpathLong(html, RegExClasses.VALOR_DE_MERCADO));
        empresa.setValorEmpresa(RegExClasses.getFieldFromXpathLong(html, RegExClasses.VALOR_EMPRESA));

        return empresa;
    }

    private Balanco htmlToBalancoObject()
        throws XPathExpressionException, ParserConfigurationException {
        Balanco balanco = new Balanco();
        balanco.setAtivos(RegExClasses.getFieldFromXpathLong(html, RegExClasses.ATIVOS));
        balanco.setDisponibilidades(RegExClasses.getFieldFromXpathLong(html, RegExClasses.DISPONIBILIDADES));
        balanco.setAtivosCirculantes(RegExClasses.getFieldFromXpathLong(html, RegExClasses.ATIVOS_CIRCULANTES));
        balanco.setDividaBruta(RegExClasses.getFieldFromXpathLong(html, RegExClasses.DIVIDA_BRUTA));
        balanco.setDividaLiquida(RegExClasses.getFieldFromXpathLong(html, RegExClasses.DIVIDA_LIQUIDA));
        balanco.setPatrimonioLiquido(RegExClasses.getFieldFromXpathLong(html, RegExClasses.PATRIMONIO_LIQUIDO));

        return balanco;
    }

    private Oscilacoes htmlToOscilacoes()
        throws XPathExpressionException, ParserConfigurationException {
        Oscilacoes oscilacoes = new Oscilacoes();
        oscilacoes.setDia(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_DIA));
        oscilacoes.setMes(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_MES));
        oscilacoes.setUltimos30Dias(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_30_DIAS));
        oscilacoes.setUltimos12Meses(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_12_MESES));
        oscilacoes.setAno2015(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_2015));
        oscilacoes.setAno2014(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_2014));
        oscilacoes.setAno2013(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_2013));
        oscilacoes.setAno2012(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_2012));
        oscilacoes.setAno2011(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_2011));
        oscilacoes.setAno2010(RegExClasses.getFieldFromXpathDouble(html, RegExClasses.OSCILACAO_2010));

        return oscilacoes;
    }

    private Demonstrativo3Meses htmlToDemonstrativo3Meses()
        throws XPathExpressionException, ParserConfigurationException {
        Demonstrativo3Meses demonstrativo = new Demonstrativo3Meses();
        demonstrativo.setReceitaLiquida(RegExClasses.getFieldFromXpathLong(html, RegExClasses.RECEITA_LIQUIDA_3_MESES));
        demonstrativo.seteBit(RegExClasses.getFieldFromXpathLong(html, RegExClasses.E_BIT_3_MESES));
        demonstrativo.setLucroLiquido(RegExClasses.getFieldFromXpathLong(html, RegExClasses.LUCRO_LIQUIDO_3_MESES));

        return demonstrativo;
    }

    private Demonstrativo12Meses htmlToDemonstrativo12Meses()
        throws XPathExpressionException, ParserConfigurationException {
        Demonstrativo12Meses demonstrativo = new Demonstrativo12Meses();
        demonstrativo.setReceitaLiquida(RegExClasses.getFieldFromXpathLong(html, RegExClasses.RECEITA_LIQUIDA_12_MESES));
        demonstrativo.setEbit(RegExClasses.getFieldFromXpathLong(html, RegExClasses.E_BIT_12_MESES));
        demonstrativo.setLucroLiquido(RegExClasses.getFieldFromXpathLong(html, RegExClasses.LUCRO_LIQUIDO_12_MESES));

        return demonstrativo;
    }

}
