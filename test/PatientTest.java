import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    @Test
    public void testConstructionAndAccessor() {
        Name name = new StandardName("Alice", "Smith");
        Date dob = new Date(0);
        PatientIdentity identity = new PatientIdentity(name, dob);
        Patient patient = new Patient(identity);

        assertSame(identity, patient.getPatientIdentity());
    }

    @Test
    public void testAccessorReturnsCorrectNameAndDob() {
        Name name = new StandardName("Bob", "Jones");
        Date dob = new Date(1000000);
        PatientIdentity identity = new PatientIdentity(name, dob);
        Patient patient = new Patient(identity);

        assertTrue(patient.getPatientIdentity().toString().contains("Jones, Bob"));
    }

    @Test
    public void testTwoPatientsWithMatchingIdentities() {
        Date dob = new Date(0);
        Patient a = new Patient(new PatientIdentity(new StandardName("Alice", "Smith"), dob));
        Patient b = new Patient(new PatientIdentity(new StandardName("alice", "smith"), dob));

        assertTrue(a.getPatientIdentity().match(b.getPatientIdentity()));
    }

    @Test
    public void testNewPatientHasEmptyPrescriptionList() {
        Patient patient = new Patient(new PatientIdentity(new StandardName("Alice", "Smith"), new Date(0)));

        assertNotNull(patient.getPrescriptionList());
        patient.getPrescriptionList().init();
        assertNull(patient.getPrescriptionList().next());
    }

    @Test
    public void testPrescriptionListIsPerPatient() {
        Patient a = new Patient(new PatientIdentity(new StandardName("Alice", "Smith"), new Date(0)));
        Patient b = new Patient(new PatientIdentity(new StandardName("Bob", "Jones"), new Date(0)));
        Prescription rx = new Prescription("heptapone", CSVReader.parseDate("2023-02-11"), 50, "Agrawal");
        a.getPrescriptionList().add(rx);

        b.getPrescriptionList().init();
        assertNull(b.getPrescriptionList().next());
        a.getPrescriptionList().init();
        assertSame(rx, a.getPrescriptionList().next());
    }

    @Test
    public void testMakePatientValidLine() {
        Patient p = Patient.makePatient("Smith,Maria,1953-06-16,35436495-e349-4830-bbeb-f321c4c9bfb5");

        assertNotNull(p);
        assertEquals("Smith", p.getPatientIdentity().getName().getLast());
        assertEquals("Maria", p.getPatientIdentity().getName().getFirst());
        assertEquals(CSVReader.parseDate("1953-06-16"), p.getPatientIdentity().getDateOfBirth());
        assertEquals(UUID.fromString("35436495-e349-4830-bbeb-f321c4c9bfb5"), p.getUUID());
    }

    @Test
    public void testMakePatientTooFewFieldsReturnsNull() {
        assertNull(Patient.makePatient("Gomez,Hector,1978-05-05"));
        assertNull(Patient.makePatient("Gomez,1978-05-05,8094b565-0f9b-472d-a63d-e9fc19321dc6"));
    }

    @Test
    public void testMakePatientBadDateReturnsNull() {
        assertNull(Patient.makePatient("Gomez,Jessica,1932-Sept-09,c0952acf-9c06-479c-a4dc-3c55318e548f"));
        assertNull(Patient.makePatient("Carpenter,James,08-21,f1b733a0-9b27-4a88-bdb9-1dfc4baba885"));
        assertNull(Patient.makePatient("Hernandez,Jose,09-20-1964,0dd6bf43-f7f0-4037-9b44-ebbbe096ea7c"));
    }

    @Test
    public void testMakePatientBadUUIDReturnsNull() {
        assertNull(Patient.makePatient("Yang,Ming,1952-02-17,27172-1713-48c6-843a-74d2d78"));
    }

    @Test
    public void testMakePatientEmptyLineReturnsNull() {
        assertNull(Patient.makePatient(""));
    }

    @Test
    public void testToCSVAndMakePatientRoundTrip() {
        Patient original = Patient.makePatient("Smith,Maria,1953-06-16,35436495-e349-4830-bbeb-f321c4c9bfb5");

        assertEquals("Smith,Maria,1953-06-16,35436495-e349-4830-bbeb-f321c4c9bfb5", original.toCSV());
        Patient copy = Patient.makePatient(original.toCSV());
        assertNotNull(copy);
        assertTrue(original.getPatientIdentity().match(copy.getPatientIdentity()));
        assertEquals(original.getUUID(), copy.getUUID());
    }

}