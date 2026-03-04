package medmanager2;

import java.time.LocalDate; 
import java.time.Period;
import java.util.Objects;

public class Patient {

    private String id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;
    private String groupeSanguin;

    public Patient(String id, String nom, String prenom, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // ---- Méthodes utiles ----
    public int getAge() {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public String getIdentiteComplete() {
        return prenom + " " + nom + " (ID: " + id + ")";
    }

    // ---- Getters ----
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getTelephone() { return telephone; }
    public String getGroupeSanguin() { return groupeSanguin; }

    // ---- Setters ----
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    // Setter téléphone avec validation (>=10 caractères, chiffres + espaces + '+')
    public boolean setTelephone(String telephone) {
        if (telephone == null) return false;

        // Autorise : +, chiffres, espaces — au moins 10 caractères
        if (!telephone.matches("[+\\d\\s]{10,}")) {
            return false;
        }
        this.telephone = telephone;
        return true;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    // equals / hashCode (par ID) 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient autre = (Patient) obj;
        return Objects.equals(id, autre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getIdentiteComplete() + " - " + getAge() + " ans";
    }
}