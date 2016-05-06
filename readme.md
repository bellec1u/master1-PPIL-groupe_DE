# PPIL-groupe-DE-web

Projet d'application web en groupe utilisant Laravel 5.2.  
Nom de code : _CallMeIshmael_

## Installation

[Référence](https://openclassrooms.com/courses/decouvrez-le-framework-php-laravel-1/installation-et-organisation-1)

- Installer WAMP ou une autre application avec Apache et MySQL.  
  Vérifier que les composants suivants de PHP sont activés :
    - Version >= 5.5.9
    - Extension PDO
    - Extension Mbstring
    - Extension OpenSSL
    - Extension Tokenizer

  De plus, vérifier que le `rewrite_module` d'Apache est activé.

- Commencer par créer un projet vierge :  
  `composer create-project --prefer-dist laravel/laravel ppil 5.2.*` ppil étant un nom de dossier

- Placez-vous dans le dossier créé, puis :  
  ```
  git init
  git add .
  git commit -m "{votre message}"
  git remote add origin https://github.com/bellec1u/PPIL-groupe-DE-web.git
  git pull origin master

  Résoudre les conflits :
  ajouter les fichiers incriminés au bout des deux commandes s'il y en a d'autres
  git checkout --theirs readme.md config/app.php composer.* .gitignore
  git add readme.md config/app.php composer.* .gitignore
  git commit
  ```
  Après cala, vous devriez être à jour dans votre copie locale.

- Mettre à jour Laravel : `composer update` depuis la racine du projet.

- Tester le fonctionnement en visitant la page : `http://localhost/{chemin}/ppil/public/`

### Base de données

Modifier le fichier _.env_ :
```
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=ppil
DB_USERNAME=root
DB_PASSWORD=
```
Si vous avez une base qui s'appelle 'ppil' et une configuration par défaut.

## Laravel

- Laravel peut démarrer le serveur de développement fourni avec PHP avec la commande :  
  `php artisan serve`  
  Page de démarrage accessible avec l'url : `http://localhost:8000`

- Si vous avez une erreur `Class '...' not found`, essayez :  
  `composer dump-autoload`


## Rappels Git

```
git status                    : informations sur l'état courant
git add fichier [fichier ...] : ajouter des fichiers au suivi ou valider les modifications de fichiers déjà suivis
git commit -m '{message}'     : enregistrer les changements de fichiers validés
git commit -am '{message}'    : valider et enregistrer les fichiers déjà suivis ('git add' inutile)

git pull [origin master] : récupérer depuis le dépôt distant et fusionner en local
git push [origin master] : envoyer au dépôt distant les changements enregistrés

Résoudre les conflits :
Corriger les fichiers incriminés à la main puis :
git add fichier-corrigé [fichier-corrigé] : valider les corrections
git commit -m 'conflits corrigés'         : enregistrer la jointure
```

