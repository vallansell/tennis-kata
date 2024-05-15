# Tennis Kata

## Introduction

Le but de ce projet est d'implémenter un calculateur de score de tennis.

Pour rappel le systeme de score doit respecter les regles suivantes :
* Chaque joueur commence un jeu avec 0 point.
* Si le joueur gagne la 1ère balle, il aura 15 points. 2ème balle gagnée : 30 points. 3ème balle gagnée : 40 points.
* Si un joueur a 40 points et gagne la balle, il gagne le jeu, cependant il y a des règles spéciales : 
  * Si les deux joueurs ont 40 points, les joueurs sont en "égalité" (deuce).
  * Si le jeu est en égalité, le gagnant de la balle aura l'avantage.
  * Si le joueur avec l'avantage gagne la balle, il gagne le jeu.
  * Si le joueur sans avantage gagne la balle, ils retournent à l'égalité (deuce).


## Choix techniques

Afin d'isoler le code metier j'ai choisi **une architecture hexagonale.** 
Elle se compose ici de deux packages (la partie infrastructure n'est pas necessaire etant donné que l'application n'utilise pas de database) : 
* ```api``` qui sert à définir et exposer les points d'entrée de l'application. Ces points d'entrée sont ici des contrôleurs RESTful qui permettent aux clients de communiquer avec le système
* ```domain``` qui contient toute la logique metier comprenant les uses cases (ici AddPoint), les modeles du domaine ainsi que les erreurs metiers qui peuvent etre engendré


J'ai utilisé **la programmation fonctionnelle** dans ce projet pour plusieurs raisons :

* La gestion des Erreurs est plus expressive et concise. ```Either``` permet de représenter une valeur qui peut être soit un succès (Right) soit une erreur (Left), ce qui améliore la clarté du code et réduit la nécessité d'utiliser des exceptions pour des erreurs metiers.

* Le code fonctionnel est plus expressif et concis, améliorant ainsi sa lisibilité et sa maintenabilité en décrivant les transformations de données de manière déclarative et intuitive.

### Strategie de test

Pour tester mon code et s'assurer que le use case ```AddPoint``` reponde aux critères d'acceptation définis plus haut j'ai utilisé
du **behavior driven development (BDD)**. Les scenarios du use case ainsi que tests correspondant sont disponible dans le package ```acceptance```

Pour completer ces tests, j'ai ajouté des tests d'integration permettant de tester le endpoit du usecase. Ces tests sont disponible dans le package ```integration```.


## Fonctionnalités

- Suivi des scores des joueurs
- Ajout de points aux joueurs
- Gestion des erreurs pour les actions invalides
- Gestion des états de fin de match

## Installation

### Prérequis

- Java 21 & Gradle 

## Utilisation

Il existe deux joueurs nommé respectivement A et B. 
Pour ajouter un point au joueurs A il suffit d'envoyer une requete post à l'url suivante ```/api/v1/game/point```
avec un body contenant
```
{
"player": "A"
}
```

* * *

## Introduction

The goal of this project is to implement a tennis score calculator.

As a reminder, the scoring system must adhere to the following rules:
* Each player starts a game with 0 points.
* If the player wins the 1st point, they will have 15 points. 2nd point won: 30 points. 3rd point won: 40 points
* If a player has 40 points and wins the point, they win the game, however, there are special rules:
  * If both players have 40 points, the players are in "deuce".
  * If the game is in deuce, the winner of the point will have the advantage.
  * If the player with the advantage wins the point, they win the game.
  * If the player without the advantage wins the point, they return to deuce.


## Technical Choices

To isolate the business code, I chose a hexagonal architecture.
It consists of two packages here (the infrastructure part is not necessary since the application does not use a database):

api which serves to define and expose the entry points of the application. These entry points are RESTful controllers that allow clients to communicate with the system.
domain which contains all the business logic including the use cases (here AddPoint), the domain models, and the business errors that may arise.
I used functional programming in this project for several reasons:

Error handling is more expressive and concise. Either allows representing a value that can be either a success (Right) or an error (Left), which improves code clarity and reduces the need to use exceptions for business errors.

Functional code is more expressive and concise, enhancing its readability and maintainability by describing data transformations in a declarative and intuitive manner.

### Test Strategy

To test my code and ensure that the AddPoint use case meets the acceptance criteria defined above, I used behavior-driven development (BDD). The scenarios for the use case and corresponding tests are available in the acceptance package.

To complement these tests, I added integration tests to test the use case endpoint. These tests are available in the integration package.

## Features

* Player score tracking
* Adding points to players
* Error handling for invalid actions
* End game state management

## Installation

### Prerequisites

- Java 21 & Gradle

## Usage

There are two players named A and B respectively.
To add a point to player A, simply send a post request to the following URL /api/v1/game/point
with a body containing
```
{
"player": "A"
}
```




