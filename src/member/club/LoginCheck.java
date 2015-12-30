package member.club;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class LoginCheck {
	Connection conn; 
	Statement stmt;
	ResultSet rs; 
	
	public LoginCheck(){
		try{
		conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/T420/Documents/NetBeansProjects/Fitness Club/Fitness_DB.accdb");
		stmt = conn.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public int getUserId(Integer pin){
		try{
			int userID;
			String sqlString = "select user_ID from Users where pin="+pin;
			rs = stmt.executeQuery(sqlString);
			if(rs.next())
			{
				userID=rs.getInt("user_ID");
				return userID;
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	//Login and LogOut function
	public boolean logIn(Integer pin){
		if(isUserExits(pin))
		{//System.out.println("UserExits");
			if(!isLogedIn(pin))
			{//System.out.println("UserLogin");
				try{
					//inserting login info into user_Log table
					LocalDateTime startTime = LocalDateTime.now();
					String strDate = startTime.getMonthValue()+"/"+
									startTime.getDayOfMonth()+"/"+
									startTime.getYear()+" "+
									startTime.getHour()+":"+
									startTime.getMinute()+":"+
									startTime.getSecond();
					
					String sqlString = "insert into user_Log (user_Id,login_time) values("+this.getUserId(pin)+
							",#"+ strDate + "#)";
					//System.out.println(sqlString);
					int j= stmt.executeUpdate(sqlString);
					//System.out.println("UserID = "+this.getUserId(pin));
					
					//udating users 'InOutStatus
					sqlString = "update Users set InOutStatus='true' where pin ="+pin;
					int i = stmt.executeUpdate(sqlString);
					if(i>0)
						return true;
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
				return true;
			}
			else{
				//for Logout 
				try{
					//updating logOut record in user_Log table
					LocalDateTime endTime = LocalDateTime.now();
					String strEndDate = endTime.getMonthValue()+"/"+
									endTime.getDayOfMonth()+"/"+
									endTime.getYear()+" "+
									endTime.getHour()+":"+
									endTime.getMinute()+":"+
									endTime.getSecond();
					rs = stmt.executeQuery("select user_ID from Users where pin = "+pin);
					int userId=0;
					if(rs.next())
						userId = rs.getInt("user_ID");
					//System.out.println("Userid="+userId);
					String sqlString = "update user_Log set logout_time = #" + 
									strEndDate + "# where (user_Id ="+ 
									userId +") and (logout_time IS NULL)";
					
					//System.out.println(sqlString);
					//System.exit(0);
					int l=stmt.executeUpdate(sqlString);
					/*while(rs.next()){
						System.out.println(rs.getInt("user_Id"));
						System.out.println(rs.getDate("logout_time"));
					}*/
					
					
					//updating user 'InOutStatus' 	
				 sqlString = "update Users set InOutStatus='false' where pin ="+pin;
				int i=stmt.executeUpdate(sqlString);
				if(i>0)
					return true;
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		else
		{
			//implement code for not a user
			return false;
		}
		return false;
	}
	public boolean logOut(Integer pin){
		return false;
	}

	
	//checking whether the user exits
	public boolean isUserExits(Integer pin){
		try{
			rs=stmt.executeQuery("select * from Users where pin="+pin);
			if(!rs.next())
				return false; 
			else
				return true; 
		}catch(SQLException e){
			e.getStackTrace();
		}
		return false; 
	}
	
	//	checking whether the user is already loged in or not
	public boolean isLogedIn(Integer pin){
		try{
			rs= stmt.executeQuery("select * from Users where pin="+pin);
			if(!rs.next())
				return false;
			do{
					
				if(rs.getBoolean("InOutStatus"))
				{
					return true;
				}
				
			}while(rs.next());
				
			
		}catch(SQLException e){
			e.getStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginCheck obj = new LoginCheck();
		System.out.println("User Logedin : " + obj.isLogedIn(527));
		obj.logIn(527);
		System.out.println("User Logedin : " + obj.isLogedIn(527));
		System.out.println("User exits:"+ obj.isUserExits(344));
	}

}
