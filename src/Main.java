import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		main.printSchools();
		//main.addNewData();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	private void addNewData()
	{
		School newSchool = new School();
		newSchool.setName("School name");
		newSchool.setAddress("School adress 22");
		
		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setProfile("profil 1");
		schoolClass.setStartYear(2021);
		schoolClass.setCurrentYear(1);
		
		Student student = new Student();
		student.setName("Adam");
		student.setSurname("Maciejczyk");
		student.setPesel("12345678901");
		
		newSchool.addClass(schoolClass);
		schoolClass.addStudent(student);
		
		Transaction transaction = session.beginTransaction();
		session.save(newSchool);
		transaction.commit();
	}

	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		for (School s : schools) {
			System.out.println("### Schools");
			System.out.println(s);
			System.out.println("    ### SchoolClasses");
			for (SchoolClass c : s.getClasses()) {
				System.out.println("    " + c);
				System.out.println("        ### ClassStudents");
				for (Student st : c.getStudents()) {
					System.out.println("        " + st);
				}
			}
			
			System.out.println();
		}
	}
}
