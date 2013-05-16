package net.geral.essomerie._shared.pedido;

public class PedidoItem {
	public float	Quantidade		= 1f;
	public String	Codigo			= "";
	public String	Item			= "[novo item]";
	public float	PrecoInteira	= 0f;
	public float	PrecoMeia		= 0f;
	
	public String getPrecoInteiraText() {
		return String.format("%,.2f", PrecoInteira);
	}
	
	public String getPrecoMeiaText() {
		return String.format("%,.2f", PrecoMeia);
	}
	
	public String getQuantidadeText() {
		if (Quantidade < 0) {
			Quantidade = 0;
			return getQuantidadeText();
		}
		
		if (Quantidade == 0) return "0";
		int qtd = (int)Quantidade;
		float meio = Quantidade - qtd;
		
		if ((meio != 0f) && (meio != 0.5f)) {
			Quantidade = Math.round(Quantidade * 2f) / 2f; //round with 0.5 precision
			return getQuantidadeText();
		}
		
		if ((qtd == 0) && (meio == 0f)) return "0";
		if ((qtd > 0) && (meio == 0f)) return String.valueOf(qtd);
		if ((qtd == 0) && (meio != 0f)) return "\u00BD";
		if ((qtd > 0) && (meio != 0f)) return qtd + " \u00BD";
		return "n/a";
	}
	
	private float getTotal() {
		int qtd = (int)Quantidade;
		boolean meia = (qtd == Quantidade);
		
		return (qtd * PrecoInteira) + (meia ? PrecoMeia : 0f);
	}
	
	public String getTotalText() {
		return String.format("%,.2f", getTotal());
	}
}
