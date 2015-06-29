package com.snk.fundamentus.ui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import com.snk.fundamentus.controller.PrincipalController;
import com.snk.fundamentus.enums.MetodoInvestimento;
import com.snk.fundamentus.interfaces.ITelaPrincipal;

public class TelaPrincipal implements ITelaPrincipal {

    private JPanel pnlTable;

    private JFrame frmStocksearch;
    private JTable tblAcoes;
    private JTable tblDetalhesBasicos;
    private JTable tblIndice;

    private JTextField txtSearch;
    private JScrollPane scrollPane;
    private PrincipalController controller;

    private JTabbedPane tabbedPaneAcao;
    private JTabbedPane tabbedPaneBalancos;
    private JTabbedPane tabbedPaneDemonstrativos;

    private JComboBox cboGurus;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    /*
                     * DownloadData data = new DownloadData();
                     * data.updateInformation();
                     */

                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

                    TelaPrincipal window = new TelaPrincipal();
                    window.frmStocksearch.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TelaPrincipal() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmStocksearch = new JFrame();
        frmStocksearch.setTitle("StockSearch");
        frmStocksearch.setBounds(100, 100, 685, 577);
        frmStocksearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmStocksearch.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

        controller = new PrincipalController(this);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setToolTipText("");
        frmStocksearch.getContentPane().add(tabbedPane, "cell 0 0,grow");

        JPanel panel = new JPanel();
        tabbedPane.addTab("Principal", null, panel, null);
        panel.setLayout(new MigLayout("", "[300px][grow][grow]", "[][grow]"));

        txtSearch = new JTextField();
        panel.add(txtSearch, "cell 0 0 2 1,hmax 25px,grow");

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(controller.getBtnBuscarActionListener());

        cboGurus = new JComboBox<MetodoInvestimento>();

        MetodoInvestimento[] values = MetodoInvestimento.values();

        for (MetodoInvestimento metodoInvestimento : values) {
            cboGurus.addItem(metodoInvestimento);
        }

        cboGurus.addItemListener(controller.getCboGuruItemListener());

        panel.add(cboGurus, "cell 2 0, hmax 25, grow");

        panel.add(btnBuscar, "cell 2 0,alignx right");

        pnlTable = new JPanel();

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tblAcoes = new JTable();

        if (null != controller.getStockModel(null)) {
            tblAcoes = new JTable(controller.getStockModel(null));
        }
        tblAcoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAcoes.getSelectionModel().addListSelectionListener(controller.getTblAcoesListSelectionListener());

        pnlTable.setPreferredSize(tblAcoes.getPreferredSize());
        scrollPane.add(pnlTable);
        scrollPane.setViewportView(pnlTable);

        panel.add(scrollPane, "cell 0 1,wmin 300,growy");
        pnlTable.setLayout(new MigLayout("", "[400px]", "[][grow]"));
        pnlTable.add(tblAcoes.getTableHeader(), "cell 0 0,growx, hmax 20, aligny top, wrap");
        pnlTable.add(tblAcoes, "cell 0 1,growx,aligny top");

        JPanel pnlDetalhes = new JPanel();
        pnlDetalhes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        pnlDetalhes.setLayout(new MigLayout("", "[grow]", "[grow]"));
        panel.add(pnlDetalhes, "cell 1 1 2 1,grow");
        //pnlDetalhes.setLayout(new MigLayout("", "[grow]", "[][grow]"));

        tabbedPaneAcao = new JTabbedPane(JTabbedPane.TOP);
        tabbedPaneAcao.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlDetalhes.add(tabbedPaneAcao, "grow");

        JPanel pnlDetalhesBasicos = new JPanel();
        tabbedPaneAcao.addTab("Detalhes", null, pnlDetalhesBasicos, null);
        pnlDetalhesBasicos.setLayout(new MigLayout("", "[grow, fill]", "[grow, fill]"));

        tblDetalhesBasicos = new JTable();
        pnlDetalhesBasicos.add(tblDetalhesBasicos, "cell 0 0,grow");

        JPanel pnlBalancos = new JPanel();
        tabbedPaneAcao.addTab("Balanços", null, pnlBalancos, null);
        pnlBalancos.setLayout(new MigLayout("", "[grow]", "[grow]"));

        tabbedPaneBalancos = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPaneBalancos.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlBalancos.add(tabbedPaneBalancos, "cell 0 0,grow");

        JPanel pnlDemonstrativos = new JPanel();
        tabbedPaneAcao.addTab("Demonstrativos", null, pnlDemonstrativos, null);
        pnlDemonstrativos.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

        tabbedPaneDemonstrativos = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPaneDemonstrativos.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlDemonstrativos.add(tabbedPaneDemonstrativos, "cell 0 0,grow");

        JPanel pnlIndice = new JPanel();
        tabbedPaneAcao.addTab("Indices", null, pnlIndice, null);
        pnlIndice.setLayout(new MigLayout("", "[grow, fill]", "[grow, fill]"));
        tblIndice = new JTable();
        tblIndice.setRowSelectionAllowed(true);
        tblIndice.setColumnSelectionAllowed(true);
        tblIndice.setCellSelectionEnabled(true);

        pnlIndice.add(tblIndice, "cell 0 0,grow");

        JButton btnAvancado = new JButton("Avan\u00E7ado");
        panel.add(btnAvancado, "cell 2 0,wmax 90,alignx right");
    }

    @Override
    public Object getSelectedGuruMethod() {
        return cboGurus.getSelectedItem();
    }

    @Override
    public void addNewBalancoTab(final String tabName, final JPanel panel) {
        tabbedPaneBalancos.addTab(tabName, null, panel, null);
    }

    @Override
    public void addNewDemonstrativoTab(final String tabName, final JPanel panel) {
        tabbedPaneDemonstrativos.addTab(tabName, null, panel, null);
    }

    @Override
    public void clearUiTabs() {
        tabbedPaneBalancos.removeAll();
        tabbedPaneDemonstrativos.removeAll();
    }

    @Override
    public JPanel getJPanelTemplateTab(final TableModel tblModel) {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

        JScrollPane scrollPaneTemplate = new JScrollPane();
        scrollPaneTemplate.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneTemplate.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel pnlTemplate = new JPanel();
        pnlTemplate.setLayout(new MigLayout("", "[grow, fill]", "[grow, fill]"));

        JTable tblTemplate = new JTable(tblModel);
        pnlTemplate.add(tblTemplate, "cell 0 0,grow");

        Dimension preferredSize = tblTemplate.getPreferredSize();
        preferredSize.setSize(preferredSize.getWidth(), preferredSize.getHeight() + 5);

        pnlTemplate.setPreferredSize(preferredSize);
        scrollPaneTemplate.add(pnlTemplate);
        scrollPaneTemplate.setViewportView(pnlTemplate);

        panelPrincipal.add(scrollPaneTemplate, "cell 0 0, grow");

        return panelPrincipal;
    }

    public void fireTableDataChange(final TableModel model) {
        AbstractTableModel aModel = (AbstractTableModel) model;
        aModel.fireTableDataChanged();
    }

    @Override
    public String getTextSearch() {
        return txtSearch.getText().toUpperCase();
    }

    @Override
    public void setTblDetalhesBasicosModel(final TableModel tblModel) {
        tblDetalhesBasicos.setModel(tblModel);
        fireTableDataChange(tblModel);
    }

    @Override
    public void setTblAcoesModel(final TableModel tblModel) {
        tblAcoes.setModel(tblModel);
        fireTableDataChange(tblModel);
        pnlTable.setPreferredSize(tblAcoes.getPreferredSize());

    }

    @Override
    public void setTblIndices(final TableModel tblModel) {
        tblIndice.setModel(tblModel);
        fireTableDataChange(tblModel);
    }

    @Override
    public int getSelectedRowAtTblAcoes() {
        return tblAcoes.getSelectedRow();
    }

    @Override
    public int getSelectedColumnAtTblAcoes() {
        return tblAcoes.getSelectedColumn();
    }

    @Override
    public Object getSelectedObjectAtTblAcoes(final int row, final int column) {
        return tblAcoes.getValueAt(row, column);
    }
}
