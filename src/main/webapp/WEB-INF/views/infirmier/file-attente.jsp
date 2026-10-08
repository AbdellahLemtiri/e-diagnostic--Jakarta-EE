<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="pageTitle" value="File d'Attente des Consultations" scope="request"/>
<c:set var="activeMenu" value="file" scope="request"/>
<jsp:include page="../common/layout-header.jsp" />

<div class="max-w-7xl mx-auto space-y-6">

    
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
            <h1 class="text-lg font-bold text-slate-900 tracking-tight">File Active des Patients du Jour</h1>
            <p class="text-xs text-slate-500">Gestion chronologique des admissions pour le médecin généraliste</p>
        </div>
        <div class="flex items-center gap-3">
            <a href="${pageContext.request.contextPath}/infirmier/accueil" 
               class="px-4 py-2 bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-semibold rounded-lg shadow-sm transition flex items-center gap-2">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
                </svg>
                Nouvelle Admission
            </a>
        </div>
    </div>

    <!-- FLASH MESSAGE -->
    <c:if test="${param.success eq 'registered'}">
        <div class="p-3 bg-emerald-50 border border-emerald-200 text-emerald-800 text-xs font-medium rounded-lg flex items-center gap-2">
            <svg class="w-4 h-4 text-emerald-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
            </svg>
            Le patient a été enregistré avec ses constantes et orienté dans la file d'attente.
        </div>
    </c:if>

    <!-- CARTE TABLEAU DE BORD (TABLE) -->
    <div class="bg-white rounded-xl border border-slate-200/80 shadow-sm overflow-hidden">
        <div class="px-6 py-4 border-b border-slate-100 flex items-center justify-between bg-slate-50/40">
            <div class="flex items-center gap-2">
                <span class="text-xs font-bold text-slate-700 uppercase tracking-wider">Patients enregistrés</span>
                <span class="px-2 py-0.5 rounded-full text-[10px] font-bold bg-slate-200 text-slate-700 font-mono">
                    ${fn:length(patientsDuJour)}
                </span>
            </div>
            <span class="text-[11px] text-slate-400 font-medium">Ordre d'arrivée (FIFO)</span>
        </div>

        <div class="overflow-x-auto">
            <table class="w-full text-left border-collapse">
                <thead>
                    <tr class="border-b border-slate-200/80 bg-slate-50/50 text-[11px] font-semibold text-slate-500 uppercase tracking-wider">
                        <th class="py-3 px-6">Ordre / Heure</th>
                        <th class="py-3 px-6">Identité Patient</th>
                        <th class="py-3 px-6">N° Sécurité Sociale</th>
                        <th class="py-3 px-6">Constantes Vitales</th>
                        <th class="py-3 px-6 text-center">État Prise en Charge</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-slate-100 text-xs text-slate-700">
                    <c:forEach var="patient" items="${patientsDuJour}" varStatus="status">
                        <tr class="hover:bg-slate-50/80 transition group">
                            <!-- Heure -->
                            <td class="py-4 px-6 font-mono text-slate-500">
                                <div class="flex items-center gap-2">
                                    <span class="w-5 h-5 rounded-full bg-slate-100 text-slate-600 flex items-center justify-center font-bold text-[10px]">
                                        ${status.index + 1}
                                    </span>
                                    <span>
                                        <c:choose>
                                            <c:when test="${not empty patient.dateEnregistrement}">
                                                ${fn:substring(patient.dateEnregistrement, 11, 16)}
                                            </c:when>
                                            <c:otherwise>--:--</c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </td>

                            <!-- Patient Nom -->
                            <td class="py-4 px-6">
                                <div class="font-bold text-slate-900">${patient.nom} ${patient.prenom}</div>
                                <div class="text-[11px] text-slate-400">Né(e) le : ${patient.dateNaissance}</div>
                            </td>

                            <!-- N° Secu -->
                            <td class="py-4 px-6 font-mono text-slate-600">
                                <span class="bg-slate-100 px-2 py-1 rounded text-[11px]">
                                    ${patient.numSecuriteSociale}
                                </span>
                            </td>

                            <!-- Constantes Vitales -->
                            <td class="py-4 px-6">
                                <c:choose>
                                    <c:when test="${not empty patient.dernierSigneVital}">
                                        <div class="flex items-center gap-3 text-[11px]">
                                            <span class="inline-flex items-center gap-1 text-slate-700 bg-slate-100 px-2 py-0.5 rounded">
                                                <span class="text-slate-400 font-semibold">TA:</span> ${patient.dernierSigneVital.tensionArterielle}
                                            </span>
                                            <span class="inline-flex items-center gap-1 text-slate-700 bg-slate-100 px-2 py-0.5 rounded">
                                                <span class="text-slate-400 font-semibold">FC:</span> ${patient.dernierSigneVital.frequenceCardiaque} bpm
                                            </span>
                                            <span class="inline-flex items-center gap-1 px-2 py-0.5 rounded ${patient.dernierSigneVital.temperatureCorporelle >= 38.5 ? 'bg-rose-100 text-rose-700 font-bold' : 'bg-slate-100 text-slate-700'}">
                                                <span class="font-semibold">T°:</span> ${patient.dernierSigneVital.temperatureCorporelle}°C
                                            </span>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-slate-400 italic text-[11px]">Aucune constante saisie</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- Statut Badge -->
                            <td class="py-4 px-6 text-center">
                                <c:choose>
                                    <c:when test="${patient.enAttente}">
                                        <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-[10px] font-bold bg-amber-50 text-amber-700 border border-amber-200">
                                            <span class="w-1.5 h-1.5 rounded-full bg-amber-500"></span>
                                            En Attente
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-[10px] font-bold bg-emerald-50 text-emerald-700 border border-emerald-200">
                                            <span class="w-1.5 h-1.5 rounded-full bg-emerald-500"></span>
                                            Consulté
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty patientsDuJour}">
                        <tr>
                            <td colspan="5" class="py-12 text-center">
                                <div class="max-w-xs mx-auto text-slate-400 space-y-2">
                                    <svg class="w-8 h-8 mx-auto text-slate-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"/>
                                    </svg>
                                    <p class="text-xs font-medium">Aucun patient n'est encore enregistré aujourd'hui dans la file.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

        </main>
    </div>
</body>
</html>