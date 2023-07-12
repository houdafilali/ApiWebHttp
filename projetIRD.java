package projetird;


import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;



/**
 *
 * @author Houda
 */
@SpringBootApplication
public class projetIRD {

    public static void main(String[] args) {
       SpringApplication.run(projetIRD.class, args);
    }
      @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Augmentez la limite de taille maximale ici (en octets)
        factory.setMaxFileSize(DataSize.ofBytes(20 * 1024 * 1024));
        factory.setMaxRequestSize(DataSize.ofBytes(20 * 1024 * 1024));
        return factory.createMultipartConfig();
    }

}

}
