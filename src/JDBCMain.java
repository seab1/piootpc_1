import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCMain {
	public static void main(String[] args) {
		executeSQL();
	}

	private static void executeSQL() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("School name: " + rs.getString("name"));
				System.out.println("       address: " + rs.getString("address"));
			}
			
			sql = "SELECT * FROM schoolClasses";
			rs = stmt.executeQuery(sql);
			System.out.println();
			while (rs.next()) {
				System.out.println("Profile: " + rs.getString("profile"));
				System.out.println("       Current year: " + rs.getString("currentYear"));
				System.out.println("       Start year: " + rs.getString("startYear"));
				System.out.println("       School: " + rs.getString("school_id"));
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
