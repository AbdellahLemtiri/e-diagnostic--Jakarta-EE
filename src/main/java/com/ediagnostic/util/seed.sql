-- Active: 1789989210413@@127.0.0.1@5432@ediagnostic
--  (mot de passe: password123)
-- Hash BCrypt pour password123 : $2a$12$e8qC10V1h6mD5O2.w0r.e.y1v6Xj4w2oWlJk.8Zz8K8gR2yM9hDqG

INSERT INTO utilisateurs (id, nom, prenom, email, mot_de_passe, telephone, role, actif)
VALUES (1, 'Alami', 'Fatima', 'infirmier@clinique.ma', '$2a$12$e8qC10V1h6mD5O2.w0r.e.y1v6Xj4w2oWlJk.8Zz8K8gR2yM9hDqG', '0611223344', 'INFIRMIER', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO infirmiers (id, matricule_pro)
VALUES (1, 'INF-2026-01')
ON CONFLICT (id) DO NOTHING;

-- 2. Compte Généraliste
INSERT INTO utilisateurs (id, nom, prenom, email, mot_de_passe, telephone, role, actif)
VALUES (2, 'Bennani', 'Karim', 'generaliste@clinique.ma', '$2a$12$e8qC10V1h6mD5O2.w0r.e.y1v6Xj4w2oWlJk.8Zz8K8gR2yM9hDqG', '0622334455', 'GENERALISTE', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO medecins_generalistes (id, matricule_ordre)
VALUES (2, 'MED-GEN-088')
ON CONFLICT (id) DO NOTHING;

-- 3. Compte Spécialiste 
INSERT INTO utilisateurs (id, nom, prenom, email, mot_de_passe, telephone, role, actif)
VALUES (3, 'Tazi', 'Youssef', 'specialiste@clinique.ma', '$2a$12$e8qC10V1h6mD5O2.w0r.e.y1v6Xj4w2oWlJk.8Zz8K8gR2yM9hDqG', '0633445566', 'SPECIALISTE', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO medecins_specialistes (id, specialite, tarif_expertise, duree_consultation_min)
VALUES (3, 'CARDIOLOGIE', 300.0, 30)
ON CONFLICT (id) DO NOTHING;