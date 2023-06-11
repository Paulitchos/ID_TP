/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import id.escritores.XPathFunctions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.List;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;

public class Wrappers {
    
    public static List<Obra> criaObra(String nome,int codigo) throws IOException { 
        List<String> isbn = obtem_isbn(nome);
        List<Obra> obras = new ArrayList<>();
        
        for (String i : isbn){
            String link = obtem_link(i);
            String autor = obtem_autor(link);
            String titulo = obtem_titulo(link,autor);
            double preco = obtem_preco(link);
            String capa = obtem_capa(link);
            String editora = obtem_editora(link);
            Obra x = new Obra(i,codigo,autor,titulo,editora,capa,preco,link);
            obras.add(x);
        }
        
        return obras;
    }
    
    public static Escritores criaEscritor(String autor) throws IOException, SaxonApiException {       
        
        try {
            String link = obtem_link_escritor(autor);
            String nome = obtem_nome(link);
            if(nome == null){
                String autorAlterado = autor.replace(" ", "_") + "_(escritor)";;
                link = obtem_link_escritor(autorAlterado);
                nome = obtem_nome(link);
            }
            String xp = "//escritor[@nomePesquisado='" + autor + "']/@nomePesquisado";
            XdmValue res = null;
            res = XPathFunctions.executaXpath(xp, "escritores.xml");
            if (res == null || res.size() == 0) {
                String nacionalidade = obtem_nacionalidade(link);
                String foto = obtem_foto(link);
                String genero = obtem_genero(link);
                String nascimento = obtem_data_nascimento(link);
                String falecimento = obtem_data_falecimento(link);
                List<String> premios = obtem_premios(link);
                List<String> ocupacoes = obtem_ocupacoes(link);
                Escritores x = new Escritores(nome,nacionalidade,foto,genero,ocupacoes,premios,nascimento,falecimento,link,autor);
                return x;
            }
            return null;
        } catch (FileNotFoundException e) {
           return null;
        }  
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
    
    public static List<String> obtem_isbn(String nome) throws IOException {
        nome = nome.replaceAll("\\s+", "+");

        HttpRequestFunctions.httpRequest1("https://www.bertrand.pt/pesquisatab/autores/", nome, "bertrand.txt");
        String link = null;
        int linksCount = 0;
        List<String> links = new ArrayList<>();
        List<String> isbn = new ArrayList<>();

        String er = "<div\\s*class=\"name\">\\s*<a\\s*href=\"([^\"]+)\">[^<]+</a>\\s*</div>";

        Pattern p = Pattern.compile(er);

        String fileContent = new String(Files.readAllBytes(Paths.get("bertrand.txt")));

        Matcher matcher = p.matcher(fileContent);
        
        if (matcher.find()) {
            link = matcher.group(1);
            HttpRequestFunctions.httpRequest1("https://www.bertrand.pt/" + link, "", "bertrand.txt");
            er = "<a\\s*class=\"track\"\\s*href=\"([^\"]+)\">";
            p = Pattern.compile(er);
            fileContent = new String(Files.readAllBytes(Paths.get("bertrand.txt")));
            matcher = p.matcher(fileContent);
            while (linksCount < 5 && matcher.find()) {
                    linksCount++;
                    links.add(matcher.group(1));               
            }
        }
        
        if (linksCount < 3) {
            return null;
        }
        
        int index = 0;
        
        for (String i : links){
            HttpRequestFunctions.httpRequest1("https://www.bertrand.pt/" + i, "", "bertrand.txt");
            fileContent = new String(Files.readAllBytes(Paths.get("bertrand.txt")));
            String isbnEr = "<div\\s*id='[^']+'\\s*class='[^']+'>ISBN:\\s*<div\\s*class='info'>([^<]+)</div>";
            p = Pattern.compile(isbnEr);
            Matcher isbnMatcher = p.matcher(fileContent);
            if (isbnMatcher.find()) {
                isbn.add(isbnMatcher.group(1));
                index++;
            }
        }
        
        return isbn;

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
        
        String er = "\"@type\":\"Person\",\"name\":\"([^\"]+)\"";
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
        String er = "publisher\":\\{\"@type\":\"Organization\",\"name\":\"([^\"]+)\"\\}";
        Scanner ler = new Scanner(new FileInputStream("bertrand.txt"));
        Pattern p = Pattern.compile(er);
        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                System.out.println();
                return unescapeUnicode(m.group(1));
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
                String name = matcher.group(1).replaceAll("\\s+$", "");
                return name.replaceAll(",", "");
            }
        }
        
        return null;
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
        
        return null;
    }
    
    public static String obtem_nacionalidade(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
              
        String er = "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Nacionalidade\\s*" +
                    "</td>\\s*<td\\s*style=\"[^\"]+\"><a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">";
        Pattern p = Pattern.compile(er);
               
        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));
        
        Matcher matcher = p.matcher(fileContent);
        
        if (matcher.find()) {
            String nacionalidade = matcher.group(1);
            nacionalidade = nacionalidade.substring(0, 1).toUpperCase() + nacionalidade.substring(1);
            return(nacionalidade);
        }
        
        return null;
    }
    
    public static String obtem_foto(String link) throws IOException{
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");
        String autor = link.substring(link.lastIndexOf("/") + 1);
        autor = autor.replaceAll("_", " ");
        autor = autor.replace("(", "\\(").replace(")", "\\)");
        System.out.println(autor);
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
                
                return("https:" + m.group(1));
            }        
        }
        input.close();
        return null;
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
                        "((?:<li><a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">[^<]+</a></li>\\s*?\\s*)+)",
                "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\"><a\\s*href=\"[^\"]+\"\\s*title=\"Gênero literário\">Gênero literário</a>\\s*"
                        + "</td>\\s*<td\\s*style=\"[^\"]+\">(.*?)\\s*</td>"
        );

        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));

        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(fileContent);

            if (matcher.find()) {
                String allGenres = matcher.group(1);
                //System.out.println("Generos: " + allGenres);
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

        return null;
    }
    
    public static List<String> obtem_premios(String link) throws IOException {
        HttpRequestFunctions.httpRequest1(link, "", "wikipedia.txt");

        List<String> patterns = Arrays.asList(
            "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Prêmios\\s*</td>\\s*<td\\s*style=\"[^\"]+\">(.*?)</td>",
            "<td\\s*scope=\"row\"\\s*style=\"[^\"]+\">Prémios\\s*</td>\\s*<td\\s*style=\"[^\"]+\">(.*?)\\s*</td>"
        );
        String fileContent = new String(Files.readAllBytes(Paths.get("wikipedia.txt")));

        List<String> premios = new ArrayList<>();

        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher matcher = p.matcher(fileContent);

            if (matcher.find()) {
                String premiosTexto = matcher.group(1);
                //System.out.println(premiosTexto);
                List<String> premiosPatterns = Arrays.asList(
                    "<a\\s*href=\"[^\"]+\"\\s*class=\"[^\"]+\"\\s*title=\"([^\"]+)\">",
                    "<a\\s*href=\"[^\"]+\"\\s*title=\"([^\"]+)\">",
                    "([^,]+),<sup id=\"cite_ref-S._1-0\""
                );

                for (String premioPattern : premiosPatterns) {
                    Pattern genreP = Pattern.compile(premioPattern);
                    Matcher premioMatcher = genreP.matcher(premiosTexto);

                    while (premioMatcher.find()) {
                        String premio = premioMatcher.group(1);
                        premios.add(premio);
                    }
                }

                return premios; // Return the extracted premios immediately after finding the first match
            }
        }

        return null; // Return null if no match is found
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
            System.out.println(ocupacoesTexto);
            List<String> ocupationPatterns = Arrays.asList(
                "<a\\s*href=\"/wiki/[^>]+\"\\s*title=\"[^>]+\">([^<]+)</a>"
            );
            for (String ocupationPattern : ocupationPatterns) {
                Pattern ocupationP = Pattern.compile(ocupationPattern);
                Matcher ocupationMatcher = ocupationP.matcher(ocupacoesTexto);
                
                while (ocupationMatcher.find()) {
                        String ocupacao = ocupationMatcher.group(1);
                        ocupacao = ocupacao.replace(",", "");
                        ocupacao = Character.toUpperCase(ocupacao.charAt(0)) + ocupacao.substring(1);
                        ocupacoes.add(ocupacao);
                }
            }       
        } else {
            return null;
        }

        return ocupacoes;
    }
    
    
    public static String unescapeUnicode(String input) {
        StringBuilder builder = new StringBuilder();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char ch = input.charAt(i);
            if (ch == '\\' && i + 1 < length && input.charAt(i + 1) == 'u') {
                String unicodeStr = input.substring(i + 2, i + 6);
                char unicodeChar = (char) Integer.parseInt(unicodeStr, 16);
                builder.append(unicodeChar);
                i += 5;
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
}
