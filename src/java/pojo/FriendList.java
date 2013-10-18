/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;
import java.sql.*;

import java.util.ArrayList;

/**
 *
 * @author Ajit Gupta 
 */
public class FriendList 
{
	protected String userid;
	protected String friendid;
	protected String name;
	protected String userimage;

	public String getUserimage()
	{
		return userimage;
	}
	public void setUserimage(String userimage)
	{
		this.userimage = userimage;
	}
	public String getFriendid()
	{
		return friendid;
	}
	public void setFriendid(String friendid)
	{
		this.friendid = friendid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
    
	public FriendList()
	{
		userid = new String();
		friendid = new String();
		name = new String();
	}
	public FriendList(String userid, String friendid,String name)
	{
		this.userid = userid;
		this.friendid = friendid;
		this.name = name;
	}
	
	public ArrayList<FriendList> getFriendList()
	{
		ArrayList<FriendList>al=new ArrayList();
		DbContainor.loadDbDriver();
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl,DbContainor.dbuser,DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("Select fname, mname, lname,EMail ,USERIMAGE from userinfo where EMail in (Select friendid from friendlist where userid=?)");
			ps.setString(1, userid);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				FriendList fl = new FriendList();
				String mname = rs.getString(2);
				/* System.out.println("mname is  :" +mname); */
				if(mname==null)
				{
					mname=" ";
				}
				fl.setName(rs.getString(1)+" "+mname+" "+rs.getString(3));
				fl.setFriendid(rs.getString(4));
				fl.setUserimage(rs.getString(5));
				al.add(fl);
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in getFriendlist of FrienList.java : " + sqle.getMessage());
		}
		return al;
	}
    
	public boolean updateFriendList()
	{
		boolean ret_val =false;
		DbContainor.loadDbDriver();
		try
		{
			Connection con  = DriverManager.getConnection(DbContainor.dburl,DbContainor.dbuser,DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("insert into friendlist values(?,?)");
			ps.setString(1,userid);
			ps.setString(2, friendid);
			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Succesfully inserted into FriendList table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not insert data into FriendList table.");
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in createFriendList() of FriendList.java : " + sqle.getMessage());
		}
		return ret_val;
	}
}