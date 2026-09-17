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

    public Patient find(PatientIdentity ID){
        return binarySearch(id);

    }
    private Patient binarySearch(PatientIdentity ID){
        int lower=0;
        int upper=nextIndex-1;

        while(upper>=lower){
            int mid=(upper+lower)/2;
            PatientIdentity midID=patientArray[mid].getPatientIdentity();
            if(midID.match(ID)){
                return patientArray[mid];
            }
            if(midID.isLessThan(ID)){
                lower=mid+1;
            }
            else{
                upper=mid-1;
            }
            return null;
        }
    }
}
