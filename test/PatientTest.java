import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    @Test
    public void testConstructionAndAccessor() {
        Name name =new StandardName("John", "Smith");
        Date dob= new Date(0);
        PatientIdentity identity=new PatientIdentity(name, dob);
        Patient patient = new Patient(identity);
        assertSame(identity, patient.getPatientIdentity());
    }


    @Test
    public void testAccessorReturnsCorrectNameAndDob() {
        Name name = new StandardName("Bob", "Jones");
        Date dob=new Date(1000000);
        PatientIdentity identity = new PatientIdentity(name, dob);
        Patient patient =new Patient(identity);
        assertTrue(patient.getPatientIdentity().toString().contains("Jones, Bob"));
    }



    @Test
    public void testTwoPatientsWithMatchingIdentities() {
        Date dob = new Date(0);
        Patient a= new Patient(new PatientIdentity(new StandardName("John", "Smith"), dob));
        Patient b=new Patient(new PatientIdentity(new StandardName("john", "smith"), dob));
        assertTrue(a.getPatientIdentity().match(b.getPatientIdentity()));
    }
}