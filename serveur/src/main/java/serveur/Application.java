package serveur;


import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) throws InterruptedException {

       // SpringApplication.run(Application.class, args);

        ConfigurableApplicationContext ctx= SpringApplication.run(Application.class, args);
        int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                // no errors
                return 0;
            }
        });

        Thread.sleep(120000);
        System.exit(exitCode);
    }


}


