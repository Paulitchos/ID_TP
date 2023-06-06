/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;


public class ObrasXML {
    public static Document adicionaObras(Obra obr, Document doc) {
        Element raiz;
        if (doc == null) {
            raiz = new Element("obras");
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element obra = new Element("obra");
        obra.setAttribute("codigoautor", String.valueOf(obr.getCodigo()));
        
        Element isbn = new Element("isbn");
        isbn.addContent(obr.getIsbn());

        Element titulo = new Element("titulo");
        titulo.addContent(obr.getTitulo());

        Element autor = new Element("autor");
        autor.addContent(obr.getAutor());

        Element editora = new Element("editora");
        editora.addContent(obr.getEditora());

        Element capa = new Element("capa");
        capa.addContent(obr.getCapa());
        
        Element preco = new Element("preco");
        preco.addContent(Double.toString(obr.getPreco()));
       
        
        obra.addContent(isbn);
        obra.addContent(titulo);
        obra.addContent(autor);
        obra.addContent(editora);
        obra.addContent(capa);
        obra.addContent(preco);
        
        raiz.addContent(obra);
        return doc;
    }
    
    public static Document removeObras(String idAutor, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("O ficheiro XML não existe.");
            return null;
        } else {
            raiz = doc.getRootElement();
        }

        List<Element> todasObras = new ArrayList<>(raiz.getChildren("obra"));
        boolean found = false;
        for (Element obra : todasObras) {
            if (obra.getAttributeValue("codigoautor").equals(idAutor)) {
                obra.getParent().removeContent(obra);
                System.out.println("Obra removida com sucesso!");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Escritor " + idAutor + " não foi encontrado.");
            return null;
        }

        return doc;
    }
}
