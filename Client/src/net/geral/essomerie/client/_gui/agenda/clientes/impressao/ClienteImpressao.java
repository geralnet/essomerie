package net.geral.essomerie.client._gui.agenda.clientes.impressao;

import java.awt.Font;

import net.geral.essomerie.client._printing.CRPrintDocument;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephone;

public class ClienteImpressao extends CRPrintDocument {
  public static enum ClienteImpressaoVia {
    ENTREGADOR,
    CAIXA;

    @Override
    public String toString() {
      return "VIA #" + (ordinal() + 1) + " (" + super.toString() + ")";
    }
  }

  private static final int          ESPACO_LINHAS_EM_BRANCO     = 20;
  private static final int          QUANTIDADE_LINHAS_EM_BRANCO = 10;

  private final PersonData          cliente;
  private final ClienteImpressaoVia via;

  public ClienteImpressao(final PersonData c, final ClienteImpressaoVia v) {
    cliente = c;
    via = v;
  }

  @Override
  protected void printBody() {
    centralizar("C#" + cliente.getId() + " - " + via, new Font("SansSerif",
        Font.BOLD, 10));
    separator();
    g.setFont(new Font("SansSerif", Font.BOLD, 10));
    writeline(cliente.getName());
    g.setFont(new Font("SansSerif", Font.PLAIN, 8));
    // writeBold("CPF: "); // TODO
    // write(cliente.cpf.isZero() ? "não informado" : cliente.cpf.toString());
    // if (cliente.notaPaulista) {
    // writeBold("   *** NOTA PAULISTA ***");
    // }
    // newline(); // terminar linha dos CPF
    newline();
    writeline(cliente.getComments().length() > 0 ? cliente.getComments() : "-");
    newline();
    for (int i = 0; i < cliente.getAddresses().getCount(); i++) {
      final String num = cliente.getAddresses().getCount() == 1 ? "" : " "
          + (i + 1);
      writeBold("Endereço" + num);
      write(" (E#" + cliente.getAddresses().get(i).getId() + ")");
      writeBold(":");
      newline(); // endereco
      writeline(cliente.getAddresses().get(i).toString());
      newline(); // espacar
    }
    writeBold("Telefone" + (cliente.getTelephones().getCount() > 1 ? "s" : "")
        + ":");
    newline(); // telefone
    for (final Telephone t : cliente.getTelephones().getAll()) {
      writeline(t.toString(true));
    }
    if (via == ClienteImpressaoVia.CAIXA) {
      feed(ESPACO_LINHAS_EM_BRANCO);
      for (int i = 0; i < QUANTIDADE_LINHAS_EM_BRANCO; i++) {
        feed(ESPACO_LINHAS_EM_BRANCO);
        separator(0.5f);
      }
      feed(ESPACO_LINHAS_EM_BRANCO);
    }
  }
}