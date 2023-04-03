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
        String titulo = Wrappers.obtem_titulo("9789892348568");
        String autor = Wrappers.obtem_autor("9789892348568");
        String editora = Wrappers.obtem_editora("9789892348568");
        double preco = Wrappers.obtem_preco("9789892348568");
        System.out.println("Titulo: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Autor: " + editora);
        System.out.println("Preço: " + preco);
    }
}
