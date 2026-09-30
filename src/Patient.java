import java.text.SimpleDateFormat;

public class Patient {
    private PatientIdentity id;

    public Patient(PatientIdentity id) {
        this.id = id;
    }

    public PatientIdentity getPatientIdentity() {
        return id;
    }

    public String toCSV(){
        PatientIdentity id=getPatientIdentity();
        Name n=id.getName();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy");
        return n.getLast()+", "+n.getFirst()+", "+dateFormat.format(id.getDateofBirth());
    }
}