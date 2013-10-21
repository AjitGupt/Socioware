/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.sql.DriverManager;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;


/**
 *
 * @author Ajit Gupta 
 */
public class Event
{
	protected String eventid;
	protected String organiserid;
	protected String eventdate;
	protected String expdate;
	protected String eventdesc;
	protected int likes;
	protected String visibility;
	protected String subject;
	protected String eventname;

	public String getEventdate()
	{
		return eventdate;
	}
	public void setEventdate(String eventdate)
	{
		this.eventdate = eventdate;
	}
	public String getEventdesc()
	{
		return eventdesc;
	}
	public void setEventdesc(String eventdesc)
	{
		this.eventdesc = eventdesc;
	}
	public String getEventid()
	{
		return eventid;
	}
	public void setEventid(String eventid)
	{
		this.eventid = eventid;
	}
	public String getEventname()
	{
		return eventname;
	}
	public void setEventname(String eventname)
	{
		this.eventname = eventname;
	}
	public String getExpdate()
	{
		return expdate;
	}
	public void setExpdate(String expdate)
	{
		this.expdate = expdate;
	}
	public int getLikes()
	{
		return likes;
	}
	public void setLikes(int likes)
	{
		this.likes = likes;
	}
	public String getOrganiserid()
	{
		return organiserid;
	}
	public void setOrganiserid(String organiserid)
	{
		this.organiserid = organiserid;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public String getVisibility()
	{
		return visibility;
	}
	public void setVisibility(String visibility)
	{
		this.visibility = visibility;
	}

	public Event()
	{
		eventid = new String();
		organiserid = new String();
		eventdate = new String();
		expdate = new String();
		eventdesc = new String();
		eventdesc = new String();
		likes = 0;
		visibility = new String();
		subject = new String();
		eventname = new String();
	}
	public Event(String eventid,String organiserid,String eventdate,String expdate,String eventdesc,int likes,String visibility,String subject,String eventname)
	{
		this.eventid=eventid;
		this.organiserid=organiserid;
		this.eventdate=eventdate;
		this.expdate=expdate;
		this.eventdesc=eventdesc;
		this.likes=likes;
		this.visibility=visibility;
		this.subject=subject;
		this.eventname=eventname;
	}    
	
	
	public boolean createEvent()
	{
		boolean ret_val = false;
		String query = null;
		DbContainor.loadDbDriver();
		try
		{
			query = "insert into events values(?,?,?,?,?,?,?,?,?)";
			Connection con = DbContainor.createConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,eventid);
			ps.setString(2, organiserid);
			try
			{
				ps.setDate(3,DbContainor.toSQLDate(eventdate));
				ps.setDate(4,DbContainor.toSQLDate(expdate));
			}
			catch (ParseException ex)
			{
				System.out.println("can not convert date : "+ex.getMessage());
			}
			ps.setString(5,eventdesc);
			ps.setInt(6, likes);
			ps.setString(7, visibility);
			ps.setString(8, subject);
			ps.setString(9, eventname);
			
			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Succesfully inserted into events table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not insert data into events table.");
			}
			con.close();
		}
		catch(NullPointerException npe)
		{
			System.out.println("DbContainor.createConnection():can not create connection to database : "+npe.getMessage());
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in createDiscussion() of Discussion.java : " + sqle.getMessage());
		}
		return ret_val;
	}

	public boolean editDiscussion()
	{
		boolean ret_val = false;
		String query = null;
		DbContainor.loadDbDriver();
		try
		{
			query = "update discussion set organiserid=?, eventdate=?, expdate=?,eventdesc=?,likes=?,visbility=?,subject=?,eventname=? where eventid=?";
			Connection con = DbContainor.createConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, organiserid);
			try
			{
				ps.setDate(2,DbContainor.toSQLDate(eventdate));
				ps.setDate(3,DbContainor.toSQLDate(expdate));
			}
			catch (ParseException ex)
			{
				System.out.println("can not convert date : "+ex.getMessage());
			}
			ps.setString(4,eventdesc);
			ps.setInt(5,likes);
			ps.setString(6, visibility);
			ps.setString(7, subject);
			ps.setString(8, eventname);
			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Succesfully updated into event table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not update data into event table.");
			}
			con.close();
		}
		catch(NullPointerException npe)
		{
			System.out.println("DbContainor.createConnection():can not create connection to database : "+npe.getMessage());
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in editEvent() of Event.java : " + sqle.getMessage());
		}
		return ret_val;
	}
	
	public boolean deleteEvent()
	{
		boolean ret_val = false;
		String query = null;
		DbContainor.loadDbDriver();
		try
		{
			query = "delete from event where eventid=?";
			Connection con = DbContainor.createConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,eventid);
			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Successfully deleted into event table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not delete data into event table.");
			}
			con.close();
		}
		catch(NullPointerException npe)
		{
			System.out.println("DbContainor.createConnection():can not create connection to database : "+npe.getMessage());
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in deleteEvent() of Event.java : " + sqle.getMessage());
		}
		return ret_val;
	}
	
	public  ArrayList<Event> findAllEvent()
	{
		ArrayList<Event> al = new ArrayList<Event>();
		String query = null;
		DbContainor.loadDbDriver();
		try
		{
			query = "Select * from event";
			Connection con = DbContainor.createConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				Event ev = new Event();
				ev.setEventid(rs.getString("eventid"));
				ev.setOrganiserid(rs.getString("organiserid"));
				ev.setEventdate(rs.getString("eventdate"));
				ev.setExpdate(rs.getString("expdate"));
				ev.setEventdesc(rs.getString("eventdesc"));
				ev.setLikes(rs.getInt("likes"));
				ev.setVisibility(rs.getString("visibility"));
				ev.setSubject(rs.getString("subject"));
				ev.setEventname(rs.getString("eventname"));
				al.add(ev);
			}
			con.close();
		}
		catch(NullPointerException npe)
		{
			System.out.println("DbContainor.createConnection():can not create connection to database : "+npe.getMessage());
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in findAllDiscussion() of Discussion.java  " + sqle.getMessage());
		}
		return al;
	}
	
	public Event findEvent()
	{
		Event ev = new Event();
		String query = null;
		DbContainor.loadDbDriver();
		try
		{
			query = "Select * from discussion where eventid=?";
			Connection con = DbContainor.createConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, eventid);
			ResultSet rs = ps.executeQuery();
            
			if(rs.next())
			{
				ev.setEventid(rs.getString("eventid"));
				ev.setOrganiserid(rs.getString("organiderid"));
				ev.setEventdate(rs.getString("eventdate"));
				ev.setExpdate(rs.getString("expdate"));
				ev.setEventdesc(rs.getString("eventdesc"));
				ev.setLikes(rs.getInt("likes"));
				ev.setVisibility(rs.getString("visibility"));
				ev.setSubject(rs.getString("subject"));
				ev.setEventname(rs.getString("eventname"));
			}
			con.close();
		}
		catch(NullPointerException npe)
		{
			System.out.println("DbContainor.createConnection():can not create connection to database : "+npe.getMessage());
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in findDiscussion() of Discussion.java : " + sqle.getMessage());
		}
		return ev;
	}
}