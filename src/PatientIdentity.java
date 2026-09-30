import java.util.Date;

public class PatientIdentity {
    private Name name;
    private Date DOB;

    public PatientIdentity(Name name, Date DOB){
        this.name=name;
        this.DOB=DOB;
    }

    public boolean match(PatientIdentity other){
        return this.name.match(other.name)&&this.DOB.equals(other.DOB);
    }
    public boolean isLessThan(PatientIdentity other){
        if(!this.name.match(other.name)){
            return this.name.isLessThan(other.name);
        }
        return this.DOB.compareTo(other.DOB)<0;
    }
    @Override
    public String toString(){return name.toString()+", "+DOB.toString();}
    public Name getName(){return name;}
    public Date getDateofBirth(){return DOB;}
}
