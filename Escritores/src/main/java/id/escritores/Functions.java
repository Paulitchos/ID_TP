/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Functions {
    
    static String ler_ficheiro(String nomeF) throws FileNotFoundException{
        if (!nomeF.endsWith(".xml")) {
            return null;
        }
        
        String linha;
        StringBuilder texto = new StringBuilder(); //permite concatenar várias String
        Scanner input = new Scanner(new FileInputStream(nomeF));
        while ((input.hasNextLine())) {
            linha = input.nextLine();
            texto.append(linha).append("\n");
        }
        input.close();
        return texto.toString(); //converter StringBuilder para String
    }
    
}
