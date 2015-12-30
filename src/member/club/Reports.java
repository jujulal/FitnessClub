package member.club;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class Reports {
	Connection conn; 
	Statement stmt;
	ResultSet rs; 

	public Reports(){
		try{
		conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/T420/Documents/NetBeansProjects/Fitness Club/Fitness_DB.accdb");
		stmt = conn.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
	}
	
	int duration(Date start, Date end){
		return 0;
	}
	//to generate oneday report
/*	long getDayReport(int user_Id, Date date){
		long totalMinutes=0;
		try{
		String sqlQuery = "select * from user_Log where user_Id="+user_Id;
		rs = stmt.executeQuery(sqlQuery);
		if(!rs.next())
			return 0;
		do{
			
			LocalDateTime startTime = rs.getTimestamp("login_time").toLocalDateTime();
			LocalDateTime endTime = rs.getTimestamp("logout_time").toLocalDateTime();
			//LocalDateTime current = date;
			//current = current.minusDays(7);
			
			if(current.compareTo( startTime) <=0)
			{
				System.out.println(current.toString());
				continue;
			}
			//System.out.print("LocalDateTime: "+ startTime.toString() + " end: "+ endTime.toString());
			totalMinutes =totalMinutes + Duration.between(startTime, endTime).toMinutes();
			
		}while(rs.next());
		//System.out.println(" Minutes:" + totalMinutes);
		return totalMinutes;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return 0L;

	}
*/	
	long getWeeklyReport(int user_Id){
		long totalMinutes=0;
		try{
		String sqlQuery = "select * from user_Log where user_Id="+user_Id;
		rs = stmt.executeQuery(sqlQuery);
		if(!rs.next())
			return 0;
		do{
			
			LocalDateTime startTime = rs.getTimestamp("login_time").toLocalDateTime();
			LocalDateTime endTime = rs.getTimestamp("logout_time").toLocalDateTime();
			LocalDateTime current = LocalDateTime.now();
			current = current.minusDays(7);
			
			if(current.compareTo( startTime) <=0)
			{
				//System.out.println(current.toString());
				continue;
			}
			//System.out.print("LocalDateTime: "+ startTime.toString() + " end: "+ endTime.toString());
			totalMinutes =totalMinutes + Duration.between(startTime, endTime).toMinutes();
			
		}while(rs.next());
		//System.out.println(" Minutes:" + totalMinutes);
		return totalMinutes;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return 0L;
	
	}
	
	long getAllReport(int user_Id){
		long totalMinutes=0;
		try{
		String sqlQuery = "select * from user_Log where user_Id="+user_Id;
		rs = stmt.executeQuery(sqlQuery);
		if(!rs.next())
			return 0;
		do{
			
			LocalDateTime startTime = rs.getTimestamp("login_time").toLocalDateTime();
			LocalDateTime endTime = rs.getTimestamp("logout_time").toLocalDateTime();
			
			//System.out.print("LocalDateTime: "+ startTime.toString() + " end: "+ endTime.toString());
			totalMinutes =totalMinutes + Duration.between(startTime, endTime).toMinutes();
			
		}while(rs.next());
		//System.out.println(" Minutes:" + totalMinutes);
		return totalMinutes;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return 0L;
	}
        
        public List<ReportModel> getAllLog(){
                List<ReportModel> reports = new ArrayList<ReportModel>();
            try{
                //SELECT * FROM Users INNER JOIN Member ON Member.user_ID = Users.user_ID  Where Users.InOutStatus=true
                String sqlQuery = "select * from user_Log LEFT JOIN Member ON user_Log.user_Id=Member.user_ID";
                rs = stmt.executeQuery(sqlQuery);

                while(rs.next()){
                    ReportModel model= new ReportModel();
                    model.setuser_Id(rs.getInt("user_ID"));
                    model.setfirst_Name(rs.getString("Member.first_Name"));
                    model.setlast_Name(rs.getString("Member.last_Name"));
                    //model.setlogin_time(new DateTime(rs.getString("user_Log.login_time")));
                    //model.setlogout_time(new DateTime(rs.getString("user_Log.logout_time").toString()));
                    reports.add(model);
                }
                
            }catch(SQLException e){
                e.printStackTrace();
            }
            return reports;
        }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Reports rpt = new Reports();
		System.out.println("Total minutes: "+ rpt.getAllReport(3));
		System.out.println("Minutes in week: "+ rpt.getWeeklyReport(3));
	}

}
