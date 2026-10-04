<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - e-Diagnostic Pro</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 min-h-screen flex items-center justify-center p-4">

    <div class="max-w-md w-full bg-white rounded-xl shadow-lg border border-slate-200 p-8 space-y-6">
        
        <div class="text-center space-y-2">
            <div class="inline-flex items-center justify-center w-12 h-12 rounded-full bg-teal-100 text-teal-600 mb-2">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                          d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"/>
                </svg>
            </div>
            <h1 class="text-2xl font-bold text-slate-800 tracking-tight">e-Diagnostic Pro</h1>
            <p class="text-sm text-slate-500">Système de Télé-expertise Médicale</p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="p-3 text-sm text-red-700 bg-red-100 border border-red-200 rounded-lg">
                ${errorMessage}
            </div>
        </c:if>

        <c:if test="${param.logout eq 'true'}">
            <div class="p-3 text-sm text-green-700 bg-green-100 border border-green-200 rounded-lg">
                Vous avez été déconnecté avec succès.
            </div>
        </c:if>

        <c:if test="${param.error eq 'unauthorized'}">
            <div class="p-3 text-sm text-amber-700 bg-amber-100 border border-amber-200 rounded-lg">
                Veuillez vous connecter pour accéder à cette ressource.
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="POST" class="space-y-4">
            <div>
                <label for="email" class="block text-sm font-medium text-slate-700 mb-1">Adresse Email</label>
                <input type="email" id="email" name="email" value="${emailValue}" required
                       placeholder="nom@clinique.ma"
                       class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500 transition">
            </div>

            <div>
                <label for="motDePasse" class="block text-sm font-medium text-slate-700 mb-1">Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" required
                       placeholder="••••••••"
                       class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500 transition">
            </div>

            <button type="submit" 
                    class="w-full py-2.5 px-4 bg-teal-600 hover:bg-teal-700 text-white font-medium rounded-lg shadow-sm hover:shadow transition duration-150 ease-in-out">
                Se connecter
            </button>
        </form>

        <div class="border-t border-slate-100 pt-4 text-xs text-slate-400 text-center">
            Accès sécurisé réservé au personnel médical autorisé.
        </div>
    </div>

</body>
</html>