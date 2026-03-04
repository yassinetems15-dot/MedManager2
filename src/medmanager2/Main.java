package medmanager2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Patient> patients = new ArrayList<>();
    static List<Medecin> medecins = new ArrayList<>();
    static List<ServiceHospitalier> services = new ArrayList<>();
    static List<RendezVous> rdvs = new ArrayList<>(); // bonus

    static int prochainIdPatient = 1;
    static int prochainIdMedecin = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Services par défaut
        services.add(new ServiceHospitalier("CARD", "Cardiologie", 3));
        services.add(new ServiceHospitalier("URG", "Urgences", 5));
        services.add(new ServiceHospitalier("PED", "Pédiatrie", 2));

        int choix;
        do {
            afficherMenu();
            choix = lireEntier(sc);

            switch (choix) {
                case 1 -> ajouterPatient(sc);
                case 2 -> ajouterMedecin(sc);
                case 3 -> afficherTousLesPatients();
                case 4 -> afficherTousLesMedecins();
                case 5 -> affecterPatientAuService(sc);
                case 6 -> tableauDeBordServices();
                case 7 -> supprimerPatient(sc);          // Exercice 2
                case 8 -> modifierPatient(sc);          // Exercice 3
                case 9 -> affecterMedecinAuService(sc); // Exercice 4
                case 10 -> planifierRdv(sc);            // Bonus
                case 11 -> afficherRdvs();              // Bonus
                case 0 -> System.out.println("\n👋 Fermeture de MedManager.");
                default -> System.out.println("⚠ Choix invalide.");
            }
        } while (choix != 0);

        sc.close();
    }

    static void afficherMenu() {
        System.out.println("\n══════ MedManager v2.0 ══════");
        System.out.println("  1. ➕ Ajouter un patient");
        System.out.println("  2. ➕ Ajouter un médecin");
        System.out.println("  3. 📋 Afficher les patients");
        System.out.println("  4. 📋 Afficher les médecins");
        System.out.println("  5. 🏥 Affecter patient → service");
        System.out.println("  6. 📊 Tableau de bord des services");
        System.out.println("  7. 🗑 Supprimer un patient");
        System.out.println("  8. ✏ Modifier un patient");
        System.out.println("  9. 👨‍⚕ Affecter médecin → service");
        System.out.println(" 10. 📅 Planifier un rendez-vous (bonus)");
        System.out.println(" 11. 📅 Afficher les rendez-vous (bonus)");
        System.out.println("  0. 🚪 Quitter");
        System.out.print("Votre choix : ");
    }

    static int lireEntier(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("⚠ Nombre attendu : ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    // AJOUT PATIENT 
    static void ajouterPatient(Scanner sc) {
        System.out.println("\n--- Nouveau Patient ---");
        String id = String.format("P%03d", prochainIdPatient++);

        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();

        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        LocalDate dn = LocalDate.parse(sc.nextLine());

        Patient p = new Patient(id, nom, prenom, dn);

        System.out.print("Téléphone (ex: +212 6 12 34 56 78) : ");
        String tel = sc.nextLine();
        if (!tel.isBlank()) {
            while (!p.setTelephone(tel)) {
                System.out.print("⚠ Téléphone invalide, ressaisir : ");
                tel = sc.nextLine();
            }
        }

        System.out.print("Groupe sanguin : ");
        String gs = sc.nextLine();
        if (!gs.isBlank()) p.setGroupeSanguin(gs);

        patients.add(p);
        System.out.println("✅ " + p.getIdentiteComplete() + " enregistré (" + p.getAge() + " ans)");
    }

    // AJOUT MEDECIN 
    static void ajouterMedecin(Scanner sc) {
        System.out.println("\n--- Nouveau Médecin ---");
        String id = String.format("M%03d", prochainIdMedecin++);

        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        LocalDate dn = LocalDate.parse(sc.nextLine());
        System.out.print("Spécialité : ");
        String spe = sc.nextLine();
        System.out.print("Matricule : ");
        String mat = sc.nextLine();

        Medecin m = new Medecin(id, nom, prenom, dn, spe, mat);
        medecins.add(m);
        System.out.println("✅ " + m + " enregistré");
    }

    // AFFICHAGES 
    static void afficherTousLesPatients() {
        if (patients.isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }
        System.out.println("\n--- Patients ---");
        System.out.printf("%-6s %-15s %-15s %-5s %-15s %-5s%n",
                "ID", "Nom", "Prénom", "Âge", "Téléphone", "Sang");
        System.out.println("─".repeat(70));
        for (Patient p : patients) {
            System.out.printf("%-6s %-15s %-15s %-5d %-15s %-5s%n",
                    p.getId(), p.getNom(), p.getPrenom(), p.getAge(),
                    p.getTelephone() != null ? p.getTelephone() : "—",
                    p.getGroupeSanguin() != null ? p.getGroupeSanguin() : "—");
        }
    }

    static void afficherTousLesMedecins() {
        if (medecins.isEmpty()) {
            System.out.println("\nAucun médecin enregistré.");
            return;
        }
        System.out.println("\n--- Médecins ---");
        for (Medecin m : medecins) {
            System.out.println("  → ID: " + m.getId() + "|" + m);
        }
    }

    // AFFECTER PATIENT
    static void affecterPatientAuService(Scanner sc) {
        Patient patient = demanderPatientParId(sc);
        if (patient == null) return;

        ServiceHospitalier service = demanderService(sc);
        if (service == null) return;

        if (service.admettre(patient)) {
            System.out.println("✅ " + patient.getIdentiteComplete() + " → " + service.getNom());
        }
    }

    // TABLEAU DE BORD 
    static void tableauDeBordServices() {
        for (ServiceHospitalier s : services) {
            s.afficherTableauDeBord();
        }
    }

    // EXERCICE 2 : SUPPRIMER PATIENT 
    static void supprimerPatient(Scanner sc) {
        Patient patient = demanderPatientParId(sc);
        if (patient == null) return;

        System.out.println("Infos : " + patient.getIdentiteComplete());
        System.out.print("Confirmer suppression ? (oui/non) : ");
        String rep = sc.nextLine().trim().toLowerCase();

        if (!rep.equals("oui")) {
            System.out.println("❌ Suppression annulée.");
            return;
        }

        // Retirer du service si affecté
        for (ServiceHospitalier s : services) {
            s.retirerPatient(patient);
        }

        // Retirer de la liste globale
        patients.remove(patient);

        System.out.println("✅ Patient supprimé.");
    }

    // EXERCICE 3 : MODIFIER PATIENT 
    static void modifierPatient(Scanner sc) {
        Patient p = demanderPatientParId(sc);
        if (p == null) return;

        System.out.println("\n--- Modification Patient ---");
        System.out.println("Actuel : " + p.getIdentiteComplete());

        System.out.print("Nouveau nom (Entrée = garder) : ");
        String saisie = sc.nextLine();
        if (!saisie.isEmpty()) p.setNom(saisie);

        System.out.print("Nouveau prénom (Entrée = garder) : ");
        saisie = sc.nextLine();
        if (!saisie.isEmpty()) p.setPrenom(saisie);

        System.out.print("Nouvelle date naissance AAAA-MM-JJ (Entrée = garder) : ");
        saisie = sc.nextLine();
        if (!saisie.isEmpty()) p.setDateNaissance(LocalDate.parse(saisie));

        System.out.print("Nouveau téléphone (Entrée = garder) : ");
        saisie = sc.nextLine();
        if (!saisie.isEmpty()) {
            while (!p.setTelephone(saisie)) {
                System.out.print("⚠ Téléphone invalide, ressaisir : ");
                saisie = sc.nextLine();
            }
        }

        System.out.print("Nouveau groupe sanguin (Entrée = garder) : ");
        saisie = sc.nextLine();
        if (!saisie.isEmpty()) p.setGroupeSanguin(saisie);

        System.out.println("✅ Modification terminée : " + p.getIdentiteComplete());
    }

    // EXERCICE 4 : AFFECTER MEDECIN 
    static void affecterMedecinAuService(Scanner sc) {
        if (medecins.isEmpty()) {
            System.out.println("\nAucun médecin à affecter.");
            return;
        }

        System.out.print("\nID du médecin : ");
        String idMed = sc.nextLine();

        Medecin med = null;
        for (Medecin m : medecins) {
            if (m.getId().equals(idMed)) { med = m; break; }
        }
        if (med == null) {
            System.out.println("⚠ Médecin introuvable.");
            return;
        }

        ServiceHospitalier service = demanderService(sc);
        if (service == null) return;

        service.ajouterMedecin(med);
        System.out.println("✅ " + med + " → " + service.getNom());
    }

    // BONUS : RENDEZ-VOUS 
    static void planifierRdv(Scanner sc) {
        Patient p = demanderPatientParId(sc);
        if (p == null) return;

        if (medecins.isEmpty()) {
            System.out.println("⚠ Aucun médecin enregistré.");
            return;
        }

        System.out.print("ID du médecin : ");
        String idMed = sc.nextLine();
        Medecin med = null;
        for (Medecin m : medecins) {
            if (m.getId().equals(idMed)) { med = m; break; }
        }
        if (med == null) {
            System.out.println("⚠ Médecin introuvable.");
            return;
        }

        System.out.print("Date/Heure (AAAA-MM-JJTHH:MM) ex: 2026-02-20T14:30 : ");
        LocalDateTime dt = LocalDateTime.parse(sc.nextLine());

        RendezVous rv = new RendezVous(p, med, dt);
        rdvs.add(rv);

        System.out.println("✅ RDV ajouté : " + rv);
    }

    static void afficherRdvs() {
        if (rdvs.isEmpty()) {
            System.out.println("\nAucun rendez-vous.");
            return;
        }
        System.out.println("\n--- Rendez-vous ---");
        for (RendezVous rv : rdvs) {
            System.out.println("  → " + rv);
        }
    }

    // OUTILS 
    static Patient demanderPatientParId(Scanner sc) {
        if (patients.isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return null;
        }
        System.out.print("\nID du patient : ");
        String idPat = sc.nextLine();

        Patient patient = null;
        for (Patient p : patients) {
            if (p.getId().equals(idPat)) { patient = p; break; }
        }
        if (patient == null) {
            System.out.println("⚠ Patient introuvable.");
            return null;
        }
        return patient;
    }

    static ServiceHospitalier demanderService(Scanner sc) {
        System.out.println("Services disponibles :");
        for (int i = 0; i < services.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + services.get(i));
        }
        System.out.print("Votre choix : ");
        int idx = lireEntier(sc) - 1;

        if (idx < 0 || idx >= services.size()) {
            System.out.println("⚠ Service invalide.");
            return null;
        }
        return services.get(idx);
    }
}