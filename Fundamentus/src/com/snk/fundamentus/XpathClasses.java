package com.snk.fundamentus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class XpathClasses {

    // Infos
    public final static String NAME = Messages.getString("RegExClasses.NAME");

    public final static String TIPO = Messages.getString("RegExClasses.TIPO");

    public final static String NOME_EMPRESA = Messages.getString("RegExClasses.NOME_EMPRESA");

    public final static String SETOR = Messages.getString("RegExClasses.SETOR");

    public final static String SUB_SETOR = Messages.getString("RegExClasses.SUB_SETOR");

    public final static String COTACAO = Messages.getString("RegExClasses.COTACAO");

    public final static String DATA_ULTIMA_COTACAO = Messages.getString("RegExClasses.DATA_ULTIMA_COTACAO");

    public final static String MIN_52_SEMANAS = Messages.getString("RegExClasses.MIN_52_SEMANAS");

    public final static String MAX_52_SEMANAS = Messages.getString("RegExClasses.MAX_52_SEMANAS");

    public final static String VOLUME_MEDIO_ULTIMOS_2_MESES = Messages.getString("RegExClasses.VOLUME_MEDIO_ULTIMOS_2_MESES");

    public final static String ULTIMO_BALANCO_PROCESSADO = Messages.getString("RegExClasses.ULTIMO_BALANCO_PROCESSADO");

    public final static String NUMERO_ACOES = Messages.getString("RegExClasses.NUMERO_ACOES");

    public final static String VALOR_DE_MERCADO = Messages.getString("RegExClasses.VALOR_DE_MERCADO");

    public final static String VALOR_EMPRESA = Messages.getString("RegExClasses.VALOR_EMPRESA");

    // Oscilações
    public final static String OSCILACAO_DIA = Messages.getString("RegExClasses.OSCILACAO_DIA");

    public final static String OSCILACAO_MES = Messages.getString("RegExClasses.OSCILACAO_MES");

    public final static String OSCILACAO_30_DIAS = Messages.getString("RegExClasses.OSCILACAO_30_DIAS");

    public final static String OSCILACAO_12_MESES = Messages.getString("RegExClasses.OSCILACAO_12_MESES");

    public final static String OSCILACAO_2015 = Messages.getString("RegExClasses.OSCILACAO_2015");

    public final static String OSCILACAO_2014 = Messages.getString("RegExClasses.OSCILACAO_2014");

    public final static String OSCILACAO_2013 = Messages.getString("RegExClasses.OSCILACAO_2013");

    public final static String OSCILACAO_2012 = Messages.getString("RegExClasses.OSCILACAO_2012");

    public final static String OSCILACAO_2011 = Messages.getString("RegExClasses.OSCILACAO_2011");

    public final static String OSCILACAO_2010 = Messages.getString("RegExClasses.OSCILACAO_2010");

    // dados de balanço
    public final static String ATIVOS = Messages.getString("RegExClasses.ATIVOS");

    public final static String DISPONIBILIDADES = Messages.getString("RegExClasses.DISPONIBILIDADES");

    public final static String ATIVOS_CIRCULANTES = Messages.getString("RegExClasses.ATIVOS_CIRCULANTES");

    public final static String DIVIDA_BRUTA = Messages.getString("RegExClasses.DIVIDA_BRUTA");

    public final static String DIVIDA_LIQUIDA = Messages.getString("RegExClasses.DIVIDA_LIQUIDA");

    public final static String PATRIMONIO_LIQUIDO = Messages.getString("RegExClasses.PATRIMONIO_LIQUIDO");

    // Dados demonstrativos de resultados
    public final static String RECEITA_LIQUIDA_12_MESES = Messages.getString("RegExClasses.RECEITA_LIQUIDA_12_MESES");

    public final static String E_BIT_12_MESES = Messages.getString("RegExClasses.E_BIT_12_MESES");

    public final static String LUCRO_LIQUIDO_12_MESES = Messages.getString("RegExClasses.LUCRO_LIQUIDO_12_MESES");

    public final static String RECEITA_LIQUIDA_3_MESES = Messages.getString("RegExClasses.RECEITA_LIQUIDA_3_MESES");

    public final static String E_BIT_3_MESES = Messages.getString("RegExClasses.E_BIT_3_MESES");

    public final static String LUCRO_LIQUIDO_3_MESES = Messages.getString("RegExClasses.LUCRO_LIQUIDO_3_MESES");

    public static String removeInvalidChars(String str) {
        str = StringEscapeUtils.unescapeHtml(str);
        return str.replace(".", "").replace("%", "").replace(",", ".");
    }

    public static String getFieldFromXpath(final String html, final String x)
            throws ParserConfigurationException, XPathExpressionException {
        TagNode tagNode = new HtmlCleaner().clean(html);
        org.w3c.dom.Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);

        String str = "";
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            str = (String) xpath.evaluate(x, doc, XPathConstants.STRING);
            str = removeInvalidChars(str);
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        return str;
    }

    public static Date getDateFieldFromXpath(final String html, final String x)
            throws XPathExpressionException, ParserConfigurationException {
        Date value = null;
        try {
            String fieldFromXpath = getFieldFromXpath(html, x);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parse = format.parse(fieldFromXpath);

            value = parse;
        }

        catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Long getLongFieldFromXpath(final String html, final String x)
            throws XPathExpressionException, ParserConfigurationException {
        Long value = 0L;
        try {
            String fieldFromXpath = getFieldFromXpath(html, x);
            value = Long.parseLong(fieldFromXpath);
        }
        catch (NumberFormatException exp) {
            exp.printStackTrace();
            System.out.println("Error parsing Long" + exp.getMessage());
        }
        return value;
    }

    public static Double getDoubleFieldFromXpath(final String html, final String x)
            throws XPathExpressionException, ParserConfigurationException {

        Double value = 0.0;

        try {
            String fieldFromXpath = getFieldFromXpath(html, x);
            value = Double.parseDouble(fieldFromXpath);
        }
        catch (NumberFormatException exp) {
            exp.printStackTrace();
            System.out.println("Error parsing Double" + exp.getMessage());
        }

        return value;
    }

}
