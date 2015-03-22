package com.snk.fundamentus;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import com.snk.fundamentus.models.Empresa;

public class ConvertHtmlResult {
	private final static String allStr = Messages
			.getString("RegExClasses.HTML"); //$NON-NLS-1$

	public static void main(String[] args) throws ParserConfigurationException,
			XPathExpressionException {
		ConvertHtmlResult obj = new ConvertHtmlResult();
		obj.htmlToEmpresaObject(allStr);
	}

	
	private Empresa htmlToEmpresaObject(String html)
			throws ParserConfigurationException, XPathExpressionException {
		Empresa empresa = new Empresa();

		empresa.setSigla(RegExClasses.getFieldFromXpath(html, RegExClasses.NAME));
		empresa.setTipo(RegExClasses.getFieldFromXpath(html, RegExClasses.TIPO));
		empresa.setNome(RegExClasses.getFieldFromXpath(html, RegExClasses.NOME_EMPRESA));
		empresa.setSetor(RegExClasses.getFieldFromXpath(html, RegExClasses.SETOR));;
		empresa.setSubSetor(RegExClasses.getFieldFromXpath(html, RegExClasses.SUB_SETOR));
		empresa.setCotacao(RegExClasses.getFieldFromXpath(html, RegExClasses.COTACAO));
		empresa.setDataUltimaCotacao(RegExClasses.getFieldFromXpath(html, RegExClasses.DATA_ULTIMA_COTACAO));
		empresa.setMinimo52Semanas(RegExClasses.getFieldFromXpath(html, RegExClasses.MIN_52_SEMANAS));
		empresa.setMaximo52Semanas(RegExClasses.getFieldFromXpath(html, RegExClasses.MAX_52_SEMANAS));
		empresa.setVolumeMedio2Meses(RegExClasses.getFieldFromXpath(html, RegExClasses.VOLUME_MEDIO_ULTIMOS_2_MESES));
		empresa.setUltimoBalancoProcessado(RegExClasses.getFieldFromXpath(html, RegExClasses.ULTIMO_BALANCO_PROCESSADO));
		empresa.setNumeroDeAcoes(RegExClasses.getFieldFromXpath(html, RegExClasses.NUMERO_ACOES));
		empresa.setValorMercado(RegExClasses.getFieldFromXpath(html, RegExClasses.VALOR_DE_MERCADO));
		empresa.setValorEmpresa(RegExClasses.getFieldFromXpath(html, RegExClasses.VALOR_EMPRESA));
		
		return empresa;
	}

}
