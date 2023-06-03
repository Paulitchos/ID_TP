/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.io.IOException;
import java.util.List;
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
}
