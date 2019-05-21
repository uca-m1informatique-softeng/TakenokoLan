# TakenokoLan

Lancement du test :

1ere console :

        mvn clean install
        cd serveur/target
        java -jar serveur-1.0.jar
        
2eme console :

        cd ..
        mvn failsafe:integration-test
        
        
Test en local (job trop long sur travis) :
![routes](doc/Test1000Parties.png)

        
