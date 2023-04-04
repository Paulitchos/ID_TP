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
        Obra obra = Wrappers.criaObra("9789892348568");
        System.out.println("ISBN: " + obra.getIsbn());
        System.out.println("Titulo: " + obra.getTitulo());
        System.out.println("Autor: " + obra.getAutor());
        System.out.println("Editora: " + obra.getEditora());
        System.out.println("Capa: " + obra.getCapa());
        System.out.println("Preco: " + obra.getPreco());
    }
}
