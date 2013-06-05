/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import pojo.*;
/**
 *
 * @author Ashish
 */
public class PublishStatusServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd=null;
        PrintWriter out = response.getWriter();
        try {
            PublishStatus ps=new PublishStatus();
            ps.setContent(request.getParameter("status").trim());
            ps.setLikes(0);
            ps.setUpdateDate(DbContainor.getDate());
            
            HttpSession session=request.getSession(false);
           
            ps.setUnid(session.getAttribute("id").toString());
           // ps.setUnid("anku.namdev@gmail.com");
            ps.setStatusId("pbl"+UniqueId.generateId());
            ps.setReport("normal");
            boolean res = ps.saveStatus();
            if(res==true){
               // rd=request.getRequestDispatcher("UserProfile.jsp");
               // rd.forward(request, response);
                response.sendRedirect("UserProfile.jsp");
            }
            else{
                rd=request.getRequestDispatcher("UserProfile.jsp");
                out.println("<span id='rid'>Data base Insertion Fail.</span>");
                rd.include(request, response);
                
            }
                   
            
        }
        finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
