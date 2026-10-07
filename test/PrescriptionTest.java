import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionTest {

    @Test
    public void testConstructorAndGetters(){
        Date d=CSVReader.parseDate("2023-02-11");
        Prescription p=new Prescription("heptapone",d,50,"Agrawal");
        assertEquals("heptapone",p.getName());
        assertEquals(d,p.getDateOfIssue());
        assertEquals(50,p.getDosage());
        assertEquals("Agrawal",p.getPrescriber());
    }

    @Test
    public void testMakePresValidLine(){
        Prescription p=Prescription.makePres("Smith,Maria,1953-06-16,heptapone,2023-02-11,50,Agrawal");
        assertNotNull(p);
        assertEquals("heptapone",p.getName());
        assertEquals(CSVReader.parseDate("2023-02-11"),p.getDateOfIssue());
        assertEquals(50,p.getDosage());
        assertEquals("Agrawal",p.getPrescriber());
    }

    @Test
    public void testMakePresTooFewFieldsReturnsNull(){
        assertNull(Prescription.makePres("Smith,Maria,1953-06-16,heptapone"));
    }

    @Test
    public void testMakePresTooManyFieldsReturnsNull(){
        assertNull(Prescription.makePres("Smith,Maria,1953-06-16,heptapone,2023-02-11,50,Agrawal,extra"));
    }

    @Test
    public void testMakePresBadDateReturnsNull(){
        assertNull(Prescription.makePres("Smith,Maria,1953-06-16,heptapone,notadate,50,Agrawal"));
    }

    @Test
    public void testMakePresWrongDateFormatReturnsNull(){
        assertNull(Prescription.makePres("Smith,Maria,1953-06-16,heptapone,02-11-2023,50,Agrawal"));
    }

    @Test
    public void testMakePresImpossibleDateReturnsNull(){
        assertNull(Prescription.makePres("Smith,Maria,1953-06-16,heptapone,2023-13-45,50,Agrawal"));
    }

    @Test
    public void testMakePresNonNumericDosageReturnsNull(){
        assertNull(Prescription.makePres("Smith,Maria,1953-06-16,heptapone,2023-02-11,lots,Agrawal"));
    }

    @Test
    public void testMakePresEmptyLineReturnsNull(){
        assertNull(Prescription.makePres(""));
    }

}