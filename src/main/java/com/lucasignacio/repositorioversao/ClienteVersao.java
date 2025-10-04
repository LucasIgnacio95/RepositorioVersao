package com.lucasignacio.repositorioversao;

public class ClienteVersao {

    private String[] clientes;
    private String[] cidadesEstapar;         
    private String[] cidadesZonaAzulBrasil;
    String[] cidadesSinalvida;
    String NumeroChamado;
    
    // Construtor vazio
    public ClienteVersao(){};
    
    // Getters e Setters
    
    public String[] getClientes() {
        return clientes;
    }
    
    public void setClientes(String[] novoCliente) {
        this.clientes = novoCliente;
    }
    
    public String[] getCidadesEstapar() {
        return cidadesEstapar;
    }
    
    public void setCidadesEstapar(String[] novoCidadesEstapar) {
        this.cidadesEstapar = novoCidadesEstapar;
    }
    
    public String[] getCidadesZonaAzulBrasil() {
        return cidadesZonaAzulBrasil;
    }
    
    public void setCidadesZonaAzulBrasil(String[] novoCidadesZonaAzulBrasil) {
        this.cidadesZonaAzulBrasil = novoCidadesZonaAzulBrasil;
    }
    
    public String[] getCidadesSinalvida() {
        return cidadesSinalvida;
    }
    
    public void setCidadesSinalvida(String[] novoCidadesSinalvida) {
        this.cidadesSinalvida = novoCidadesSinalvida;
    }
    
    
    
    // método GET: retorna o valor do NumeroChamado
    public String getNumeroChamado() {
        return NumeroChamado;
    }

    // Método SET: altera o valor do NumeroChamado
    public void setNumeroChamado(String novoNumeroChamado) {
        this.NumeroChamado = novoNumeroChamado;
    }
}
