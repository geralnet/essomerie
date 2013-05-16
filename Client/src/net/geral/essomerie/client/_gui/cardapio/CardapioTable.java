package net.geral.essomerie.client._gui.cardapio;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.geral.essomerie._shared.cardapio.CardapioCategoria;
import net.geral.essomerie._shared.cardapio.CardapioItem;
import net.geral.log4j.Log4J;

public class CardapioTable extends JTable implements TableModel,
    ComponentListener {
  private static final long               serialVersionUID = 1L;

  private static final String[]           COLUMNS          = { "Categoria",
      "Sub-Categoria", "Cod", "Item", "Descrição", "Meia", "Inteira" };
  private static final int[]              WIDTHS           = { 100, 100, 40,
      200, 0, 60, 60                                      };

  private final JScrollPane               parentScroll;
  private final CardapioItem[]            encontrados      = new CardapioItem[0];

  private final CardapioTableCellRenderer normalRenderer   = CardapioTableCellRenderer
                                                               .createNormal();
  private final CardapioTableCellRenderer codigoRenderer   = CardapioTableCellRenderer
                                                               .createCodigo();
  private final CardapioTableCellRenderer precoRenderer    = CardapioTableCellRenderer
                                                               .createPreco();

  public CardapioTable(final JScrollPane scroll) {
    parentScroll = scroll;
    parentScroll.addComponentListener(this);
    setModel(this);
    setAutoResizeMode(AUTO_RESIZE_OFF);
  }

  @Override
  public void addTableModelListener(final TableModelListener l) {
  }

  @Override
  public void componentHidden(final ComponentEvent e) {
  }

  @Override
  public void componentMoved(final ComponentEvent e) {
  }

  @Override
  public void componentResized(final ComponentEvent e) {
    final TableColumnModel cm = getColumnModel();
    final int width = parentScroll.getViewport().getWidth();

    int total = 0;
    for (int i = 0; i < WIDTHS.length; i++) {
      cm.getColumn(i).setPreferredWidth(WIDTHS[i]);
      total += WIDTHS[i];
    }

    cm.getColumn(4).setPreferredWidth(width - total); // descricao
  }

  @Override
  public void componentShown(final ComponentEvent e) {
  }

  public void filter(final String txt) {
    // String codigo = txt.gntrim();
    // codigo = codigo.toUpperCase();
    //
    // final String nome = Conversor.toAZ09(txt, false);
    //
    // encontrados = (txt.length() == 0) ?
    // Client.cache().cardapio().getAllItems() : Client.cache().cardapio()
    // .getItemsWith(codigo, nome);
    //
    // tableChanged(new TableModelEvent(this));
  }

  @Override
  public TableCellRenderer getCellRenderer(final int row, final int column) {
    switch (column) {
      case 2:
        return codigoRenderer;
      case 5:
      case 6:
        return precoRenderer;
      default:
        return normalRenderer;
    }
  }

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return String.class;
  }

  @Override
  public int getColumnCount() {
    return COLUMNS.length;
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return COLUMNS[columnIndex];
  }

  @Override
  public int getRowCount() {
    return encontrados.length;
  }

  @Override
  public Object getValueAt(final int rowIndex, final int columnIndex) {
    final CardapioItem item = encontrados[rowIndex];
    CardapioCategoria categoria = item.getCategoria();
    CardapioCategoria subcategoria = null;
    if (categoria.getBase() != null) {
      subcategoria = categoria;
      categoria = categoria.getBase();
    }

    switch (columnIndex) {
      case 0:
        return (categoria == null) ? "" : categoria.nome;
      case 1:
        return (subcategoria == null) ? "" : subcategoria.nome;
      case 2:
        return item.codigo;
      case 3:
        return item.nome;
      case 4:
        return item.descricaoPortugues;
      case 5:
        return item.meia.toString();
      case 6:
        return item.inteira.toString();
      default:
        Log4J.warning("Invalid column: " + columnIndex);
        return "n/a";
    }
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return false;
  }

  @Override
  public void removeTableModelListener(final TableModelListener l) {
  }

  @Override
  public void setValueAt(final Object aValue, final int rowIndex,
      final int columnIndex) {
    Log4J.warning("Cannot change value.");
  }
}
