/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member.club;

import java.util.Date;

/**
 *
 * @author T420
 */
public class ReportModel {
    int user_Id;
    String first_Name;
    String last_Name;
    Date login_time;
    Date logout_time;

    public int getuser_Id(){
        return this.user_Id;
    }
    public void setuser_Id(int id){
        this.user_Id=id;
    }
    public String getfirst_Name(){
        return this.first_Name;
    }
    public void setfirst_Name(String fname){
        this.first_Name=fname;
    }
    public String getlast_Name(){
        return this.last_Name;
    }
    public void setlast_Name(String lname){
        this.last_Name=lname;
    }
    public Date getlogin_time(){
        return this.login_time;
    }
    public void setlogin_time(Date login){
        this.login_time=login;
    }
    public Date getlogout_time(){
        return this.logout_time;
    }
    public void setlogout_time(Date logout){
        this.logout_time = logout;
    }
}
