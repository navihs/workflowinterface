package Vues;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Afficheur extends HttpServlet 
{
	//-------- GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException
	{
		
		// on récupère la session en cours
		HttpSession session=request.getSession();
		
		if(session.getAttribute("workflowPackage")==null)
		{
			testAfficheur test = new testAfficheur("POuet");
			session.setAttribute("testAfficheur",test);
			//request.setAttribute("testAfficheur",test);
		}else{
			String a = getRequestParameter(request,"action");
			if(a!=null)
			{
				request.setAttribute("testAfficheur", session.getAttribute("testAfficheur"));
				this.forward("/aff.jsp", request, response);
			}
				
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		doGet(request,response);
	}
	
	//-------- INIT
	public void init(){
	}
	
	private String getServletInitParam(String name)
	{
		return getServletConfig().getInitParameter("urlSessionInvalide");
	}
	
	private void forward(String url,HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
	{
		try{
			getServletContext().getRequestDispatcher(url).forward(request,response);
		}catch(Exception err){}
	}
	
	private String getRequestParameter(HttpServletRequest request,String paramName)
	{
		return request.getParameter(paramName);
	}
	
}
