package net.geral.essomerie.server.db.areas;

import java.sql.SQLException;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie._shared.contagem.ContagemMotivo;
import net.geral.essomerie._shared.contagem.ContagemMotivos;
import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie._shared.contagem.InventoryItem;

//TODO translate & check
public class InventoryDB extends DatabaseArea {
    public InventoryDB(final Database database) {
	super(database);
    }

    private InventoryGroup[] getContagemGrupos() throws SQLException {
	return getContagemGrupos(0);
    }

    private InventoryGroup[] getContagemGrupos(final int pai)
	    throws SQLException {
	final String sql = "SELECT `id`,`nome` "// select
		+ "FROM `contagem_grupos` "// from
		+ " WHERE `subgrupo_de`=? AND `excluido`='N' "// where
		+ " ORDER BY `ordem` ASC, `nome` ASC";
	final PreparedResultSet p = db.select(sql, pai);
	final InventoryGroup[] cg = new InventoryGroup[p.getRowCount()];
	for (int i = 0; i < cg.length; i++) {
	    p.rs.next();
	    final int id = p.rs.getInt("id");
	    final String nome = p.rs.getString("nome");
	    final InventoryGroup[] subgrupos = (pai == 0) ? getContagemGrupos(id)
		    : null; // apenas um nivel de subgrupos
	    cg[i] = new InventoryGroup(id, nome, subgrupos);
	}
	p.close();
	return cg;
    }

    private InventoryItem[] getContagemItens() throws SQLException {
	final String sql = "SELECT `id`,`nome`,`unidade`,`quantidade` "// select
		+ "FROM `contagem_itens` "// from
		+ " WHERE `excluido`='N' "// where
		+ " ORDER BY `nome` ASC";
	final PreparedResultSet p = db.select(sql);
	final InventoryItem[] ci = new InventoryItem[p.getRowCount()];
	for (int i = 0; i < ci.length; i++) {
	    p.rs.next();
	    final int id = p.rs.getInt("id");
	    final String nome = p.rs.getString("nome");
	    final String unidade = p.rs.getString("unidade");
	    final float quantidade = p.rs.getFloat("quantidade");
	    final int[] grupos = getContagemItensGrupos(id);
	    ci[i] = new InventoryItem(id, nome, unidade, quantidade, grupos);
	}
	p.close();
	return ci;
    }

    private int[] getContagemItensGrupos(final int iditem) throws SQLException {
	final String sql = "SELECT `idgrupo` "// select
		+ "FROM `contagem_itemXgrupo` "// from
		+ " WHERE `iditem`=? ";// where
	final PreparedResultSet p = db.select(sql, iditem);
	final int[] is = new int[p.getRowCount()];
	for (int i = 0; i < is.length; i++) {
	    p.rs.next();
	    is[i] = p.rs.getInt(1);
	}
	p.close();
	return is;
    }

    private ContagemMotivos getContagemMotivos() throws SQLException {
	final String sql = "SELECT `id`,`tipo`,`motivo` "// select
		+ "FROM `contagem_motivos` ORDER BY `tipo` ASC, `id` ASC";
	final PreparedResultSet p = db.select(sql);
	final ContagemMotivo[] cm = new ContagemMotivo[p.getRowCount()];
	for (int i = 0; i < cm.length; i++) {
	    p.rs.next();
	    final int id = p.rs.getInt("id");
	    final char tipo = p.rs.getString("tipo").charAt(0);
	    final String motivo = p.rs.getString("motivo");
	    cm[i] = new ContagemMotivo(id, tipo, motivo);
	}
	p.close();
	return new ContagemMotivos(cm);
    }

    public Inventory getFullData() throws SQLException {
	final Inventory c = new Inventory();
	c.setMotivos(getContagemMotivos());
	c.setGrupos(getContagemGrupos());
	c.setItens(getContagemItens());
	return c;
    }
}
