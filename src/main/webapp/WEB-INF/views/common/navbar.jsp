<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="bg-white border-b border-slate-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        <div class="flex items-center space-x-3">
            <span class="text-xl font-bold text-teal-600">e-Diagnostic Pro</span>
            <span class="text-xs bg-teal-50 text-teal-700 px-2.5 py-1 rounded-full font-medium border border-teal-200">
                ${sessionScope.userRole}
            </span>
        </div>
        <div class="flex items-center space-x-6">
            <span class="text-sm text-slate-600">Bonjour, <strong class="text-slate-800">${sessionScope.userName}</strong></span>
            <a href="${pageContext.request.contextPath}/logout" 
               class="text-sm font-medium text-red-600 hover:text-red-700 transition">Déconnexion</a>
        </div>
    </div>
</header>