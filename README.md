# Merch time !

Le [cours](https://www.i3s.unice.fr/master-info/assets/s1/graphes-prog-dyn/merchTime.pdf) sur le problème.

### Planning :
**Séance** : Mercredi 25 Novembre à 17h  
**Rendu** : Vendredi 18 Décembre à 23:59  
**Debrief** : Mercredi 9 Décembre à 17h (à changer?) 

### Notation du projet :
- 50% Collectif
- 50% individuel 

Référencez les commits avec les # des issues, indiquez qui a travaillé sur quoi dans les commentaires.

### Choix du solveur :
Le choix du solveur à être utiliser pour la solution doit être définit dans le fichier [application.properties](src/main/resources/application.properties). \
Exemple: Pour exécuter le projet avec le solveur D on devra le définir comme suite : `solveur=D`


### Niveaux des logs :
Les niveaux des logs sont définis dans le fichier [logback.xml](src/main/resources/logback.xml). \
Par exemple le niveau de log qui sera afficher sur l'écran est dans notre cas **INFO** : \
`<root level="INFO">
    <appender-ref ref="stdout"/>
</root>`\
et le niveau de log qui sera sauvegarder dans le fichier [merchlog.log](logs/merchlog.log) est dans notre cas **DEBUG** : \
`<root level="DEBUG">
    <appender-ref ref="file_out"/>
</root>`
