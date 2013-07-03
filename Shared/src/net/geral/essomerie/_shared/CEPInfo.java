package net.geral.essomerie._shared;

import java.security.InvalidParameterException;

public class CEPInfo {
  public static final String APENAS_UF     = "SP";
  public static final String APENAS_CIDADE = "São Paulo";

  public static int toInteger(String cepString) {
    cepString = cepString.replaceAll("[^\\d]", "");
    if (cepString.length() != 8) {
      throw new InvalidParameterException("Invalid CEP: " + cepString);
    }
    return Integer.parseInt(cepString);
  }

  public static String toString(final int cepInt) {
    return String.format("%05d-%03d", cepInt / 1000, cepInt % 1000);
  }

  public final int    cep;
  public final String uf;
  public final String cidade;

  public final String bairro;

  public final String endereco;

  public CEPInfo(final int cep, final String uf, final String cidade,
      final String bairro, final String endereco) {
    this.cep = cep;
    this.uf = uf;
    this.cidade = cidade;
    this.bairro = bairro;
    this.endereco = endereco;
  }

  public boolean checarUfCidadeValidos() {
    return (APENAS_UF.endsWith(uf) && APENAS_CIDADE.endsWith(cidade));
  }

  @Override
  public String toString() {
    return toString(cep);
  }
}
