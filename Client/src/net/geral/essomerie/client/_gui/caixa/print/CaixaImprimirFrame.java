package net.geral.essomerie.client._gui.caixa.print;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import net.geral.essomerie.client._gui.caixa.CaixaDetalhes;
import net.geral.essomerie.client._gui.caixa.Lancamentos;

public class CaixaImprimirFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  // private final ResumoPrinter resumo;
  private final JButton     btnImprimir1;

  public CaixaImprimirFrame(final CaixaDetalhes detalhes,
      final Lancamentos lancamentos) {
    setTitle("Print Preview");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(new Dimension(600, 400));
    getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

    // resumo = new ResumoPrinter(detalhes, lancamentos);

    final JPanel column1 = new JPanel();
    getContentPane().add(column1);
    column1.setLayout(new BorderLayout(0, 0));

    final JScrollPane scroll1 = new JScrollPane();
    scroll1
        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroll1
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    column1.add(scroll1, BorderLayout.CENTER);

    // final ___PrintPreviewPanel resumoPanel = new
    // ___PrintPreviewPanel(resumo);
    // scroll1.setViewportView(resumoPanel);

    btnImprimir1 = new JButton("Imprimir");
    column1.add(btnImprimir1, BorderLayout.SOUTH);
    btnImprimir1.addActionListener(this);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final Object src = e.getSource();
    if (src == btnImprimir1) {
      // PrintSupport.print(resumo);
    }
  }
}
