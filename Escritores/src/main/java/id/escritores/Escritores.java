package id.escritores;

import java.util.List;

public class Escritores {
    private static int idCounter = 0;
    int id;
    String nome, nacionalidade, fotografia, genero, link, nascimento, morte, nomePesquisa;
    List<String> ocupacoes, premios;

    public Escritores(String nome, String nacionalidade, String fotografia, String genero, List<String> ocupacoes, List<String> premios, String nascimento, String morte, String link, String nomePesquisa) {
        this.id = ++idCounter;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.fotografia = fotografia;
        this.genero = genero;
        this.ocupacoes = ocupacoes;
        this.premios = premios;
        this.nascimento = nascimento;
        this.morte = morte;
        this.link = link;
        this.nomePesquisa = nomePesquisa;
    }
    
    public static void setIdCounter(int value) {
        idCounter = value;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<String> getOcupacoes() {
        return ocupacoes;
    }

    public void setOcupacoes(List<String> ocupacoes) {
        this.ocupacoes = ocupacoes;
    }

    public List<String> getPremios() {
        return premios;
    }

    public void setPremios(List<String> premios) {
        this.premios = premios;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getMorte() {
        return morte;
    }

    public void setMorte(String morte) {
        this.morte = morte;
    }
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNomePesquisa() {
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa) {
        this.nomePesquisa = nomePesquisa;
    }
    
    
}
