/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.escritores;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.saxon.s9api.*;


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
    
    static int getLastIdFromXML(String fileName) throws SaxonApiException {
        String xp = "(//escritor/@id)[last()]";
        XdmValue res = XPathFunctions.executaXpath(xp, fileName);
        if (res == null) {
            System.out.println("Ficheiro XML não existe");
            return -1; // Return -1 or any other appropriate value to indicate an error
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
            return -1; // Return -1 or any other appropriate value to indicate no results
        } else {
            String strID = res.itemAt(0).getStringValue();
            return Integer.parseInt(strID);
        }
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
    static List<String> pesquisa_obras_autor(String id) throws SaxonApiException {
        String xp = "//obra[contains(@codigoautor, '" + id + "')]";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
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
    
    
    //Qual o escritor mais premiado
    static String escritor_mais_premiado() throws SaxonApiException {
        String xp = "//escritor[premios/@npremios = max(//escritor/premios/@npremios)]/nome";
        XdmValue res = XPathFunctions.executaXpath(xp, "escritores.xml");
        if (res == null) {
            System.out.println("Ficheiro XML nao existe");
        } else if (res.size() == 0) {
            System.out.println("Sem Resultados");
        } else {
            String nomeEscritor = res.itemAt(0).getStringValue();
            System.out.println("Escritor mais premiado: " + nomeEscritor);
            return nomeEscritor;
        }
        
        return null;
    }
    
    
    //Quais os livros publicados por uma determinada editora, com preço acima de um dado valor
    static List<String> livros_editora_preco(String editora, String precoMin) throws SaxonApiException {
        
        String xp = "//obra[contains(editora, '" + editora + "') and preco > " + precoMin + "]/concat(titulo/text(), ' - ', autor/text())";
        XdmValue res = XPathFunctions.executaXpath(xp, "obras.xml");
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
    
    
    //EXTRA (PESQUISAS XPATH)
    
    static double averagePriceOfAwardWinningAuthors() throws SaxonApiException {
        String xp = "//escritor[count(premios/pre) >= 1]/@id";
        XdmValue authorIds = executaXpath(xp, "escritores.xml");

        if (authorIds == null) {
            System.out.println("Ficheiro XML nao existe");
            return -1.0;
        } else if (authorIds.size() == 0) {
            System.out.println("Sem Resultados");
            return -1.0;
        } else {
            double total = 0;
            int count = 0;

            for (XdmItem itemId : authorIds) {
                String id = itemId.getStringValue();
                String xpPrice = "//obra[@codigoautor='" + id + "']/preco";
                XdmValue prices = executaXpath(xpPrice, "obras.xml");

                if (prices != null) {
                    for (XdmItem itemPrice : prices) {
                        double price = Double.parseDouble(itemPrice.getStringValue());
                        total += price;
                        count++;
                    }
                }
            }

            if (count > 0) {
                double averagePrice = total / count;
                
                
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedAveragePrice = decimalFormat.format(averagePrice);

                return Double.parseDouble(formattedAveragePrice);
            } else {
                return -1.0;
            }
        }
    }
   
}
