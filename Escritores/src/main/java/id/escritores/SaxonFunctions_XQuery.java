/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.escritores;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.trans.XPathException;


public class SaxonFunctions_XQuery {

    public static void xQueryToText(String outputFile, String queryFile) throws XPathException, IOException{
        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = sqc.compileQuery(new FileReader(queryFile));
        DynamicQueryContext dynamicContext = new DynamicQueryContext(config);
        
        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "text");
        exp.run(dynamicContext, new StreamResult(new File(outputFile)), props);
    }
    
    public static void xQueryToHtml(String outputFile, String queryFile) throws XPathException, IOException{
        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = sqc.compileQuery(new FileReader(queryFile));
        DynamicQueryContext dynamicContext = new DynamicQueryContext(config);
       
        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "html");   
        exp.run(dynamicContext, new StreamResult(new File(outputFile)), props);
    }
    
    public static void xQueryToXml(String outputFile, String queryFile) throws XPathException, IOException{
        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = sqc.compileQuery(new FileReader(queryFile));
        DynamicQueryContext dynamicContext = new DynamicQueryContext(config);
       
        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        exp.run(dynamicContext, new StreamResult(new File(outputFile)), props);
    }
    
    
    //XML 5 Livros Caros 
    public static Document top5ObrasCaras() throws XPathException, IOException {
        SaxonFunctions_XQuery.xQueryToXml("top5ObrasCaras.xml", "top5ObrasCaras.xql");
        Document doc = XMLJDomFunctions.lerDocumentoXML("top5ObrasCaras.xml");
        if(doc != null){
            return doc;
        }
        return null;
    }
        
    //Combinar info dos escritores e suas obras
    public static boolean escritoresObras(String id) throws Exception {
        
        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = sqc.compileQuery(new FileReader("obrasEscritor.xql"));
        DynamicQueryContext dynamicContext = new DynamicQueryContext(config);

        // Set the external variable value
        dynamicContext.setParameter("author-id", new XdmAtomicValue(id).getStringValue());
        
        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "html");   
        exp.run(dynamicContext, new StreamResult(new File("obrasEscritor.html")), props);
        
        String url = "obrasEscritor.html";
        File htmlFile = new File(url);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    //EXTRA - Transformação de todas as obras de um autor específico em HTML
    public static void htmlTodasObrasAutor(String nomeAutor) throws XPathException, IOException {
        StaticQueryContext sqc = new StaticQueryContext(new Configuration());
        XQueryExpression exp = sqc.compileQuery(new FileReader("todasObrasAutor.xql"));
        DynamicQueryContext dynamicContext = new DynamicQueryContext(sqc.getConfiguration());
        dynamicContext.setParameter("nomeAutor", nomeAutor);

        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        exp.run(dynamicContext, new StreamResult(new File("todasObrasAutor.xml")), props);

        Document doc = XMLJDomFunctions.lerDocumentoXML("todasObrasAutor.xml");
        if (doc != null) {
            Document novo = JDOMFunctions_XSLT.transformaDocumento(doc, "todasObrasAutor.xml", "todasObrasAutor.xsl");
            XMLJDomFunctions.escreverDocumentoParaFicheiro(novo, "todasObrasAutor.html");
        }
    }
    
    
    //EXTRA - Gerar um ficheiro XML com os escritores agrupados por nacionalidade
    public static void escritoresPorNacionalidade() throws XPathException, IOException {
        StaticQueryContext sqc = new StaticQueryContext(new Configuration());
        XQueryExpression exp = sqc.compileQuery(new FileReader("escritoresPorNacionalidade.xql"));

        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        exp.run(new DynamicQueryContext(sqc.getConfiguration()), new StreamResult(new File("escritoresPorNacionalidade.xml")), props);
    }
}
