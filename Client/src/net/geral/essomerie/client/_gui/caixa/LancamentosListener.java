package net.geral.essomerie.client._gui.caixa;

import java.util.EventListener;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;

public interface LancamentosListener extends EventListener {
	public void lancamentoAlterado(BaseLancamentos origem, int row);
}
