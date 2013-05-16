package net.geral.essomerie.client._gui.caixa.base;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.geral.essomerie._shared.Dinheiro;
import net.geral.essomerie.client._gui.caixa.LancamentosListener;
import net.geral.essomerie.client._gui.shared.label.DinheiroLabel;

public abstract class BaseLancamentosPanel extends JPanel implements
	LancamentosListener {
    private static final long serialVersionUID = 1L;
    private final BaseLancamentosTable table;
    private final DinheiroLabel lblTotal;

    public BaseLancamentosPanel(final String titulo,
	    final BaseLancamentosTableModel model) {
	table = new BaseLancamentosTable(model);
	setLayout(new BorderLayout(0, 0));

	final JPanel panel = new JPanel();
	add(panel, BorderLayout.NORTH);
	panel.setLayout(new BorderLayout(0, 0));

	final JLabel lblTitulo = new JLabel(titulo);
	panel.add(lblTitulo, BorderLayout.WEST);
	lblTitulo.setFont(lblTitulo.getFont().deriveFont(
		lblTitulo.getFont().getStyle() | Font.BOLD));

	lblTotal = new DinheiroLabel(true);
	panel.add(lblTotal, BorderLayout.EAST);

	final JScrollPane scrollPane = new JScrollPane();
	scrollPane
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	add(scrollPane, BorderLayout.CENTER);

	scrollPane.setViewportView(table);

	table.setScroll(scrollPane);

	model.lancamentos.addLancamentosListener(this);
	lancamentoAlterado(model.lancamentos, -1);
    }

    @Override
    public void lancamentoAlterado(final BaseLancamentos origem, final int index) {
	final Dinheiro d = origem.getTotal();
	if (d == null) {
	    lblTotal.showErro();
	    lblTotal.setVisible(false);
	} else {
	    lblTotal.setDinheiro(d);
	    lblTotal.setVisible(true);
	}
    }
}
