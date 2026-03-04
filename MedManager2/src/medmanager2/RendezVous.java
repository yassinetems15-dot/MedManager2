package medmanager2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RendezVous {

    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateHeure;

    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure) {
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
    }

    public Patient getPatient() { return patient; }
    public Medecin getMedecin() { return medecin; }
    public LocalDateTime getDateHeure() { return dateHeure; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "RDV [" + dateHeure.format(fmt) + "] : " + patient.getIdentiteComplete()
                + " avec " + medecin;
    }
}