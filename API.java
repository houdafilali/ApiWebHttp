/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetird;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Houda
 */
@RestController
public class API {
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {
        try {
            // Vérifiez si le fichier est vide
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier est vide.");
            }
             String destinationPath = " C:/Users/Houda/Desktop/ProjetIRD/src/fichier.xml";
            
        
        // Enregistrez le fichier XML sur le disque
        byte[] fileBytes = file.getBytes();
        Files.write(Paths.get(destinationPath), fileBytes);
        XML xml=new XML(destinationPath);
        xml.lireFichier();
            // Répondre avec un message de succès
            return ResponseEntity.ok("Fichier importé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de l'importation du fichier.");
        }
    }
    
}
