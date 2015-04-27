package com.snk.fundamentus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.ui.TelaPrincipal;

public class PrincipalController {
    private final TelaPrincipal principal;
    private final DaoFactory daoFactory;

    public PrincipalController(final TelaPrincipal principal) {
        this.principal = principal;
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

        if (listAllElements.isEmpty() && listAllElements.size() > 0) {

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

    public ActionListener getBtnBuscarActionListener() {
        ActionListener btnActionListener = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                String textSearch = principal.getTextSearch();
                TableModel stockModel = getStockModel(textSearch);

                if (null != stockModel) {
                    principal.setTblAcoesModel(stockModel);
                }
            }
        };

        return btnActionListener;
    }
}
