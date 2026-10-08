<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="pageTitle" value="Accueil & Admission Patient" scope="request"/>
<c:set var="activeMenu" value="accueil" scope="request"/>
<jsp:include page="../common/layout-header.jsp" />

<div class="max-w-6xl mx-auto space-y-6">

 
    <div class="bg-white rounded-xl border border-slate-200/80 shadow-sm p-5">
        <form action="${pageContext.request.contextPath}/infirmier/rechercher" method="GET" class="flex flex-col sm:flex-row items-center gap-4">
            <div class="flex-1 w-full relative">
                <div class="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-slate-400">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                    </svg>
                </div>
                <input type="text" name="numSecu" value="${param.numSecu}" required
                       placeholder="Numéro de Sécurité Sociale (ex: 198023456789)..." 
                       class="w-full pl-10 pr-4 py-2.5 bg-slate-50 border border-slate-200 rounded-lg text-xs font-mono text-slate-800 focus:bg-white focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
            </div>
            <button type="submit" 
                    class="w-full sm:w-auto px-5 py-2.5 bg-slate-900 hover:bg-slate-800 text-white text-xs font-semibold rounded-lg shadow-sm transition flex items-center justify-center gap-2">
                <span>Vérifier le dossier</span>
            </button>
        </form>
    </div>

 
    <c:if test="${not empty patientExistant}">
        <div class="bg-emerald-500/10 border border-emerald-500/20 rounded-xl p-4 flex items-center justify-between">
            <div class="flex items-center gap-3">
                <div class="w-8 h-8 rounded-full bg-emerald-500 text-white flex items-center justify-center font-bold text-xs">
                    ✓
                </div>
                <div>
                    <h3 class="text-xs font-bold text-emerald-900">Dossier Médical Existant Retrouvé</h3>
                    <p class="text-[11px] text-emerald-700">Le patient est reconnu. Renseignez uniquement les nouveaux paramètres vitaux pour l'envoyer en consultation.</p>
                </div>
            </div>
            <span class="text-xs font-mono font-semibold text-emerald-800 px-3 py-1 bg-white rounded-md border border-emerald-200">
                N° ${patientExistant.numSecuriteSociale}
            </span>
        </div>
    </c:if>

    <c:if test="${patientNonTrouve}">
        <div class="bg-amber-500/10 border border-amber-500/20 rounded-xl p-4 flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-amber-500 text-white flex items-center justify-center font-bold text-xs">
                !
            </div>
            <div>
                <h3 class="text-xs font-bold text-amber-900">Aucun dossier trouvé pour le N° ${numSecuRecherche}</h3>
                <p class="text-[11px] text-amber-700">Veuillez renseigner le formulaire ci-dessous pour créer le dossier médical et intégrer la file d'attente.</p>
            </div>
        </div>
    </c:if>

 
    <form action="${pageContext.request.contextPath}/infirmier/enregistrer" method="POST" class="space-y-6">
        <input type="hidden" name="_csrf" value="${csrfToken}" />

        <div class="grid grid-cols-1 lg:grid-cols-12 gap-6">
 
            <div class="lg:col-span-7 bg-white rounded-xl border border-slate-200/80 shadow-sm overflow-hidden flex flex-col">
                <div class="px-6 py-4 border-b border-slate-100 flex items-center justify-between bg-slate-50/40">
                    <h2 class="text-xs font-bold uppercase tracking-wider text-slate-700">1. État Civil & Anamnèse</h2>
                    <span class="text-[10px] text-slate-400 font-semibold tracking-wider uppercase">Données Dossier</span>
                </div>

                <div class="p-6 space-y-4 flex-1">
                    <c:choose>
                        <c:when test="${not empty patientExistant}">
 
                            <input type="hidden" name="patientId" value="${patientExistant.id}" />

                            <div class="grid grid-cols-2 gap-4 bg-slate-50 p-4 rounded-lg border border-slate-100 text-xs">
                                <div>
                                    <span class="text-slate-400 block text-[10px] uppercase font-semibold">Nom & Prénom</span>
                                    <span class="font-bold text-slate-900 text-sm">${patientExistant.nom} ${patientExistant.prenom}</span>
                                </div>
                                <div>
                                    <span class="text-slate-400 block text-[10px] uppercase font-semibold">Date de Naissance</span>
                                    <span class="font-medium text-slate-800">${patientExistant.dateNaissance}</span>
                                </div>
                                <div>
                                    <span class="text-slate-400 block text-[10px] uppercase font-semibold">Téléphone</span>
                                    <span class="font-medium text-slate-800">${not empty patientExistant.telephone ? patientExistant.telephone : 'Non renseigné'}</span>
                                </div>
                                <div>
                                    <span class="text-slate-400 block text-[10px] uppercase font-semibold">Mutuelle</span>
                                    <span class="font-medium text-slate-800">${not empty patientExistant.mutuelle ? patientExistant.mutuelle : 'Aucune'}</span>
                                </div>
                                <div class="col-span-2 pt-2 border-t border-slate-200/60">
                                    <span class="text-slate-400 block text-[10px] uppercase font-semibold">Allergies & Contre-indications</span>
                                    <span class="font-semibold text-rose-600">${not empty patientExistant.allergies ? patientExistant.allergies : 'Néant'}</span>
                                </div>
                            </div>
                        </c:when>

                        <c:otherwise>
 
                            <div class="grid grid-cols-2 gap-4">
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">Nom <span class="text-rose-500">*</span></label>
                                    <input type="text" name="nom" required placeholder="Ex: El Amrani"
                                           class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                                </div>
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">Prénom <span class="text-rose-500">*</span></label>
                                    <input type="text" name="prenom" required placeholder="Ex: Mohamed"
                                           class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                                </div>
                            </div>

                            <div class="grid grid-cols-2 gap-4">
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">Date de Naissance <span class="text-rose-500">*</span></label>
                                    <input type="date" name="dateNaissance" required
                                           class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                                </div>
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">N° Sécurité Sociale <span class="text-rose-500">*</span></label>
                                    <input type="text" name="numSecu" value="${numSecuRecherche}" required placeholder="1234567890"
                                           class="w-full px-3 py-2 text-xs font-mono border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                                </div>
                            </div>

                            <div class="grid grid-cols-2 gap-4">
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">Téléphone</label>
                                    <input type="text" name="telephone" placeholder="06..."
                                           class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                                </div>
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">Mutuelle / Assurance</label>
                                    <input type="text" name="mutuelle" placeholder="CNOPS, CNSS..."
                                           class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                                </div>
                            </div>

                            <div>
                                <label class="block text-xs font-semibold text-slate-600 mb-1">Adresse Résidentielle</label>
                                <input type="text" name="adresse" placeholder="Quartier, Ville..."
                                       class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition">
                            </div>

                            <div class="space-y-3 pt-2 border-t border-slate-100">
                                <div>
                                    <label class="block text-xs font-semibold text-slate-600 mb-1">Antécédents Pathologiques</label>
                                    <textarea name="antecedents" rows="2" placeholder="Diabète, HTA, Asthme..."
                                              class="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 transition"></textarea>
                                </div>
                                <div>
                                    <label class="block text-xs font-semibold text-rose-600 mb-1">Allergies Connues</label>
                                    <input type="text" name="allergies" placeholder="Pénicilline, Aliments..."
                                           class="w-full px-3 py-2 text-xs border border-rose-200 bg-rose-50/20 rounded-lg focus:outline-none focus:ring-2 focus:ring-rose-500/20 focus:border-rose-500 transition">
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

 
            <div class="lg:col-span-5 bg-white rounded-xl border border-slate-200/80 shadow-sm overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="px-6 py-4 border-b border-slate-100 flex items-center justify-between bg-slate-50/40">
                        <h2 class="text-xs font-bold uppercase tracking-wider text-slate-700">2. Constantes Physiologiques</h2>
                        <span class="text-[10px] text-emerald-600 font-bold uppercase tracking-wider">Mesures Immédiates</span>
                    </div>

                    <div class="p-6 space-y-4">
                       
                        <div class="grid grid-cols-2 gap-3">
                            <div class="p-3 bg-slate-50 rounded-lg border border-slate-100">
                                <label class="block text-[11px] font-semibold text-slate-600 mb-1">Tension Artérielle</label>
                                <div class="relative">
                                    <input type="text" name="tension" placeholder="12/8"
                                           class="w-full py-1.5 px-2 text-sm font-semibold bg-white border border-slate-200 rounded focus:outline-none focus:border-emerald-500">
                                    <span class="text-[10px] text-slate-400 absolute right-2 top-2">cmHg</span>
                                </div>
                            </div>
                            <div class="p-3 bg-slate-50 rounded-lg border border-slate-100">
                                <label class="block text-[11px] font-semibold text-slate-600 mb-1">Fréq. Cardiaque</label>
                                <div class="relative">
                                    <input type="number" name="frequenceCardiaque" placeholder="72"
                                           class="w-full py-1.5 px-2 text-sm font-semibold bg-white border border-slate-200 rounded focus:outline-none focus:border-emerald-500">
                                    <span class="text-[10px] text-slate-400 absolute right-2 top-2">bpm</span>
                                </div>
                            </div>
                        </div>

                         
                        <div class="grid grid-cols-2 gap-3">
                            <div class="p-3 bg-slate-50 rounded-lg border border-slate-100">
                                <label class="block text-[11px] font-semibold text-slate-600 mb-1">Température</label>
                                <div class="relative">
                                    <input type="number" step="0.1" name="temperature" placeholder="37.0"
                                           class="w-full py-1.5 px-2 text-sm font-semibold bg-white border border-slate-200 rounded focus:outline-none focus:border-emerald-500">
                                    <span class="text-[10px] text-slate-400 absolute right-2 top-2">°C</span>
                                </div>
                            </div>
                            <div class="p-3 bg-slate-50 rounded-lg border border-slate-100">
                                <label class="block text-[11px] font-semibold text-slate-600 mb-1">Fréq. Respiratoire</label>
                                <div class="relative">
                                    <input type="number" name="frequenceRespiratoire" placeholder="16"
                                           class="w-full py-1.5 px-2 text-sm font-semibold bg-white border border-slate-200 rounded focus:outline-none focus:border-emerald-500">
                                    <span class="text-[10px] text-slate-400 absolute right-2 top-2">/min</span>
                                </div>
                            </div>
                        </div>

                        <!-- Poids & Taille -->
                        <div class="grid grid-cols-2 gap-3">
                            <div class="p-3 bg-slate-50 rounded-lg border border-slate-100">
                                <label class="block text-[11px] font-semibold text-slate-600 mb-1">Poids</label>
                                <div class="relative">
                                    <input type="number" step="0.1" name="poids" placeholder="70"
                                           class="w-full py-1.5 px-2 text-sm font-semibold bg-white border border-slate-200 rounded focus:outline-none focus:border-emerald-500">
                                    <span class="text-[10px] text-slate-400 absolute right-2 top-2">kg</span>
                                </div>
                            </div>
                            <div class="p-3 bg-slate-50 rounded-lg border border-slate-100">
                                <label class="block text-[11px] font-semibold text-slate-600 mb-1">Taille</label>
                                <div class="relative">
                                    <input type="number" step="0.1" name="taille" placeholder="175"
                                           class="w-full py-1.5 px-2 text-sm font-semibold bg-white border border-slate-200 rounded focus:outline-none focus:border-emerald-500">
                                    <span class="text-[10px] text-slate-400 absolute right-2 top-2">cm</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- BOUTON D'ENVOI FINAL -->
                <div class="p-6 bg-slate-50/50 border-t border-slate-100">
                    <button type="submit" 
                            class="w-full py-3 px-4 bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold rounded-lg shadow-sm hover:shadow transition duration-150 flex items-center justify-center gap-2">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01"/>
                        </svg>
                        <span>Enregistrer & Orienter vers la File</span>
                    </button>
                </div>
            </div>

        </div>
    </form>

</div>

        </main>
    </div>
</body>
</html>