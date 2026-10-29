import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {
    private static final int[] ORDER={10,7,15,3,18,9,12,6,20,1};

    private PatientIdentity idFor(int n){
        return new PatientIdentity(new StandardName("Pat","L"+(n<10?"0":"")+n),new Date(0));
    }

    @Test
    public void testFindOnEmptyTreeReturnsNull(){
        BinarySearchTree tree =new BinarySearchTree();
        assertNull(tree.find(idFor(10)));
    }

    @Test
    public void testAddAndFind(){
        BinarySearchTree tree=new BinarySearchTree();
        Patient p= new Patient(idFor(10));
        tree.add(p);
        assertSame(p,tree.find(idFor(10)));
        assertNull(tree.find(idFor(11)));
    }

    @Test
    public void testFindsEveryPatientInNotesOrder(){
        BinarySearchTree tree=new BinarySearchTree();
        for(int n:ORDER){
            tree.add(new Patient(idFor(n)));
        }
        for(int n:ORDER){
            assertTrue(tree.find(idFor(n)).getPatientIdentity().match(idFor(n)));
        }
        assertNull(tree.find(idFor(8)));
    }

    @Test
    public void testFindsEveryPatientInReverseOrder(){
        BinarySearchTree tree=new BinarySearchTree();
        for(int i=ORDER.length-1;i>=0;i--){
            tree.add(new Patient(idFor(ORDER[i])));
        }
        for(int n:ORDER){
            assertTrue(tree.find(idFor(n)).getPatientIdentity().match(idFor(n)));
        }
        assertNull(tree.find(idFor(8)));
    }

    @Test
    public void testDuplicateIsNotAddedTwice(){
        BinarySearchTree tree=new BinarySearchTree();
        Patient first= new Patient(idFor(10));
        tree.add(first);
        tree.add(new Patient(idFor(10)));
        assertSame(first,tree.find(idFor(10)));
    }
}
