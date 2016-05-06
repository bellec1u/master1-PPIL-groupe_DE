# PPIL-groupe-DE-web

Projet d'application web en groupe utilisant Laravel 5.2.  
Nom de code : CallMeIshmael

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
`composer create-project --prefer-dist laravel/laravel PPIL 5.2.*` PPIL étant un nom de dossier

- Placez-vous dans le dossier créé, puis :  
  ```
  git init
  git add .
  git commit -m "votre message"
  git remote add origin https://github.com/bellec1u/PPIL-groupe-DE-web.git
  git pull origin master
  ```
  Après cala, vous devriez être à jour dans votre copie locale.

- Mettre à jour Laravel : `composer update` depuis la racine du projet.

- Tester le fonctionnement en visitant la page : `http://localhost/{dossier_de_projets}/ppil/public/`