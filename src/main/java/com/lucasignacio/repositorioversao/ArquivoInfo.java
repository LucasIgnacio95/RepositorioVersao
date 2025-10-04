package com.lucasignacio.repositorioversao;


// Esta classe serve como um "pacote" para carregar os dados de uma linha do seu resultado.
public class ArquivoInfo {

    private int id_chamado;
    private String cliente;
    private String cidade;
    private int chamado;
    private String observacao;
    private String caminhoArquivo;
    private String nomeArquivo;

    // Construtor para facilitar a criação do objeto
    public ArquivoInfo(int id_chamado, String cliente, String cidade, int chamado, String observacao, String caminhoArquivo, String nomeArquivo) {
        this.id_chamado = id_chamado;
        this.cliente = cliente;
        this.cidade = cidade;
        this.chamado = chamado;
        this.observacao = observacao;
        this.caminhoArquivo = caminhoArquivo;
        this.nomeArquivo = nomeArquivo;
    }

  

    // Métodos "get" para acessar os dados de fora da classe
    public int getId_chamado() {
        return id_chamado;
    }
    
    public String getCliente() {
        return cliente;
    }

    public String getCidade() {
        return cidade;
    }

    public int getChamado() {
        return chamado;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
}