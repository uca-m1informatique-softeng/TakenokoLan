package joueur;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  /*  private final Environment environment;

    public  Application(Environment environment) {
        this.environment = environment;
    }*/

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);

       /* ConfigurableApplicationContext ctx= SpringApplication.run(Application.class, args);
        int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                // no errors
                return 0;
            }
        });

        /*Thread.sleep(600000);
        System.exit(exitCode);*/
    }



}