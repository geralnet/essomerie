package net.geral.essomerie._shared.cliente;

import java.io.Serializable;

import net.geral.essomerie._shared.CPF;
import net.geral.lib.strings.GNStrings;

//TODO remove search from here, go to clientes cache (?)
public class ClienteData implements Comparable<ClienteData>, Serializable {
  private static final long serialVersionUID = 1L;
  public static final int   NOME_MAX_LENGTH  = 250;

  public final int          idcliente;
  public String             nome;
  public final CPF          cpf;
  public final boolean      notaPaulista;
  public final String       observacoes;

  public ClienteData(final ClienteData cd) {
    this(cd.idcliente, cd.nome, cd.cpf, cd.notaPaulista, cd.observacoes);
  }

  public ClienteData(final int _idcliente, final String _nome, final CPF _cpf,
      final boolean _notaPaulista, final String _observacoes) {
    idcliente = _idcliente;
    nome = GNStrings.trim(_nome);
    cpf = _cpf;
    notaPaulista = _notaPaulista;
    observacoes = GNStrings.trim(_observacoes);
  }

  @Override
  public int compareTo(final ClienteData o) {
    return nome.compareToIgnoreCase(o.nome);
  }
}
