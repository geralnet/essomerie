package net.geral.essomerie.client._gui.agenda.clientes.paineis;

import net.geral.lib.strings.GNStrings;
import net.geral.lib.strings._Conversor;

public class ClientePanelFilter {
  private static int prepareCodigo(String codigo) {
    codigo = _Conversor.stringOrEmpty(codigo, true);
    if (codigo.length() == 0) {
      return 0;
    }
    try {
      return Integer.parseInt(codigo);
    } catch (final NumberFormatException e) {
      return -1;
    }
  }

  private static String prepareCPF(String cpf) {
    cpf = _Conversor.stringOrEmpty(cpf, true);
    if (cpf.length() == 0) {
      return null;
    }
    cpf.replaceAll("[\\ \\. \\-]", "");
    try {
      Long.parseLong(cpf);
      return cpf;
    } catch (final NumberFormatException e) {
      return "";
    }
  }

  private static String[] prepareEndereco(String endereco) {
    endereco = GNStrings.trim(endereco);
    if (endereco.length() == 0) {
      return new String[0];
    }
    return endereco.split(" ");
  }

  private static String[] prepareNome(String nome) {
    nome = _Conversor.stringOrEmpty(nome, true);
    if (nome.length() == 0) {
      return new String[0];
    }
    nome = _Conversor.toPhonetic(nome);
    return nome.split(" ");
  }

  private static String[] prepareObservacoes(String observacoes) {
    observacoes = observacoes.replaceAll("\\s+", " ");
    observacoes = _Conversor.toAZ09(observacoes, false);
    if (observacoes.length() == 0) {
      return new String[0];
    }
    return observacoes.split(" ");
  }

  private static String prepareTelefone(String telefone) {
    telefone = _Conversor.stringOrEmpty(telefone, true);
    if (telefone.length() == 0) {
      return null;
    }
    return telefone.toUpperCase().replaceAll("[^0-9A-Z]", "");
  }

  private final int      codigo;
  private final String[] nome;
  private final String   cpf;
  private final String   telefone;
  private final String[] endereco;
  private final String[] enderecoFonetico;
  private final String[] enderecoNumerico;
  private final String[] observacoes;

  public ClientePanelFilter(final int _codigo) {
    codigo = _codigo;
    nome = new String[0];
    cpf = null;
    telefone = null;
    endereco = new String[0];
    enderecoFonetico = new String[endereco.length];
    enderecoNumerico = new String[endereco.length];
    observacoes = new String[0];
  }

  public ClientePanelFilter(final String _codigo, final String _nome,
      final String _cpf, final String _telefone, final String _endereco,
      final String _observacoes) {
    codigo = prepareCodigo(_codigo);
    nome = prepareNome(_nome);
    cpf = prepareCPF(_cpf);
    telefone = prepareTelefone(_telefone);
    endereco = prepareEndereco(_endereco);
    enderecoFonetico = new String[endereco.length];
    enderecoNumerico = new String[endereco.length];
    observacoes = prepareObservacoes(_observacoes);
    prepareEnderecos();
  }

  public Boolean isFoundByCodigo(final int checkCodigo) {
    if (codigo == 0) {
      return null;
    }
    if (codigo == -1) {
      return false;
    }
    return (codigo == checkCodigo);
  }

  public Boolean isFoundByCPF(final String filterCPF) {
    if (cpf == null) {
      return null;
    }
    if (cpf.length() == 0) {
      return false;
    }
    return filterCPF.contains(cpf);
  }

  public Boolean isFoundByEnderecos(final String filterFonetico,
      final String filterNumerico) {
    // se nao foi fornecido endereco, null
    if (endereco.length == 0) {
      return null;
    }
    // para cada parametro, ou tem numerico ou fonetico
    for (int i = 0; i < endereco.length; i++) {
      final String ef = enderecoFonetico[i];
      final String en = enderecoNumerico[i];
      final Boolean fonetico = (ef.length() == 0) ? null : filterFonetico
          .contains(ef);
      final Boolean numerico = (en.length() == 0) ? null : filterNumerico
          .contains(en);
      // termo nao da pra pesquisar, ignorar
      if ((fonetico == null) && (numerico == null)) {
        continue;
      }
      // termo nao eh fonetico, se nao achou numerico, nao achou
      if (fonetico == null) {
        if (!numerico) {
          return false;
        }
      }
      // termo nao eh numerico, se nao achou fonetico, nao achou
      else if (numerico == null) {
        if (!fonetico) {
          return false;
        }
      }
      // se nao achou nenhum dos dois termos, nao achou
      else {
        if (!(numerico.booleanValue() && fonetico.booleanValue())) {
          return false;
        }
      }
    }
    // nao achou nenhuma divergencia
    return true;
  }

  public Boolean isFoundByNome(final String filterNome) {
    if (nome.length == 0) {
      return null;
    }
    for (final String n : nome) {
      if (!filterNome.contains(n)) {
        return false;
      }
    }
    return true;
  }

  public Boolean isFoundByObservacoes(final String filterObservacoes) {
    if (observacoes.length == 0) {
      return null;
    }
    for (final String n : observacoes) {
      if (!filterObservacoes.contains(n)) {
        return false;
      }
    }
    return true;
  }

  public Boolean isFoundByTelefones(final String filterTelefones) {
    if (telefone == null) {
      return null;
    }
    return filterTelefones.contains(telefone);
  }

  public boolean nenhum() {
    if (codigo != 0) {
      return false;
    }
    if (nome.length != 0) {
      return false;
    }
    if (cpf != null) {
      return false;
    }
    if (telefone != null) {
      return false;
    }
    if (endereco.length != 0) {
      return false;
    }
    if (observacoes.length > 0) {
      return false;
    }
    return true;
  }

  public boolean porCodigo() {
    return (codigo != 0);
  }

  private void prepareEnderecos() {
    for (int i = 0; i < endereco.length; i++) {
      final String e = endereco[i];
      enderecoFonetico[i] = _Conversor.toPhonetic(e);
      enderecoNumerico[i] = e.replaceAll("[\\D]", "");
    }
  }
}
