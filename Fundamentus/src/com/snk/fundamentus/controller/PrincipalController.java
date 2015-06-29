package com.snk.fundamentus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.enums.DataType;
import com.snk.fundamentus.enums.MetodoInvestimento;
import com.snk.fundamentus.enums.ShowOnTable;
import com.snk.fundamentus.enums.ShowOnTableRecursive;
import com.snk.fundamentus.interfaces.ITelaPrincipal;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.models.Indices;
import com.snk.fundamentus.report.GuruMethod;

public class PrincipalController {
    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private final ITelaPrincipal view;
    private final DaoFactory daoFactory;

    public PrincipalController(final ITelaPrincipal iView) {
        this.view = iView;
        this.daoFactory = new DaoFactory();
    }

    public TableModel getStockModel(final String search) {

        TableStockModel model = null;
        List<Empresa> listAllElements = new ArrayList<Empresa>();

        if (MetodoInvestimento.None.equals(view.getSelectedGuruMethod())) {
            if (null == search || search.isEmpty()) {
                listAllElements.addAll(this.daoFactory.getEmpresaDao().listAllElements());
            }
            else {

                String[] split = search.split(",");

                for (String string : split) {
                    Empresa empresa = this.daoFactory.getEmpresaDao().findEmpresaBySigla(string);
                    if (null != empresa) {
                        listAllElements.add(empresa);
                    }
                }
            }
        }
        else {
            GuruMethod gurus = GuruMethod.getGurusMethodInstance((MetodoInvestimento) view.getSelectedGuruMethod(), null);
            List<Empresa> lstEmpresa = gurus.getLstEmpresa();
            listAllElements.addAll(lstEmpresa);
        }

        if (false == listAllElements.isEmpty() && listAllElements.size() > 0) {

            String[] columnHeader = new String[] {
                    "Nome", "Sigla", "Numero de Acões", "Data da última cotação"
            };

            List<Object[]> list = new ArrayList<Object[]>();

            for (Empresa empresa : listAllElements) {
                list.add(new Object[] {
                        empresa.getNome(), empresa.getSigla(), empresa.getNumeroDeAcoes(), empresa.getDataUltimaCotacao()
                });
            }

            Object[][] data = new Object[listAllElements.size()][columnHeader.length];

            for (int i = 0; i < list.size(); i++) {
                Object[] objects = list.get(i);

                for (int j = 0; j < objects.length; j++) {
                    data[i][j] = objects[j];
                }
            }

            model = new TableStockModel(data, columnHeader);
        }

        return model;
    }

    private void buildTextSearchTableModel() {
        String textSearch = view.getTextSearch();
        TableModel stockModel = getStockModel(textSearch);

        if (null != stockModel) {
            view.setTblAcoesModel(stockModel);
        }
    }

    private TableStockModel buildObjectTableModel(final Object object)
            throws IllegalArgumentException, IllegalAccessException {
        TableStockModel model = null;

        Object[][] model2dArray = buildTableModelGenerically(object, "com.snk.fundamentus", null);
        model = new TableStockModel(model2dArray, new String[] {
                "Name", "Valor"
        });
        return model;
    }

    private Object[][] buildTableModelGenerically(final Object obj, final String classNameContains, Map<String, Object> map)
            throws IllegalAccessException {
        Object[][] twoDimObject = null;

        if (null != obj && obj.getClass().getName().contains(classNameContains)) {
            if (null == map) {
                map = new TreeMap<String, Object>();
            }

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(ShowOnTable.class)) {
                    ShowOnTable[] annotationsByType = field.getAnnotationsByType(ShowOnTable.class);
                    String name = annotationsByType[0].name();
                    DataType type = annotationsByType[0].type();

                    if (null == name || name.isEmpty()) {
                        name = field.getName();
                    }

                    String format = annotationsByType[0].format();

                    field.setAccessible(true);
                    Object value = field.get(obj);

                    try {
                        if (DataType.Date.equals(type)) {
                            DateFormat dateformat = DateFormat.getDateInstance();
                            Date parse = dateformat.parse(value.toString());
                            if (null != format && false == format.isEmpty()) {
                                value = String.format(format, parse);
                            }
                        }
                        else if (DataType.Currency.equals(type)) {
                            if (value instanceof Double || value instanceof Long) {
                                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(LOCALE_BR);
                                value = currencyFormatter.format(value);
                            }
                        }
                        else if (DataType.Decimal.equals(type)) {
                            DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE_BR);
                            DecimalFormatSymbols symbols = decimalFormatter.getDecimalFormatSymbols();
                            symbols.setCurrencySymbol("");
                            decimalFormatter.setDecimalFormatSymbols(symbols);

                            value = decimalFormatter.format(value);
                        }
                        else {
                            if (null != format && false == format.isEmpty()) {
                                value = String.format(format, value);

                                if (DataType.Percentage.equals(type)) {
                                    value = "%" + value;
                                }
                            }
                        }
                    }
                    catch (ParseException e) {
                    }

                    map.put(name, value);
                }
                else if (field.isAnnotationPresent(ShowOnTableRecursive.class) && Object.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    buildTableModelGenerically(field.get(obj), classNameContains, map);
                }
            }

            twoDimObject = convertHashMapTo2dArrayObject(map);
        }
        return twoDimObject;
    }

    private Object[][] convertHashMapTo2dArrayObject(final Map<String, Object> map) {
        int size = map.values().size();
        Object[][] object = new Object[size][2];
        Object[] keyArray = map.keySet().toArray();
        Object[] valueArray = map.values().toArray();

        for (int i = 0; i < size; i++) {
            object[i][0] = keyArray[i].toString();
            object[i][1] = valueArray[i].toString();
        }
        return object;
    }

    private void updateViewByEmpresaObject() {
        TableStockModel buildBasicDetailsTableModel = null;
        try {
            view.clearUiTabs();
            int selectedRowAtTblAcoes = view.getSelectedRowAtTblAcoes();
            if (selectedRowAtTblAcoes != -1) {

                String empSigla = (String) view.getSelectedObjectAtTblAcoes(selectedRowAtTblAcoes, 1);
                Empresa empresa = daoFactory.getEmpresaDao().findEmpresaBySigla(empSigla);

                buildBasicDetailsTableModel = buildObjectTableModel(empresa);

                updateBalancoViewByEmpresaObject(empresa);

                updateDemonstrativoViewByEmpresaObject(empresa);

                updateIndicesViewByEmpresaObject(empresa);

                view.setTblDetalhesBasicosModel(buildBasicDetailsTableModel);
            }

        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void updateDemonstrativoViewByEmpresaObject(final Empresa empresa)
            throws IllegalAccessException {
        List<DemonstrativoResultado> demonstrativoList = empresa.getDemonstrativoList();

        if (null != demonstrativoList) {
            for (DemonstrativoResultado demonstrativo : demonstrativoList) {
                TableStockModel buildBalancoTableModel = buildObjectTableModel(demonstrativo);

                JPanel jPanelTemplateTab = view.getJPanelTemplateTab(buildBalancoTableModel);
                view.addNewDemonstrativoTab(
                        String.format("%1$te/%1$tm/%1$tY", demonstrativo.getDataDemonstrativo()),
                        jPanelTemplateTab);
            }
        }
    }

    private void updateBalancoViewByEmpresaObject(final Empresa empresa)
            throws IllegalAccessException {
        List<BalancoPatrimonial> balancoList = empresa.getBalancoList();

        if (null != balancoList) {
            for (BalancoPatrimonial balanco : balancoList) {
                TableStockModel buildBalancoTableModel = buildObjectTableModel(balanco);

                JPanel jPanelTemplateTab = view.getJPanelTemplateTab(buildBalancoTableModel);
                view.addNewBalancoTab(String.format("%1$te/%1$tm/%1$tY", balanco.getDataDoBalanco()), jPanelTemplateTab);
            }
        }
    }

    private void updateIndicesViewByEmpresaObject(final Empresa empresa)
            throws IllegalAccessException {
        Indices indices = new Indices(empresa, Calendar.getInstance().get(Calendar.YEAR) - 1);

        if (null != indices) {
            TableStockModel indiceTableModel = buildObjectTableModel(indices);
            view.setTblIndices(indiceTableModel);
        }
    }

    public ItemListener getCboGuruItemListener() {
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent arg0) {
                if (ItemEvent.SELECTED == arg0.getStateChange()) {
                    TableModel stockModel = getStockModel(null);

                    if (null != stockModel) {
                        view.setTblAcoesModel(stockModel);
                    }
                }
            }
        };

        return itemListener;
    }

    public ActionListener getBtnBuscarActionListener() {
        ActionListener btnActionListener = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                buildTextSearchTableModel();
            }
        };

        return btnActionListener;
    }

    public ListSelectionListener getTblAcoesListSelectionListener() {
        ListSelectionListener mouseAdapter = new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent e) {
                updateViewByEmpresaObject();
            }
        };

        return mouseAdapter;
    }
}
