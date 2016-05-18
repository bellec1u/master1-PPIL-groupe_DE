# PPIL-groupe-DE-web

Contributeurs :  
    BELLEC Léopold    
    DAUZVARDIS Juozas  
    MAZROU Abdelghani  
    JUNGES Pierre-Marie  
    BOUQUIN Laurent  
    BEN TOUNES Samy  
    DEMIR Yasar  
    CHABAUX Paul  
    GREINER Billy  
    AJDARPASIC Nihad  

Projet d'application web en groupe utilisant Laravel 5.2.  
Nom de code : _CallMeIshmael_

## Coding Style

Toute l'application sera codée en **anglais** (noms de variables, classes, méthodes, commentaires).  
L'indentation est de **4 espaces** (pas de tabulations) comme suit :  
```php
class Foo extends Bar implements FooInterface
{
    public function sampleFunction($a, $b = null)
    {
        if ($a === $b) {
            bar();
        }
        ...
```
[Référence PSR](http://www.php-fig.org/psr/psr-2/) 


## Installation

### Web

[Référence](https://openclassrooms.com/courses/decouvrez-le-framework-php-laravel-1/installation-et-organisation-1)

- Installer WAMP ou une autre application avec Apache et MySQL.  
  Vérifier que les composants suivants de PHP sont activés :
    - Version >= 5.5.9
    - Extension PDO
    - Extension Mbstring
    - Extension OpenSSL
    - Extension Tokenizer

  De plus, vérifier que le `rewrite_module` d'Apache est activé.

- Commencer par créer un projet vierge après avoir installé [Composer](https://getcomposer.org/download/) :  
  `composer create-project --prefer-dist laravel/laravel ppil 5.2.*` ppil étant un nom de dossier

- Placez-vous dans le dossier créé, puis :  
  ```
  git init
  git add .
  git commit -m "{votre message}"
  git remote add origin https://github.com/bellec1u/PPIL-groupe-DE-web.git
  git pull origin master
  git branch --set-upstream-to=origin/master master

  Résoudre les conflits (dans ce cas là UNIQUEMENT) :
  git checkout --theirs .
  git add .
  git commit
  ```
  Après cala, vous devriez être à jour dans votre copie locale.

- Mettre à jour Laravel : `composer update` depuis la racine du projet.

- Tester le fonctionnement en visitant la page : `http://localhost/{chemin}/ppil/public/`

### Mobile

```
git clone -b mobile https://github.com/bellec1u/PPIL-groupe-DE-web.git ppil_mobile
```

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
  En revanche, la base de données ne sera pas accessible.

- Laravel vous permet d'interagir avec votre application via la console lancée par la commande :  
  `php artisan tinker` fonctionne comme une console Python

- Si vous avez une erreur `Class '...' not found`, essayez :  
  `composer dump-autoload`

- Nettoyer le cache de la config Laravel :  
  `php artisan config:clear`

## Rappels Git

Quand j'utilise Git pour la première fois sans lire la doc.  
![Gif git](http://ljdchost.com/14u7r2W.gif)

```
Configuration :
git config --list                    : afficher les paramètres courants
git config user.name "{votre_nom}"   
git config user.email "{votre_mail}" : mail doit être le même que pour github si vous voulez que github calcule vos statistiques
                                       vous pouvez passer l'option --global pour que ces paramètres soient les mêmes pour tous les dépôts
git config --global core.editor nano : idiquer l'éditeur que vous voulez utiliser


git status                    : informations sur l'état courant
git add fichier [fichier ..]  : ajoute des fichiers au suivi ou valide les modifications de fichiers déjà suivis
git commit -m '{message}'     : enregistre les changements de fichiers validés
git commit -am '{message}'    : valide et enregistrer les fichiers déjà suivis ('git add' inutile pour valider les changements)

git pull [origin master] : récupére depuis le dépôt distant et fusionne en local
git push [origin master] : envoye au dépôt distant les changements enregistrés


Résoudre les conflits :
Corriger les fichiers incriminés à la main puis :
git add fichier-corrigé [fichier-corrigé] : valide les corrections
git commit -m 'conflits corrigés'         : enregistre la fusion

    A utiliser avec précautions :
git checkout --theirs fichier [fichier ..] : récupère la version des fichiers du dépôt distant et écrase vos modifications
git checkout --ours fichier [fichier ..]   : applique votre version des fichiers et écrase la version du dépôt distant
git add fichier [fichier ..]               : valide les modifications
git commit                                 : enregistre la fusion
```

