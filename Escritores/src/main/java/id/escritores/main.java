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
        Obra obra = Wrappers.criaObra("9789898857071");
        String link = Wrappers.obtem_link_escritor("José Saramago");
        String nome = Wrappers.obtem_nome(link);
        String data_nascimento = Wrappers.obtem_data_nascimento(link);
        String data_falecimento = Wrappers.obtem_data_falecimento(link);
        System.out.println("Link: " + link);
        System.out.println("Nome: " + nome);
        System.out.println("Data Nascimento: " + data_nascimento);
        System.out.println("Data Falecimento: " + data_falecimento);
        System.out.println("ISBN: " + obra.getIsbn());
        System.out.println("Titulo: " + obra.getTitulo());
        System.out.println("Autor: " + obra.getAutor());
        System.out.println("Editora: " + obra.getEditora());
        System.out.println("Capa: " + obra.getCapa());
        System.out.println("Preco: " + obra.getPreco());
    }
}
