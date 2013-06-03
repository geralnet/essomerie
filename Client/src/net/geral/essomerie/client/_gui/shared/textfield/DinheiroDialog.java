package net.geral.essomerie.client._gui.shared.textfield;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client.gui.shared.textfield.MoneyTextField;
import net.geral.essomerie.shared.money.Money;
import net.geral.essomerie.shared.money.MoneyType;
import net.geral.gui.textfield.IntegerTextField;
import net.geral.gui.textfield.formula.FormulaTextField;

// TODO translate & check
public class DinheiroDialog extends JDialog implements ActionListener,
    KeyListener {
  private static final long        serialVersionUID = 1L;
  private final JPanel             contentPanel     = new JPanel();
  private final IntegerTextField   txtNotas100Q;
  private final MoneyTextField     txtNotas100V;
  private final IntegerTextField   txtNotas50Q;
  private final MoneyTextField     txtNotas50V;
  private final IntegerTextField   txtNotas20Q;
  private final MoneyTextField     txtNotas20V;
  private final IntegerTextField   txtNotas10Q;
  private final MoneyTextField     txtNotas10V;
  private final IntegerTextField   txtNotas5Q;
  private final MoneyTextField     txtNotas5V;
  private final IntegerTextField   txtNotas2Q;
  private final MoneyTextField     txtNotas2V;
  private final IntegerTextField   txtMoedas100Q;
  private final MoneyTextField     txtMoedas100V;
  private final IntegerTextField   txtMoedas50Q;
  private final MoneyTextField     txtMoedas50V;
  private final IntegerTextField   txtMoedas25Q;
  private final MoneyTextField     txtMoedas25V;
  private final IntegerTextField   txtMoedas10Q;
  private final MoneyTextField     txtMoedas10V;
  private final IntegerTextField   txtMoedas5Q;
  private final MoneyTextField     txtMoedas5V;
  private final IntegerTextField   txtMoedas1Q;
  private final MoneyTextField     txtMoedas1V;
  private final MoneyTextField     txtValesV;
  private final MoneyTextField     txtChequesV;
  private final MoneyTextField     txtCartoesV;
  private final MoneyTextField     txtOutrosV;
  private final JPanel             panelBaixo;
  private final JButton            btnAceitar;
  private final JButton            btnCancelar;
  private final JPanel             panelBotoes;
  private final JLabel             lblTotal;
  private final JLabel             lblTotalValor;
  private final JPanel             panelTotal;

  private final IntegerTextField[] txtQuantidades;
  private final MoneyTextField[]   txtValores;
  private final JPanel             panelDetalhamento;
  private boolean                  cancelado        = true;

  private final MoneyType[]        emReais          = Money.getStandardTypes();

  @Deprecated
  public DinheiroDialog() {
    this(null, "Detalhamento");
  }

  public DinheiroDialog(final MoneyTextField ref, final String titulo) {
    setAlwaysOnTop(true);
    setModal(true);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setType(Type.UTILITY);
    setTitle("Detalhamento");
    setResizable(false);
    setSize(235, 550);
    setLocationRelativeTo(ref);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(new BorderLayout(0, 0));

    contentPanel.add(new TitleLabel(titulo), BorderLayout.NORTH);

    panelDetalhamento = new JPanel();
    contentPanel.add(panelDetalhamento, BorderLayout.CENTER);
    panelDetalhamento.setLayout(new GridLayout(0, 3, 1, 1));

    final JLabel lblNull = new JLabel("");
    panelDetalhamento.add(lblNull);

    final JLabel lblQtd = new JLabel("Qtd");
    lblQtd.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblQtd.setHorizontalAlignment(SwingConstants.CENTER);
    panelDetalhamento.add(lblQtd);

    final JLabel lblValor = new JLabel("$$$");
    lblValor.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblValor.setHorizontalAlignment(SwingConstants.CENTER);
    panelDetalhamento.add(lblValor);

    final JLabel lblNotas100 = new JLabel("Notas $100");
    panelDetalhamento.add(lblNotas100);

    txtNotas100Q = new IntegerTextField(false);
    panelDetalhamento.add(txtNotas100Q);
    txtNotas100Q.setColumns(10);

    txtNotas100V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtNotas100V);
    txtNotas100V.setColumns(10);

    final JLabel lblNotas50 = new JLabel("Notas $50");
    panelDetalhamento.add(lblNotas50);

    txtNotas50Q = new IntegerTextField(false);
    panelDetalhamento.add(txtNotas50Q);
    txtNotas50Q.setColumns(10);

    txtNotas50V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtNotas50V);
    txtNotas50V.setColumns(10);

    final JLabel lblNotas20 = new JLabel("Notas $20");
    panelDetalhamento.add(lblNotas20);

    txtNotas20Q = new IntegerTextField(false);
    panelDetalhamento.add(txtNotas20Q);
    txtNotas20Q.setColumns(10);

    txtNotas20V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtNotas20V);
    txtNotas20V.setColumns(10);

    final JLabel lblNotas10 = new JLabel("Notas $10");
    panelDetalhamento.add(lblNotas10);

    txtNotas10Q = new IntegerTextField(false);
    panelDetalhamento.add(txtNotas10Q);
    txtNotas10Q.setColumns(10);

    txtNotas10V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtNotas10V);
    txtNotas10V.setColumns(10);

    final JLabel lblNotas5 = new JLabel("Notas $5");
    panelDetalhamento.add(lblNotas5);

    txtNotas5Q = new IntegerTextField(false);
    panelDetalhamento.add(txtNotas5Q);
    txtNotas5Q.setColumns(10);

    txtNotas5V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtNotas5V);
    txtNotas5V.setColumns(10);

    final JLabel lblNotas2 = new JLabel("Notas $2");
    panelDetalhamento.add(lblNotas2);

    txtNotas2Q = new IntegerTextField(false);
    panelDetalhamento.add(txtNotas2Q);
    txtNotas2Q.setColumns(10);

    txtNotas2V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtNotas2V);
    txtNotas2V.setColumns(10);

    final JLabel lblMoedas100 = new JLabel("Moedas 1,00");
    panelDetalhamento.add(lblMoedas100);

    txtMoedas100Q = new IntegerTextField(false);
    panelDetalhamento.add(txtMoedas100Q);
    txtMoedas100Q.setColumns(10);

    txtMoedas100V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtMoedas100V);
    txtMoedas100V.setColumns(10);

    final JLabel lblMoedas50 = new JLabel("Moedas 0,50");
    panelDetalhamento.add(lblMoedas50);

    txtMoedas50Q = new IntegerTextField(false);
    panelDetalhamento.add(txtMoedas50Q);
    txtMoedas50Q.setColumns(10);

    txtMoedas50V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtMoedas50V);
    txtMoedas50V.setColumns(10);

    final JLabel lblMoedas25 = new JLabel("Moedas 0,25");
    panelDetalhamento.add(lblMoedas25);

    txtMoedas25Q = new IntegerTextField(false);
    panelDetalhamento.add(txtMoedas25Q);
    txtMoedas25Q.setColumns(10);

    txtMoedas25V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtMoedas25V);
    txtMoedas25V.setColumns(10);

    final JLabel lblMoedas10 = new JLabel("Moedas 0,10");
    panelDetalhamento.add(lblMoedas10);

    txtMoedas10Q = new IntegerTextField(false);
    panelDetalhamento.add(txtMoedas10Q);
    txtMoedas10Q.setColumns(10);

    txtMoedas10V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtMoedas10V);
    txtMoedas10V.setColumns(10);

    final JLabel lblMoedas5 = new JLabel("Moedas 0,05");
    panelDetalhamento.add(lblMoedas5);

    txtMoedas5Q = new IntegerTextField(false);
    panelDetalhamento.add(txtMoedas5Q);
    txtMoedas5Q.setColumns(10);

    txtMoedas5V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtMoedas5V);
    txtMoedas5V.setColumns(10);

    final JLabel lblMoedas1 = new JLabel("Moedas 0,01");
    panelDetalhamento.add(lblMoedas1);

    txtMoedas1Q = new IntegerTextField(false);
    panelDetalhamento.add(txtMoedas1Q);
    txtMoedas1Q.setColumns(10);

    txtMoedas1V = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtMoedas1V);
    txtMoedas1V.setColumns(10);

    final JLabel lblVales = new JLabel("Vales");
    panelDetalhamento.add(lblVales);

    final JLabel lbl1 = new JLabel("");
    panelDetalhamento.add(lbl1);

    txtValesV = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtValesV);
    txtValesV.setColumns(10);

    final JLabel lblCheques = new JLabel("Cheques");
    panelDetalhamento.add(lblCheques);

    final JLabel lbl2 = new JLabel("");
    panelDetalhamento.add(lbl2);

    txtChequesV = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtChequesV);
    txtChequesV.setColumns(10);

    final JLabel lblCartoes = new JLabel("Cart\u00F5es");
    panelDetalhamento.add(lblCartoes);

    final JLabel lbl3 = new JLabel("");
    panelDetalhamento.add(lbl3);

    txtCartoesV = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtCartoesV);
    txtCartoesV.setColumns(10);

    final JLabel lblOutros = new JLabel("Outros");
    panelDetalhamento.add(lblOutros);

    final JLabel lbl4 = new JLabel("");
    panelDetalhamento.add(lbl4);

    txtOutrosV = new MoneyTextField(false, true, true, false);
    panelDetalhamento.add(txtOutrosV);
    txtOutrosV.setColumns(10);

    panelBaixo = new JPanel();
    contentPanel.add(panelBaixo, BorderLayout.SOUTH);
    panelBaixo.setLayout(new BorderLayout(0, 0));

    panelBotoes = new JPanel();
    final FlowLayout flowLayout = (FlowLayout) panelBotoes.getLayout();
    flowLayout.setAlignment(FlowLayout.TRAILING);
    panelBaixo.add(panelBotoes, BorderLayout.SOUTH);

    btnAceitar = new JButton("Aceitar");
    btnAceitar.addActionListener(this);
    btnAceitar.setHorizontalAlignment(SwingConstants.TRAILING);
    panelBotoes.add(btnAceitar);

    btnCancelar = new JButton("Cancelar");
    btnCancelar.addActionListener(this);
    btnCancelar.setHorizontalAlignment(SwingConstants.TRAILING);
    panelBotoes.add(btnCancelar);

    panelTotal = new JPanel();
    panelBaixo.add(panelTotal, BorderLayout.NORTH);
    panelTotal.setLayout(new FlowLayout(FlowLayout.TRAILING, 4, 0));

    lblTotal = new JLabel("Total:  ");
    panelTotal.add(lblTotal);
    lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblTotal.setHorizontalAlignment(SwingConstants.TRAILING);

    lblTotalValor = new JLabel("-.--");
    panelTotal.add(lblTotalValor);

    txtQuantidades = new IntegerTextField[] { txtNotas100Q, txtNotas50Q,
        txtNotas20Q, txtNotas10Q, txtNotas5Q, txtNotas2Q, txtMoedas100Q,
        txtMoedas50Q, txtMoedas25Q, txtMoedas10Q, txtMoedas5Q, txtMoedas1Q };

    txtValores = new MoneyTextField[] { txtNotas100V, txtNotas50V, txtNotas20V,
        txtNotas10V, txtNotas5V, txtNotas2V, txtMoedas100V, txtMoedas50V,
        txtMoedas25V, txtMoedas10V, txtMoedas5V, txtMoedas1V };

    ajustarTextFields();
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final Object src = e.getSource();
    if (src instanceof JButton) {
      cancelado = (src == btnCancelar);
      setVisible(false);
    }
  }

  private void ajustarTextFields() {
    for (int i = 0; i < emReais.length; i++) {
      txtValores[i].setMultiple(Money.fromLong(emReais[i].getMultiplier()));
    }

    for (final Component c : panelDetalhamento.getComponents()) {
      if (c instanceof JTextField) {
        c.addKeyListener(this);
      }
    }
  }

  private void alterarValorCorrespondente(final Object src) {
    try {
      for (int i = 0; i < emReais.length; i++) {
        if (src == txtQuantidades[i]) {
          txtValores[i].setValue(Money.fromDouble(txtQuantidades[i]
              .getInteger() * emReais[i].getDouble()));
          return;
        }
        if (src == txtValores[i]) {
          txtQuantidades[i].setValue(new Integer((int) (txtValores[i].getValue(
              false).getDouble() / emReais[i].getDouble())));
          return;
        }
      }
    } catch (final NumberFormatException e) {
    }
  }

  private void atualizarTotal() {
    final Money d = getValue();
    if (d == null) {
      lblTotalValor.setText("-,--");
      btnAceitar.setEnabled(false);
    } else {
      lblTotalValor.setText(d.toString());
      btnAceitar.setEnabled(true);
    }
  }

  public boolean getCancelled() {
    return cancelado;
  }

  public Money getValue() throws NumberFormatException {
    // verificar por erros
    for (final Component c : panelDetalhamento.getComponents()) {
      if (c instanceof FormulaTextField<?>) {
        if (((FormulaTextField<?>) c).hasError()) {
          return null;
        }
      }
    }

    Money valor = Money.zero();
    for (final MoneyTextField txtValore : txtValores) {
      valor = valor.add(txtValore.getValue(false));
    }
    valor = valor.add(txtValesV.getValue(false));
    valor = valor.add(txtChequesV.getValue(false));
    valor = valor.add(txtCartoesV.getValue(false));
    valor = valor.add(txtOutrosV.getValue(false));

    return valor;
  }

  @Override
  public void keyPressed(final KeyEvent e) {
  }

  @Override
  public void keyReleased(final KeyEvent e) {
    alterarValorCorrespondente(e.getSource());
    atualizarTotal();
  }

  @Override
  public void keyTyped(final KeyEvent e) {
  }

  public void reset() {
    for (final Component c : panelDetalhamento.getComponents()) {
      if (c instanceof IntegerTextField) {
        final IntegerTextField dftf = (IntegerTextField) c;
        dftf.setValue(null);
      }
    }
    atualizarTotal();
  }

  public void setValue(final Money dinheiro) {
    if (dinheiro != getValue()) {
      reset();
    }
  }
}
