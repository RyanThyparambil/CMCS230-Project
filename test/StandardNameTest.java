import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardNameTest {
    @Test
    public void testFullname(){
        StandardName name=new StandardName("John", "Smith");
        assertEquals("Smith, John", name.fullName());
    }

    @Test
    public void testMatchEqualNames(){
        StandardName a = new StandardName("John", "Smith");
        StandardName b=new StandardName("John", "Smith");
        assertTrue(a.match(b));
    }


    @Test
    public void testMatchCaseInsensitive(){
        StandardName a = new StandardName("John", "Smith");
        StandardName b=new StandardName("JOHN", "smith");
        assertTrue(a.match(b));
    }
    @Test
    public void testMatchDifferentNames(){
        StandardName a=new StandardName("John", "Smith");
        StandardName b=new StandardName("Bob", "White");
        assertFalse(a.match(b));
    }

    @Test
    public void testIsLessThanByLastName(){
        StandardName a=new StandardName("Zach", "Adams");
        StandardName b=new StandardName("Aaron", "Baker");
        assertTrue(a.isLessThan(b));
    }

    @Test
    public void testIsLessThanByFirstNameWhenLastNamesTie() {
        StandardName a = new StandardName("Alice", "Smith");
        StandardName b = new StandardName("Bob", "Smith");
        assertTrue(a.isLessThan(b));
    }

    @Test
    public void testIsLessThanCaseInsensitive() {
        StandardName a = new StandardName("alice", "adams");
        StandardName b = new StandardName("BOB", "BAKER");
        assertTrue(a.isLessThan(b));
    }

    @Test
    public void testNotLessThanWhenGreater() {
        StandardName a = new StandardName("Bob", "Smith");
        StandardName b = new StandardName("Alice", "Smith");
        assertFalse(a.isLessThan(b));
    }

}