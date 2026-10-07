import org.junit.jupiter.api.Test;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionTest {

    @Test
    public void testConstructorAndGetters() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        Date d=df.parse("02-03-2026");
        Prescription p=new Prescription("Amoxicillin",d,500,"Dr. Lee");
        assertEquals("Amoxicillin",p.getName());
        assertEquals(d,p.getDateOfIssue());
        assertEquals(500,p.getDosage());
        assertEquals("Dr. Lee",p.getPrescriber());
    }

    @Test
    public void testMakePresValidLine() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        Prescription p=Prescription.makePres("Alice Adams, 01-01-1990, Amoxicillin, 02-03-2026, 500, Dr. Lee");
        assertNotNull(p);
        assertEquals("Amoxicillin",p.getName());
        assertEquals(df.parse("02-03-2026"),p.getDateOfIssue());
        assertEquals(500,p.getDosage());
        assertEquals("Dr. Lee",p.getPrescriber());
    }

    @Test
    public void testMakePresTooFewFieldsReturnsNull(){
        assertNull(Prescription.makePres("Alice Adams, 01-01-1990, Amoxicillin"));
    }

    @Test
    public void testMakePresTooManyFieldsReturnsNull(){
        assertNull(Prescription.makePres("Alice Adams, 01-01-1990, Amoxicillin, 02-03-2026, 500, Dr. Lee, extra"));
    }

    @Test
    public void testMakePresBadDateReturnsNull(){
        assertNull(Prescription.makePres("Alice Adams, 01-01-1990, Amoxicillin, notadate, 500, Dr. Lee"));
    }

    @Test
    public void testMakePresImpossibleDateReturnsNull(){
        assertNull(Prescription.makePres("Alice Adams, 01-01-1990, Amoxicillin, 13-45-2026, 500, Dr. Lee"));
    }

    @Test
    public void testMakePresNonNumericDosageReturnsNull(){
        assertNull(Prescription.makePres("Alice Adams, 01-01-1990, Amoxicillin, 02-03-2026, lots, Dr. Lee"));
    }

    @Test
    public void testMakePresEmptyLineReturnsNull(){
        assertNull(Prescription.makePres(""));
    }

}