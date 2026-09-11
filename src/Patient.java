public class Patient {
    private PatientIdentity id;

    public Patient(PatientIdentity id) {
        this.id = id;
    }

    public PatientIdentity getPatientIdentity() {
        return id;
    }
}