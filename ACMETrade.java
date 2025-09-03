// Imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class ACMETrade {
    private Scanner entrada = new Scanner(System.in);  
    private PrintStream saidaPadrao = System.out;  
    private final String nomeArquivoEntrada = "entrada.txt";  
    private final String nomeArquivoSaida = "saida.txt";  
    private Federacao federacao;
    private Convencao convencao;
    public ACMETrade() {
        redirecionaIn();    
        redirecionaOut();   
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
        //2
        cadastrarAcordos();
        //3
        consultarPaisPelaSigla();
        //4:
        consultarAcordoPeloCodigo();
        //5:
        consultarAcordoPelaSiglaComp();
        //6
        mudarONomeDoPais();
        //7:
        removerAcordosPelaSiglaComp();
        //8:
        listarTodosOsAcordos();
        //9:
        listarNaoVendedores();


        
        
        // metodo consultar um pais pela sigla;
        // metodo consultar acordo pelo codigo;
        // metodo consultar acordo pela sigla do comprador;
        // metodo mudar nome de um determinado pais;
        // metodo remover acordos de um determinado pais comprador;
        // metodo listar todos os acordos;
        // metodo lista todos os paises vendedores;
        // metodo mostrar o pais com a maior quantidade de acordos como vendedor;
    }
//-------------------metodo-1------------------------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------------------------------------------
//-------------------metodo-2------------------------------------------------------------------------------------------------
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
//---------------------------------------------------------------------------------------------------------------------------
//---------------------metodo-3----------------------------------------------------------------------------------------------
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
//---------------------------------------------------------------------------------------------------------------------------
//---------------------metodo-4----------------------------------------------------------------------------------------------
    public void consultarAcordoPeloCodigo() {
        int codigo = entrada.nextInt();
        entrada.nextLine();
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
//---------------------------------------------------------------------------------------------------------------------------
//--------------------metodo-5-----------------------------------------------------------------------------------------------
    public void consultarAcordoPelaSiglaComp() {
        String sigla = entrada.nextLine();
        Acordo a = null;
        for(int i = 0; i < convencao.getAcordo().size(); i++) {
            if(convencao.getAcordo().get(i).getComprador().getSigla().equals(sigla)) {
                a = convencao.getAcordo().get(i);
                System.out.println("5:" + a.getCodigo() + ";" + a.getProduto() + ";" + a.getTaxa() + ";" + a.getVendedor().getSigla() + ";" + a.getComprador().getSigla());
            }
        }
        if(a == null) {
            System.out.println("5:erro-sigla inexistente.");
        }
    }
//---------------------------------------------------------------------------------------------------------------------------
//-----------------metodo-6--------------------------------------------------------------------------------------------------
    public void mudarONomeDoPais(){
        String sigla = entrada.nextLine();
        String name = entrada.nextLine();
        Pais pais = null;      
        String nome = null;  
        for(int i = 0; i<federacao.getPaises().size(); i++){
            if(federacao.getPaises().get(i).getSigla().equals(sigla)){
                pais = federacao.getPaises().get(i);
                nome = pais.getNome();
                pais.setNome(nome);
                System.out.println("6:" + pais.getSigla() + ";" + name);
            }if(pais == null){
                System.out.println("6:erro-sigla inexistente.");
            }
        }
    }
//---------------------------------------------------------------------------------------------------------------------------
//-----------------metodo-7--------------------------------------------------------------------------------------------------
    public void removerAcordosPelaSiglaComp() {
        String sigla = entrada.nextLine();
        boolean verify = false;
        Acordo a = null;
        for(int i = 0; i < convencao.getAcordo().size(); i++) {
            a = convencao.getAcordo().get(i);
            if(a.getComprador().getSigla().equals(sigla)) {
                convencao.getAcordo().remove(i);
                verify = true;
            }
        }
        if(verify) {
            System.out.println("7:acordos removidos.");
        } else {
            System.out.println("7:erro-nenhum acordo encontrado");
        }
    }
//---------------------------------------------------------------------------------------------------------------------------
//-----------------metodo-8--------------------------------------------------------------------------------------------------
    public void listarTodosOsAcordos(){
        Acordo acordo = null;
        if(convencao.getAcordo().size() > 0){
            for(int i = 0; i <= convencao.getAcordo().size(); i++){
                acordo = convencao.getAcordo().get(i);
                System.out.println("8:" + acordo.getCodigo() + ";" + acordo.getProduto() + ";" + acordo.getTaxa() + ";" + acordo.getComprador().getSigla() + ";" + acordo.getVendedor().getSigla());                    
            }
        }else{
            System.out.println("8:erro-nenhum acordo cadastrado.");
        }
        
    }
//---------------------------------------------------------------------------------------------------------------------------
//-----------------metodo-9--------------------------------------------------------------------------------------------------
    public void listarNaoVendedores(){
        ArrayList<Pais> paises = new ArrayList<Pais>();
        Pais c = null;
        for(int i = 0; i < federacao.getPaises().size(); i++) {
            paises.add(federacao.getPaises().get(i));
        }
        for(int i = 0; i < paises.size(); i++) {
            for(int j = 0; j < convencao.getAcordo().size(); j++) {
                if(paises.get(i).getSigla().equals(convencao.getAcordo().get(j).getVendedor().getSigla())) {
                    paises.remove(i);
                }
            }
        }
        if(paises.size() == 0) {
            System.out.println("9:erro-nenhum país encontrado.");
        } else {
            for(int k = 0; k < paises.size(); k++) {
                System.out.println("9:" + paises.get(k).getSigla() + ";" + paises.get(k).getNome());
            }
        }
    }
//---------------------------------------------------------------------------------------------------------------------------
//-----------------metodo-10--------------------------------------------------------------------------------------------------
    public void metodo10() {

    }
//---------------------------------------------------------------------------------------------------------------------------
    private void redirecionaIn() {
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader(nomeArquivoEntrada));
            entrada = new Scanner(streamEntrada);   
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   
        entrada.useLocale(Locale.ENGLISH);  
    }
    private void redirecionaOut() {
        try {
            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), Charset.forName("UTF-8"));
            System.setOut(streamSaida);             
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH); 
    }
    
    private void restauraEntrada() {
        entrada = new Scanner(System.in);
    }
    private void restauraSaida() {
        System.setOut(saidaPadrao);
    }
}
