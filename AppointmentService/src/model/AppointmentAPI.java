package model;

import model.Appointment;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ItemsAPI
 */
@WebServlet("/AppointmentAPI")
public class AppointmentAPI extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
    
	Appointment appoObj = new Appointment();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointmentAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String output = appoObj.insertApointment(request.getParameter("doctor_id"),      
				request.getParameter("patient_id"),     
				request.getParameter("prescription"),  
				request.getParameter("doc_notes")); 
	 
	 response.getWriter().write(output); 
		
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Map paras = getParasMap(request); 
		 
		 String output = appoObj.updateAppointment(paras.get("hidItemIDSave").toString(),     
				 paras.get("doctor_id").toString(),     
				 paras.get("patient_id").toString(),        
				 paras.get("prescription").toString(),
				 paras.get("doc_notes").toString()); 
		 
		 response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Map paras = getParasMap(request); 
		 
		 String output = appoObj.deleteAppor(paras.get("app_id").toString()); 
		 
		 response.getWriter().write(output);
	}

	
	// Convert request parameters to a Map 
		private static Map getParasMap(HttpServletRequest request) 
		{  
			Map<String, String> map = new HashMap<String, String>();  
			try  
			{   
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");   
				String queryString = scanner.hasNext() ?          
						scanner.useDelimiter("\\A").next() : "";  
						scanner.close(); 
		 
		  String[] params = queryString.split("&");   
		  for (String param : params)   
		  { 

	 
		   String[] p = param.split("=");    
		   map.put(p[0], p[1]);   
		   }  
		  }  
			catch (Exception e)  
			{  
				
			}  return map; 
			
		}
}