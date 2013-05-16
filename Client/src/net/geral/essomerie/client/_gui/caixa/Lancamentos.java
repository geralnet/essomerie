package net.geral.essomerie.client._gui.caixa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.conferencia.ConferenciaLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.delivery.DeliveryLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.entradas.EntradasLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.fundo.FundoLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.plantao.PlantaoLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.saidas.SaidasLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.salao.SalaoLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.viagem.ViagemLancamentos;
import net.geral.essomerie._shared.BuildInfo;
import net.geral.essomerie._shared.CRUtil;

public class Lancamentos {
	public static final File			SAVE_DIRECTORY	= new File("caixa");
	public static final File			SAVE_BACKUP		= new File(SAVE_DIRECTORY, "backup");
	public static final String			EXTENSION		= ".txt";

	public final FundoLancamentos		fundo			= new FundoLancamentos();
	public final EntradasLancamentos	entradas		= new EntradasLancamentos();
	public final SaidasLancamentos		saidas			= new SaidasLancamentos();
	public final DeliveryLancamentos	delivery		= new DeliveryLancamentos();
	public final ViagemLancamentos		viagem			= new ViagemLancamentos();
	public final PlantaoLancamentos		plantao			= new PlantaoLancamentos();
	public final ConferenciaLancamentos	conferencia		= new ConferenciaLancamentos();
	public final SalaoLancamentos		salao			= new SalaoLancamentos();

	private final BaseLancamentos[]		todos;

	public Lancamentos() {
		todos = new BaseLancamentos[] { fundo, entradas, saidas, delivery, viagem, plantao, conferencia, salao };
	}

	public void addLancamentosListener(final LancamentosListener l) {
		for (final BaseLancamentos bl : todos) {
			bl.addLancamentosListener(l);
		}
	}

	private void createDirectory() throws SaveLoadException {
		try {
			//checar SAVE_BACKUP pois SAVE_DIRECTORY esta nele
			if (!SAVE_BACKUP.exists()) {
				SAVE_BACKUP.mkdirs();
			}
			if (!SAVE_BACKUP.isDirectory()) { throw new SaveLoadException("Invalid directory '"
					+ SAVE_BACKUP.toPath().toAbsolutePath() + "'."); }
		}
		catch (final Exception e) {
			throw new SaveLoadException(e);
		}
	}

	private File createFile(final CaixaDetalhes detalhes) throws SaveLoadException {
		createDirectory();
		final String base = getBaseFilename(detalhes);
		moveToBackup(base);
		return new File(SAVE_DIRECTORY, base + EXTENSION);
	}

	private String getBaseFilename(final CaixaDetalhes detalhes) {
		final String dn = detalhes.getDia() ? "Dia" : "Noite";
		return String.format("%s %s %s", detalhes.getComputador(), CRUtil.calendar2amd(detalhes.getData()), dn);
	}

	private boolean load(final BufferedReader r, final String panel) throws IOException, SaveLoadException {
		if ("Fundo".equals(panel)) { return fundo.load(r); }
		if ("Entradas".equals(panel)) { return entradas.load(r); }
		if ("Pagamentos".equals(panel)) { return saidas.load(r); }
		if ("Delivery".equals(panel)) { return delivery.load(r); }
		if ("Viagem".equals(panel)) { return viagem.load(r); }
		if ("Plantao".equals(panel)) { return plantao.load(r); }
		if ("Conferencia".equals(panel)) { return conferencia.load(r); }
		if ("Salao".equals(panel)) { return salao.load(r); }
		return false;
	}

	public CaixaDetalhes load(final File file) throws SaveLoadException {
		final BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(file));

			final String version = r.readLine();
			if (!BuildInfo.CURRENT.getVersion().equals(version)) {
				JOptionPane.showMessageDialog(null,
						"Versão salva: " + version + "\nVersão atual: " + BuildInfo.CURRENT.getVersion()
								+ "\n\nFavor verificar se os dados foram lidos corretamente.", "Aviso de Versão",
						JOptionPane.WARNING_MESSAGE);
			}

			final CaixaDetalhes cd = CaixaDetalhes.load(r.readLine());

			String line = r.readLine();
			while (line != null) {
				load(r, line);
				line = r.readLine();
			}
			r.close();

			return cd;
		}
		catch (final IOException e) {
			throw new SaveLoadException(e);
		}
	}

	private void moveToBackup(final String base) {
		final File src = new File(SAVE_DIRECTORY, base + EXTENSION);
		if (!src.exists()) { return; }
		int i = 1;
		File dest = null;
		do {
			dest = new File(SAVE_BACKUP, String.format("%s.%04d%s", base, i++, EXTENSION));
		} while (dest.exists());
		src.renameTo(dest);
	}

	public void removeLancamentosListener(final LancamentosListener l) {
		for (final BaseLancamentos bl : todos) {
			bl.removeLancamentosListener(l);
		}
	}

	public void salvar(final CaixaDetalhes detalhes) throws SaveLoadException {
		try {
			final File f = createFile(detalhes);
			final PrintStream p = new PrintStream(new FileOutputStream(f, false));

			p.println(BuildInfo.CURRENT.getVersion());
			p.println(detalhes.toString());

			p.println("Fundo");
			fundo.save(p);
			p.println("Entradas");
			entradas.save(p);
			p.println("Pagamentos");
			saidas.save(p);
			p.println("Delivery");
			delivery.save(p);
			p.println("Viagem");
			viagem.save(p);
			p.println("Plantao");
			plantao.save(p);
			p.println("Conferencia");
			conferencia.save(p);
			p.println("Salao");
			salao.save(p);

			p.close();
		}
		catch (final Exception e) {
			throw new SaveLoadException(e);
		}
	}
}
