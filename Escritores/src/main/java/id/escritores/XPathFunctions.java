/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.escritores;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.s9api.*;
import org.w3c.dom.Node;


/**
 *
 * @author abs
 */
public class XPathFunctions {

    static XdmValue executaXpath(String xp, String xmlFile) throws SaxonApiException {
        XdmValue resultado = null;
        File f = new File(xmlFile);
        if (f.exists()) {
            Processor proc = new Processor(false);
            XPathCompiler xpath = proc.newXPathCompiler();

            DocumentBuilder builder = proc.newDocumentBuilder();

            XdmNode xml = builder.build(new File(xmlFile));
            XPathSelector selector = xpath.compile(xp).load();

            selector.setContextItem(xml);
            resultado = selector.evaluate();
        }
        return resultado;
    }

    static List<String> listaResultado(XdmValue lista) {
        List<String> results = new ArrayList<>();
        if (lista != null) {
            System.out.println("RESULTADO DA PESQUISA XPATH:");
            for (XdmItem item : lista) {
                results.add(item.getStringValue());
            }
        }
        return results;

    }
    
    //Pesquisa pelo nome do autor e mostra a informacao relevante
    static List<String> pesquisa_nome_autor(String nomeAutor) throws SaxonApiException {
        String xp = "//escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/@id "
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/nome/text()"
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/nacionalidade/text()"
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/datanascimento/text()"
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/datafalecimento/text()"
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/generoliterario/gen/text()"
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/ocupacoes/oc/text()"
                + "| //escritor[contains(@nomePesquisado, '" + nomeAutor + "')]/premios/pre/text()";
        XdmValue res = XPathFunctions.executaXpath(xp, "escritores.xml");
        if (res == null) {
            System.out.println("Ficheiro XML nao existe");
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
        } else {
            List<String> results = listaResultado(res);
            return results;
        }
        
        return null;
    }
    
    
    //Pesquisar autores com uma nacionalidade especifica
    static List<String> pesquisa_nacionalidade_autor(String nacionalidade) throws SaxonApiException {
        String xp = "//escritor[contains(nacionalidade, '" + nacionalidade + "')]/nome/text()";
        XdmValue res = XPathFunctions.executaXpath(xp, "escritores.xml");
        List<String> s = XPathFunctions.listaResultado(res);
        if (res == null) {
            System.out.println("Ficheiro XML nao existe");
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
        } else {
            System.out.println(s);
            return s;
        }
        return null;
    }
    
    
    //Pesquisar as obras de um determinado autor
    static void pesquisa_obras_autor() throws SaxonApiException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduza o nome de um autor");
        String nomeAutor = ler.nextLine();
        String xp = "//Obra[contains(Autor, '" + nomeAutor + "')]";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
        String s = XPathFunctions.listaResultado(res);
        if (res == null) {
            System.out.println("Ficheiro XML nao existe");
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
        } else {
            System.out.println(s);
            }
    }
    
    
    //Qual o escritor mais premiado?
    static void escritor_mais_premiado() throws SaxonApiException {
        String xp = "//Escritor[Premios = max(//Escritor/Premios)]";
        XdmValue res = XPathFunctions.executaXpath(xp, "escritores.xml");
        String s = XPathFunctions.listaResultado(res);
        if (res == null) {
            System.out.println("Ficheiro XML nao existe");
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
        } else {
            System.out.println(s);
            }
    }
    
    
    //Quais os livros publicados por uma determinada editora, com preço acima de um dado valor
    static void livros_editora_preco() throws SaxonApiException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduza o nome de uma editora");
        String editora = ler.nextLine();
        System.out.println("Introduza o valor minimo do preco");
        int precoMin = ler.nextInt();
        String xp = "//Obra[contains(Editora, '" + editora + "') and Preco > " + precoMin + "]";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
        String s = XPathFunctions.listaResultado(res);
        if (res == null) {
            System.out.println("Ficheiro XML nao existe");
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
        } else {
            System.out.println(s);
            }
    }
    
    
    //EXTRA (PESQUISAS XPATH)
    
    //Pesquisa por livros de um determinado genero
    static void livros_genero() throws SaxonApiException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduza o genero do livro");
        String genero = ler.nextLine();
        String xp = "//Obra[contains(Genero, '" + genero + "')]";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
        String s = XPathFunctions.listaResultado(res);
        if(res == null){
            System.out.println("Ficheiro XML nao existe");
        } else if(res.size() == 0) {
            System.out.println("Sem Resultados");
        } else{
            System.out.println(s);
            }
    }
    
    
    //Pesquisa por obras publicadas num determinado período de tempo
    static void obras_periodo() throws SaxonApiException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduza o ano inicial");
        int anoInicial = ler.nextInt();
        System.out.println("Introduza o ano final");
        int anoFinal = ler.nextInt();
        String xp = "//Obra[AnoPublicacao >= " + anoInicial + " and AnoPublicacao <= " + anoFinal + "]";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
        String s = XPathFunctions.listaResultado(res);
        if(res == null){
            System.out.println("Ficheiro XML nao existe");
        } else if(res.size() == 0) {
            System.out.println("Sem Resultados");
        } else{
            System.out.println(s);
            }
    }
    
    
    //Pesquisa por obras que tenham recebido um premio especifico
    static void obras_premio() throws SaxonApiException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduza o nome do premio");
        String premio = ler.nextLine();
        String xp = "//Obra[contains(Premios, '" + premio + "')]";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
        String s = XPathFunctions.listaResultado(res);
        if(res == null){
            System.out.println("Ficheiro XML nao existe");
        } else if(res.size() == 0) {
            System.out.println("Sem Resultados");
        } else{
            System.out.println(s);
            }
    }

}
