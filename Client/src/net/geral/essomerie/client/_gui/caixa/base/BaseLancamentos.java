package net.geral.essomerie.client._gui.caixa.base;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client._gui.caixa.LancamentosListener;
import net.geral.essomerie.client._gui.caixa.SaveLoadException;
import net.geral.essomerie.shared.money.Money;

public abstract class BaseLancamentos {
  protected final Vector<BaseLancamento> lancamentos = new Vector<BaseLancamento>();
  private final EventListenerList        listeners   = new EventListenerList();

  public BaseLancamentos() {
    compactar();
  }

  public void addLancamentosListener(final LancamentosListener l) {
    listeners.add(LancamentosListener.class, l);
  }

  protected void compactar() {
    int i;
    // remover todos em branco, menos o ultimo
    for (i = 0; i < (lancamentos.size() - 1); i++) {
      if (lancamentos.get(i).isNull()) {
        lancamentos.remove(i);
        i--;
      }
    }
    // se o ultimo nao for em banco, criar novo
    if ((lancamentos.size() == 0) || (!lancamentos.get(i).isNull())) {
      criarNovo();
    }
  }

  public int count() {
    return lancamentos.size();
  }

  protected abstract void criarNovo();

  protected void fireLancamentosAlterados(final int index) {
    final Object[] ls = listeners.getListenerList();
    for (int i = 0; i < ls.length; i += 2) {
      if (ls[i] == LancamentosListener.class) {
        ((LancamentosListener) ls[i + 1]).lancamentoAlterado(this, index);
      }
    }
  }

  public BaseLancamento get(final int index) {
    if (index == lancamentos.size()) {
      return null;
    }
    return lancamentos.get(index);
  }

  public Object get(final int campo, final int index) {
    return get(index).get(campo);
  }

  protected abstract int getFieldCount();

  public abstract Money getTotal();

  public boolean load(final BufferedReader r) throws SaveLoadException {
    try {
      final int n = Integer.parseInt(r.readLine());
      lancamentos.clear();
      for (int i = 0; i < n; i++) {
        final String line = r.readLine();
        String[] parts = line.split(BaseLancamento.SEPARATOR);
        parts = setPartsArray(parts, getFieldCount());
        load(parts);
      }
    } catch (final Exception e) {
      if (e instanceof SaveLoadException) {
        throw (SaveLoadException) e;
      }
      throw new SaveLoadException(e);
    }
    fireLancamentosAlterados(-1);
    return true;
  }

  protected abstract void load(String[] ss);

  public void removeLancamentosListener(final LancamentosListener l) {
    listeners.remove(LancamentosListener.class, l);
  }

  public void save(final PrintStream p) throws SaveLoadException {
    try {
      p.println(lancamentos.size());
      for (final BaseLancamento l : lancamentos) {
        p.println(l.toString());
      }
    } catch (final Exception e) {
      throw new SaveLoadException(e);
    }
  }

  public void set(final int index, final int campo, final Object valor) {
    get(index).set(campo, valor);
    compactar();
    fireLancamentosAlterados(index);
  }

  private String[] setPartsArray(final String[] parts, final int size) {
    final String[] res = new String[size];
    for (int i = 0; i < size; i++) {
      res[i] = ((i >= parts.length) || (parts[i].length() == 0)) ? null
          : parts[i];
    }
    return res;
  }
}
