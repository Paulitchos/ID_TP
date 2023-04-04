/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author paulo
 */
public class Wrappers {
    
    public static Obra criaObra(String isbn) throws IOException {
        String autor = Wrappers.obtem_autor(isbn);
        String titulo = Wrappers.obtem_titulo(isbn,autor);
        double preco = Wrappers.obtem_preco(isbn);
        String capa = Wrappers.obtem_capa(isbn);
        String editora = Wrappers.obtem_editora(isbn);
        String codigo = "1";
        Obra x = new Obra(isbn,codigo,autor,titulo,editora,capa,preco);
        return x;
    }
    
    public static String obtem_link(String isbn) throws IOException{
        HttpRequestFunctions.httpRequest1("https://www.wook.pt/pesquisa/=", isbn, "wook.txt");
        String er = "<link\\s*rel=\"canonical\"\\s*href=\"([^\"]+)\"";
        Scanner ler = new Scanner(new FileInputStream("wook.txt"));
        Pattern p = Pattern.compile(er);
        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }

        }
        ler.close();
        return null;
    }
    
    public static String obtem_titulo(String isbn,String autor) throws IOException{
        String link = obtem_link(isbn);
        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
        String er = "<title>([^<]+)\\s*de\\s*" + autor + "\\s*-\\s*Livro\\s*-\\s*WOOK</title>";
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
        String link = obtem_link(isbn);
        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
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
        String link = obtem_link(isbn);
        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
        String er = "editor:\\s*<span\\s*class=\\\"name font-medium\\\">\\s*([^,]+)";
        Pattern p = Pattern.compile(er);
        
        String fileContent = new String(Files.readAllBytes(Paths.get("wook.txt")));

        Matcher matcher = p.matcher(fileContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        
        return null;
    }
    
    public static String obtem_capa(String isbn) throws IOException{
        String link = obtem_link(isbn);
        
        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
   
        String er = "<meta\\s*property=\"og:image\"\\s*content=\"([^\"]+)\"";
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
    String link = obtem_link(isbn);
    HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
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
