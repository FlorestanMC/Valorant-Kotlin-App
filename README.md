# Valorant Kotlin App

Bienvenue sur mon application, elle a été développée en Kotlin et utilise le Valorant - API répertoriant la majorité des données à propos du jeu lien [ici](https://valorant-api.com/)

## Fonctionnalités

*   **Consultation des cartes présentent dans le jeu :** Affiche la liste complète des cartes de Valorant.
*   **Détail d'une carte :** Pour chaque carte, l'application présente une vue détaillée qui inclut :
    *   L'image de la carte (`displayIcon`).
    *   L'affichage des "callouts" (noms des zones) positionnés directement sur l'image.
*   **Jeu Memory** On retrouve en premier jeu un petit memory avec pour cartes les personnages du jeu. Le nombre de coup et le temps passé pour le résoudre est comptabilisé et j'ai également mis en place une petite animation de victoire.
*   **Espace Statistiques Memory** Une fois une partie effectuée on retrouve sur cette pages l'historique des parties, le temps passé en moyenne, nombre de coup et l'agent tombant le plus lors des parties jouées.
*   **Jeu ouverture de caisse** A l'instar du jeu Counter Strike Global Offensive, Valorant contient énormément de contenu visuel additionnel aussi appelés skins, j'ai donc mis en place une sorte de mini lotterie faisant gagner à l'utilisateur un "skin" aléatoirement. Comme les statistiques du mémory, ces données sont conservées dans la bdd locale de l'application.
*   **Espace Inventaire Skins** Tous les skins gagnés lors du jeu d'ouverture de caisse sont conservés dans cette page. On peut également les trier par type d'arme ou par collection de skins.



## Architecture

L'application suit une architecture **MVVM (Model-View-ViewModel)**, qui est la norme recommandée pour le développement Android moderne. Cette architecture sépare clairement les responsabilités :

*   **View (UI Layer) :** Les écrans (`Composable functions` comme `MapDetailScreen`), construits avec **Jetpack Compose**. Ils sont responsables de l'affichage des données et de la capture des interactions utilisateur.
*   **ViewModel (`MainViewModel`) :** Sert de pont entre la couche de données et l'interface utilisateur. Il expose l'état de l'UI (via `StateFlow`) et contient la logique de présentation, sans avoir connaissance des composants UI.
*   **Model (Data Layer) :** Géré par le `ValRepository`, il est responsable de la récupération des données. Il agit comme une source de vérité unique, qu'il aille chercher les données depuis une source distante (API) ou locale (base de données).

## Technologies et Bibliothèques Principales

Ce projet utilise technologies et bibliothèques suivantes :

*   **Langage :** [Kotlin](https://kotlinlang.org/) (langage principal, avec une approche "Kotlin-first").
*   **UI :** [Jetpack Compose](https://developer.android.com/jetpack/compose) pour la construction de l'interface utilisateur de manière déclarative et moderne.
*   **Design System :** [Material 3](https://m3.material.io/) pour les composants graphiques et le respect des dernières lignes directrices de Google.
*   **Navigation :** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) pour gérer la navigation entre les écrans de l'application.
*   **Asynchronisme :** [Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html) pour gérer les opérations en arrière-plan de manière simple et efficace (appels réseau, accès à la base de données).
*   **Gestion d'état :** [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) pour conserver l'état de l'UI lors des changements de configuration.
*   **Réseau :** [Ktor](https://ktor.io/) comme client HTTP moderne et flexible pour effectuer les appels à l'API externe de Valorant.
*   **Sérialisation :** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) pour convertir les objets Kotlin en JSON et inversement.
*   **Base de données locale :** [Room](https://developer.android.com/training/data-storage/room) pour la persistance des données et la mise en cache, afin d'offrir une expérience hors-ligne et de limiter les appels réseau.
*   **Chargement d'images :** [Coil](https://coil-kt.github.io/coil/) pour charger et afficher les images depuis une URL de manière asynchrone et performante.
*   **Effets visuels :** [Konfetti](https://github.com/DanielMartinus/konfetti) pour l'ajout d'animations de confettis.

