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

    private final File FichierXML;

    public XML(final File file) {
        FichierXML= file;
    }

    public double[][][] lireFichierXML() throws ParserConfigurationException, SAXException, IOException {

        // Charger le fichier XML
        Document document = parseXmlFile(FichierXML);
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
                value = value.replaceAll("[^\\d.]", ""); // Supprimer tous les caractères non numériques sauf le point décimal
                double floatValue = Double.parseDouble(value);
                matrices[i][j][0] = floatValue;
            }

        }
        return matrices;

    }
    private Document parseXmlFile(File file) throws ParserConfigurationException, IOException, SAXException {
        // Creer un objet DocumentBuilderFactory
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        // Creer un objet DocumentBuilder
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        // Charger le fichier XML
        return dBuilder.parse(file);
    }
}
