import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescription {
    private String name;
    private Date doi;
    private int dosage;
    private String prescriber;

    public Prescription(String name, Date doi,int dosage,String prescriber){
        this.name=name;
        this.doi=doi;
        this.dosage=dosage;
        this.prescriber=prescriber;
    }
    public String getName(){return name;}
    public Date getDateOfIssue(){return doi;}
    public int getDosage(){return dosage;}
    public String getPrescriber(){return prescriber;}
    public static Prescription makePres(String line){
        try{
            String[] parts=line.split(",");
            if(parts.length!=6){
                return null;
            }
            String medicine=parts[2].trim();
            SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy");
            dateFormat.setLenient(false);
            Date issued=dateFormat.parse(parts[3].trim());
            int dose=Integer.parseInt(parts[4].trim());
            String prescriber=parts[5].trim();
            return new Prescription(medicine,issued,dose,prescriber);
        }
        catch(Exception e){
            return null;
        }
    }
}
