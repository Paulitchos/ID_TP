/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.escritores;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.jdom2.Document;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

/**
 *
 * @author abs
 */
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
    public static void top5LivrosCaros() throws XPathException, IOException {
        SaxonFunctions_XQuery.xQueryToXml("top5LivrosCaros.xml", "top5LivrosCaros.xql");
        Document doc = XMLJDomFunctions.lerDocumentoXML("top5LivrosCaros.xml");
        String t = XMLJDomFunctions.escreverDocumentoString(doc);
    }
    
    
    //HTML FOTOS LIVROS AUTOR - XQUERY/XSLT
    public static void executaConsultaXQuery(String nomeAutor) throws Exception {
        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = sqc.compileQuery(new FileReader("livrosAutor.xql"));

        DynamicQueryContext dynamicContext = new DynamicQueryContext(config);
        dynamicContext.setParameter("nomeAutor", nomeAutor);

        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        exp.run(dynamicContext, new StreamResult(new File("livrosAutor.xml")), props);
    }
    
    
    //Combinar info dos escritores e suas obras
    public static void combinarEscritoresEObras() throws Exception {
        Configuration config = new Configuration();
        StaticQueryContext sqc = new StaticQueryContext(config);
        XQueryExpression exp = sqc.compileQuery(new FileReader("combinar.xql"));

        DynamicQueryContext dynamicContext = new DynamicQueryContext(config);

        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        exp.run(dynamicContext, new StreamResult(new File("combinado.xml")), props);
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
