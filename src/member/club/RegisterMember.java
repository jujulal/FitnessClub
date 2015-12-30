package member.club;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import member.club.RegisterModel;

public class RegisterMember {

	
	static int preToken = 525;
	public Connection conn;
	public Statement stmt;

	public RegisterMember() { }

	public boolean connectDatabase(){

		boolean isDbConnected = false;

		try {
			isDbConnected =	ConnectionString();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isDbConnected;
	}
        //String SQLQuery = "SELECT * FROM Users INNER JOIN Member ON Member.user_ID = Users.user_ID  Where Users.InOutStatus=true";
        public List<Members> getEnrollMembers() throws SQLException{
		String SQLQuery = "SELECT * FROM Users INNER JOIN Member ON Member.user_ID = Users.user_ID  Where Users.InOutStatus=true";
		ResultSet rs = stmt.executeQuery(SQLQuery);
			List<Members> enrolls = new ArrayList<Members>();
		while(rs.next()){
			Members meb = new Members();
			meb.setUser_ID(rs.getInt("package_ID"));
			meb.setfName(rs.getString("first_Name"));
			meb.setlName(rs.getString("last_name"));
			
			enrolls.add(meb);
		}
		
		return enrolls;
	}
        
	public boolean saveFileToServer(int pId, String fName, String lName, String street, String city, String state, 
			int zip, String phone, String email) throws SQLException {
		String DbQuery = "INSERT INTO Member (package_ID, first_Name, last_name, street, city, state, zip, phone, email) VALUES (" + pId +  
				",'" + fName + "','" + lName + "','"+street+"','"+city+"','"+state+"',"+zip+",'"+phone+"','"+email+"')";
		int result =  stmt.executeUpdate(DbQuery);
		if(result > 0){
			String SQLQuery = "SELECT * FROM Member";
			ResultSet rs = stmt.executeQuery(SQLQuery) ;
			int lastId = -1;
			while(rs.next()){
				lastId = rs.getInt("user_ID");
			}
			if(lastId != -1){
				int pinId = generateUserToken(lastId);
				String pinQuery = "INSERT INTO Users (user_ID, pin) VALUES (" + lastId + "," + pinId + ")";
				stmt.executeUpdate(pinQuery);
			}
			return true;
		}else
			return false;
	}

	private int generateUserToken(int lastId) {
		int id = preToken + lastId;
		return id;
	}

	public boolean editFileToServer(int pId, String fName, String lName, String street, String city, String state, 
			int zip, String phone, String email, int userId) throws SQLException {

		String DbQuery = "UPDATE Member " + "SET package_ID = "  + pId + ","
				+ "first_Name = " + " ' "+fName +" ' "+","
				+ "last_name = "+ " ' "+ lName + " ' "+ ","
				+ "street = "+ " ' "+street + " ' "+"," 
				+ "city =" + " ' "+city + " ' "+ "," 
				+ "state=" + " ' "+state+ " ' "+ ","
				+ "zip=" + zip+ ","
				+ "phone=" + " ' "+ phone+ " ' "+ ","
				+ "email=" + " ' "+ email+ " ' "
				+ " WHERE user_ID=" + userId;

		int result =  stmt.executeUpdate(DbQuery);
		if(result > 0){
			return true;
		}else
			return false;
	}

	public boolean deleteFileFromServer(int userId) throws SQLException{
		String DbQuery = "DELETE FROM" + " Member " + "WHERE " + "user_ID = "+ userId; 

		int result =  stmt.executeUpdate(DbQuery);
		if(result > 0){
			return true;
		}else
			return false;
	}

	public List<RegisterModel> getAllMembers() throws SQLException{
		String SQLQuery = "SELECT * FROM Member";
		ResultSet rs = stmt.executeQuery(SQLQuery) ;
		List<RegisterModel> members = new ArrayList<RegisterModel>();
		while(rs.next()){
			RegisterModel member = new RegisterModel();
                        member.setUser_ID(rs.getInt("user_ID"));
			member.setPackage_ID(rs.getInt("package_ID"));
			member.setFirst_Name(rs.getString("first_Name"));
			member.setLast_Name(rs.getString("last_name"));
			member.setStreet(rs.getString("street"));
			member.setCity(rs.getString("city"));
			member.setState(rs.getString("state"));
			member.setZip(rs.getInt("zip"));
			member.setPhone(rs.getString("phone"));
			member.setEmail(rs.getString("email"));
			
			members.add(member);
		}
		return members;
	}
	
	public RegisterModel getTheMember(int id) throws SQLException{
		String SQLQuery = "SELECT * FROM Member WHERE user_ID = "+ id;
		ResultSet rs = stmt.executeQuery(SQLQuery);
		RegisterModel model = new RegisterModel();
		while(rs.next()){
			model.setPackage_ID(rs.getInt("package_ID"));
			model.setFirst_Name(rs.getString("first_Name"));
			model.setLast_Name(rs.getString("last_name"));
			model.setStreet(rs.getString("street"));
			model.setCity(rs.getString("city"));
			model.setState(rs.getString("state"));
			model.setZip(rs.getInt("zip"));
			model.setPhone(rs.getString("phone"));
			model.setEmail(rs.getString("email"));
		}
		
		return model;
	}
	
        public ResultSet getMember(int id) throws SQLException{
            String SQLQuery = "SELECT * FROM Member WHERE user_ID = "+ id;
		ResultSet rs = stmt.executeQuery(SQLQuery);
                return rs;
        }
	
	public boolean ConnectionString() throws SQLException {
		conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/T420/Documents/NetBeansProjects/Fitness Club/Fitness_DB.accdb");
		stmt = conn.createStatement();
		if(conn != null) 
			return true;
		else 
			return false;
	}

}
