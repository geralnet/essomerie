package net.geral.essomerie._shared.roster;

import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.LocalDate;

// TODO translate check
public class RosterInfo implements Serializable {
  private static final long                      serialVersionUID = 1L;
  private final LocalDate                        data;
  private final boolean                          dia;

  private final ArrayList<RosterInfoAssignments> escala           = new ArrayList<RosterInfoAssignments>();

  public RosterInfo() {
    data = new LocalDate(0);
    dia = true;
  }

  public RosterInfo(final LocalDate data, final boolean dia) {
    this.data = data;
    this.dia = dia;
  }

  public void addFuncaoFuncionarios(final int id, final String funcao,
      final String funcionarios) {
    addFuncaoFuncionarios(new RosterInfoAssignments(id, funcao, funcionarios));
  }

  public void addFuncaoFuncionarios(final RosterInfoAssignments ria) {
    escala.add(ria);
  }

  public int countAssignments() {
    return escala.size();
  }

  public RosterInfoAssignments[] getAllAssignments() {
    return escala.toArray(new RosterInfoAssignments[escala.size()]);
  }

  public String getAssignment(final int i) {
    return escala.get(i).getFuncao();
  }

  public LocalDate getDate() {
    return data;
  }

  public boolean getDayShift() {
    return dia;
  }

  public String getEmployees(final int i) {
    return escala.get(i).getFuncionariosString();
  }

  public RosterInfoAssignments[] getEscala() {
    return escala.toArray(new RosterInfoAssignments[escala.size()]);
  }

  public RosterInfoAssignments getEscalaFuncionario(final int i) {
    return escala.get(i);
  }

  public void remover(final int i) {
    escala.remove(i);
  }

  public void remover(final RosterInfoAssignments re) {
    escala.remove(re);
  }

  public void setFuncao(final int i, final String f) {
    escala.get(i).setFuncao(f);
  }

  public void setFuncaoFuncionarios(final int i, final RosterInfoAssignments ref) {
    escala.set(i, ref);
  }

  public void setFuncionarios(final int i, final String f) {
    escala.get(i).setFuncionarios(f);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + data + ";" + dia + ";" + escala
        + "]";
  }
}
