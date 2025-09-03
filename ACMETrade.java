// Imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

public class ACMETrade {
    // Atributos para redirecionamento de E/S
    private Scanner entrada = new Scanner(System.in);  // Atributo para entrada padrao (teclado)
    private PrintStream saidaPadrao = System.out;   // Guarda a saida padrao - tela (console)
    private final String nomeArquivoEntrada = "entrada.txt";  // Nome do arquivo de entrada de dados
    private final String nomeArquivoSaida = "saida.txt";  // Nome do arquivo de saida de dados
    private Federacao federacao;
    private Convencao convencao;

    // Construtor da classe de aplicacao
    public ACMETrade() {
        redirecionaIn();    // Redireciona Entrada para arquivos
        redirecionaOut();    // Redireciona Saida para arquivos
        federacao = new Federacao();
        convencao = new Convencao();
    }


    public void executar(){
        while(true){
            String sigla = entrada.nextLine();
            if (sigla.equals("-1")) {
                break;                
            }

            String nome = entrada.nextLine();
            cadastrarPaises(sigla, nome);//metodo cadastrar paises
        }

        cadastrarAcordos();
        consultarPaisPelaSigla();

        //4:
        int codigo = entrada.nextInt();
        entrada.nextLine();
        consultarAcordoPeloCodigo(codigo);
        
        
        // metodo consultar um pais pela sigla;
        // metodo consultar acordo pelo codigo;
        // metodo consultar acordo pela sigla do comprador;
        // metodo mudar nome de um determinado pais;
        // metodo remover acordos de um determinado pais comprador;
        // metodo listar todos os acordos;
        // metodo lista todos os paises vendedores;
        // metodo mostrar o pais com a maior quantidade de acordos como vendedor;
    }

    public void cadastrarPaises(String sigla, String nome){
        boolean cadastrar = true;

        for(int i = 0; i < federacao.getPaises().size(); i++){
            if(federacao.getPaises().get(i).getSigla().equals(sigla)){
                cadastrar = false;
                System.out.println("1:erro-sigla repetida.");
                break;                
            }
        }
        if(cadastrar){
            Pais p = new Pais(sigla, nome);
            federacao.getPaises().add(p);
            System.out.println("1:" + sigla + ";"+ nome);
        }

    }

    public void cadastrarAcordos(){
        while(true){
            int codigo = entrada.nextInt();
            entrada.nextLine();
            boolean verificar = verificaSeCodigoJaExiste(codigo);
            if (codigo == -1) {
                break;
            }
            String produto = entrada.nextLine();
            double taxa = entrada.nextDouble();
            entrada.nextLine();
            String comp = entrada.nextLine();
            String vend = entrada.nextLine();
            Pais comprador = verificaSeExistePaisComprador(comp);
            Pais vendedor = verificaSeExistePaisVendedor(vend);
            //metodo de cadastrar acordos

            if(verificar && comprador != null && vendedor != null){
                Acordo acordo = new Acordo(codigo, produto, taxa, comprador, vendedor);
                convencao.getAcordo().add(acordo);
                System.out.println("2:" + codigo + ";" + produto + ";" + taxa + ";" + comp + ";" + vend);
            }if(!verificar){
                System.out.println("2:erro-codigo repetido.");
            }if (comprador == null) {
                System.out.println("2:erro-comprador inexistente.");    
            }if (vendedor == null){
                System.out.println("2:erro-vendedor inexistente.");
            }
        }

    }

    public boolean verificaSeCodigoJaExiste(int codigo){
        boolean verificar = true;
        for(int i = 0; i<convencao.getAcordo().size(); i++){
            if (convencao.getAcordo().get(i).getCodigo() == codigo ) {
                verificar = false;
                break;
            }
        }
        return verificar;
    }

    public Pais verificaSeExistePaisComprador(String c){
        String comprador = null;
        Pais verifica = null;

        for(int i = 0; i < federacao.getPaises().size(); i++){
            if(federacao.getPaises().get(i).getSigla().equals(c) ){
                comprador = federacao.getPaises().get(i).getSigla();
                verifica = federacao.getPaises().get(i);
            }
        }
        return verifica;
    }

    public Pais verificaSeExistePaisVendedor(String v){
        String vendedor = null;
        Pais verificar = null;
        for(int i = 0; i < federacao.getPaises().size(); i++){
            if(federacao.getPaises().get(i).getSigla().equals(v)){
                vendedor = federacao.getPaises().get(i).getSigla();
                verificar = federacao.getPaises().get(i);
            }
        }
        return verificar;
    }

    public void consultarPaisPelaSigla(){
        String sigla = entrada.nextLine();
        Pais pais = null; 
        for(int i = 0; i< federacao.getPaises().size(); i++){
            if(federacao.getPaises().get(i).getSigla().equals(sigla)){
                pais = federacao.getPaises().get(i);
            }
        }
        if(pais != null){
            System.out.println("3:" + pais.getSigla() + ";" + pais.getNome());
        }else{
            System.out.println("3:erro-sigla inexistente.");
        }
    }

    public void consultarAcordoPeloCodigo(int codigo) {
        Acordo a = null;
        for(int i = 0; i < convencao.getAcordo().size(); i++) {
            a = convencao.getAcordo().get(i);
            if(a.getCodigo() == codigo) {
                System.out.println("4:" + codigo + ";" + a.getProduto() + ";" + a.getTaxa() + ";" + a.getComprador().getSigla() + ";" + a.getComprador().getSigla());
                a = null;
            }
        }
        if(a == null) {
            System.out.println("4:erro-codigo inexistente.");
        }
    }

    


    // Redireciona Entrada de dados para arquivos em vez de teclado
    // Chame este metodo para redirecionar a leitura de dados para arquivos
    private void redirecionaIn() {
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader(nomeArquivoEntrada));
            entrada = new Scanner(streamEntrada);   // Usa como entrada um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
        entrada.useLocale(Locale.ENGLISH);   // Ajusta para leitura para ponto decimal
    }

    // Redireciona Saida de dados para arquivos em vez da tela (terminal)
    // Chame este metodo para redirecionar a escrita de dados para arquivos
    private void redirecionaOut() {
        try {
            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), Charset.forName("UTF-8"));
            System.setOut(streamSaida);             // Usa como saida um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
    }

    // Restaura Entrada padrao para o teclado
    // Chame este metodo para retornar a leitura de dados para o teclado
    private void restauraEntrada() {
        entrada = new Scanner(System.in);
    }

    // Restaura Saida padrao para a tela (terminal)
    // Chame este metodo para retornar a escrita de dados para a tela
    private void restauraSaida() {
        System.setOut(saidaPadrao);
    }

    

}
