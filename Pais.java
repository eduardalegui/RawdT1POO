public class Pais {
    private String sigla;
    private String nome;

    public Pais(String sigla, String nome){
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla(){
        return sigla;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}
