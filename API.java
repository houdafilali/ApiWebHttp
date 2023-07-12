package projetird;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author Houda
 */
@RestController
public class API{
    private static final String cheminFichier = "model.h5";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("file") File file)  {
        try {

            XML filexml = new XML(file);
            //extraire les donneees du fichier XML
            double[][][] donnees = filexml.lireFichierXML();

            // Load the pre-trained model
            ClassLoader classLoader = getClass().getClassLoader();
            File modelFile = new File(classLoader.getResource(cheminFichier).getFile());
            String MODEL_PATH = modelFile.getAbsolutePath();

            // Vérification de la présence du fichier et du chargement du modèle
            if (!new File(MODEL_PATH ).exists()) {
                System.out.println("Le fichier modèle n'a pas été trouvé : " + MODEL_PATH);
                return null; // ou lancez une exception appropriée
            }

            // Chargement du modèle
            ComputationGraph model;
            try {
                model = KerasModelImport.importKerasModelAndWeights(MODEL_PATH,false);
            } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
                System.out.println("Erreur lors du chargement du modèle : " + e.getMessage());
                e.printStackTrace();
                return null; // ou lancez une exception appropriée
            }

            // Vérification du modèle chargé
            if (model == null) {
                System.out.println("Le modèle n'a pas été chargé avec succès.");
                return null; // ou lancez une exception appropriée
            }

            // Préparer les tenseurs d'entrée et de sortie
            int numDonnees = donnees.length;
            if (numDonnees == 0) {
                System.out.println("Les données d'entrée sont vides.");
                return null;
            }
            INDArray donneesEntrees = Nd4j.create(donnees);

            // Make predictions
            INDArray[] donneesSorties = model.output(donneesEntrees);

            // Convert the predictions to a 2D float array
            double[][] predictions = new double[donneesSorties.length][];
            for (int i = 0; i < donneesSorties.length; i++) {
                INDArray output = donneesSorties[i];
                predictions[i] = output.toDoubleVector();
            }

            // Convert predictions to JSON
            String json = objectMapper.writeValueAsString(predictions);

            // Return the predictions as JSON
            return ResponseEntity.ok().body(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }


}







