package medmanager2;

import java.util.ArrayList;
import java.util.List;

public class ServiceHospitalier {

    private String code;
    private String nom;
    private int capaciteLits;
    private List<Medecin> medecins;
    private List<Patient> patients;

    public ServiceHospitalier(String code, String nom, int capaciteLits) {
        this.code = code;
        this.nom = nom;
        this.capaciteLits = capaciteLits;
        this.medecins = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    // Ajouter un médecin
    public void ajouterMedecin(Medecin medecin) {
        if (medecin == null) return;
        if (!medecins.contains(medecin)) { // évite doublons
            medecins.add(medecin);
        }
    }

    // Admettre patient (capacité)
    public boolean admettre(Patient patient) {
        if (patient == null) return false;

        if (patients.size() >= capaciteLits) {
            System.out.println("⚠ Service " + nom + " complet (" + capaciteLits + "/" + capaciteLits + " lits)");
            return false;
        }
        if (!patients.contains(patient)) {
            patients.add(patient);
        }
        return true;
    }

    // Retirer un patient (utile pour Exercice supprimer)
    public boolean retirerPatient(Patient patient) {
        return patients.remove(patient);
    }

    // Retirer un médecin (optionnel)
    public boolean retirerMedecin(Medecin medecin) {
        return medecins.remove(medecin);
    }

    public int getLitsDisponibles() {
        return capaciteLits - patients.size();
    }

    public String getNom() { return nom; }
    public String getCode() { return code; }
    public int getCapaciteLits() { return capaciteLits; }
    public List<Medecin> getMedecins() { return medecins; }
    public List<Patient> getPatients() { return patients; }

    public void afficherTableauDeBord() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.printf("║  🏥 %-25s [%s]                 ║%n", nom, code);
        System.out.printf("║  🛏 Lits disponibles : %-3d / %-3d                     ║%n",
                getLitsDisponibles(), capaciteLits);
        System.out.println("╠══════════════════════════════════════════════════════╣");

        System.out.println("║  👨‍⚕ Médecins :");
        if (medecins.isEmpty()) {
            System.out.println("║    (aucun)");
        } else {
            for (Medecin m : medecins) {
                System.out.println("║    → " + m);
            }
        }

        System.out.println("║  👤 Patients :");
        if (patients.isEmpty()) {
            System.out.println("║    (aucun)");
        } else {
            for (Patient p : patients) {
                System.out.printf("║    → %-30s %3d ans            ║%n",
                        p.getIdentiteComplete(), p.getAge());
            }
        }

        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return nom + " [" + getLitsDisponibles() + "/" + capaciteLits + " lits dispo]";
    }
}