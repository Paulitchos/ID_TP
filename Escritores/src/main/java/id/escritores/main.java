/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.IOException;

/**
 *
 * @author paulo
 */
public class main {
    
    public static void main(String[] args) throws IOException {
        
        Escritores escritor = Wrappers.criaEscritor("José Luís Peixoto");
        System.out.println("Autor:");
        System.out.println("\nLink: " + escritor.getLink());
        System.out.println("Nome: " + escritor.getNome());
        System.out.println("Data Nascimento: " + escritor.getNascimento());
        System.out.println("Data Falecimento: " + escritor.getMorte());
        System.out.println("Nacionalidade: " + escritor.getNacionalidade());
        System.out.println("Foto: " + escritor.getFotografia());
        System.out.println("Genero Literario: " + escritor.getGenero());
        System.out.println("Ocupacoes: " + escritor.getOcupacoes());
        System.out.println("Premios: " + escritor.getPremios());
        System.out.println("Outra Informacao: " + escritor.getOutraInfo());
        
        Obra obra = Wrappers.criaObra("9789898857071");
        System.out.println("\nObra:");
        System.out.println("\nISBN: " + obra.getIsbn());
        System.out.println("Titulo: " + obra.getTitulo());
        System.out.println("Autor: " + obra.getAutor());
        System.out.println("Editora: " + obra.getEditora());
        System.out.println("Capa: " + obra.getCapa());
        System.out.println("Preco: " + obra.getPreco());
        
    }
}
