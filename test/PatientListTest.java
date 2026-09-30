import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PatientListTest {



    @Test
    public void testFindNotPresentReturnsNull(){
        PatientList list=new PatientList();
        PatientIdentity id1=new PatientIdentity(new StandardName("John", "Smith"),new Date(0));
        list.add(new Patient(id1));
        PatientIdentity missing=new PatientIdentity(new StandardName("Bob", "Jones"),new Date(0));
        assertNull(list.find(missing));
    }

    @Test
    public void testFindOnEmptyListReturnsNull(){
        PatientList list=new PatientList();
        PatientIdentity id1=new PatientIdentity(new StandardName("John", "Smith"),new Date(0));
        assertNull(list.find(id1));
    }



    @Test
    public void testAddandFind(){
        PatientList list=new PatientList();
        PatientIdentity id1=new PatientIdentity(new StandardName("John", "Smith"),new Date(0));
        Patient p1=new Patient(id1);
        assertTrue(list.add(p1));
        assertSame(p1,list.find(id1));
    }

    @Test
    public void testMultipleAddsFindEachCorrectly(){
        PatientList list=new PatientList();
        PatientIdentity idA=new PatientIdentity(new StandardName("Alice", "Adams"),new Date(0));
        PatientIdentity idB=new PatientIdentity(new StandardName("Bob", "Baker"),new Date(0));
        PatientIdentity idC=new PatientIdentity(new StandardName("Carl", "Carter"),new Date(0));
        Patient pA=new Patient(idA);
        Patient pB=new Patient(idB);
        Patient pC=new Patient(idC);
        list.add(pB);
        list.add(pC);
        list.add(pA);
        assertSame(pA,list.find(idA));
        assertSame(pB,list.find(idB));
        assertSame(pC,list.find(idC));
    }



    @Test
    public void testIteratorReturnsInSortedOrder(){
        PatientList list=new PatientList();
        Patient pA=new Patient(new PatientIdentity(new StandardName("Alice", "Adams"),new Date(0)));
        Patient pB=new Patient(new PatientIdentity(new StandardName("Bob", "Baker"),new Date(0)));
        Patient pC=new Patient(new PatientIdentity(new StandardName("Carl", "Carter"),new Date(0)));
        list.add(pC);
        list.add(pA);
        list.add(pB);
        PatientList.Iterator iter=list.new Iterator();
        assertSame(pA,iter.next());
        assertSame(pB,iter.next());
        assertSame(pC,iter.next());
        assertNull(iter.next());
    }

    @Test
    public void testIteratorOnEmptyListReturnsNullImmediately(){
        PatientList list=new PatientList();
        PatientList.Iterator iter=list.new Iterator();
        assertNull(iter.next());
    }


    @Test
    public void testMultipleIndependentIterators(){
        PatientList list=new PatientList();
        Patient pA=new Patient(new PatientIdentity(new StandardName("Alice", "Adams"),new Date(0)));
        Patient pB=new Patient(new PatientIdentity(new StandardName("Bob", "Baker"),new Date(0)));
        list.add(pA);
        list.add(pB);
        PatientList.Iterator iter1=list.new Iterator();
        PatientList.Iterator iter2=list.new Iterator();
        assertSame(pA,iter1.next());
        assertSame(pA,iter2.next());
        assertSame(pB,iter1.next());
        assertSame(pB,iter2.next());
    }

    @Test
    public void testAddReturnsFalseWhenFull(){
        PatientList list=new PatientList();
        for(int i=0;i<1000;i++){
            Patient p=new Patient(new PatientIdentity(new StandardName("First"+i, "Last"+i),new Date(0)));
            assertTrue(list.add(p));
        }
        Patient overflow=new Patient(new PatientIdentity(new StandardName("Extra", "Patient"),new Date(0)));
        assertFalse(list.add(overflow));
    }
    @Test
    public void testPeekAfterExhaustingIteratorReturnsNull(){
        PatientList list=new PatientList();
        Patient pA= new Patient(new PatientIdentity(new StandardName("Alice", "Adams"),new Date(0)));
        list.add(pA);
        PatientList.Iterator iter=list.new Iterator();
        iter.next();
        assertNull(iter.peek());
    }

    @Test
    public void testPeekDoesNotAdvance(){
        PatientList list=new PatientList();
        Patient pA= new Patient(new PatientIdentity(new StandardName("Alice", "Adams"), new Date(0)));
        Patient pB=new Patient(new PatientIdentity(new StandardName("Bob", "Baker"),new Date(0)));
        list.add(pA);
        list.add(pB);
        PatientList.Iterator iter=list.new Iterator();
        assertSame(pA,iter.peek());
        assertSame(pA,iter.peek());
        assertSame(pA,iter.next());
        assertSame(pB,iter.peek());
    }

    @Test
    public void testPeekOnEmptyListReturnsNull(){
        PatientList list=new PatientList();
        PatientList.Iterator iter=list.new Iterator();
        assertNull(iter.peek());
    }


}