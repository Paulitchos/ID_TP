/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.escritores;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class JDOMFunctions_Validar {
    
    // Caminho para os arquivos XML
    private static final String CAMINHO_ESCRITORES = "escritores.xml";
    private static final String CAMINHO_OBRA = ".xml";

    //Executa validação do documento XML usando DTD 
    public static Document validarDTD(String caminhoFicheiro) throws IOException{
        try {
            SAXBuilder builder = new SAXBuilder(true);  // true ativa a validação
            Document doc = builder.build(new File(caminhoFicheiro));
            System.out.println("Documento XML " + caminhoFicheiro + " é válido (DTD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " apresenta erros e não é válido (DTD)");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " não foi encontrado");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
     //Executa validação do documento XML usando XSD
    public static Document validarXSD(String caminhoFicheiro){
        try {
            SAXBuilder builder = new SAXBuilder(true); // true ativa a validação
            
            // esta linha ativa a validação com XSD
            builder.setFeature("http://apache.org/xml/features/validation/schema", true);

            Document doc = builder.build(new File(caminhoFicheiro));
            System.out.println("Documento XML " + caminhoFicheiro + " é válido (XSD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " apresenta erros e não é válido (XSD)");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + caminhoFicheiro + " não foi encontrado");
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String[] args) {
        try {
            // Validar os ficheiros XML usando DTD e XSD
            validarDTD(CAMINHO_ESCRITORES);
            validarXSD(CAMINHO_ESCRITORES);
            validarDTD(CAMINHO_OBRA);
            validarXSD(CAMINHO_OBRA);
        } catch (IOException ex) {
            Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}