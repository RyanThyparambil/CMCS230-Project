import java.util.Date;

public class PrescriptionReader extends CSVReader {
    private PatientList patientList;
    private int unmatchedLines=0;

    public PrescriptionReader(PatientList patientList){
        this.patientList=patientList;
    }

    @Override
    public boolean read(String filename){
        unmatchedLines=0;
        return super.read(filename);
    }

    @Override
    protected boolean processLine(String line){
        String[] parts=line.split(",");
        if(parts.length!=7){
            return false;
        }
        Date dob=parseDate(parts[2].trim());
        if(dob==null){
            return false;
        }
        PatientIdentity identity=new PatientIdentity(new StandardName(parts[1].trim(),parts[0].trim()),dob);
        Prescription rx=Prescription.makePres(line);
        if(rx==null){
            return false;
        }
        Patient pat=patientList.find(identity);
        if(pat==null){
            unmatchedLines++;
            return false;
        }
        pat.getPrescriptionList().add(rx);
        return true;
    }

    public int getUnmatchedLines(){return unmatchedLines;}
}