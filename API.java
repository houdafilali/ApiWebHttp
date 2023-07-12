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
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("matrices") MultipartFile matrices )  {
        try {

            // Validate the file extension
            String filename = matrices.getOriginalFilename();
            if (filename == null || !filename.toLowerCase().endsWith(".xml")) {
                return ResponseEntity.badRequest().body(null);
            }

            // Save the uploaded file
            File uploadedFile = new File("C:\\Users\\Houda\\IdeaProjects\\ApiWebHttp\\src\\main\\resources\\" + filename);
            Path filePath = uploadedFile.toPath();
            Files.copy(matrices.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            XML filexml = new XML(uploadedFile);
            //extraire les donneees du fichier XML
           double[][][] donnees = filexml.lireFichierXML();
            //Load the pre-trained model
            ClassLoader classLoader = getClass().getClassLoader();
            File modelFile = new File(classLoader.getResource("model.h5").getFile());
            String MODEL_PATH = modelFile.getAbsolutePath();
            // Chargement du modèle
            ComputationGraph model;
            try {
                model = KerasModelImport.importKerasModelAndWeights(MODEL_PATH, false);
            } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
                return ResponseEntity.badRequest().body("Erreur lors du chargement du modèle : " + e.getMessage());
            }


            // Vérification du modèle chargé
            if (model == null) {
                return ResponseEntity.badRequest().body("Le modèle n'a pas été chargé avec succès.");
            }

            // Préparer les tenseurs d'entrée et de sortie
            int numDonnees = donnees.length;
            if (numDonnees == 0) {
                return ResponseEntity.badRequest().body("Les données d'entrée sont vides.");
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







