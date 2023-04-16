/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.escritores;

public class Escritores {
    private static int idCounter = 0;
    int id;
    String nome, nacionalidade, fotografia, genero, ocupacao, premios,link,nascimento, morte;

    public Escritores(String nome, String nacionalidade, String fotografia, String genero, String ocupacao, String premios, String nascimento, String morte, String link) {
        this.id = ++idCounter;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.fotografia = fotografia;
        this.genero = genero;
        this.ocupacao = ocupacao;
        this.premios = premios;
        this.nascimento = nascimento;
        this.morte = morte;
        this.link = link;
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

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
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
    
}
