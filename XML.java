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
   public double[][][] lireFichierXML() throws ParserConfigurationException, SAXException, IOException {

        // Creer un objet DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Creer un objet DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Charger le fichier XML
        Document document = builder.parse(new File(cheminFichier));
        // Obtenir les éléments <Matrix>
        NodeList matrixList = document.getElementsByTagName("Matrix");
        int nombreDonnees = matrixList.getLength();
        // Stocker les valeurs de la matrice dans un tab
        double[][][] matrices = new double[nombreDonnees][5000][1];
        // Parcourir les matrices
        for (int i = 0; i < nombreDonnees; i++) {
            Element matrixElement = (Element) matrixList.item(i);


            NodeList rowList = matrixElement.getElementsByTagName("Row");
            for (int j = 0; j < rowList.getLength(); j++) {
                Element rowElement = (Element) rowList.item(j);
                String value = rowElement.getTextContent();
                double floatValue = Double.parseDouble(value);
                matrices[i][j][0] = floatValue;
            }

        }
        return matrices;

    }  
    
}
