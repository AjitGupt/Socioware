/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.sql.*;
import java.text.ParseException;

/**
 *
 * @author Ashish
 */
public class FriendRequest
{
	private String reqSender;
	private String reqReciever;
	private String msg;
	private String reqdate;
	private String status;
	private String reqid;
	private String name;
	private String email;
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getName()
	{
	return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getReqid()
	{
		return reqid;
    }
	public void setReqid(String reqid)
	{
		this.reqid = reqid;
	}
	public String getReqdate()
	{
		return reqdate;
	}
	public void setReqdate(String reqdate)
	{
		this.reqdate = reqdate;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public void setReqReciever(String reqReciever)
	{
		this.reqReciever = reqReciever;
	}
	public void setReqSender(String reqSender)
	{
		this.reqSender = reqSender;
	}
	public String getMsg()
	{
		return msg;
	}
	public String getReqReciever()
	{
		return reqReciever;
	}
	public String getReqSender()
	{
		return reqSender;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
	this.status=status;
	}
	
	Connection con = null;
    PreparedStatement ps = null;
	
	public boolean sendRequest()
	{
		boolean ret_val = false;
		DbContainor.loadDbDriver();
		try
		{
			con = DriverManager.getConnection(DbContainor.dburl,DbContainor.dbuser,DbContainor.dbpwd);
			ps = con.prepareStatement("insert into friendrequest values(?,?,?,?,?,?)");
			ps.setString(1, "reqid");
			ps.setString(2, "reqsender");
			ps.setString(3,"reqreceiver");
			try
			{
				ps.setDate(4,DbContainor.toSQLDate(reqdate));
			}
			catch (ParseException ex)
			{
				System.out.println("can not convert date : "+ex.getMessage());
			}
			ps.setString(5,"message");
			ps.setString(6,"status");
			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Succesfully updated into FriendRequest table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not update data into discussion table.");
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("SQL Error in sendRequest() of FriendRequest.java");
		}
		return false;  
	}
    
	public FriendRequest findReceivedRequest()
	{
		FriendRequest fr = new FriendRequest();
		DbContainor.loadDbDriver();
		try
		{
			con = DriverManager.getConnection(DbContainor.dburl,DbContainor.dbuser,DbContainor.dbpwd);
			ps =con.prepareStatement("select fname, mname, lname, email from userinfo where email in (select reqsender from friendrequest where REQRECEIVER=? and status='unconfirmed')");
			ps.setString(1,reqReciever);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				fr.setName(rs.getString("fname")+" "+rs.getString("mname")+" "+rs.getString("lname"));
				fr.setEmail(rs.getString("email"));
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("SQL Error in findRecievedRequest() of FriendRequest.java  :"+ sqle.getMessage());
		}		
		return fr;
	}
     
	public boolean updateRequest()
	{
		boolean ret_val = false;
		DbContainor.loadDbDriver();
		try
		{
			con = DriverManager.getConnection(DbContainor.dburl,DbContainor.dbuser,DbContainor.dbpwd);
			ps = con.prepareStatement("update friendrequest set status='confirmed' where reqsender=? and reqreceiver=?");
			ps.setString(1,reqSender);
			ps.setString(2,reqReciever);
			if(ps.executeUpdate()>0)
			{
				System.out.println("friendrequest table updated successfully in updateRequest() in class FriendRequest.java");
				ret_val = true;
			}
			else
			{
				System.out.println("c'ldn't update friendrequest table  in updateRequest() in class FriendRequest.java");
			}
		}
		catch(SQLException sqle)
		{
			System.out.println("SQL Error in updateRequest() of FriendRequest.java  :"+ sqle.getMessage());
		}			
		return ret_val;
	}
}