package net.geral.essomerie._shared.cliente;

public class Customer extends ClienteData {
  private static final long serialVersionUID = 1L;

  public Customer(final ClienteData cd) {
    super(cd);
    // TODO Auto-generated constructor stub
  }
  // public static int compareByCPF(final Customer c1, final Customer c2) {
  // final long n1 = c1.cpf.getNumero();
  // final long n2 = c2.cpf.getNumero();
  // if (n1 == n2) {
  // return 0;
  // }
  // if (n1 == 0) {
  // return 1;
  // }
  // if (n2 == 0) {
  // return -1;
  // }
  // return (n1 < n2) ? -1 : 1;
  // }
  //
  // public static int compareByEndereco(final Customer c1, final Customer c2) {
  // // se nao tem 1 endereco, fica pro final
  // final int n1 = c1.enderecos.length;
  // final int n2 = c2.enderecos.length;
  // if ((n1 == 1) && (n2 == 1)) {
  // final String e1 = c1.getComparingEndereco();
  // final String e2 = c2.getComparingEndereco();
  // return e1.compareTo(e2);
  // }
  // if (n1 == n2) {
  // return 0;
  // }
  // if (n1 == 1) {
  // return -1; // n2 != 1
  // }
  // if (n2 == 1) {
  // return 1; // n1 != 1
  // }
  // return n1 < n2 ? -1 : 1;
  // }
  //
  // public static int compareByID(final Customer c1, final Customer c2) {
  // if (c1.idcliente == c2.idcliente) {
  // return 0;
  // }
  // return (c1.idcliente < c2.idcliente ? -1 : 1);
  // }
  //
  // public static int compareByNome(final Customer c1, final Customer c2) {
  // final String n1 = c1.getComparingNome();
  // final String n2 = c2.getComparingNome();
  // final int l1 = n1.length();
  // final int l2 = n2.length();
  // if ((l1 == 0) && (l2 == 0)) {
  // return 0;
  // }
  // if (l1 == 0) {
  // return 1;
  // }
  // if (l2 == 0) {
  // return -1;
  // }
  // return n1.compareTo(n2);
  // }
  //
  // public static int compareByNotaPaulista(final Customer c1, final Customer
  // c2) {
  // final boolean n1 = c1.notaPaulista;
  // final boolean n2 = c2.notaPaulista;
  // if (n1 == n2) {
  // return 0;
  // }
  // if (n1) {
  // return -1;
  // }
  // return 1;
  // }
  //
  // public static int compareByObservacoes(final Customer c1, final Customer
  // c2) {
  // final String o1 = c1.getComparingObservacoes();
  // final String o2 = c2.getComparingObservacoes();
  // final int l1 = o1.length();
  // final int l2 = o2.length();
  // if ((l1 == 0) && (l2 == 0)) {
  // return 0;
  // }
  // if (l1 == 0) {
  // return 1;
  // }
  // if (l2 == 0) {
  // return -1;
  // }
  // return o1.compareTo(o2);
  // }
  //
  // public static int compareByTelefone(final Customer c1, final Customer c2) {
  // final int n1 = c1.telefones.length;
  // final int n2 = c2.telefones.length;
  // // se nao tem telefone, pro final
  // if ((n1 == 0) && (n2 == 0)) {
  // return 0;
  // }
  // if (n1 == 0) {
  // return 1;
  // }
  // if (n2 == 0) {
  // return -1;
  // }
  // // primeiro por ordem de quantos telefones tem
  // if (n1 != n2) {
  // return n1 - n2;
  // }
  // // depois por qual tem telefone com menos digitos
  // final int min1 = Integer.MAX_VALUE;
  // for (final Telephone t : c1.telefones) {
  // // if (t.telefone.length() < min1) {
  // // min1 = t.telefone.length();
  // // }
  // }
  // final int min2 = Integer.MAX_VALUE;
  // for (final Telephone t : c2.telefones) {
  // // if (t.telefone.length() < min2) {
  // // min2 = t.telefone.length();
  // // }
  // }
  // if (min1 != min2) {
  // return min1 - min2;
  // }
  // // entao comparar o primeiro telefone
  // return 0;// c1.telefones[0].telefone.compareTo(c2.telefones[0].telefone);
  // }
  //
  // private static void soma(final Object[] soma, final Double d, final int
  // peso) {
  // if (d == null) {
  // return;
  // }
  // double somad = (Double) soma[0];
  // int pesoi = (Integer) soma[1];
  // somad += (d * peso);
  // pesoi += peso;
  // soma[0] = somad;
  // soma[1] = pesoi;
  // }
  //
  // public final Address[] enderecos;
  // public final Telephone[] telefones;
  //
  // private String telefonesString = null;
  // private String filterNome = null;
  // private String filterCPF = null;
  // private String filterEnderecoFonetico = null;
  // private String filterEnderecoNumerico = null;
  // private String filterTelefones = null;
  // private String filterObservacoes = null;
  // private String compareNome = null;
  // private String compareEndereco = null;
  //
  // private String compareObservacoes = null;
  //
  // public Customer(final ClienteData cd, final Address[] _enderecos,
  // final Telephone[] _telefones) {
  // super(cd);
  // enderecos = _enderecos;
  // telefones = _telefones;
  // }
  //
  // public Customer(final int _idcliente, final String _nome, final CPF _cpf,
  // final boolean _notaPaulista, final String _observacoes,
  // final Address[] _enderecos, final Telephone[] _telefones) {
  // super(_idcliente, _nome, _cpf, _notaPaulista, _observacoes);
  // enderecos = _enderecos;
  // telefones = _telefones;
  // }
  //
  // public String debug() {
  // final StringBuilder sb = new StringBuilder();
  // sb.append("Cliente Debug Info\n");
  // sb.append("id=" + idcliente + "\n");
  // sb.append("nome=" + nome + "\n");
  // sb.append("cpf=" + cpf + "\n");
  // sb.append("notaPaulista=" + notaPaulista + "\n");
  // for (int i = 0; i < enderecos.length; i++) {
  // final Address e = enderecos[i];
  // final String pref = "enderecos[" + i + "].";
  // sb.append(pref + "id=" + e.id + "\n");
  // sb.append(pref + "logradouro=" + e.logradouro + "\n");
  // sb.append(pref + "numeroAptoBloco=" + e.numeroAptoBloco + "\n");
  // sb.append(pref + "bairro=" + e.bairro + "\n");
  // sb.append(pref + "cep=" + e.cep + "\n");
  // sb.append(pref + "observacoes=" + e.observacoes + "\n");
  // }
  // for (int i = 0; i < telefones.length; i++) {
  // final Telephone t = telefones[i];
  // final String pref = "telefones[" + i + "].";
  // sb.append(pref + "id=" + t.id + "\n");
  // // sb.append(pref + "idreferencia(idcliente)=" + t.idreferencia + "\n");
  // // sb.append(pref + "tipo=" + t.tipo + "\n");
  // // sb.append(pref + "telefone=" + t.telefone + "\n");
  // // sb.append(pref + "observacoes=" + t.observacoes + "\n");
  // }
  // return sb.toString();
  // }
  //
  // public String getComparingEndereco() {
  // if (compareEndereco == null) {
  // if (enderecos.length != 1) {
  // compareEndereco = "";
  // } else {
  // // retirar parte do logradouro
  // final String[] p = enderecos[0].getEnderecoCompleto().split(" ", 2);
  // if (p.length < 2) {
  // compareEndereco = "";
  // } else {
  // compareEndereco = _Conversor.toAZ09(p[1], false);
  // }
  // }
  // }
  // return compareEndereco;
  // }
  //
  // public String getComparingNome() {
  // if (compareNome == null) {
  // compareNome = _Conversor.toAZ09(nome, false);
  // }
  // return compareNome;
  // }
  //
  // public String getComparingObservacoes() {
  // if (compareObservacoes == null) {
  // compareObservacoes = _Conversor.toAZ09(observacoes, false);
  // }
  // return compareObservacoes;
  // }
  //
  // public String getEnderecoString_PrimeiroOuContagem() {
  // if (enderecos.length == 0) {
  // return "[nenhum endereço cadastrado]";
  // }
  // if (enderecos.length > 1) {
  // return "[" + enderecos.length + " endereços cadastrados]";
  // }
  // return enderecos[0].getEnderecoCompleto();
  // }
  //
  // public String getFilterCPF() {
  // if (filterCPF == null) {
  // filterCPF = (cpf.getNumero() == 0) ? "" : String.format("%011d",
  // cpf.getNumero());
  // }
  // return filterCPF;
  // }
  //
  // public String getFilterEnderecoFonetico() {
  // if (filterEnderecoFonetico == null) {
  // final StringBuilder sb = new StringBuilder();
  // for (final Address e : enderecos) {
  // sb.append(e.logradouro);
  // sb.append(' ');
  // sb.append(e.numeroAptoBloco);
  // sb.append(' ');
  // sb.append(e.bairro);
  // sb.append(' ');
  // }
  // filterEnderecoFonetico = _Conversor.toPhonetic(sb.toString());
  // }
  // return filterEnderecoFonetico;
  // }
  //
  // public String getFilterEnderecoNumerico() {
  // if (filterEnderecoNumerico == null) {
  // final StringBuilder sb = new StringBuilder();
  // for (final Address e : enderecos) {
  // sb.append(e.numeroAptoBloco.replaceAll("[\\D]", ""));
  // sb.append(' ');
  // if (!e.cep.isZero()) {
  // sb.append(String.format("%08d", e.cep.getNumero()));
  // sb.append(' ');
  // }
  // }
  // filterEnderecoNumerico = sb.toString();
  // }
  // return filterEnderecoNumerico;
  // }
  //
  // public String getFilterNome() {
  // if (filterNome == null) {
  // filterNome = _Conversor.toPhonetic(nome);
  // }
  // return filterNome;
  // }
  //
  // public String getFilterObservacoes() {
  // if (filterObservacoes == null) {
  // filterObservacoes = _Conversor.toAZ09(observacoes, false);
  // }
  // return filterObservacoes;
  // }
  //
  // public String getFilterTelefones() {
  // if (filterTelefones == null) {
  // final StringBuilder sb = new StringBuilder();
  // for (final Telephone t : telefones) {
  // // sb.append(t.telefone.toUpperCase().replaceAll("[^0-9A-Z]", ""));
  // // sb.append(' ');
  // }
  // filterTelefones = sb.toString();
  // }
  // return filterTelefones;
  // }
  //
  // public String getTelefonesString() {
  // if (telefonesString != null) {
  // return telefonesString;
  // }
  //
  // final StringBuilder s = new StringBuilder();
  // boolean espaco = false;
  // for (final Telephone t : telefones) {
  // if (espaco) {
  // s.append(" / ");
  // }
  // s.append(t.toString());
  // espaco = true;
  // }
  //
  // telefonesString = s.toString();
  // return telefonesString;
  // }
  //
  // public float match(final Customer lookFor) {
  // final boolean[] matchNome = matchString(lookFor.getFilterNome(),
  // getFilterNome());
  // final Boolean matchCPF = matchCPF(lookFor.cpf, cpf);
  // final boolean[] matchEndereco = matchEndereco(
  // lookFor.getFilterEnderecoFonetico(),
  // lookFor.getFilterEnderecoNumerico(), getFilterEnderecoFonetico(),
  // getFilterEnderecoNumerico());
  // final boolean[] matchTelefone = matchString(lookFor.getFilterTelefones(),
  // getFilterTelefones());
  // final boolean[] matchObservacoes = matchString(
  // lookFor.getFilterObservacoes(), getFilterObservacoes());
  //
  // final Double mNome = media(matchNome);
  // final Double mCPF = (matchCPF == null) ? null
  // : (matchCPF.booleanValue() ? 1.0 : 0.0);
  // final Double mEndereco = media(matchEndereco);
  // final Double mTelefone = media(matchTelefone);
  // final Double mObservacoes = media(matchObservacoes);
  //
  // final Object[] soma = new Object[] { new Double(0.0), new Integer(0) };
  // soma(soma, mNome, 10);
  // soma(soma, mCPF, 30);
  // soma(soma, mEndereco, 5);
  // soma(soma, mTelefone, 20);
  // soma(soma, mObservacoes, 1);
  // final double media = (double) soma[0] / (int) soma[1];
  // return (float) media;
  // }
  //
  // private Boolean matchCPF(final CPF lookFor, final CPF lookInto) {
  // if (lookFor.isZero() || lookInto.isZero()) {
  // return null;
  // }
  // return (lookFor.getNumero() == lookInto.getNumero());
  // }
  //
  // private boolean[] matchEndereco(final String lookForF, final String
  // lookForN,
  // final String lookIntoF, final String lookIntoN) {
  // final boolean[] fonetico = matchString(lookForF, lookIntoF);
  // final boolean[] numerico = matchString(lookForN, lookIntoN);
  // final boolean[] res = new boolean[fonetico.length + numerico.length];
  // int i = 0;
  // for (final boolean b : fonetico) {
  // res[i++] = b;
  // }
  // for (final boolean b : numerico) {
  // res[i++] = b;
  // }
  // return res;
  // }
  //
  // private boolean[] matchString(final String lookFor, final String lookInto)
  // {
  // final String[] lookForArray = _Conversor.splitOrEmpty(lookFor, " ");
  // final boolean[] res = new boolean[lookForArray.length];
  // for (int i = 0; i < lookForArray.length; i++) {
  // res[i] = lookInto.contains(lookForArray[i]);
  // }
  // return res;
  // }
  //
  // private Double media(final boolean[] bs) {
  // if (bs.length == 0) {
  // return null;
  // }
  //
  // int trues = 0;
  // for (final boolean b : bs) {
  // if (b) {
  // trues++;
  // }
  // }
  // return (double) trues / (double) (bs.length);
  // }
  //
  // public boolean sameData(final Customer other, final boolean checkids) {
  // final Customer c1 = this;
  // final Customer c2 = other;
  // if (checkids) {
  // if (c1.idcliente != c2.idcliente) {
  // return false;
  // }
  // }
  // if (c1.notaPaulista != c2.notaPaulista) {
  // return false;
  // }
  // if (c1.cpf.getNumero() != c2.cpf.getNumero()) {
  // return false;
  // }
  // if (!c1.nome.equals(c2.nome)) {
  // return false;
  // }
  // if (!c1.observacoes.equals(c2.observacoes)) {
  // return false;
  // }
  // // telefones
  // if (c1.telefones.length != c2.telefones.length) {
  // return false;
  // }
  // for (int i = 0; i < c1.telefones.length; i++) {
  // final Telephone t1 = c1.telefones[i];
  // final Telephone t2 = c2.telefones[i];
  // // if (!t1.sameData(t2, checkids)) {
  // // return false;
  // // }
  // }
  // // enderecos
  // if (c1.enderecos.length != c2.enderecos.length) {
  // return false;
  // }
  // for (int i = 0; i < c1.enderecos.length; i++) {
  // final Address e1 = c1.enderecos[i];
  // final Address e2 = c2.enderecos[i];
  // if (!e1.sameData(e2, checkids)) {
  // return false;
  // }
  // }
  // // nothing different
  // return true;
  // }
}
