/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author paulo
 */
public class Wrappers {
    
    public static String obtem_titulo(String isbn) throws IOException{
        String link = "https://www.wook.pt/pesquisa/=";
        HttpRequestFunctions.httpRequest1(link, isbn, "wook.txt");
        String er = "<title>([^<]+)\\s*-\\s*Livro\\s*-\\s*WOOK</title>";
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("wook.txt"));
        
        String linha;
        
        while((input.hasNextLine())){
            linha = input.nextLine();
            m = p.matcher(linha);
            
            
            if (m.find()){
                input.close();
                return(m.group(1));
            }        
        }
        input.close();
        return null;
    }
    
    public static String obtem_autor(String isbn) throws IOException{
        String link = "https://www.wook.pt/pesquisa/=";
        HttpRequestFunctions.httpRequest1(link, isbn, "wook.txt");
        String er = "<a href=\'#author-\\d*\'>([^<]+)</a>";
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("wook.txt"));
        
        String linha;
        
        while((input.hasNextLine())){
            linha = input.nextLine();
            m = p.matcher(linha);
            
            
            if (m.find()){
                input.close();
                return(m.group(1));
            }        
        }
        input.close();
        return null;
    }
    
    public static String obtem_editora(String isbn) throws IOException{
        String link = "https://www.wook.pt/pesquisa/=";
        HttpRequestFunctions.httpRequest1(link, isbn, "wook.txt");
        String er = "<td>\\s*Editor:\\s*</td>\\s*<td class=\"font-medium\">\\s*([^<]+)\\s*</td>";
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("wook.txt"));
        
        String linha;
        
        while((input.hasNextLine())){
            linha = input.nextLine();
            m = p.matcher(linha);
            
            
            if (m.find()){
                input.close();
                return(m.group(1));
            }        
        }
        input.close();
        return null;
    }
    
    public static double obtem_preco(String isbn) throws IOException{
    String link = "https://www.wook.pt/pesquisa/=";
    HttpRequestFunctions.httpRequest1(link, isbn, "wook.txt");
    Scanner ler = new Scanner(new FileInputStream("wook.txt"));
    String er = "<span\\s*class=\"price-rpl\">([^€]+)€</span>";
    Pattern p = Pattern.compile(er);
    String linha;
    while (ler.hasNextLine()) {
        linha = ler.nextLine();
        Matcher m = p.matcher(linha);
        if (m.find()) {
            ler.close();
            return Double.parseDouble(m.group(1).replace(",", "."));
        }

    }
    ler.close();
    return -1;
    }
}
