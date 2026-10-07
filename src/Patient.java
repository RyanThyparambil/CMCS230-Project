import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Patient {
    private PatientIdentity id;
    private UUID uuid;
    private PrescriptionList prescriptions;

    public Patient(PatientIdentity id){
        this(id,UUID.randomUUID());
    }

    public Patient(PatientIdentity id,UUID uuid){
        this.id=id;
        this.uuid=uuid;
        prescriptions=new PrescriptionList();
    }

    public PatientIdentity getPatientIdentity(){
        return id;
    }

    public UUID getUUID(){
        return uuid;
    }

    public PrescriptionList getPrescriptionList(){
        return prescriptions;
    }

    public String toCSV() {
        Name name=id.getName();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return name.getLast()+","+name.getFirst()+","+dateFormat.format(id.getDateOfBirth())+","+uuid.toString();
    }

    public static Patient makePatient(String line){
        try{
            String[] parts=line.split(",");
            if(parts.length!=4){
                return null;
            }
            String last=parts[0].trim();
            String first=parts[1].trim();
            Date dob=CSVReader.parseDate(parts[2].trim());
            UUID uuid=parseUUID(parts[3].trim());
            if(last.length()==0||first.length()==0||dob==null||uuid==null){
                return null;
            }
            return new Patient(new PatientIdentity(new StandardName(first,last),dob),uuid);
        }
        catch(Exception e){
            return null;
        }
    }

    private static UUID parseUUID(String text){
        if(text.length()!=36){
            return null;
        }
        if(text.charAt(8)!='-'||text.charAt(13)!='-'||text.charAt(18)!='-'||text.charAt(23)!='-'){
            return null;
        }
        try{
            return UUID.fromString(text);
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }
}