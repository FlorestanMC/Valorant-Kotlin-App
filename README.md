# Projet Final Kotlin ~ Valorant Data App

Bienvenue sur mon application, elle a été développée en Kotlin et utilise le Valorant - API répertoriant la majorité des données à propos du jeu lien [ici](https://valorant-api.com/)

📘 Documentation de l’application

### 🏠 Écran Home (Accueil)
L’écran d’accueil propose deux carrousels distincts permettant une navigation rapide et intuitive :
1. Carrousel des personnages

Contient tous les personnages du jeu.  
Défilement horizontal fluide.  
Permet d’accéder rapidement aux informations visuelles concernant chaque agent.

2. Carrousel des maps

Contient toutes les cartes disponibles.  
Chaque carte est cliquable.  
Une fois sélectionnée, l’utilisateur peut zoomer sur le plan.
Le zoom permet de voir le nom exact de chaque zone de la carte (callouts).


### 🎮 Écran Jeu (Memory)
Cet écran propose un jeu de memory basé sur les personnages.
Fonctionnalités principales

Cartes du memory représentant les personnages du jeu.
Compteur de coups : indique le nombre d’essais effectués.
Bouton “Recommencer” : permet de réinitialiser la partie instantanément.
Animation de fin de partie, réalisée via des plugins (ex : Konfetti).
Gestion fluide de la logique du jeu : découverte des cartes, paires, redémarrage.


### 📊 Écran Stats (Statistiques)
Cet écran affiche toutes les statistiques du joueur liées à ses parties.
Données affichées

Nombre total de coups joués sur toutes les parties.
Temps total passé sur le jeu.
Agent le plus récurrent sur l’ensemble des parties.
Historique complet des parties (score, date, coups…).

Caractéristiques techniques

Les données ne sont jamais réinitialisées, même après fermeture de l’application.
Elles sont stockées via le local storage.


### 🎰 Écran Jeu / Casino
Un second type de jeu est disponible : un système d’ouverture de boîtes (lootboxes) pour obtenir de nouveaux skins.
Fonctionnement

L’utilisateur ouvre des boîtes.
Chaque boîte donne un nouveau skin de manière aléatoire.
Le skin obtenu est automatiquement enregistré dans l’espace Collection.


### 🗂️ Écran Collection
Cet écran regroupe tous les skins d’armes obtenus via l’écran Casino.
Fonctionnalités

Affichage de tous les skins débloqués.
Possibilité de trier :
par arme,
par collection.


Interface claire et organisée pour parcourir les skins obtenus.


### ✨ Animations & Plugins
L’application propose plusieurs animations pour enrichir l’expérience utilisateur :

Utilisation de plugins comme Konfetti pour :
les victoires en memory,
l’ouverture de boîtes dans le casino,
d’autres moments clés de l’application.

## Fabriqué avec

* [Kotlint](https://kotlinlang.org/) - Framework Android
* [Ktor](https://ktor.io/) -  Outil multiplateforme pour la conception de services web

## Versions
**Dernière version stable :** 1.0
Liste des versions : [Cliquer pour afficher](/)

## Auteurs
Listez le(s) auteur(s) du projet ici !
* **Florestan Mellé** _alias_ [@FlorestanMC](https://github.com/FlorestanMC)

