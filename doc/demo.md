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
     






**
     
    ****** DEMO 3 Alexandre *****
    
Branche "testIntegrationJoueurServeurTravis"

On veut montrer la communication entre un joueur et le serveur sur Travis.

- On créé un test d'intégration en nous basant sur la fonction connect() du joueur.
Elle nous permet de récupérer un id de partie compris entre 0 et 4999 ainsi qu'un id de joueur
qui doit être égal à 0.
- Sur Travis, on lance d'abord le serveur avant de pouvoir exécuter le test d'intégration
(ce sera le seul test qui sera alors affiché).
   
   
    
  
**
  
    ****** DEMO 4 Pierre *******