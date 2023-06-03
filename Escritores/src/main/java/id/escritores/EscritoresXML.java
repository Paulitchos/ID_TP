/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.escritores;

import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;


public class EscritoresXML {
    
    public static Document adicionaEscritor(Escritores esc, Document doc) {
        Element raiz;
        if (doc == null) {
            raiz = new Element("escritores");
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element escritor = new Element("escritor");
        escritor.setAttribute("id", String.valueOf(esc.getId()));
        escritor.setAttribute("nomePesquisado", String.valueOf(esc.getNomePesquisa()));
        
        if (esc.getNome() != null) {
            Element nome = new Element("nome");
            nome.addContent(esc.getNome());
            escritor.addContent(nome);
        }

        if (esc.getNascimento() != null) {
            Element dnascimento = new Element("datanascimento");
            dnascimento.addContent(esc.getNascimento());
            escritor.addContent(dnascimento);
        }

        if (esc.getMorte() != null) {
            Element dfalecimento = new Element("datafalecimento");
            dfalecimento.addContent(esc.getMorte());
            escritor.addContent(dfalecimento);
        }

        if (esc.getNacionalidade() != null) {
            Element nacionalidade = new Element("nacionalidade");
            nacionalidade.addContent(esc.getNacionalidade());
            escritor.addContent(nacionalidade);
        }

        if (esc.getFotografia() != null) {
            Element foto = new Element("foto");
            foto.addContent(esc.getFotografia());
            escritor.addContent(foto);
        }
        
        String str = esc.getGenero();
        String[] generos = null;

        if (str != null) {
            generos = str.split(", ");
            Element gliterario = new Element("generoliterario");
            gliterario.setAttribute("ngeneros", String.valueOf(generos.length));

            for (String genero : generos) {
                Element gen = new Element("gen");
                gen.addContent(genero);
                gliterario.addContent(gen);
            }

            escritor.addContent(gliterario);
        }
        
        List<String> ocupacoesList = esc.getOcupacoes();

        if (ocupacoesList != null && !ocupacoesList.isEmpty()) {
            Element ocupacoes = new Element("ocupacoes");
            ocupacoes.setAttribute("nocupacaos", String.valueOf(ocupacoesList.size()));

            for (String ocupacao : ocupacoesList) {
                Element oc = new Element("oc");
                oc.addContent(ocupacao);
                ocupacoes.addContent(oc);
            }

            escritor.addContent(ocupacoes);
        }

        List<String> premiosList = esc.getPremios();

        if (premiosList != null && !premiosList.isEmpty()) {
            Element premios = new Element("premios");
            premios.setAttribute("npremios", String.valueOf(premiosList.size()));

            for (String premio : premiosList) {
                Element pre = new Element("pre");
                pre.addContent(premio);
                premios.addContent(pre);
            }

            escritor.addContent(premios);
        }
               
        raiz.addContent(escritor);
        return doc;
    }
    
    public static Document removeEscritor(String autor, Document doc){
        Element raiz;
        String idAutor = "";
        if (doc == null) {
            System.out.println("O ficheiro XML não existe.");
            return null;
        } else {
            raiz = doc.getRootElement();
        }

        List<Element> todosEscritores = raiz.getChildren("escritor");
        boolean found = false;
        for (int i = 0; i < todosEscritores .size(); i++) {
            Element escritor = todosEscritores .get(i);
            if (escritor.getAttributeValue("nomePesquisado").equals(autor)) {
                escritor.getParent().removeContent(escritor);
                System.out.println("Escritor removido com sucesso!");
                found = true;
                idAutor = escritor.getAttributeValue("id");
                Document docObras = XMLJDomFunctions.lerDocumentoXML("obras.xml");
                docObras = ObrasXML.removeObras(idAutor, docObras);
                
                if(docObras != null){
                    XMLJDomFunctions.escreverDocumentoParaFicheiro(docObras, "obras.xml");
                }
            }
        }

        if (!found) {
            System.out.println("Escritor " + autor + " não foi encontrado.");
            return null;
        }

        return doc;
    }
}
