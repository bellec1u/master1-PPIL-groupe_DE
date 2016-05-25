<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
</head>
<body>
    <div>
        <p>Merci d'avoir créé un compte sur notre site !</p>
        <p>Vérifiez votre adresse mail en suivant le lien :</p>
        <p><a href="{{ url('user/verify/' . $token) }}">{{ url('user/verify/' . $token) }}</a></p>
    </div>
</body>
</html>