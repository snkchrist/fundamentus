package com.snk.fundamentus.ui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import com.snk.fundamentus.controller.PrincipalController;

public class TelaPrincipal {

    private JFrame frmStocksearch;
    private JTable tblAcoes;
    private JTextField txtSearch;
    private JScrollPane scrollPane;
    private PrincipalController controller;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
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
        panel.setLayout(new MigLayout("", "[grow][grow][][]", "[][grow]"));

        txtSearch = new JTextField();
        panel.add(txtSearch, "cell 0 0 3 1,hmax 25px,grow");

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(controller.getBtnBuscarActionListener());

        panel.add(btnBuscar, "flowx,cell 3 0");

        JPanel pnlTable = new JPanel();

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        if (null != controller.getStockModel(null)) {
            tblAcoes = new JTable(controller.getStockModel(null));
        }
        else {
            tblAcoes = new JTable();
        }

        pnlTable.setPreferredSize(tblAcoes.getPreferredSize());
        scrollPane.add(pnlTable);
        scrollPane.setViewportView(pnlTable);

        panel.add(scrollPane, "cell 0 1,wmax 300,grow");
        pnlTable.setLayout(new MigLayout("", "[grow]", "[][grow]"));
        pnlTable.add(tblAcoes.getTableHeader(), "cell 0 0,growx, hmax 20, aligny top, wrap");
        pnlTable.add(tblAcoes, "cell 0 1,growx,aligny top");

        JPanel pnlDetalhes = new JPanel();
        panel.add(pnlDetalhes, "cell 1 1, span 3, grow");
        pnlDetalhes.setLayout(new MigLayout("", "[]", "[]"));

        JLabel lblDetalhes = new JLabel("Detalhes");
        pnlDetalhes.setLayout(new MigLayout("", "[][grow]", "[][]"));
        pnlDetalhes.add(lblDetalhes, "wrap");

        JButton btnAvancado = new JButton("Avan\u00E7ado");
        panel.add(btnAvancado, "cell 3 0,wmax 90");
    }

    public String getTextSearch() {
        return txtSearch.getText();
    }

    public void setTblAcoesModel(final TableModel tblModel) {
        tblAcoes.setModel(tblModel);
        tblAcoes.repaint();
        tblAcoes.revalidate();

    }
}
