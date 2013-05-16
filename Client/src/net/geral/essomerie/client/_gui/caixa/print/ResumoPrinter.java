package net.geral.essomerie.client._gui.caixa.print;

import java.awt.print.PageFormat;

import net.geral.printing.PrintDocument;

public class ResumoPrinter extends PrintDocument {

	@Override
	public PageFormat getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean print(final int pageIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	/*	private final CaixaDetalhes	detalhes;
		private final Lancamentos	lancamentos;

		private DocumentPrinter		p	= null;

		public ResumoPrinter(final CaixaDetalhes detalhes, final Lancamentos lancamentos) {
			if ((detalhes == null) || (lancamentos == null)) throw new NullPointerException();
			this.detalhes = detalhes;
			this.lancamentos = lancamentos;
		}

		@Override
		public int getPages() {
			return 7;
		}

		private void print(final DeliveryLancamentos delivery) {
			print("Delivery:");

			for (int i = 0; i < delivery.count(); i++) {
				final DeliveryLancamento e = (DeliveryLancamento)delivery.get(i);
				if (e.isNull()) {
					continue;
				}
				final String t = e.getString(DeliveryLancamento.ENTREGADOR);
				final String v = e.getString(DeliveryLancamento.VALOR);
				p.println(String.format("%25s: %10s", t, v));
			}
			p.println(String.format("%25s  %10s", "", "----------"));
			final String v = delivery.getTotal().toString();
			p.println(String.format("%25s  %10s", "", v));
		}

		private void print(final PlantaoLancamentos plantao) {
			print("Plantão:");

			p.println(String.format("   %4s %7s %10s %7s", "Mesa", "Pessoas", "Consumo", "Média"));
			for (int i = 0; i < plantao.count(); i++) {
				final PlantaoLancamento e = (PlantaoLancamento)plantao.get(i);
				if (e.isNull()) {
					continue;
				}
				final String m = e.getString(PlantaoLancamento.MESA);
				final String s = e.getString(PlantaoLancamento.PESSOAS);
				final String c = e.getString(PlantaoLancamento.CONSUMO);
				final Dinheiro cm = e.getConsumoMedio();
				final String cms = (cm == null) ? "" : cm.toString();
				p.println(String.format("   %4s %7s %10s %7s", m, s, c, cms));
			}
			p.println(String.format("   %4s %7s %10s %7s", "----", "-------", "----------", "-------"));
			final String m = String.valueOf(plantao.getMesasCount());
			final String s = String.valueOf(plantao.getPessoas());
			final String c = plantao.getTotal().toString();
			final Dinheiro cm = plantao.getConsumoMedio();
			final String cms = (cm == null) ? "" : cm.toString();
			p.println(String.format("   %4s %7s %10s %7s", m, s, c, cms));
		}

		private void print(final SalaoLancamentos salao) {
			print("Salão:");

			final ResumoMesas rm = new ResumoMesas();
			p.println(String.format("   %4s %7s %10s %7s", "Mesa", "Pessoas", "Consumo", "Média"));
			for (int i = 0; i < salao.count(); i++) {
				final SalaoLancamento e = (SalaoLancamento)salao.get(i);
				if (e.isNull()) {
					continue;
				}
				rm.add(e);
				final String m = e.getString(SalaoLancamento.MESA);
				final String s = e.getString(SalaoLancamento.PESSOAS);
				final String c = e.getString(SalaoLancamento.CONSUMO);
				final Dinheiro cm = e.getConsumoMedio();
				final String cms = (cm == null) ? "" : cm.toString();
				p.println(String.format("   %4s %7s %10s %7s", m, s, c, cms));
			}
			p.println(String.format("   %4s %7s %10s %7s", "----", "-------", "----------", "-------"));
			final String m = String.valueOf(rm.getMesas(ResumoMesas.RESULTADO));
			final String s = String.valueOf(rm.getPessoas(ResumoMesas.RESULTADO));
			final String c = rm.getConsumo(ResumoMesas.RESULTADO).toString();
			final String cm = rm.getMedia(ResumoMesas.RESULTADO).toString();
			p.println(String.format("   %4s %7s %10s %7s", m, s, c, cm));
		}

		private void print(final String titulo) {
			p.println();
			p.setBold();
			p.println(titulo);
			p.setPlain();
		}

		private void print(final StringDinheiroLancamentos lancamentos, final String titulo) {
			print(titulo + ":");

			for (int i = 0; i < lancamentos.count(); i++) {
				final StringDinheiroLancamento e = (StringDinheiroLancamento)lancamentos.get(i);
				if (e.isNull()) {
					continue;
				}
				final String d = e.getString(StringDinheiroLancamento.DESCRICAO);
				final String v = e.getString(StringDinheiroLancamento.VALOR);
				p.println(String.format("%25s: %10s", d, v));
			}
			p.println(String.format("%25s  %10s", "", "----------"));
			final String t = lancamentos.getTotal().toString();
			p.println(String.format("%25s  %10s", "", t));
		}

		@Override
		public int printAll(final DocumentPrinter printer) {
			if (printer == null) throw new NullPointerException();
			p = printer;

			for (int i = 0; i < getPages(); i++) {
				printPage(i, printer);
			}
			return p.getHeight();
		}

		@Override
		public int printPage(final int pageIndex, final DocumentPrinter printer) {
			if (printer == null) throw new NullPointerException();
			p = printer;

			final Dinheiro fundo = lancamentos.fundo.getTotal();
			final Dinheiro entradas = lancamentos.entradas.getTotal();
			final Dinheiro saidas = lancamentos.saidas.getTotal();
			final Dinheiro a = entradas.subtract(saidas);
			final Dinheiro b = fundo.add(a);

			switch (pageIndex) {
				case 0:
					p.setBold();
					p.println(CRUtil.calendar2dma(detalhes.getData()) + " - " + detalhes.getDiaNoite()
							+ " - " + detalhes.getComputador());
					print(lancamentos.fundo, "Fundo de Caixa");
					print(lancamentos.entradas, "Entradas");
					print(lancamentos.saidas, "Saídas");

					p.setBold();
					p.println();
					p.println();
					p.println(String.format("        Entradas - Saídas: %10s", a.toString()));
					p.println(String.format("Fundo + Entradas - Saídas: %10s", b.toString()));
					p.println();
					return p.getHeight();
				case 1:
					print(lancamentos.conferencia, "Conferência");

					final Dinheiro conferencia = lancamentos.conferencia.getTotal();
					final Dinheiro c = conferencia.subtract(b);
					p.setBold();
					p.println();
					p.println(String.format("                Diferença: %10s", c.toString()));
					p.println();
					return p.getHeight();
				case 2:
					print(lancamentos.delivery);
					return p.getHeight();
				case 3:
					print(lancamentos.viagem, "Viagem");
					return p.getHeight();
				case 4:
					print(lancamentos.plantao);
					return p.getHeight();
				case 5:
					print(lancamentos.salao);
					return p.getHeight();
				case 6:
					printResumo();
					return p.getHeight();
				default:
					return p.getHeight();
			}
		}

		private void printResumo() {
			final ResumoMesas rm = new ResumoMesas();
			for (int i = 0; i < lancamentos.salao.count(); i++) {
				final SalaoLancamento e = (SalaoLancamento)lancamentos.salao.get(i);
				if (e.isNull()) {
					continue;
				}
				rm.add(e);
			}
			for (int i = 0; i < lancamentos.plantao.count(); i++) {
				final PlantaoLancamento e = (PlantaoLancamento)lancamentos.plantao.get(i);
				if (e.isNull()) {
					continue;
				}
				rm.add(e);
			}

			p.println();
			p.println();
			p.println();
			p.setBold();
			p.setSize(-2f);
			final String fmt = "%13s %10s %10s %10s %10s";
			p.setBold();
			p.println(String.format(fmt, "", "S. Novo", "S. Velho", "Plantão", "Resultado"));
			p.setPlain();
			final String[] pm = new String[4];
			final String[] c = new String[4];
			final String[] s = new String[4];
			final String[] r = new String[4];
			final String[] mc = new String[4];
			final String[] g = new String[4];
			final String[] vp = new String[4];
			for (int i = 0; i < 4; i++) {
				Dinheiro d;
				pm[i] = rm.getPessoasMesas(i);

				d = rm.getConsumo(i);
				c[i] = (d == null) ? "" : d.toString();

				d = rm.getServico(i);
				s[i] = (d == null) ? "" : d.toString();

				d = rm.getRepique(i);
				r[i] = (d == null) ? "" : d.toString();

				d = rm.getMedia(i);
				mc[i] = (d == null) ? "" : d.toString();

				d = rm.getGarcons(i);
				g[i] = (d == null) ? "" : d.toString();

				d = rm.get20Percento(i);
				vp[i] = (d == null) ? "" : d.toString();
			}
			p.println(String.format(fmt, "Pess (Mes):", pm[ResumoMesas.SALAO_VELHO],
					pm[ResumoMesas.SALAO_NOVO], pm[ResumoMesas.PLANTAO], pm[ResumoMesas.RESULTADO]));
			p.println(String.format(fmt, "Consumo:", c[ResumoMesas.SALAO_VELHO],
					c[ResumoMesas.SALAO_NOVO], c[ResumoMesas.PLANTAO], c[ResumoMesas.RESULTADO]));
			p.println(String.format(fmt, "Serviço:", s[ResumoMesas.SALAO_VELHO],
					s[ResumoMesas.SALAO_NOVO], s[ResumoMesas.PLANTAO], s[ResumoMesas.RESULTADO]));
			p.println(String.format(fmt, "Média Cons.:", mc[ResumoMesas.SALAO_VELHO],
					mc[ResumoMesas.SALAO_NOVO], mc[ResumoMesas.PLANTAO], mc[ResumoMesas.RESULTADO]));
			p.println(String.format(fmt, "Garçons:", g[ResumoMesas.SALAO_VELHO],
					g[ResumoMesas.SALAO_NOVO], g[ResumoMesas.PLANTAO], g[ResumoMesas.RESULTADO]));
			p.println(String.format(fmt, "20%:", vp[ResumoMesas.SALAO_VELHO],
					vp[ResumoMesas.SALAO_NOVO], vp[ResumoMesas.PLANTAO], vp[ResumoMesas.RESULTADO]));
		}*/
}
