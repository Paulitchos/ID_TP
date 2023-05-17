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
            raiz = new Element("escritores"); // cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element escritor = new Element("escritor");
        escritor.setAttribute("id", String.valueOf(esc.getId()));

        Element nome = new Element("nome");
        nome.addContent(esc.getNome());

        Element dnascimento = new Element("datanascimento");
        dnascimento.addContent(esc.getNascimento());

        Element dfalecimento = new Element("datafalecimento");
        dfalecimento.addContent(esc.getMorte());

        Element nacionalidade = new Element("nacionalidade");
        nacionalidade.addContent(esc.getNacionalidade());

        Element foto = new Element("foto");
        foto.addContent(esc.getFotografia());
        
        String str = esc.getGenero();
        String[] generos = str.split(", ");
       
        Element gliterario = new Element("generoliterario");
        gliterario.setAttribute("ngeneros", String.valueOf(generos.length));
               
        for (String genero : generos){
            Element gen = new Element("gen");
            gen.addContent(genero);
            gliterario.addContent(gen);
        }
        
        List<String> ocupacoesList = esc.getOcupacoes();
        
        Element ocupacoes = new Element("ocupacoes");
        ocupacoes.setAttribute("nocupacaos", String.valueOf(ocupacoesList.size()));
        
        for (String ocupacao : ocupacoesList){
            Element oc = new Element("oc");
            oc.addContent(ocupacao);
            ocupacoes.addContent(oc);
        }
        
        List<String> premiosList = esc.getPremios();
        
        Element premios = new Element("premios");
        premios.setAttribute("npremios", String.valueOf(premiosList.size()));
        
        for (String premio : premiosList){
            Element pre = new Element("pre");
            pre.addContent(premio);
            premios.addContent(pre);
        }
        
        escritor.addContent(nome);
        escritor.addContent(dnascimento);
        escritor.addContent(dfalecimento);
        escritor.addContent(nacionalidade);
        escritor.addContent(foto);
        escritor.addContent(gliterario);
        escritor.addContent(ocupacoes);
        escritor.addContent(premios);
        
        raiz.addContent(escritor);
        return doc;
    }
}
