package net.geral.essomerie.client.gui.organizer.persons.editors;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import net.geral.essomerie._shared.Dinheiro;
import net.geral.essomerie.client.gui.organizer.persons.PersonEditorPanel;
import net.geral.essomerie.client.gui.organizer.persons.editors.tables.sales.SalesTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonSale;
import net.geral.essomerie.shared.person.PersonSales;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

public class SalesPersonEditor extends PersonEditorPanel {
  private static final Logger logger           = Logger
                                                   .getLogger(SalesPersonEditor.class);
  private static final long   serialVersionUID = 1L;
  private final JPanel        summaryPanel;
  private final JLabel        lblOrders;
  private final JLabel        lblSpent;
  private final JLabel        lblAverage;
  private final JLabel        lblLastOrder;
  private final SalesTable    table;
  private final JLabel        lblTFirstOrder;
  private final JLabel        lblFirstOrder;

  public SalesPersonEditor() {
    setLayout(new BorderLayout(0, 0));

    summaryPanel = new JPanel();
    summaryPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(summaryPanel, BorderLayout.NORTH);
    summaryPanel.setLayout(new GridLayout(2, 0, 0, 0));

    final JLabel lblTOrders = new JLabel(S.ORGANIZER_PERSONS_SALES_ORDERS.s());
    lblTOrders.setHorizontalAlignment(SwingConstants.CENTER);
    lblTOrders.setFont(lblTOrders.getFont().deriveFont(
        lblTOrders.getFont().getStyle() | Font.BOLD));
    summaryPanel.add(lblTOrders);

    final JLabel lblTSpent = new JLabel(S.ORGANIZER_PERSONS_SALES_SPENT.s());
    lblTSpent.setHorizontalAlignment(SwingConstants.CENTER);
    lblTSpent.setFont(lblTSpent.getFont().deriveFont(
        lblTSpent.getFont().getStyle() | Font.BOLD));
    summaryPanel.add(lblTSpent);

    final JLabel lblTAverage = new JLabel(S.ORGANIZER_PERSONS_SALES_AVERAGE.s());
    lblTAverage.setHorizontalAlignment(SwingConstants.CENTER);
    lblTAverage.setFont(lblTAverage.getFont().deriveFont(
        lblTAverage.getFont().getStyle() | Font.BOLD));
    summaryPanel.add(lblTAverage);

    lblTFirstOrder = new JLabel(S.ORGANIZER_PERSONS_SALES_FIRSTORDER.s());
    lblTFirstOrder.setHorizontalAlignment(SwingConstants.CENTER);
    lblTFirstOrder.setFont(lblTFirstOrder.getFont().deriveFont(
        lblTFirstOrder.getFont().getStyle() | Font.BOLD));
    summaryPanel.add(lblTFirstOrder);

    final JLabel lblTLastOrder = new JLabel(
        S.ORGANIZER_PERSONS_SALES_LASTORDER.s());
    lblTLastOrder.setHorizontalAlignment(SwingConstants.CENTER);
    lblTLastOrder.setFont(lblTLastOrder.getFont().deriveFont(
        lblTLastOrder.getFont().getStyle() | Font.BOLD));
    summaryPanel.add(lblTLastOrder);

    lblOrders = new JLabel("-");
    lblOrders.setHorizontalAlignment(SwingConstants.CENTER);
    summaryPanel.add(lblOrders);

    lblSpent = new JLabel("-");
    lblSpent.setHorizontalAlignment(SwingConstants.CENTER);
    summaryPanel.add(lblSpent);

    lblAverage = new JLabel("-");
    lblAverage.setHorizontalAlignment(SwingConstants.CENTER);
    summaryPanel.add(lblAverage);

    lblFirstOrder = new JLabel("-");
    lblFirstOrder.setHorizontalAlignment(SwingConstants.CENTER);
    summaryPanel.add(lblFirstOrder);

    lblLastOrder = new JLabel("-");
    lblLastOrder.setHorizontalAlignment(SwingConstants.CENTER);
    summaryPanel.add(lblLastOrder);

    table = new SalesTable();
    add(table.getScroll(), BorderLayout.CENTER);
  }

  public PersonSales getSales(final int idperson) {
    return new PersonSales(idperson, table.getModel().getAll());
  }

  private void set(final PersonSales sales) {
    if (sales == null) {
      lblOrders.setText("-");
      lblSpent.setText("-");
      lblAverage.setText("-");
      lblFirstOrder.setText("-");
      lblLastOrder.setText("-");
      table.getModel().clear();
    } else {
      sales.calculateCache();
      lblOrders.setText(String.valueOf(sales.getCount()));
      lblSpent.setText(sales.getCacheTotal().toString());
      final Dinheiro average = sales.getCacheAverage();
      lblAverage.setText(average == null ? "-" : average.toString());
      final PersonSale first = sales.getCacheFirstOrder();
      lblFirstOrder.setText(first == null ? "-" : first.getWhen().toString(
          DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s())));
      final PersonSale last = sales.getCacheLastOrder();
      lblLastOrder.setText(last == null ? "-" : last.getWhen().toString(
          DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s())));
      table.getModel().setData(sales.getAll());
    }
  }

  @Override
  public void setEditable(final boolean yn) {
    table.setEditable(yn);
  }

  @Override
  public void setPerson(final Person p) {
    if (p == null) {
      set(null);
    } else if (!(p instanceof PersonData)) {
      set(null);
    } else {
      set(((PersonData) p).getSales());
    }
  }
}
