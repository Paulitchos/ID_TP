/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.SaxonApiException;
import org.jdom2.Document;


public class main {
    
    public static void main(String[] args) throws IOException, SaxonApiException {
        
        Escritores escritor = Wrappers.criaEscritor("Daniel Silva");
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
        
        Document docEscritor = XMLJDomFunctions.lerDocumentoXML("escritores.xml");
        
        docEscritor = EscritoresXML.adicionaEscritor(escritor, docEscritor);
        
        XMLJDomFunctions.escreverDocumentoParaFicheiro(docEscritor, "escritores.xml");
        
        List<Obra> obras = Wrappers.criaObra("Oscar Wilde", escritor.getId());
        
        Document docObras = XMLJDomFunctions.lerDocumentoXML("obras.xml");
        if (obras != null && !obras.isEmpty()) {
            System.out.println("Obras:");
            for (Obra obra : obras) {
                System.out.println("\nISBN: " + obra.getIsbn());
                System.out.println("Titulo: " + obra.getTitulo());
                System.out.println("Autor: " + obra.getAutor());
                System.out.println("Editora: " + obra.getEditora());
                System.out.println("Capa: " + obra.getCapa());
                System.out.println("Preco: " + obra.getPreco());
                
                docObras = ObrasXML.adicionaObras(obra, docObras);
                XMLJDomFunctions.escreverDocumentoParaFicheiro(docObras, "obras.xml");
            }
        } else {
            System.out.println("Nenhuma obra encontrada.");
        }
   
    }
    
    //4.7 PESQUISAS XPATH
    
    //Pesquisa pelo nome do autor e mostra a informacao relevante
    static void pesquisa_nome_autor() throws SaxonApiException {
    Scanner ler = new Scanner(System.in);
    System.out.println("Introduza o nome de um autor");
    String nomeAutor = ler.nextLine();
    String xp = "//Escritor[contains(Nome, '" + nomeAutor + "')]";
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
    
    
    //Pesquisar autores com uma nacionalidade especifica
    static void pesquisa_nacionalidade_autor() throws SaxonApiException {
    Scanner ler = new Scanner(System.in);
    System.out.println("Introduza uma nacionalidade");
    String nacionalidade = ler.nextLine();
    String xp = "//Escritor[contains(Nacionalidade, '" + nacionalidade + "')]";
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
