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
            if(parts.length!=7){
                return null;
            }
            String medicine=parts[3].trim();
            Date issued=CSVReader.parseDate(parts[4].trim());
            int dose=Integer.parseInt(parts[5].trim());
            String prescriber=parts[6].trim();
            if(medicine.length()==0||prescriber.length()==0||issued==null){
                return null;
            }
            return new Prescription(medicine,issued,dose,prescriber);
        }
        catch(Exception e){
            return null;
        }
    }
}