# PharmarcieManagement

Application de gestion de stock de médicaments pour pharmacie.

Développée en **JavaFX 21 + SQLite** avec Maven.

## Prérequis

- **JDK 21** ou supérieur
- **Maven 3.8+**
- **JavaFX 21** (non bundlé avec JDK 21 — géré automatiquement par Maven)

## Compilation et exécution

```bash
# Développement (lance l'application directement)
mvn clean javafx:run

# Construction du JAR exécutable
mvn clean package
```

Le JAR se trouve dans `target/PharmarcieManagement-1.0-SNAPSHOT.jar`.

## Identifiants de test

| Utilisateur | Mot de passe |
|---|---|
| `admin` | `admin123` |

*(À créer via l'écran Sign Up au premier lancement, ou présent dans la base livrée)*

## Structure du projet

```
PharmarcieManagement/
├── src/main/java/tg/univlome/epl/pharmarciemanagement/
│   ├── PharmarcieManagement.java       # Point d'entrée
│   ├── controllers/                     # Couche présentation (FXML)
│   ├── models/                          # Entités métier
│   ├── dao/                             # Accès aux données (JDBC)
│   ├── services/                        # Logique métier
│   ├── utils/                           # Utilitaires
│   └── exceptions/                      # Exceptions personnalisées
├── src/main/resources/
│   ├── fxml/                            # Vues JavaFX
│   ├── css/                             # Styles
│   └── images/                          # Icônes et logo
├── database/                            # Base SQLite et scripts
├── executable/                          # Exécutable autonome
├── installer/                           # Installeur
├── documentation/                       # Guides + captures
└── video/                               # Vidéo de démonstration
```

## Livrables

1. Code source + base de données
2. Installeur `.exe`
3. Exécutable `.exe`
4. Documentation (guides d'installation et d'utilisation)
5. README (présent document)
6. Vidéo de démonstration (10 min)

## Technologies

- **Java 21** avec **JavaFX 21**
- **SQLite** (via sqlite-jdbc)
- **BCrypt** (chiffrement mots de passe)
- **Maven** (build, dépendances, packaging)
- **jpackage** (génération exécutable/installeur)
