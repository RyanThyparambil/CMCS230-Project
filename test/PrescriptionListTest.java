import org.junit.jupiter.api.Test;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionListTest {

    @Test
    public void testNextOnEmptyListReturnsNull(){
        PrescriptionList list=new PrescriptionList();
        list.init();
        assertNull(list.next());
    }

    @Test
    public void testNextWithoutInitReturnsNull() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        list.add(new Prescription("Amoxicillin",df.parse("01-01-2026"),500,"Dr. Lee"));
        assertNull(list.next());
    }

    @Test
    public void testAddOneAndIterate() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription p1=new Prescription("Amoxicillin",df.parse("01-01-2026"),500,"Dr. Lee");
        list.add(p1);
        list.init();
        assertSame(p1,list.next());
        assertNull(list.next());
    }

    @Test
    public void testNewestFirstWhenAddedInOrder() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription oldest=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription middle=new Prescription("B",df.parse("02-01-2026"),2,"Dr. Lee");
        Prescription newest=new Prescription("C",df.parse("03-01-2026"),3,"Dr. Lee");
        list.add(oldest);
        list.add(middle);
        list.add(newest);
        list.init();
        assertSame(newest,list.next());
        assertSame(middle,list.next());
        assertSame(oldest,list.next());
        assertNull(list.next());
    }

    @Test
    public void testNewestFirstWhenAddedInReverseOrder() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription oldest=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription middle=new Prescription("B",df.parse("02-01-2026"),2,"Dr. Lee");
        Prescription newest=new Prescription("C",df.parse("03-01-2026"),3,"Dr. Lee");
        list.add(newest);
        list.add(middle);
        list.add(oldest);
        list.init();
        assertSame(newest,list.next());
        assertSame(middle,list.next());
        assertSame(oldest,list.next());
        assertNull(list.next());
    }

    @Test
    public void testAddInTheMiddle() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription oldest=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription middle=new Prescription("B",df.parse("02-01-2026"),2,"Dr. Lee");
        Prescription newest=new Prescription("C",df.parse("03-01-2026"),3,"Dr. Lee");
        list.add(newest);
        list.add(oldest);
        list.add(middle);
        list.init();
        assertSame(newest,list.next());
        assertSame(middle,list.next());
        assertSame(oldest,list.next());
        assertNull(list.next());
    }

    @Test
    public void testAddNewerThanHeadBecomesFirst() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription older=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription newer=new Prescription("B",df.parse("06-01-2026"),2,"Dr. Lee");
        list.add(older);
        list.add(newer);
        list.init();
        assertSame(newer,list.next());
        assertSame(older,list.next());
    }

    @Test
    public void testAddOlderThanAllGoesToEnd() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription newer=new Prescription("A",df.parse("06-01-2026"),1,"Dr. Lee");
        Prescription older=new Prescription("B",df.parse("01-01-2026"),2,"Dr. Lee");
        list.add(newer);
        list.add(older);
        list.init();
        assertSame(newer,list.next());
        assertSame(older,list.next());
        assertNull(list.next());
    }

    @Test
    public void testSameDatePrescriptionsAreAllKept() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription p1=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription p2=new Prescription("B",df.parse("01-01-2026"),2,"Dr. Kim");
        Prescription p3=new Prescription("C",df.parse("01-01-2026"),3,"Dr. Ray");
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.init();
        int count=0;
        while(list.next()!=null){
            count++;
        }
        assertEquals(3,count);
    }

    @Test
    public void testSameDateAddedAfterOlderOneStaysAheadOfIt() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription old=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription newOne=new Prescription("B",df.parse("05-01-2026"),2,"Dr. Lee");
        Prescription newTwo=new Prescription("C",df.parse("05-01-2026"),3,"Dr. Lee");
        list.add(old);
        list.add(newOne);
        list.add(newTwo);
        list.init();
        Prescription first=list.next();
        Prescription second=list.next();
        Prescription third=list.next();
        assertEquals(df.parse("05-01-2026"),first.getDateOfIssue());
        assertEquals(df.parse("05-01-2026"),second.getDateOfIssue());
        assertSame(old,third);
    }

    @Test
    public void testInitResetsIteration() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        Prescription p1=new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee");
        Prescription p2=new Prescription("B",df.parse("02-01-2026"),2,"Dr. Lee");
        list.add(p1);
        list.add(p2);
        list.init();
        assertSame(p2,list.next());
        assertSame(p1,list.next());
        assertNull(list.next());
        list.init();
        assertSame(p2,list.next());
    }

    @Test
    public void testNextStaysNullAfterExhaustingList() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PrescriptionList list=new PrescriptionList();
        list.add(new Prescription("A",df.parse("01-01-2026"),1,"Dr. Lee"));
        list.init();
        list.next();
        assertNull(list.next());
        assertNull(list.next());
    }

}