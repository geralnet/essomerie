package net.geral.essomerie.client._gui.agenda.funcionarios.detalhes;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import net.geral.essomerie._shared.funcionario.Funcionario;
import net.geral.essomerie.client._gui.shared.label.TitleLabel;

public class FuncionarioDetalhes extends JPanel {
  private static final long                  serialVersionUID = 1L;
  private final TitleLabel                   lblTitulo;
  private final FuncionarioPessoalPanel      tabPessoal;
  private final FuncionarioProfissionalPanel tabProfissional;

  public FuncionarioDetalhes() {
    setLayout(new BorderLayout(0, 0));

    lblTitulo = new TitleLabel("[Carregando...]");
    add(lblTitulo, BorderLayout.NORTH);

    final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    add(tabbedPane);

    tabPessoal = new FuncionarioPessoalPanel();
    tabbedPane.addTab("Pessoal", null, tabPessoal, null);

    tabProfissional = new FuncionarioProfissionalPanel();
    tabbedPane.addTab("Profissional", null, tabProfissional, null);

    setFuncionario(null);
  }

  public void setCargosBancos(final String[] cargos, final String[] bancos) {
    tabProfissional.setCargos(cargos);
    tabProfissional.setBancos(bancos);
  }

  public void setFuncionario(final Funcionario f) {
    final boolean n = (f == null);
    // lblTitulo.setText(n ? "Dados de Funcionários" : f.pessoal.nome);
    // tabPessoal.setDados(n ? null : f.cadastradoEm, n ? null : f.pessoal);
    tabProfissional.setDados(n ? null : f.cadastradoEm, n ? null
        : f.profissional);
  }
}
