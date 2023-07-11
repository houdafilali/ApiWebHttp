/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetird;


import com.google.gson.Gson;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import java.io.File;

@RestController
public class ReseauNeurone {
    private static final String cheminFichier = "C:\\Users\\Houda\\Desktop\\ProjetIRD\\model.h5";

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("file") File file)  {
        try {
            // Extraire les données du fichier XML
            XML filexml = new XML(file);
            double[][][] donnees = filexml.lireFichierXML();

            // Charger le modèle à partir du fichier HDF5 en utilisant la classe KerasModelImport de Deeplearning4j
            ComputationGraph model = KerasModelImport.importKerasModelAndWeights(cheminFichier);

            // Préparer les tensors d'entrée et de sortie
            INDArray donneeEntree = Nd4j.create(donnees);
            INDArray donneeSortie = model.outputSingle(donneeEntree);
            System.out.println(donneeSortie);

            // Convertir les prédictions en JSON
            String jsonResult = convertToJson(donneeSortie);

            return ResponseEntity.ok(jsonResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    private String convertToJson(INDArray predictions) {
        // Convertir les prédictions en JSON en utilisant une bibliothèque JSON (par exemple, Gson)
        Gson gson = new Gson();
        return gson.toJson(predictions.toDoubleMatrix());
    }
}

