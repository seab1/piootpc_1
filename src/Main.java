import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		//main.printSchools();
		//main.addNewData();
		//main.executeQueries();
		//main.searchData();
		main.updateData();
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
	
	private void UESchoolsQuery()
	{
		String hql = "FROM School S WHERE S.name='UE'";
        Query query = session.createQuery(hql);
        List<School> results = query.list();
        System.out.println(results);
	}
	
	private void deleteUESchoolsQuery()
	{
		String hql = "FROM School S WHERE S.name='UE'";
        Query query = session.createQuery(hql);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        for(School school : results)
        {
        	session.delete(school);
        }
        transaction.commit();
	}
	
	private void countSchoolsQuery()
	{
		String hql = "SELECT COUNT(S) FROM School S";
        Query query = session.createQuery(hql);
        Long SchoolsCount = (Long) query.uniqueResult();
        System.out.println("Schools count:" + SchoolsCount);
	}
	
	private void countStudentsQuery()
	{
		String hql = "SELECT COUNT(S) FROM Student S";
        Query query = session.createQuery(hql);
        Long StudentCount = (Long) query.uniqueResult();
        System.out.println("Student count:" + StudentCount);
	}
	
	private void moreThan2ClassesSchoolsQuery()
	{
		String hql = "SELECT COUNT(S) FROM School S WHERE size(S.classes)>=2";
        Query query = session.createQuery(hql);
        Long SchoolsCount = (Long) query.uniqueResult();
        System.out.println("Schools count:" + SchoolsCount);
	}
	
	private void specificSchoolQuery()
	{
		String hql = "SELECT s FROM School s INNER JOIN s.classes c WHERE c.profile = 'mat-fiz' AND c.currentYear>=2";
        Query query = session.createQuery(hql);
        List<School> results = query.list();
        System.out.println(results);
	}
	
	private void executeQueries()
	{
        UESchoolsQuery();
        deleteUESchoolsQuery();
        countSchoolsQuery();
        countStudentsQuery();
        moreThan2ClassesSchoolsQuery();
        specificSchoolQuery();
    }
	
	private void searchData()
	{
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 2);
		School school = (School) query.uniqueResult();
		System.out.println(school);
	}
	
	private void updateData()
	{
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 2);
		School school = (School) query.uniqueResult();
		System.out.println(school);
		school.setName("New School Name");
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
		System.out.println(school);
	}
}
