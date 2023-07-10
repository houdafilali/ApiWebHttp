/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetird;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Houda
 */
public class XML {
    
    private  final String  cheminFichier;
    public XML(final String c){
        cheminFichier=c;
    }
    public void lireFichier() throws ParserConfigurationException, SAXException, IOException
    {
        
      
       //obtenir un parseur DOM  pour traiter le fichier XML
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       //onstruire un objet Document représentant le contenu du fichier XML.
       DocumentBuilder builder = factory.newDocumentBuilder();
       //analyser le fichier XML et retourne un objet Document. Cet objet Document représente la structure et le contenu du fichier XML
       Document document = builder.parse(new File(cheminFichier));
       // Récupération du contenu de la balise <import>
       NodeList importList = document.getElementsByTagName("import");
       if (importList.getLength() > 0) {
           Element importElement = (Element) importList.item(0);
           String importContent = importElement.getTextContent();
           // Affichage du contenu de la balise <import>
           System.out.println("Contenu de la balise <import> : " + importContent);
            } else {
                System.out.println("Aucune balise <import> trouvée dans le fichier XML.");
            }
    }   
    
}
