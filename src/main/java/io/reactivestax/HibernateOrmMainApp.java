package io.reactivestax;

import io.reactivestax.entity.*;
import io.reactivestax.entity.utils.HibernateConfigurationUtils;
import io.reactivestax.utils.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.util.List;

public class HibernateOrmMainApp {

    private static DateUtils du = DateUtils.getInstance();

    public static void main(String[] args) throws Exception {
        // Create a StandardServiceRegistry
        // Getting SessionFactory by defining configuration in code
        SessionFactory sf = HibernateConfigurationUtils.getSessionFactoryByCodeConfig();

        // Getting SessionFactory by defining configuration in hibnernate.cfg.xml file
        // SessionFactory sf= HibernateConfigurationUtils.getSessionFactoryByXmlConfig();

        Session session = sf.openSession();

        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setName("Java Technology Bootcamp");
        bootcamp.setStartDate(du.formatDate("18-May-2019"));

        BootcampLocation bootcampLocation = new BootcampLocation();
        bootcampLocation.setCity("Toronto");
        bootcampLocation.setProvince("ON");
        bootcampLocation.setCountry("Canada");
        bootcampLocation.setStreet("The Esplanade");
        bootcampLocation.setSuite("506");
        bootcampLocation.setStreetNo("56");
        bootcampLocation.setPostalCode("M5E 1A6");

        Student stdAnkur =  getStudent("Ankur","Ghosh","ankuar.ghosh@fullstacklabs.ca", "31-Jan-1988","1-May-2019");
        Student stdMan =  getStudent("Manpreet","Kaur","Manpreet.Kaur@fullstacklabs.ca", "31-Jan-1995","1-May-2019");
        Student stdViv =  getStudent("Vivek","Patel","Vivek.Patel@fullstacklabs.ca", "31-Jan-1995","1-May-2019");

        Transaction tx = session.beginTransaction();
        // 1. saving bootcamp
        session.save(bootcamp);
        // Marker1:: enable this line would setup the bootcamp id on the bootcamp location while inserting, uncomment the Marker1 and comment Marker1-match
        // bootcampLocation.setBootcamp(bootcamp); //** Marker1 is commented
        session.save(bootcampLocation);


        // look the log in the console you would see an update like update BOOTCAMP_LOCATION set bootcamp_id=?, city=?, country=?, postal_code=?, province=?, street=?, street_no=?, suite=? where id=?
        bootcampLocation.setBootcamp(bootcamp); //** Marker1-match comment this and enable Marker1

        //2. set student relationship with the bootcamp
        stdAnkur.setBootcamp(bootcamp);
        stdMan.setBootcamp(bootcamp);
        stdViv.setBootcamp(bootcamp);

        //3. saving student now
        session.save(stdAnkur);
        session.save(stdMan);
        session.save(stdViv);


        // 4. creating instructors entities
        Instructor instructor1 = getInstructor("Brijesh  Sood", "brij@fullstacklabs.ca", "647-291-9024");
        Instructor instructor2 = getInstructor("Robin Bajaj", "robin@fullstacklabs.ca", "647-300-9262");


        // 5. setting up assocation between instructor and bootcamp , try option 1 or option below
        // Option 1a saving instructor
        session.save(instructor1);
        session.save(instructor2);

        // Option 1b saving bootcamp
        instructor1.addBootcamp(bootcamp);
        instructor2.addBootcamp(bootcamp);

        // Option 1c saving instructors again to save the assocation setup above
        session.save(instructor1);
        session.save(instructor2);

        // or Option 2
//        bootcamp.addInstructor(instructor1);
//        bootcamp.addInstructor(instructor2);
//        session.save(bootcamp);

        // 7. linking instructors with bootcamp

        instructor1.setName("Brijesh K Sood");

        Technology java = new Technology();
        java.setName("Java");
        java.setDetails("Java Programing Language");
        java.setBootcamp(bootcamp);

        Technology springfrmk = new Technology();
        springfrmk.setName("Spring");
        springfrmk.setDetails("SpringFramework");
        java.setBootcamp(bootcamp);
        springfrmk.setBootcamp(bootcamp);

        // Saving technologies included in the bootcamp
        session.save(java);
        session.save(springfrmk);


        TechnologyReference javaRef = new TechnologyReference();
        javaRef.setDetails("Oracle Sun Java Reference Doc");
        javaRef.setName("Oracle");
        javaRef.setUrl("https://www.oracle.com/java/");
        javaRef.setTechnology(java);

        TechnologyReference springRef = new TechnologyReference();
        springRef.setDetails("SpringFramework Doc");
        springRef.setName("Pivotal");
        springRef.setUrl("https://spring.io/");
        springRef.setTechnology(springfrmk);

        // Saving technology references
        session.save(javaRef);
        session.save(springRef);


        System.out.println("Bootcamp Id "+bootcamp.getId());

        System.out.println("Student Id "+stdAnkur.getId());
        System.out.println("Student Id "+stdMan.getId());
        System.out.println("Student Id "+stdViv.getId());

        System.out.println("Instructor1 Id "+instructor1.getId());
        System.out.println("Instructor2 Id "+instructor2.getId());

        System.out.println("Object saved successfully.....!!");

        // committing the transaction
        tx.commit();
        // closing the session
        session.close();

        // opening the new session to load the object
        // Retrives all the students enrollement in the bootcamp
        session = sf.openSession();
        bootcamp = session.load(Bootcamp.class, bootcamp.getId());
        List<Student> students = bootcamp.getStudents();
        System.out.println("No of students "+students.size());

        for (Student st: students) {
            System.out.println(st.getEmail());
        }
        session.close();

        sf.close();
        HibernateConfigurationUtils.shutdown();

    }

    private static Student getStudent(String fname, String lname, String email, String dob, String enrollementDate) throws ParseException {
        Student std = new Student();
        std.setFirstName(fname);
        std.setLastName(lname);
        std.setEmail(email);
        std.setDob(du.formatDate(dob));
        std.setEnrollmentDate(du.formatDate(enrollementDate));
        return std;
    }

    private static Instructor getInstructor(String name, String email, String mobileNo) {
        Instructor instructor = new Instructor();
        instructor.setName(name);
        instructor.setEmail(email);
        instructor.setMobileNo(mobileNo);
        return instructor;
    }
}
