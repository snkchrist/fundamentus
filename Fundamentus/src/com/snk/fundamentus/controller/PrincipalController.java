package com.snk.fundamentus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.interfaces.ITelaPrincipal;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.models.RepresentableOnTable;

public class PrincipalController {
    private final ITelaPrincipal view;
    private final DaoFactory daoFactory;

    public PrincipalController(final ITelaPrincipal iView) {
        this.view = iView;
        this.daoFactory = new DaoFactory();
    }

    public TableModel getStockModel(final String search) {

        TableStockModel model = null;
        List<Empresa> listAllElements = new ArrayList<Empresa>();

        if (null == search || search.isEmpty()) {
            listAllElements.addAll(this.daoFactory.getEmpresaDao().listAllElements());
        }
        else {
            Empresa empresa = this.daoFactory.getEmpresaDao().findEmpresaBySigla(search);
            if (null != empresa) {
                listAllElements.add(empresa);
            }
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

    private TableStockModel buildDetalhesBasicosTableModel(final Empresa empresa)
            throws IllegalArgumentException, IllegalAccessException {

        TableStockModel model = null;

        Object[][] model2dArray = buildTableModelGenerically(empresa, "com.snk.fundamentus", null);
        model = new TableStockModel(model2dArray, new String[] {
                "Name", "Valor"
        });
        return model;
    }

    private TableStockModel buildBalancoTableModel(final BalancoPatrimonial balanco)
            throws IllegalArgumentException, IllegalAccessException {
        TableStockModel model = null;

        Object[][] model2dArray = buildTableModelGenerically(balanco, "com.snk.fundamentus", null);
        model = new TableStockModel(model2dArray, new String[] {
                "Name", "Valor"
        });
        return model;
    }

    private TableStockModel buildDemonstrativoTableModel(final DemonstrativoResultado demonstrativo)
            throws IllegalArgumentException, IllegalAccessException {
        TableStockModel model = null;

        Object[][] model2dArray = buildTableModelGenerically(demonstrativo, "com.snk.fundamentus", null);
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
                if (field.isAnnotationPresent(RepresentableOnTable.class)) {
                    RepresentableOnTable[] annotationsByType = field.getAnnotationsByType(RepresentableOnTable.class);
                    String name = annotationsByType[0].name();

                    if (null == name || name.isEmpty()) {
                        name = field.getName();
                    }

                    String format = annotationsByType[0].format();

                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if (null != format && false == format.isEmpty()) {

                        try {
                            DateFormat dateformat = DateFormat.getDateInstance();
                            Date parse = dateformat.parse(value.toString());
                            value = String.format(format, parse);
                        }
                        catch (ParseException e) {
                            value = String.format(format, value);
                        }
                    }

                    map.put(name, value);
                }
                else if (Object.class.isAssignableFrom(field.getType())) {
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

    public ActionListener getBtnBuscarActionListener() {
        ActionListener btnActionListener = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                buildTextSearchTableModel();
            }
        };

        return btnActionListener;
    }

    public MouseAdapter getTblAcoesMouseAdapter() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent arg0) {
                TableStockModel buildBasicDetailsTableModel = null;
                try {
                    view.clearBalancoTab();
                    int selectedRowAtTblAcoes = view.getSelectedRowAtTblAcoes();
                    String empSigla = (String) view.getSelectedObjectAtTblAcoes(selectedRowAtTblAcoes, 1);
                    Empresa empresa = daoFactory.getEmpresaDao().findEmpresaBySigla(empSigla);

                    buildBasicDetailsTableModel = buildDetalhesBasicosTableModel(empresa);

                    List<BalancoPatrimonial> balancoList = empresa.getBalancoList();

                    if (null != balancoList) {
                        for (BalancoPatrimonial balanco : balancoList) {
                            TableStockModel buildBalancoTableModel = buildBalancoTableModel(balanco);

                            JPanel jPanel = view.getJPanelTemplateToBalancoTab(buildBalancoTableModel);
                            view.addNewBalancoTab(String.format("%1$te/%1$tm/%1$tY", balanco.getDataDoBalanco()), jPanel);
                        }
                    }

                    List<DemonstrativoResultado> demonstrativoList = empresa.getDemonstrativoList();

                    if (null != demonstrativoList) {
                        for (DemonstrativoResultado demonstrativo : demonstrativoList) {
                            TableStockModel buildBalancoTableModel = buildDemonstrativoTableModel(demonstrativo);

                            JPanel jPanel = view.getJPanelTemplateToBalancoTab(buildBalancoTableModel);
                            view.addNewBalancoTab(String.format("%1$te/%1$tm/%1$tY", demonstrativo.getDataDemonstrativo()), jPanel);
                        }
                    }

                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                view.setTblDetalhesBasicosModel(buildBasicDetailsTableModel);

            }
        };

        return mouseAdapter;
    }
}
