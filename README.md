# Gestion de Pharmacie — Sujet 5

Application desktop de gestion de stock pharmaceutique.
**JavaFX 21 + SQLite + Maven**

Université de Lomé — École Polytechnique de Lomé
Développement d'Applications Desktop — Semestre 6, 2026

---

## Aperçu des fonctionnalités

- Authentification sécurisée (Login / Sign Up) avec **BCrypt**
- Tableau de bord avec statistiques (total, valeur du stock, périmés)
- Gestion complète des médicaments (ajout, suppression, visualisation)
- Détection automatique des péremptions avec **coloration des lignes**
- Messages inline dans l'interface (aucune popup indépendante)
- Base de données **SQLite** embarquée et portable

---

## Prérequis

| Logiciel | Version |
|----------|---------|
| JDK | 21 ou supérieur |
| Maven | 3.8+ |
| JavaFX | 21 *(géré automatiquement par Maven)* |

---

## Compilation et exécution

```bash
# Mode développement (lancement direct)
mvn clean javafx:run

# Construction de l'uber-JAR
mvn clean package

# Génération de l'exécutable autonome (Windows)
jpackage --type app-image --input target/ \
  --main-jar PharmarcieManagement-1.0-SNAPSHOT.jar \
  --main-class tg.univlome.epl.pharmarciemanagement.PharmarcieManagement \
  --name PharmarcieManager \
  --module-path "<chemin-vers-javafx-jmods>" \
  --add-modules javafx.controls,javafx.fxml

# Génération de l'installeur (Windows)
jpackage --type exe --input target/ \
  --main-jar PharmarcieManagement-1.0-SNAPSHOT.jar \
  --main-class tg.univlome.epl.pharmarciemanagement.PharmarcieManagement \
  --name PharmarcieManager \
  --module-path "<chemin-vers-javafx-jmods>" \
  --add-modules javafx.controls,javafx.fxml
```

---

## Identifiants de test

| Utilisateur | Mot de passe |
|-------------|--------------|
| `admin` | `admin123` |

*À créer via l'écran d'inscription au premier lancement.*

---

## Architecture du projet

```
PharmarcieManagement/
├── pom.xml                              # Dépendances et plugins Maven
├── .github/workflows/build.yml          # CI/CD GitHub Actions (Windows)
│
├── src/
│   ├── main/java/tg/univlome/epl/pharmarciemanagement/
│   │   ├── PharmarcieManagement.java    # Point d'entrée JavaFX
│   │   ├── controllers/                 # Couche présentation
│   │   │   ├── LoginController.java
│   │   │   ├── SignUpController.java
│   │   │   ├── HomeController.java
│   │   │   └── AddMedicamentController.java
│   │   ├── models/                      # Entités métier
│   │   │   ├── User.java
│   │   │   └── Medicament.java
│   │   ├── dao/                         # Accès aux données (JDBC)
│   │   │   ├── DatabaseConnection.java
│   │   │   ├── UserDAO.java
│   │   │   └── MedicamentDAO.java
│   │   ├── services/                    # Logique métier
│   │   │   ├── AuthService.java
│   │   │   ├── MedicamentService.java
│   │   │   └── ValidationService.java
│   │   ├── exceptions/                  # Exceptions personnalisées
│   │   │   ├── DatabaseException.java
│   │   │   ├── ValidationException.java
│   │   │   └── AuthenticationException.java
│   │   └── utils/                       # Utilitaires
│   │       ├── PasswordHasher.java
│   │       ├── AlertUtils.java
│   │       └── SessionManager.java
│   ├── main/resources/
│   │   ├── fxml/                        # Vues JavaFX
│   │   │   ├── login.fxml
│   │   │   ├── signup.fxml
│   │   │   ├── home.fxml
│   │   │   └── add_medicament.fxml
│   │   ├── css/application.css          # Styles
│   │   └── images/                      # Icônes et logo
│   └── test/java/tg/univlome/epl/pharmarciemanagement/
│       ├── dao/MedicamentDAOTest.java   # 5 tests CRUD
│       └── services/ValidationServiceTest.java  # 20 tests validation
│
├── database/                            # Scripts SQL
│   ├── schema.sql                       # CREATE TABLE
│   └── data.sql                         # Données de test
├── executable/                          # Exécutable autonome (.exe)
├── installer/                           # Installeur (.exe)
├── documentation/                       # Guides + captures d'écran
└── video/                               # Vidéo de démonstration
```

---

## Couches fonctionnelles

| Couche | Rôle | Classes |
|--------|------|---------|
| **Présentation** | Écrans et interactions utilisateur | 4 contrôleurs FXML |
| **Service** | Logique métier et validation | AuthService, MedicamentService, ValidationService |
| **DAO** | Accès base de données | 3 DAOs avec requêtes paramétrées |
| **Modèles** | Entités avec propriétés JavaFX | User, Medicament |
| **Exceptions** | Gestion d'erreurs | DatabaseException, ValidationException, AuthenticationException |

---

## Base de données

- **Moteur :** SQLite (fichier unique `app.db`)
- **Emplacement :** `~/.pharmacie/app.db` (dans le répertoire utilisateur)
- **Portable :** création automatique au premier lancement, ne dépend pas du chemin d'exécution
- **Tables :** `users` (authentification) et `medicaments` (stock)

---

## Tests

```bash
mvn clean test
```

- **30 tests unitaires** (20 validation + 5 DAO)
- Couverture : validation des champs, CRUD médicaments, unicité des codes

---

## CI/CD

Plateforme : **GitHub Actions** (`windows-latest`)
- Compilation et tests à chaque push sur `main`
- Construction de l'uber-JAR
- Génération de l'exécutable autonome (`app-image`) et de l'installeur (`.exe`)
- Utilisation de `jpackage` avec JavaFX jmods

---

## Technologies

| Technologie | Utilisation |
|-------------|-------------|
| Java 21 | Langage et plateforme |
| JavaFX 21 | Interface graphique (FXML, CSS, contrôles) |
| SQLite (sqlite-jdbc) | Base de données embarquée |
| BCrypt (jBCrypt 0.4) | Hachage des mots de passe |
| Maven | Build, dépendances, packaging |
| jpackage | Exécutable et installeur natifs |
| JUnit 4 | Tests unitaires |

---

## Livrables

1. **Code source** + base de données (`database/app.db`, `database/schema.sql`)
2. **Exécutable autonome** (`executable/`)
3. **Installeur** (`installer/`)
4. **Documentation** — guides d'installation et d'utilisation + captures d'écran (`documentation/`)
5. **README** (présent document)
6. **Vidéo de démonstration** (10 min) (`video/`)
