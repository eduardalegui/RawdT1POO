public class Acordo {
    private int codigo;
    private String produto;
    private double taxa;
    private Pais comprador;
    private Pais vendedor;

    public Acordo(int codigo, String produto, double taxa, Pais comprador, Pais vendedor){
        this.codigo = codigo;
        this.produto = produto;
        this.taxa = taxa;
        this.comprador = comprador;
        this.vendedor = vendedor;
    }

    public int getCodigo(){
        return codigo;
    }

    public String getProduto(){
        return produto;
    }

    public double getTaxa(){
        return taxa;
    }

    public Pais getComprador(){
        return comprador;
    }

    public Pais getVendedor(){
        return vendedor;
    }
}
