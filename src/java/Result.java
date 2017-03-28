/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *
 * @author admin
 */
public class Result extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
                    String num=request.getParameter("roll");
         try (PrintWriter out = response.getWriter()) {
             out.print("<!-- Latest compiled and minified CSS -->\n" +
"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
"\n" +
"<!-- Optional theme -->\n" +
"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">\n" +
"\n" +
"<!-- Latest compiled and minified JavaScript -->\n" +
"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
             if(num.trim()=="")
                    {
                        
                        RequestDispatcher rd=request.getRequestDispatcher("OnlineResult.html");   
                         out.print("<div class=\"alert alert-danger\" role=\"alert\">Sorry Roll number can not be empty!</div>");
                        rd.include(request, response);
                       
                    }
             
             try{
                 int n=Integer.parseInt(num);
             }catch(Exception e)
             {
                
                        
                        RequestDispatcher rd=request.getRequestDispatcher("OnlineResult.html");   
                         out.print(" <div class=\"alert alert-danger\" role=\"alert\">Sorry Roll number not valid...!</div> ");
                        rd.include(request, response);
                     
             }
             
             String subjectName[];
             int subjectCode[];
             int e_marksObtained[];
             int e_minMarks[];
           int e_maxMarks[];
          
          int i_marksObtained[];
          int i_minMarks[];
          int i_maxMarks[];
          String grad=new String();
          char finalGrade=0;
          int cgpa=0;
           int id = 0 ;
           String first = null;
           String last = null;
        
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
             out.println("<meta charset=\"UTF-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                         "<link rel=\"stylesheet\" type=\"text/css\" href=\"result.css\">"); 
             //out.println("<style>\n" +"#al{\n" +"width:90px;\n" +"}\n" +"</style>");
            out.println("<title>  Result</title>");            
            out.println("</head>");
            out.println("<body>");
      out.println("<div class=\"container\">");
      
            

             
             // Loading required libraries

  
      
        final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
        final String DB_URL="jdbc:mysql://localhost/axy";

      //  Database credentials
        final String USER = "root";
        final String PASS = "tiger";
 
      String title = "Online Result";
       
       
         /*out.println("<html> <head><title>" + title + "</title></head>\n" +
         "<body>\n" +
         "<div class=\"panel panel-info\">\n" +
"  <div class=\"panel-heading\">"
                 + "<div class=\"page-header\">\n" +
"  <h1 class=\"panel-title\"> Online Result </h1>\n" +
"</div></div>\n" +
"  <div class=\"panel-body\">");*/
      try{
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");

         // Open a connection
         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

         // Execute SQL query
         Statement stmt = conn.createStatement();
         String sql;
         sql = "select su.subject_name,s.student_id,s.fname,s.lname,e.subject_code,e.e_marksObtained,\n" +
"e.e_maxMarks,e.e_minMarks,i.i_marksObtained,\n" +
"i.i_maxMarks,i.i_minMarks from student s, externalmarks e ,subject su, internalmarks i \n" +
"where s.student_id=i.student_id and s.student_id=e.student_id and su.subject_code=e.subject_code \n" +
"and su.subject_code=i.subject_code and s.student_id="+num;
         ResultSet rs = stmt.executeQuery(sql);
         if(!rs.next())
         {
             RequestDispatcher rd=request.getRequestDispatcher("OnlineResult");   
              out.print("<div class=\"alert alert-danger\" role=\"alert\">Sorry Roll number not found. <br> please check the roll number!</div>");
               rd.include(request, response);
         }
         int walker=0;
         // Extract data from result set
         rs.last();
          int size=rs.getRow();
          
           subjectName=new String[size];
          float  gp=0;
           subjectCode=new int[size];
           e_marksObtained=new int[size];
           e_minMarks=new int[size];
           e_maxMarks=new int[size];
          
           i_marksObtained=new int[size];
           i_minMarks=new int[size];
           i_maxMarks=new int[size];
         
          rs.beforeFirst();
             while(rs.next()){
            //Retrieve by column name
              id  = rs.getInt("student_ID");
              first = rs.getString("fname");
              last = rs.getString("lname");            
              
           subjectName[walker]=rs.getString("subject_name");
          
           subjectCode[walker]=rs.getInt("subject_code");
           e_marksObtained[walker]=rs.getInt("e_marksobtained");
           e_minMarks[walker]=rs.getInt("e_minMarks");
           e_maxMarks[walker]=rs.getInt("e_maxmarks");
          
           i_marksObtained[walker]=rs.getInt("i_marksObtained");
           i_minMarks[walker]=rs.getInt("i_minMarks");
           i_maxMarks[walker]=rs.getInt("i_maxMarks");
           walker++;
         } 
             walker=0;
             int flag=1;
             for(int i=0;i<size;i++)
             {
                 if(e_marksObtained[i]<e_minMarks[i]||i_marksObtained[i]<i_minMarks[i])
                     flag=0;
             }
            
             if(flag==1)
                  out.println("<div class=\"alert alert-success\" role=\"alert\"> Congratulations "+first+" You have cleared the exam </div>");
                 else
                 out.println("<div class=\"alert alert-danger\" role=\"alert\"> Sorry "+first+" You have failed the exam </div>");
                
              //Display values
            out.println("<b>Roll Number: " + id + "</b><br>"); 
            out.println("<b>First Name: " + first + "</b><br>");
            out.println("<b>Last Name: " + last + "</b><br>");
            
            //collapse
                       /* out.println("<div class = \"panel-group \" id = \"accordion\">\n" +
                            "<div class = \"panel panel-info \">\n" +
                                "<div class = \"panel-heading\">\n" +
                                "<h4 class = \"panel-title\">");
            out.println("<a data-toggle = \"collapse\" data-parent = \"#accordion\" href = \"#collapseOne\" class=\"text-center btn btn-block\">\n" +
                        "Show Details</a></h4>\n" +
                        "</div>");
            out.println("<div id = \"collapseOne\" class = \"panel-collapse collapse\">\n" +
                            " <div class = \"panel-body\">");
            */
            
         out.println("<table class=\"table table-sm\">");
         out.println("<thead><tr> <th> subject name </th> <th> subject code </th> <th> External marks obtained </th>  <th> External marks Minimum </th> <th> External marks maximum </th> <th> Internal marks obtained </th>  <th> internal marks Minimum </th> <th> internal marks maximum </th> <th> Grade </th> </tr></thead>  ");
         
         for(int i=0;i<size;i++)
         {
           out.println(" <td> " +subjectName[i] +"</td>");
          
           out.println("<td> "+ subjectCode[i] + "</td>");
           out.println("<td> "+e_marksObtained[i]+ "</td>");
           out.println("<td> "+e_minMarks[i]+"</td>");
           out.println("<td> "+e_maxMarks[i]+"</td>");
          
           out.println("<td> "+i_marksObtained[i]+"</td>");
           out.println("<td>"+i_minMarks[i]+"</td>");
           out.println("<td>"+i_maxMarks[i]+"</td>");
             gp+=((e_marksObtained[i]+i_marksObtained[i])*100/(e_maxMarks[i]+i_maxMarks[i]));
           
           if(((e_marksObtained[i]+i_marksObtained[i])*100/(e_maxMarks[i]+i_maxMarks[i]))>80){grad="O";}
           else if(((e_marksObtained[i]+i_marksObtained[i])*100/(e_maxMarks[i]+i_maxMarks[i]))>75){grad="A";}
           else if(((e_marksObtained[i]+i_marksObtained[i])*100/(e_maxMarks[i]+i_maxMarks[i]))>65){grad="B";}
           else if(((e_marksObtained[i]+i_marksObtained[i])*100/(e_maxMarks[i]+i_maxMarks[i]))>60){grad="P";}
           else if(((e_marksObtained[i]+i_marksObtained[i])*100/(e_maxMarks[i]+i_maxMarks[i]))>49){grad="<span style="+"color:red"+">F</span>";}
           else{grad="<span style="+"color:red"+">F</span>";}
          
           out.println("<td>"+grad +"</td></tr>");  
         }
         
         out.println("</table>");
         
         out.println("<table class=\"table table-sm\" >");
         
         out.println("<tr>");
         if(flag==1)
           out.println("<td align=\"left\" class=\"alert alert-success\"> Result: Pass</td>");
         else
           out.println("<td align=\"left\" class=\"alert alert-danger\"> Result: Fail</td>");
             
         out.println("<td align=\"right\" class=\"alert alert-info\"> CGPA:"+gp/50 +" </td>");
         
         out.println("</tr>");
         out.println("</table>");
       
         out.println("</div></div></div></div>");//detail end
          
        out.println(" </div></div>");//panel close
         out.println("<a href=\"OnlineResult.html\" style=\"margin-left:650px;font-size:25px;\"><u>Go back </u></a>");
         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         se.printStackTrace();
      }catch(Exception e){
         //Handle errors for Class.forName
         e.printStackTrace();
     
    
} 
            //out.println("<script src=\"./css/bootstrap/js/jquery.js\"></script>");
            //out.println("<script type=\"text/javascript\" src=\"./css/bootstrap/js/bootstrap.js\"></script>");
            
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
