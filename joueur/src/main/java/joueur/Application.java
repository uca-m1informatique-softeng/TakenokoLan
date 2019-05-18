package joueur;


import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        //SpringApplication.run(Application.class, args);

        ConfigurableApplicationContext ctx= SpringApplication.run(Application.class, args);
        int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                // no errors
                return 0;
            }
        });

        Thread.sleep(600000);
        System.exit(exitCode);
    }



}