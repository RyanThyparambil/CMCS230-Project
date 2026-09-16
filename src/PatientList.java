public class PatientList {
    private Patient[] patientArray;
    private final int maxPatients=1000;
    private int nextIndex=0;

    public PatientList(){
        patientArray=new Patient[maxPatients];
    }
}
