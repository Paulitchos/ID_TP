/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Date;
import java.util.List;

public class Wrappers {
    
    public static Obra criaObra(String isbn) throws IOException {
        String link = obtem_link(isbn);
        String autor = obtem_autor(link);
        String titulo = obtem_titulo(link,autor);
        double preco = obtem_preco(link);
        String capa = obtem_capa(link);
        String editora = obtem_editora(link);
        String codigo = "1";
        Obra x = new Obra(isbn,codigo,autor,titulo,editora,capa,preco,link);
        return x;
    }
    
    public static Escritores criaEscritor(String autor) throws IOException {
        String link = obtem_link_escritor(autor);
        String nome = obtem_nome(link);
        String nacionalidade = obtem_nacionalidade(link);
        String foto = obtem_foto(link);
        String genero = obtem_genero(link);
        String nascimento = obtem_data_nascimento(link);
        String falecimento = obtem_data_falecimento(link);
        List<String> premios = obtem_premios(link);
        List<String> ocupacoes = obtem_ocupacoes(link);
        String outra_info = obter_outra_info(link);
        Escritores x = new Escritores(nome,nacionalidade,foto,genero,ocupacoes,premios,outra_info,nascimento,falecimento,link);
        return x;
    }
 
    public static String obtem_link(String isbn) throws IOException{
//        HttpRequestFunctions.httpRequest1("https://www.wook.pt/pesquisa/=", isbn, "wook.txt");
//        String er = "<link\\s*rel=\"canonical\"\\s*href=\"([^\"]+)\"";
//        Scanner ler = new Scanner(new FileInputStream("wook.txt"));
        HttpRequestFunctions.httpRequest1("https://www.bertrand.pt/pesquisa/", isbn, "bertrand.txt");
        String er = "<a\\s*class=\"title-lnk track\"\\s*href=\"([^\"]+)\">";
        Scanner ler = new Scanner(new FileInputStream("bertrand.txt"));
        Pattern p = Pattern.compile(er);
        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return "https://www.bertrand.pt" + m.group(1);
            }

        }
        ler.close();
        return null;
    }
    
    public static String obtem_link_escritor(String autor) throws IOException{
        autor = autor.replaceAll("\\s+", "_");
        HttpRequestFunctions.httpRequest1("https://pt.wikipedia.org/wiki/", autor, "wikipedia.txt");
        return "https://pt.wikipedia.org/wiki/" + autor;
    }
    
    public static String obtem_titulo(String link,String autor) throws IOException{
//        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
//        String er = "<title>([^<]+)\\s*de\\s*" + autor + "\\s*-\\s*Livro\\s*-\\s*WOOK</title>";
        HttpRequestFunctions.httpRequest1(link, "", "bertrand.txt");
        
        String er = "<h1\\s*id=\"productPageRightSectionTop-title-h1\"\\s*class=\"[^\"]+\">([^<]+)</h1>";
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("bertrand.txt"));
        
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
    
    public static String obtem_autor(String link) throws IOException{
//        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
//        String er = "<a href=\'#author-\\d*\'>([^<]+)</a>";

        HttpRequestFunctions.httpRequest1(link, "", "bertrand.txt");
        
        String er = "<a\\s*href=\"/autor/[\\w\\s/\\d-]*\">([^<]+)</a>";
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("bertrand.txt"));
        
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
    
    public static String obtem_editora(String link) throws IOException{
//        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
//        String er = "editor:\\s*<span\\s*class=\"name font-medium\">\\s*([^,]+)";
        HttpRequestFunctions.httpRequest1(link, "", "bertrand.txt");
        String er = "Editor:\\s*<span class=\"info\">([^<]+)</span>";
        Scanner ler = new Scanner(new FileInputStream("bertrand.txt"));
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
//        Pattern p = Pattern.compile(er);
//        
//        String fileContent = new String(Files.readAllBytes(Paths.get("wook.txt")));
//
//        Matcher matcher = p.matcher(fileContent);
//        if (matcher.find()) {
//            return matcher.group(1);
//        }
//        
//        
//        return null;
    }
    
    public static String obtem_capa(String link) throws IOException{     
//        HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
//   
//        String er = "<meta\\s*property=\"og:image\"\\s*content=\"([^\"]+)\"";

        HttpRequestFunctions.httpRequest1(link, "", "bertrand.txt");
        String er = "<meta property=\"og:image\" content=\"([^\"]+)\" /><";
        Pattern p = Pattern.compile(er);
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("bertrand.txt"));
        
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
    
    public static double obtem_preco(String link) throws IOException{
//    HttpRequestFunctions.httpRequest1(link, "", "wook.txt");
//    Scanner ler = new Scanner(new FileInputStream("wook.txt"));
//    String er = "<span\\s*class=\"price-rpl\">([^€]+)€</span>";

    HttpRequestFunctions.httpRequest1(link, "", "bertrand.txt");
    String er = "<div\\s*class=\"current\"\\s*id=\"productPageRightSectionTop-saleAction-price-current\">\\s*([^€]+)€</div>";
    Scanner ler = new Scanner(new FileInputStream("bertrand.txt"));
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
    
    public static String obtem_nome(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
          
        List<String> patterns = Arrays.asList(
        "<tr>\\s*<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Nome\\s*completo\\s*</td>\\s*<td\\s*style=\"[^\"]+\">([^<]+)</td></tr>",
        "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Nascimento\\s*</td>\\s*<td style=\"[^\"]+\">([^<]+)<br\\s*/>",
        "</tbody></table>\\s*<p><b>([^<]+)</b>"
        );
                
        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));
        
        
        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(fileContent);

            if (matcher.find()) {
                return matcher.group(1).replaceAll("\\s+$", "");
            }
        }
        
        return "";
    }
    
    public static String obtem_data_nascimento(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
   
        String er = "<a\\s*href=\"[^#]+#Nascimentos\"\\s*title=\"([^\"]+)\">[^<]+</a>([^<]+)<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">";
        Pattern p = Pattern.compile(er);
               
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("wikipedia.txt"));
        
        String linha;
        
        while((input.hasNextLine())){
            linha = input.nextLine();
            m = p.matcher(linha);
            
            if (m.find()){
                input.close();
                return(m.group(1)+m.group(2)+m.group(3));
            }        
        }
        input.close();
        return null;
    }
    
    public static String obtem_data_falecimento(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
   
        String er = "<tr>\\s*<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Morte\\s*</td>\\s*" +
                    "<td\\s*style=\"[^\"]+\"><span\\s*style=\"[^\"]+\"><a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">[^<]+</a>" +
                    "([^<]+)<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">";
        Pattern p = Pattern.compile(er);
               
        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));
        
        Matcher matcher = p.matcher(fileContent);
        
        if (matcher.find()) {
            return(matcher.group(1) + matcher.group(2) + matcher.group(3));
        }
        
        return "Não faleceu";
    }
    
    public static String obtem_nacionalidade(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
   
        String er = "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Nacionalidade\\s*" +
                    "</td>\\s*<td\\s*style=\"[^\"]+\"><a\\s*href=\"[^\"]+\"\\s*title=\"[^\"]+\">" + 
                    "([^<]+)<";
        Pattern p = Pattern.compile(er);
               
        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));
        
        Matcher matcher = p.matcher(fileContent);
        
        if (matcher.find()) {
            String nacionalidade = matcher.group(1);
            nacionalidade = nacionalidade.substring(0, 1).toUpperCase() + nacionalidade.substring(1);
            return(nacionalidade);
        }
        
        return "";
    }
    
    public static String obtem_foto(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
        String autor = link.substring(link.lastIndexOf("/") + 1);
        autor = autor.replaceAll("_", " ");
        String er = "<a\\s*href=\"[^\"]+\"\\s*class=\"image\"\\s*title=\""+ autor +"\"><img\\s*alt=\"\"\\s*src=\"([^\"]+)\"";
        Pattern p = Pattern.compile(er);
        
        Matcher m;
        
        Scanner input = new Scanner(new FileInputStream("wikipedia.txt"));
        
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
        return "";
    }
    
    public static String obtem_genero(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");

        List<String> patterns = Arrays.asList(
                "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">\\s*<a\\s*href=\"[^\"]+\"\\s*title=\"Gênero literário\">\\s*Género literário\\s*</a>\\s*" +
                        "</td>\\s*<td\\s*style=\"[^\"]+\">\\s*((?:<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">\\s*([^<]+)\\s*</a>\\s*,?\\s*)+)",
                "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Género literário\\s*</td>\\s*" +
                        "<td\\s*style=\"[^\"]+\">\\s*((?:<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">\\s*([^<]+)\\s*</a>\\s*,?\\s*)+)",
                "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\"><a\\s*href=\"/[^\"]+\"\\s*title=\"Gênero literário\">Gênero literário</a>\\s*</td>\\s*" +
                        "<td\\s*style=\"[^\"]+\"><div class=\"hlist\">\\s*<ul>" +
                        "((?:<li><a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">[^<]+</a></li>\\s*?\\s*)+)"
        );

        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));

        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(fileContent);

            if (matcher.find()) {
                String allGenres = matcher.group(1);
                System.out.println("Generos: " + allGenres);
                List<String> genrePatterns = Arrays.asList(
                        "<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">\\s*([^<]+)\\s*</a>",
                        "<li><a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">[^<]+</a></li>"
                );
                for (String genrePattern : genrePatterns) {
                    Pattern genreP = Pattern.compile(genrePattern);
                    Matcher genreMatcher = genreP.matcher(allGenres);
                    StringBuilder generos = new StringBuilder();
                    while (genreMatcher.find()) {
                        if (generos.length() > 0) {
                            generos.append(", ");
                        }
                        generos.append(genreMatcher.group(1));
                    }
                    return generos.toString();
                }

            }
        }

        return "Não tem";
    }
    
    public static List<String> obtem_premios(String link) throws IOException {
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
        String er = "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Prêmios\\s*</td>\\s*<td\\s*style=\"[^\"]+\">(.*?)</td>";
        Pattern p = Pattern.compile(er, Pattern.DOTALL);

        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));

        Matcher matcher = p.matcher(fileContent);
        List<String> premios = new ArrayList<>();

        if (matcher.find()) {
            String premiosTexto = matcher.group(1);
            Pattern premiosPattern = Pattern.compile("<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">");
            Matcher premiosMatcher = premiosPattern.matcher(premiosTexto);
            while (premiosMatcher.find()) {
                String premio = premiosMatcher.group(1);
                premios.add(premio);
            }
            
        }else {
            premios.add("Não tem");
        }

        return premios;
    }
    
    public static List<String> obtem_ocupacoes(String link) throws IOException {
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
        String er = "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Ocupação\\s*</td>\\s*<td\\s*style=\"[^\"]+\">(.*?)</td>";
        Pattern p = Pattern.compile(er, Pattern.DOTALL);

        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));

        Matcher matcher = p.matcher(fileContent);
        List<String> ocupacoes = new ArrayList<>();

        if (matcher.find()) {
            String ocupacoesTexto = matcher.group(1);
            Pattern ocupationPattern = Pattern.compile("<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">");
            Matcher ocupationMatcher = ocupationPattern.matcher(ocupacoesTexto);
            while (ocupationMatcher.find()) {
                String ocupacao = ocupationMatcher.group(1);
                ocupacoes.add(ocupacao);
            }
            
        } else {
            ocupacoes.add("Não tem");
        }

        return ocupacoes;
    }
    
    public static String obter_outra_info(String link) throws IOException {
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
        String er = "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Outra\\s*informação\\s*</td>\\s*<td\\s*style=\"[^\"]+\">(.*?)</td>";
        Pattern p = Pattern.compile(er, Pattern.DOTALL);

        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));

        Matcher matcher = p.matcher(fileContent);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return "";
    }
}
