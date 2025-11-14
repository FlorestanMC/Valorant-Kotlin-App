# Projet Final Kotlin ~ Valorant Data App

Bienvenue sur mon application, elle a été développée en Kotlin et utilise le Valorant - API répertoriant la majorité des données à propos du jeu lien [ici](https://valorant-api.com/)

## Pour commencer

Ce projet est a été développé dans le cadre  du cours Android avec M. Singer. Les consignes sont simples : "développer une application mobile en Kotlin utilisant un API quelconque.

/////////////////////////////////
A PRODUIRE ENCORE
/////////////////////////////////

## Fonctionnalités Page d'accueil
### - Header

#### Barre de recherche interactive

/////////////////////////////////

#### Accès Admin sécurisé

/////////////////////////////////

### - Main

/////////////////////////////////

#### Affichage de la liste avec données récupérées via API :

/////////////////////////////////
Dénomination
Quantité
Forme
Image
Gestion de la recherche
/////////////////////////////////

Aucun résultat ? Un message adapté s’affiche + bouton pour réinitialiser la recherche.

Navigation améliorée
Une petite flèche flottante apparaît dès que l’on scrolle suffisamment : cliquez pour remonter en haut de la page !

## Fonctionnalités Page Admin
### - Header

#### Barre de recherche interactive

S’affiche de façon élégante au survol dans le header.
Recherche envoyée au clavier (Entrée) ou en cliquant sur la loupe.
Défilement smooth vers la liste des résultats après une recherche.

#### Accès Accueil

Un bouton dédié pour revenir sur la page accueil

### - Main

Gestion avancée des médicaments

#### Ajout d’un médicament

Ajout facile via une modale dédiée.
Saisie de toutes les informations nécessaires.
Tableau interactif des médicaments

Affichage de tous les médicaments avec leurs informations clés.
Actions rapides sur chaque médicament :
+ : Ajouter 1 à la quantité disponible.
– : Retirer 1 à la quantité disponible.

⚠️ On ne peut pas retirer 1 à la quantité de médicament si celle ci est déjà à 1. On ne peut pas non modifier la quantité de médicaments en dessous de 1

#### Modification d’un médicament

Un bouton Modifier ouvre une modale pré-remplie avec les anciennes informations, pour ajuster facilement le nom, la quantité, la forme, ou l’image.
Suppression d’un médicament

Un bouton Supprimer pour retirer définitivement un médicament de la base.

#### Sécurité & retours
Modales et feedbacks clairs : messages de succès, erreurs ou confirmations.
Accès restreint : seules les personnes authentifiées peuvent utiliser ces actions. ( Via une variable localStorage )

Aucun résultat ? Un message adapté s’affiche + bouton pour réinitialiser la recherche.

Navigation améliorée
Une petite flèche flottante apparaît dès que l’on scrolle suffisamment : cliquez pour remonter en haut de la page !

## Fabriqué avec

* [Kotlint](https://kotlinlang.org/) - Framework Android
* [Ktor](https://ktor.io/) - Outil de gestion API et bien plus encore

## Versions
**Dernière version stable :** 1.0
Liste des versions : [Cliquer pour afficher](/)

## Auteurs
Listez le(s) auteur(s) du projet ici !
* **Florestan Mellé** _alias_ [@FlorestanMC](https://github.com/FlorestanMC)

