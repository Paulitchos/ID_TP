/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.escritores;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.transform.XSLTransformException;
import org.jdom2.transform.XSLTransformer;


public class JDOMFunctions_XSLT {
    
      /* Não permitte Transformações para TXT method="text"*/
  public static Document transformaDocumento(Document XMLdoc, String xmlFile, String xslFile){
        
      try{    
           DocType d = XMLdoc.getDocType();
           if(d!=null){
               XMLdoc.setDocType(null);
               XMLJDomFunctions.escreverDocumentoParaFicheiro(XMLdoc, xmlFile);
               XMLdoc=XMLJDomFunctions.lerDocumentoXML(xmlFile);
           }
           XSLTransformer transformer = new XSLTransformer(xslFile);
           Document doc2 = transformer.transform(XMLdoc);
           if(doc2 == null) System.out.println("Null");
           return doc2;
        }
        catch  (XSLTransformException ex) {
            Logger.getLogger(JDOMFunctions_XSLT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
  }
  
  
    /* Também perimte Transformações para TXT method="text"
       Não cria objeto DOM Document
       Cria diretamente em disco o ficheiro da transformação*/
    public static void transformaDocumento2(String xmlFile,String xslFile, String sOutFile)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader(xmlFile));
            String         sLine;
            StringBuilder   sBuffer = new StringBuilder();
            while ( (sLine = br.readLine()) != null ) 
                sBuffer.append(sLine).append("\n");
            String sXML = sBuffer.toString();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
            transformer.transform(new StreamSource(new StringReader(sXML)),
                                  new StreamResult(new FileOutputStream(sOutFile)));
        }
        catch(IOException | TransformerException ex){
            Logger.getLogger(JDOMFunctions_XSLT.class.getName()).log(Level.SEVERE, null, ex);
        }
    
  }
    
    //HTML Autores
    public static boolean escritoresFotosTabela() throws IOException {
        Document doc = XMLJDomFunctions.lerDocumentoXML("escritores.xml");
            if (doc != null) {
                Document novo = JDOMFunctions_XSLT.transformaDocumento(doc, "escritores.xml", "HtmlEscritoresFotos.xsl");
                XMLJDomFunctions.escreverDocumentoParaFicheiro(novo, "EscritoresFotos.html");
                String url = "EscritoresFotos.html";
                File htmlFile = new File(url);
                try {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
            
            return false;
    }
    
    
    //TXT Autores
    public static boolean listarEscritores() throws FileNotFoundException {
        Document doc = XMLJDomFunctions.lerDocumentoXML("escritores.xml");
        if (doc != null) {
            JDOMFunctions_XSLT.transformaDocumento2("escritores.xml", "escritores.xsl", "escritoresListagem.txt");
            return true;
        }
        
        return false;
    }
    
    public static boolean mostarObras() throws FileNotFoundException {
        Document doc = XMLJDomFunctions.lerDocumentoXML("obras.xml");
        if (doc != null) {
            Document novo = JDOMFunctions_XSLT.transformaDocumento(doc, "obras.xml", "mostrarObras.xsl");
            XMLJDomFunctions.escreverDocumentoParaFicheiro(novo, "mostrarObras.html");
            String url = "mostrarObras.html";
            File htmlFile = new File(url);
            try {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        
        return false;
    }
       
    //EXTRA - Transformação XSLT para criar um ficheiro HTML a partir do ficheiro combinado de escritores e suas obras
    public static void htmlEscritoresObras() throws IOException {
        Document doc = XMLJDomFunctions.lerDocumentoXML("escritoresObras.xml");
        if (doc != null) {
            Document novo = JDOMFunctions_XSLT.transformaDocumento(doc, "escritoresObras.xml", "escritoresObras.xsl");
            XMLJDomFunctions.escreverDocumentoParaFicheiro(novo, "escritoresObras.html");
        }
    }
}
