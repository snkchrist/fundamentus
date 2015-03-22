package com.snk.fundamentus;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;


public class RegExClasses {
	//Infos
	private final static String NAME  = Messages.getString("RegExClasses.NAME"); //$NON-NLS-1$
	private final static String TIPO  = Messages.getString("RegExClasses.TIPO"); //$NON-NLS-1$
	private final static String NOME_EMPRESA  = Messages.getString("RegExClasses.NOME_EMPRESA"); //$NON-NLS-1$
	private final static String SETOR  = Messages.getString("RegExClasses.SETOR"); //$NON-NLS-1$
	private final static String SUB_SETOR  = Messages.getString("RegExClasses.SUB_SETOR"); //$NON-NLS-1$
	private final static String COTACAO  = Messages.getString("RegExClasses.COTACAO"); //$NON-NLS-1$
	private final static String DATA_ULTIMA_COTACAO  = Messages.getString("RegExClasses.DATA_ULTIMA_COTACAO"); //$NON-NLS-1$
	private final static String MIN_52_SEMANAS  = Messages.getString("RegExClasses.MIN_52_SEMANAS"); //$NON-NLS-1$
	private final static String MAX_52_SEMANAS  = Messages.getString("RegExClasses.MAX_52_SEMANAS"); //$NON-NLS-1$
	private final static String VOLUME_MEDIO_ULTIMOS_2_MESES  = Messages.getString("RegExClasses.VOLUME_MEDIO_ULTIMOS_2_MESES"); //$NON-NLS-1$
	private final static String ULTIMO_BALANCO_PROCESSADO  = Messages.getString("RegExClasses.ULTIMO_BALANCO_PROCESSADO"); //$NON-NLS-1$
	private final static String NUMERO_ACOES  = Messages.getString("RegExClasses.NUMERO_ACOES"); //$NON-NLS-1$
	private final static String VALOR_DE_MERCADO  = Messages.getString("RegExClasses.VALOR_DE_MERCADO"); //$NON-NLS-1$
	private final static String VALOR_EMPRESA  = Messages.getString("RegExClasses.VALOR_EMPRESA"); //$NON-NLS-1$
	//Oscila��es
	private final static String OSCILACAO_DIA = Messages.getString("RegExClasses.OSCILACAO_DIA"); //$NON-NLS-1$
	private final static String OSCILACAO_MES = Messages.getString("RegExClasses.OSCILACAO_MES"); //$NON-NLS-1$
	private final static String OSCILACAO_30_DIAS = Messages.getString("RegExClasses.OSCILACAO_30_DIAS"); //$NON-NLS-1$
	private final static String OSCILACAO_12_MESES = Messages.getString("RegExClasses.OSCILACAO_12_MESES"); //$NON-NLS-1$
	private final static String OSCILACAO_2015 = Messages.getString("RegExClasses.OSCILACAO_2015"); //$NON-NLS-1$
	private final static String OSCILACAO_2014 = Messages.getString("RegExClasses.OSCILACAO_2014"); //$NON-NLS-1$
	private final static String OSCILACAO_2013 = Messages.getString("RegExClasses.OSCILACAO_2013"); //$NON-NLS-1$
	private final static String OSCILACAO_2012 = Messages.getString("RegExClasses.OSCILACAO_2012"); //$NON-NLS-1$
	private final static String OSCILACAO_2011 = Messages.getString("RegExClasses.OSCILACAO_2011"); //$NON-NLS-1$
	private final static String OSCILACAO_2010 = Messages.getString("RegExClasses.OSCILACAO_2010"); //$NON-NLS-1$
//dados de balan�o
	private final static String ATIVOS =  Messages.getString("RegExClasses.ATIVOS"); //$NON-NLS-1$
	private final static String DISPONIBILIDADES = Messages.getString("RegExClasses.DISPONIBILIDADES"); //$NON-NLS-1$
	private final static String ATIVOS_CIRCULANTES = Messages.getString("RegExClasses.ATIVOS_CIRCULANTES"); //$NON-NLS-1$
	private final static String DIVIDA_BRUTA = Messages.getString("RegExClasses.DIVIDA_BRUTA"); //$NON-NLS-1$
	private final static String DIVIDA_LIQUIDA = Messages.getString("RegExClasses.DIVIDA_LIQUIDA"); //$NON-NLS-1$
	private final static String PATRIMONIO_LIQUIDO = Messages.getString("RegExClasses.PATRIMONIO_LIQUIDO"); //$NON-NLS-1$
	//Dados demonstrativos de resultados
	private final static String RECEITA_LIQUIDA_12_MESES = Messages.getString("RegExClasses.RECEITA_LIQUIDA_12_MESES"); //$NON-NLS-1$
	private final static String E_BIT_12_MESES=Messages.getString("RegExClasses.E_BIT_12_MESES"); //$NON-NLS-1$
	private final static String LUCRO_LIQUIDO_12_MESES = Messages.getString("RegExClasses.LUCRO_LIQUIDO_12_MESES"); //$NON-NLS-1$
	
		private final static String RECEITA_LIQUIDA_3_MESES = Messages.getString("RegExClasses.RECEITA_LIQUIDA_3_MESES"); //$NON-NLS-1$
	private final static String E_BIT_3_MESES=Messages.getString("RegExClasses.E_BIT_3_MESES"); //$NON-NLS-1$
	private final static String LUCRO_LIQUIDO_3_MESES = Messages.getString("RegExClasses.LUCRO_LIQUIDO_3_MESES"); //$NON-NLS-1$
	

	private final static String allStr = Messages.getString("RegExClasses.HTML"); //$NON-NLS-1$
	
	public static void main(String[] args ) throws ParserConfigurationException, XPathExpressionException{
		System.out.println(getFieldFromXpath(allStr, NAME));
		System.out.println(getFieldFromXpath(allStr, TIPO));
		System.out.println(getFieldFromXpath(allStr, NOME_EMPRESA));
		System.out.println(getFieldFromXpath(allStr, SETOR));
		System.out.println(getFieldFromXpath(allStr, SUB_SETOR));
		System.out.println(getFieldFromXpath(allStr, COTACAO));
		System.out.println(getFieldFromXpath(allStr, DATA_ULTIMA_COTACAO));
		System.out.println(getFieldFromXpath(allStr, MIN_52_SEMANAS));
		System.out.println(getFieldFromXpath(allStr, MAX_52_SEMANAS));
		System.out.println(getFieldFromXpath(allStr, VOLUME_MEDIO_ULTIMOS_2_MESES));
		System.out.println(getFieldFromXpath(allStr, ULTIMO_BALANCO_PROCESSADO));
		System.out.println(getFieldFromXpath(allStr, NUMERO_ACOES));
		System.out.println(getFieldFromXpath(allStr, VALOR_DE_MERCADO));
		System.out.println(getFieldFromXpath(allStr, VALOR_EMPRESA));
	}

	private static String getFieldFromXpath(String html, String x)
			throws ParserConfigurationException, XPathExpressionException {
		TagNode tagNode = new HtmlCleaner().clean(html);
		org.w3c.dom.Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
		
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String str = (String) xpath.evaluate(x, doc, XPathConstants.STRING);
		
		return str;
	}
}
