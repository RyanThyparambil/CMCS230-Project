import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientIdentityTest {
     @Test
     public void testConstructionAndToString() {
          Name name = new StandardName("John", "Smith");
          Date dob = new Date(0);
          PatientIdentity identity=new PatientIdentity(name, dob);
          assertTrue(identity.toString().contains("Smith, John"));
     }

     @Test
     public void testMatchSameIdentity() {
          Date dob = new Date(0);
          PatientIdentity a=new PatientIdentity(new StandardName("John", "Smith"), dob);
          PatientIdentity b=new PatientIdentity(new StandardName("john", "smith"), dob);
          assertTrue(a.match(b));
     }

     @Test
     public void testMatchDifferentName() {
          Date dob = new Date(0);
          PatientIdentity a = new PatientIdentity(new StandardName("John", "Smith"), dob);
          PatientIdentity b = new PatientIdentity(new StandardName("Joe", "Jones"), dob);
          assertFalse(a.match(b));
     }

     @Test
     public void testIsLessThanByName() {
          Date dob = new Date(0);
          PatientIdentity a = new PatientIdentity(new StandardName("Alice", "Adams"), dob);
          PatientIdentity b = new PatientIdentity(new StandardName("Bob", "Baker"), dob);
          assertTrue(a.isLessThan(b));
     }

     @Test
     public void testNotLessThanWhenGreater() {
          Date dob = new Date(0);
          PatientIdentity a = new PatientIdentity(new StandardName("Bob", "Baker"), dob);
          PatientIdentity b = new PatientIdentity(new StandardName("Alice", "Adams"), dob);
          assertFalse(a.isLessThan(b));
     }

     @Test
     public void testMatchDifferentDate() {
          PatientIdentity a = new PatientIdentity(new StandardName("John", "Smith"), new Date(3));
          PatientIdentity b = new PatientIdentity(new StandardName("John", "Smith"), new Date(99999));
          assertFalse(a.match(b));
     }



     @Test
     public void testIsLessThanByDateWhenNamesMatch() {
          PatientIdentity a = new PatientIdentity(new StandardName("John", "Smith"), new Date(0));
          PatientIdentity b = new PatientIdentity(new StandardName("John", "Smith"), new Date(1000000));
          assertTrue(a.isLessThan(b));
     }



}