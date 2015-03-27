package com.snk.fundamentus.tools;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import com.snk.fundamentus.models.Balanco;
import com.snk.fundamentus.models.Demonstrativo12Meses;
import com.snk.fundamentus.models.Demonstrativo3Meses;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.models.Oscilacoes;

public class HtmlConverter {

	private final String html;

	public HtmlConverter(final String html) {
		this.html = html;
	}

	public String getHtml() {
		return html;
	}

	public Empresa getEmpresa() throws XPathExpressionException,
			ParserConfigurationException {
		final Empresa empresa = htmlToEmpresaObject();
		final Balanco htmlToBalancoObject = htmlToBalancoObject();
		final Oscilacoes htmlToOscilacoes = htmlToOscilacoes();
		final Demonstrativo3Meses htmlToDemonstrativo3Meses = htmlToDemonstrativo3Meses();
		final Demonstrativo12Meses htmlToDemonstrativo12Meses = htmlToDemonstrativo12Meses();

		empresa.setBalanco(htmlToBalancoObject);
		empresa.setOscilacoes(htmlToOscilacoes);
		empresa.setDemonstracao3meses(htmlToDemonstrativo3Meses);
		empresa.setDemonstracao12meses(htmlToDemonstrativo12Meses);

		return empresa;

	}

	private Empresa htmlToEmpresaObject() throws ParserConfigurationException,
			XPathExpressionException {

		final Empresa empresa = new Empresa();

		empresa.setSigla(XpathClasses
				.getFieldFromXpath(html, XpathClasses.NAME));
		empresa.setTipo(XpathClasses.getFieldFromXpath(html, XpathClasses.TIPO));

		empresa.setNome(XpathClasses.getFieldFromXpath(html,
				XpathClasses.NOME_EMPRESA));
		empresa.setSetor(XpathClasses.getFieldFromXpath(html,
				XpathClasses.SETOR));

		empresa.setSubSetor(XpathClasses.getFieldFromXpath(html,
				XpathClasses.SUB_SETOR));
		empresa.setCotacao(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.COTACAO));
		empresa.setDataUltimaCotacao(XpathClasses.getFieldFromXpath(html,
				XpathClasses.DATA_ULTIMA_COTACAO));
		empresa.setMinimo52Semanas(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.MIN_52_SEMANAS));
		empresa.setMaximo52Semanas(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.MAX_52_SEMANAS));
		empresa.setVolumeMedio2Meses(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.VOLUME_MEDIO_ULTIMOS_2_MESES));
		empresa.setUltimoBalancoProcessado(XpathClasses.getFieldFromXpath(html,
				XpathClasses.ULTIMO_BALANCO_PROCESSADO));
		empresa.setNumeroDeAcoes(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.NUMERO_ACOES));
		empresa.setValorMercado(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.VALOR_DE_MERCADO));
		empresa.setValorEmpresa(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.VALOR_EMPRESA));

		return empresa;
	}

	private Balanco htmlToBalancoObject() throws XPathExpressionException,
			ParserConfigurationException {
		final Balanco balanco = new Balanco();
		balanco.setAtivos(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.ATIVOS));
		balanco.setDisponibilidades(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.DISPONIBILIDADES));
		balanco.setAtivosCirculantes(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.ATIVOS_CIRCULANTES));
		balanco.setDividaBruta(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.DIVIDA_BRUTA));
		balanco.setDividaLiquida(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.DIVIDA_LIQUIDA));
		balanco.setPatrimonioLiquido(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.PATRIMONIO_LIQUIDO));

		return balanco;
	}

	private Oscilacoes htmlToOscilacoes() throws XPathExpressionException,
			ParserConfigurationException {
		final Oscilacoes oscilacoes = new Oscilacoes();
		oscilacoes.setDia(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_DIA));
		oscilacoes.setMes(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_MES));
		oscilacoes.setUltimos30Dias(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_30_DIAS));
		oscilacoes.setUltimos12Meses(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_12_MESES));
		oscilacoes.setAno2015(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_2015));
		oscilacoes.setAno2014(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_2014));
		oscilacoes.setAno2013(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_2013));
		oscilacoes.setAno2012(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_2012));
		oscilacoes.setAno2011(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_2011));
		oscilacoes.setAno2010(XpathClasses.getFieldFromXpathDouble(html,
				XpathClasses.OSCILACAO_2010));

		return oscilacoes;
	}

	private Demonstrativo3Meses htmlToDemonstrativo3Meses()
			throws XPathExpressionException, ParserConfigurationException {
		final Demonstrativo3Meses demonstrativo = new Demonstrativo3Meses();
		demonstrativo.setReceitaLiquida(XpathClasses.getFieldFromXpathLong(
				html, XpathClasses.RECEITA_LIQUIDA_3_MESES));
		demonstrativo.seteBit(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.E_BIT_3_MESES));
		demonstrativo.setLucroLiquido(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.LUCRO_LIQUIDO_3_MESES));

		return demonstrativo;
	}

	private Demonstrativo12Meses htmlToDemonstrativo12Meses()
			throws XPathExpressionException, ParserConfigurationException {
		final Demonstrativo12Meses demonstrativo = new Demonstrativo12Meses();
		demonstrativo.setReceitaLiquida(XpathClasses.getFieldFromXpathLong(
				html, XpathClasses.RECEITA_LIQUIDA_12_MESES));
		demonstrativo.setEbit(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.E_BIT_12_MESES));
		demonstrativo.setLucroLiquido(XpathClasses.getFieldFromXpathLong(html,
				XpathClasses.LUCRO_LIQUIDO_12_MESES));

		return demonstrativo;
	}

}
