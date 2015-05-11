package com.snk.fundamentus.interfaces;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

public interface ITelaPrincipal {
    String getTextSearch();

    void setTblDetalhesBasicosModel(final TableModel tblModel);

    void setTblAcoesModel(final TableModel tblModel);

    void setTblIndices(TableModel tblModel);

    Object getSelectedObjectAtTblAcoes(final int row, final int column);

    int getSelectedRowAtTblAcoes();

    int getSelectedColumnAtTblAcoes();

    void addNewBalancoTab(String tabName, JPanel panel);

    void addNewDemonstrativoTab(String tabName, JPanel panel);

    JPanel getJPanelTemplateTab(TableModel tblModel);

    void clearUiTabs();

    Object getSelectedGuruMethod();

}
