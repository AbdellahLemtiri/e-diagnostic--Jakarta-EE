<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-slate-50">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} | e-Diagnostic Pro</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        brand: {
                            50: '#ecfdf5',
                            100: '#d1fae5',
                            500: '#10b981',
                            600: '#059669',
                            700: '#047857',
                            900: '#064e3b',
                        }
                    }
                }
            }
        }
    </script>
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Plus Jakarta Sans', sans-serif; }
    </style>
</head>
<body class="h-full flex overflow-hidden text-slate-800 antialiased">

    <!-- SIDEBAR MEDICALE -->
    <aside class="w-64 bg-slate-900 border-r border-slate-800 flex flex-col justify-between flex-shrink-0 z-20">
        <div>
            <!-- Logo / Brand -->
            <div class="h-16 flex items-center px-6 border-b border-slate-800 gap-3">
                <div class="w-9 h-9 rounded-lg bg-emerald-500/10 border border-emerald-500/20 flex items-center justify-center text-emerald-400 font-bold text-lg">
                    +
                </div>
                <div>
                    <h1 class="text-sm font-bold text-white tracking-wide uppercase leading-tight">e-Diagnostic</h1>
                    <span class="text-[10px] text-emerald-400 font-semibold tracking-wider">HOSPITAL OS</span>
                </div>
            </div>

            <!-- Role Badge -->
            <div class="px-6 py-4">
                <div class="bg-slate-800/60 border border-slate-700/60 rounded-lg p-3 flex items-center gap-3">
                    <div class="w-8 h-8 rounded-full bg-emerald-500/20 text-emerald-400 flex items-center justify-center text-xs font-bold">
                        IN
                    </div>
                    <div class="overflow-hidden">
                        <p class="text-xs font-medium text-slate-200 truncate">${sessionScope.userName != null ? sessionScope.userName : 'Infirmier de Garde'}</p>
                        <p class="text-[10px] text-emerald-400 font-semibold uppercase tracking-wider">Poste d'Accueil</p>
                    </div>
                </div>
            </div>

            <!-- Navigation Links -->
            <nav class="px-3 space-y-1">
                <a href="${pageContext.request.contextPath}/infirmier/accueil" 
                   class="flex items-center gap-3 px-3 py-2.5 rounded-lg text-xs font-medium transition ${activeMenu eq 'accueil' ? 'bg-emerald-600 text-white shadow-sm' : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/50'}">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"/>
                    </svg>
                    Accueil & Admission
                </a>

                <a href="${pageContext.request.contextPath}/infirmier/file-attente" 
                   class="flex items-center gap-3 px-3 py-2.5 rounded-lg text-xs font-medium transition ${activeMenu eq 'file' ? 'bg-emerald-600 text-white shadow-sm' : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/50'}">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                    </svg>
                    File d'Attente du Jour
                </a>
            </nav>
        </div>

        <!-- Logout Bottom -->
        <div class="p-3 border-t border-slate-800">
            <a href="${pageContext.request.contextPath}/logout" 
               class="flex items-center gap-3 px-3 py-2 rounded-lg text-xs font-medium text-rose-400 hover:bg-rose-500/10 transition">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
                </svg>
                Déconnexion
            </a>
        </div>
    </aside>

    <!-- MAIN WRAPPER -->
    <div class="flex-1 flex flex-col h-full overflow-hidden">
        <!-- Top bar -->
        <header class="h-16 bg-white border-b border-slate-200/80 px-8 flex items-center justify-between flex-shrink-0 z-10">
            <div class="flex items-center gap-2">
                <span class="text-xs text-slate-400 font-medium">Service de Médecine Générale</span>
                <span class="text-slate-300">/</span>
                <span class="text-xs font-semibold text-slate-700 uppercase tracking-wider">${pageTitle}</span>
            </div>
            <div class="flex items-center gap-4">
                <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-semibold bg-emerald-50 text-emerald-700 border border-emerald-200">
                    <span class="w-1.5 h-1.5 rounded-full bg-emerald-500 animate-pulse"></span>
                    Système En Ligne
                </span>
            </div>
        </header>

        <!-- Main Content Slot -->
        <main class="flex-1 overflow-y-auto bg-slate-50/60 p-8">