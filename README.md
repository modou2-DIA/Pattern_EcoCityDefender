🏙️ Eco City Defender

Sauvez la ville de la pollution, un Design Pattern à la fois.

📖 À propos

Eco City Defender est un jeu de gestion stratégique type "Tower Defense" où le joueur incarne un maire écologique. L'objectif est de gérer la pollution d'une ville en constante expansion en construisant des infrastructures vertes et en améliorant les bâtiments existants.

Ce projet a été conçu avant tout comme une démonstration architecturale pour illustrer l'implémentation concrète de Design Patterns classiques en Java.

🏗️ Architecture & Design Patterns

Le cœur du projet repose sur une architecture découplée, séparant la logique métier (model) de l'interface utilisateur (view).

Patterns Implémentés

Pattern

Rôle dans le projet

🏺 Composite

Gère la structure hiérarchique de la ville (District → Building → Leaf). Permet de traiter un bâtiment unique ou un quartier entier de la même manière (calcul pollution, rendu).

🚦 State

Gère le cycle de vie du jeu (Menu, Playing, Pause, GameOver, Victory) sans enchaînement de if/else complexes.

👀 Observer

Lie le modèle (GameManager) à l'interface (HUD). L'interface réagit automatiquement aux changements de score et de pollution sans "polling".

🎁 Decorator

Permet d'ajouter dynamiquement des fonctionnalités aux bâtiments (ex: SolarPanelDecorator ajoute un panneau solaire visuel et réduit la pollution) sans modifier la classe Building.

🏭 Factory

Centralise la création complexe des entités (Building, PollutionSource) et garantit l'intégrité des objets créés.

👑 Singleton

Assure l'unicité des gestionnaires critiques : GameManager (état global) et GameLogger (traçabilité).

Diagramme de Classes

(Voir le fichier Architecture_EcoCity.puml ou l'image générée dans le dossier /docs)

🚀 Installation et Lancement

Prérequis

JDK 17 ou supérieur.

Maven 3.8+ (ou votre IDE préféré avec support Maven).

Comment lancer le jeu

Cloner le dépôt :

git clone https://github.com/modou2-DIA/Pattern_EcoCityDefender.git


Accéder au dossier :

cd EcoCityDefender


Compiler et lancer (via Maven) :

mvn javafx:run


🎮 Comment Jouer ?

Démarrer : Lancez la simulation depuis le Menu Principal.

Observer : Surveillez la barre de santé de la ville (HUD).

Agir :

Les quartiers résidentiels et industriels génèrent de la pollution.

Construisez des parcs pour absorber le CO2.

Cliquez sur les bâtiments pour les améliorer (Panneaux Solaires, Filtres).

Gagner : Maintenez la pollution sous 50% pendant 30 secondes.

📂 Structure du Projet

src/main/java/com/ecocity
├── model
│   ├── composite   # Structure de la ville (Composite)
│   ├── decorator   # Améliorations (Decorator)
│   ├── factory     # Création d'entités (Factory)
│   └── state       # États du jeu (State)
├── view            # Interface JavaFX & Observateurs
├── utils           # Logger & Outils
├── GameManager.java
└── Main.java


📝 Auteur

Projet réalisé dans le cadre du cours de Génie Logiciel / Design Patterns.

[Modou DIA && Oulimata SALL && Marwa HADHRAOUI] - Développeur Principal

Ce projet respecte les principes SOLID et démontre une séparation stricte entre la logique et l'affichage.
