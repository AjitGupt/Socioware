package servlets;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import pojo.NgoSignup;

/**
 *
 * @author Ashish
 */
public class NgoSignupServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        NgoSignup nsgn=new NgoSignup();
        nsgn.setName(request.getParameter("fname").trim());
        nsgn.setEmail(request.getParameter("email").trim());
        nsgn.setRemail(request.getParameter("remail").trim());
        nsgn.setPwd(request.getParameter("pwd").trim());
        nsgn.setWebsite(request.getParameter("website").trim());
        nsgn.setEstd_yr(Integer.parseInt(request.getParameter("estdyr").trim()));
        nsgn.setContact_no(request.getParameter("cno"));
        nsgn.setAdd(request.getParameter("add").trim());
        nsgn.setCity(request.getParameter("city").trim());
        nsgn.setCntry(request.getParameter("cntry").trim());
        nsgn.setSq1(request.getParameter("sq1"));
        nsgn.setAns1(request.getParameter("ans1").trim());
        nsgn.setSq2(request.getParameter("sq2"));
        nsgn.setAns2(request.getParameter("ans2").trim());
        request.setAttribute("id",request.getParameter("email"));
        RequestDispatcher rd=null;
        
        boolean chk=nsgn.isRegisteredNgo();
        if(chk==true)
        {
            rd=request.getRequestDispatcher("NgoSignup.jsp");
            out.println("<span id='msg'>Already Registered </span>");
            rd.include(request, response);
        }
        else
        {
            boolean res=nsgn.setNgoinfo();
            if(res==true){
                rd=request.getRequestDispatcher("Home.jsp");
                rd.forward(request, response);
            }
            else{
                rd=request.getRequestDispatcher("NgoSignup.java");
                out.println("<span id='msg1'>Unable to Register This Time Try again Later</span>");
                 rd.include(request, response);
            }
        }
        out.close();
    }
    

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
