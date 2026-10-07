import org.junit.jupiter.api.Test;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Test
    public void testImportOnNonexistentFileReturnsFalse(){
        PatientList list=new PatientList();
        assertFalse(list.importFromFile("fileThatDoesNotExist.csv"));
    }

    @Test
    public void testImportSkipsMalformedLinesButKeepsValidOnes() throws IOException, ParseException{
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String filename="testMalformed.csv";
        FileWriter writer=new FileWriter(filename);
        writer.write("Adams,Alice,1990-01-01,35436495-e349-4830-bbeb-f321c4c9bfb5\n");
        writer.write("this line is garbage\n");
        writer.write("Baker,Bob,1991-02-02,8d79c7c9-fd79-4d41-b1d4-02c0b1bb2bb2\n");
        writer.close();

        PatientList list=new PatientList();
        assertTrue(list.importFromFile(filename));

        PatientIdentity idA=new PatientIdentity(new StandardName("Alice", "Adams"),df.parse("1990-01-01"));
        PatientIdentity idB=new PatientIdentity(new StandardName("Bob", "Baker"),df.parse("1991-02-02"));
        assertNotNull(list.find(idA));
        assertNotNull(list.find(idB));

        new File(filename).delete();
    }

    @Test
    public void testSaveToFileWritesCorrectLines(){
        PatientList list=new PatientList();
        list.add(new Patient(new PatientIdentity(new StandardName("Alice", "Adams"),new Date(0))));
        list.add(new Patient(new PatientIdentity(new StandardName("Bob", "Baker"),new Date(0))));
        String filename="testSave.csv";
        assertTrue(list.saveToFile(filename));

        File file=new File(filename);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testImportIntoExistingListMergesCorrectly() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PatientList list=new PatientList();
        PatientIdentity idA=new PatientIdentity(new StandardName("Alice", "Adams"),df.parse("01-01-1990"));
        list.add(new Patient(idA));

        String filename="testMerge.csv";
        PatientList toSave=new PatientList();
        PatientIdentity idB=new PatientIdentity(new StandardName("Bob", "Baker"),df.parse("02-02-1991"));
        toSave.add(new Patient(idB));
        toSave.saveToFile(filename);

        assertTrue(list.importFromFile(filename));
        assertNotNull(list.find(idA));
        assertNotNull(list.find(idB));

        new File(filename).delete();
    }

    @Test
    public void testSaveAndImportRoundTrip() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PatientList list=new PatientList();
        PatientIdentity idA=new PatientIdentity(new StandardName("Alice", "Adams"),df.parse("01-01-1990"));
        PatientIdentity idB=new PatientIdentity(new StandardName("Bob", "Baker"),df.parse("02-02-1991"));
        list.add(new Patient(idA));
        list.add(new Patient(idB));

        String filename="testRoundTrip.csv";
        assertTrue(list.saveToFile(filename));

        PatientList newList=new PatientList();
        assertTrue(newList.importFromFile(filename));
        assertNotNull(newList.find(idA));
        assertNotNull(newList.find(idB));

        new File(filename).delete();
    }

    @Test
    public void testImportMaintainsSortedOrder() throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
        PatientList list=new PatientList();
        list.add(new Patient(new PatientIdentity(new StandardName("Carl", "Carter"),df.parse("03-03-1992"))));
        list.add(new Patient(new PatientIdentity(new StandardName("Alice", "Adams"),df.parse("01-01-1990"))));
        list.add(new Patient(new PatientIdentity(new StandardName("Bob", "Baker"),df.parse("02-02-1991"))));
        String filename="testSortedImport.csv";
        list.saveToFile(filename);

        PatientList newList=new PatientList();
        newList.importFromFile(filename);

        PatientList.Iterator iter=newList.new Iterator();
        assertEquals("Adams", iter.next().getPatientIdentity().getName().getLast());
        assertEquals("Baker", iter.next().getPatientIdentity().getName().getLast());
        assertEquals("Carter", iter.next().getPatientIdentity().getName().getLast());

        new File(filename).delete();
    }

    @Test
    public void testImportPatientsErrorsFileKeepsOnlyTheFiveValidLines(){
        PatientList list=new PatientList();
        assertTrue(list.importFromFile("patientsErrors.csv"));

        int count=0;
        PatientList.Iterator iter=list.new Iterator();
        while(iter.next()!=null){
            count++;
        }
        assertEquals(5,count);
        assertNotNull(list.find(new PatientIdentity(new StandardName("Jennifer","Carpenter"),CSVReader.parseDate("1985-08-13"))));
        assertNotNull(list.find(new PatientIdentity(new StandardName("Barbara","Jones"),CSVReader.parseDate("2012-06-16"))));
        assertNull(list.find(new PatientIdentity(new StandardName("Jessica","Gomez"),CSVReader.parseDate("1932-09-09"))));
        assertNull(list.find(new PatientIdentity(new StandardName("Ming","Yang"),CSVReader.parseDate("1952-02-17"))));
    }

    @Test
    public void testImportPatients1000LoadsEveryLineAndSaveWritesThemAllBack(){
        PatientList list=new PatientList();
        assertTrue(list.importFromFile("patients1000.csv"));

        int count=0;
        PatientList.Iterator iter=list.new Iterator();
        while(iter.next()!=null){
            count++;
        }
        assertEquals(1000,count);

        String filename="testSaved1000.csv";
        assertTrue(list.saveToFile(filename));
        PatientList again=new PatientList();
        assertTrue(again.importFromFile(filename));
        int countAgain=0;
        PatientList.Iterator iter2=again.new Iterator();
        while(iter2.next()!=null){
            countAgain++;
        }
        assertEquals(1000,countAgain);

        new File(filename).delete();
    }

}