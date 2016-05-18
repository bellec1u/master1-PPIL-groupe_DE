<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
</head>
<body>
    <div>
        Merci d'avoir créé un compte sur notre site. <br>
        Vérifiez votre adresse mail en suivant le lien :<br>
        {{ url('user/verify/' . $token) }}<br/>
    </div>

</body>
</html>