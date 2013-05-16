package net.geral.essomerie._shared.roster;

import java.io.Serializable;
import java.util.Arrays;

//TODO translate & check
public class RosterInfoAssignments implements Serializable {
  private static final long     serialVersionUID = 1L;
  // TODO standard (padrao) from database
  private static final String[] padrao           = { "Abertura", "Caixa (T)",
      "Caixa (1)", "Garçom (TV)", "Garçom (TN)", "Garçom (1V)", "Garçom (1N)",
      "Copa (T)", "Copa (1)", "Cozinha (T)", "Cozinha (1)", "Cozinha (2)",
      "Delivery", "Entregador", "Faxina", "Escritório", "Maître (T)",
      "Maître (1)", "Garçom (P)"                };

  static {
    Arrays.sort(padrao);
  }

  public static String[] getFuncoesDefault() {
    return padrao.clone();
  }

  private final int id;
  private String    funcao;
  private String[]  funcionarios;

  public RosterInfoAssignments() {
    this(0, "", "");
  }

  public RosterInfoAssignments(final int id, final String funcao,
      final String funcionarios) {
    this.id = id;
    this.funcao = funcao;
    this.funcionarios = new String[] { funcionarios };
  }

  public RosterInfoAssignments(final int id, final String funcao,
      final String[] funcionarios) {
    this.id = id;
    this.funcao = funcao;
    this.funcionarios = funcionarios;
  }

  public String getFuncao() {
    return funcao;
  }

  public String[] getFuncionariosArray() {
    return funcionarios;
  }

  public String getFuncionariosString() {
    if (funcionarios.length == 0) {
      return "n/a";
    }
    if (funcionarios.length == 1) {
      return funcionarios[0];
    }

    final StringBuilder sb = new StringBuilder();
    boolean primeiro = true;
    for (final String f : funcionarios) {
      if (primeiro) {
        primeiro = false;
      } else {
        sb.append(" ; ");
      }
      sb.append(f);
    }

    return sb.toString();
  }

  public int getId() {
    return id;
  }

  public void setFuncao(final String funcao) {
    this.funcao = funcao;
  }

  public void setFuncionarios(final String funcionarios) {
    this.funcionarios = new String[] { funcionarios };
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + id + ";" + funcao + ";"
        + funcionarios.length + "]";
  }
}
