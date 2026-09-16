public class PatientList {
    private Patient[] patientArray;
    private final int maxPatients=1000;
    private int nextIndex=0;

    public PatientList(){
        patientArray=new Patient[maxPatients];
    }
    public boolean add(Patient pat){

    }
    private boolean addOrdered(Patient pat){
        if (nextIndex>=patientArray.length){return false;}
        int currentIndex=nextIndex-1;
        while(currentIndex>=0&&patientArray[currentIndex].getPatientIdentity().isLessThan(pat.getPatientIdentity()) == false && patientArray[currentIndex].getPatientIdentity().match(pat.getPatientIdentity()) == false){
            patientArray[currentIndex+1]=patientArray[currentIndex];
            currentIndex--;
        }
        patientArray[currentIndex+1]=pat;
        nextIndex++;
        return true;
    }
}
