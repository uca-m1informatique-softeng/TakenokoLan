    ****** DEMO 1 Chloé *****
    
Branche  "PlusieursPartiesDocker":

On veut montrer le lancement de 10 parties de 2 joueurs chacune dans docker.
    Sur travis : on affiche les connexions et créations des containers, images, etc.
    En local : le log du docker-compose up

On lance 10 images joueurs et 1 image serveur.

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
    
   
   
   
    
  
**
  
    ****** DEMO 4 Pierre *******