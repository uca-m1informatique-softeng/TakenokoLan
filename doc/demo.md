    ****** DEMO 1 Chloé *****
    
Branche  "PlusieursPartiesDocker":

On veut montrer le lancement de 5 parties dans docker.
    Sur travis : on affiche les connexions et créations des containers, images, etc.
    En local : le log du docker-compose up

On lance 5 images joueurs et 1 image serveur.

=> connexion de "joueur" sur un port aléatoire (on récupère le port sur lequel tomcat s'est initialisé)
=> connexion de "serveur" sur port 8080
    
    ...
    @Autowired
    Environment environment;
    String getPort(){
        return environment.getProperty("local.server.port");
    }
    ...
     int[] tab = connect("172.18.0.2", "8080", InetAddress.getLocalHost().getHostAddress(), getPort());//pour travis
    ...

**

    ****** DEMO 2 Yacine *******
     
Test de charge : 

Nombre de connexions supportées par le serveur.
Utilisation de gatling et module scala.
Résultats générés dans une archive.


**
     
    ****** DEMO 3 Alexandre *****
    
Branche "testIntegrationJoueurServeurTravis"

Build #222 - https://travis-ci.com/uca-m1informatique-softeng/TakenokoLan/builds/112346158

On veut montrer la communication entre un joueur et le serveur sur Travis.

- On créé un test d'intégration en nous basant sur la fonction connect() du joueur.
Elle nous permet de récupérer un id de partie compris entre 0 et 4999 ainsi qu'un id de joueur
qui doit être égal à 0.

        ...
        assertTrue("Error, game numero beyond 5000.",0 <= tab[0] & tab[0] < 5000);
        assertEquals(0,tab[1]);
        ...
        
- Sur Travis, on lance dans un premier temps l'image docker du serveur puis on exécute notre test d'intégration
en nous appuyant sur le plugin Failsafe (ce sera le seul test qui sera alors affiché).
    
  
**
  
    ****** DEMO 4 Pierre *******

On veut montrer que les tests d'intégrations couvrent les échanges joueur/serveur (une partie) avec des tests cucumber.

-On exécute les tests dans travis qui confirme que tous nos scénarios sont réalisables.
- Les routes sont testées dans les scénarios.

        Scenario: le client appelle /DeplacerJardinier
        When le client appelle /DeplacerJardinier
        Then si c'est possible le jardinier est déplacé sur le terrain

        ...

        @When("^le client appelle /DeplacerJardinier")
            public void deplacementJardinier() {
                poserParcelle(); // a cause du Background on doit reposer la parcelle
                HttpEntity<Coordonnees> request = new HttpEntity<>(p.getCoord());
                response = template.exchange("http://localhost:8080/0/0/DeplacerJardinier", HttpMethod.POST, request,
                        String.class);
            }

        @Then("^si c'est possible le jardinier est déplacé sur le terrain")
            public void deplacementJardinierEffectue() {
                ResponseEntity<Coordonnees> temp = template.exchange(
                        "http://localhost:8080/0/JardinierGetCoordonnees",
                        HttpMethod.GET,
                        null,
                        Coordonnees.class);
                Assert.assertEquals(p.getCoord(), temp.getBody());
                assertEquals("done", response.getBody());
            }

        ...
