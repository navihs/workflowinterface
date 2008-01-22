package Vues;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import XPDLInterface.*;

/***
 * Servlet d'affichage d'un xpdl
 * @author Laurent
 * Les actions possibles :
 * 		action="doParse"&file="fichier"
 * 		action="doGetPackage"
 * 		action="doGetWorkflow"&id="idWorkflow"
 * 		action="doGetActivity"&id="idActivity"
 * 		action="doGetParticipant"&id="idParticipant"
 * 		...
 */
public class Afficheur extends HttpServlet 
{
	//-------- GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException
	{
		
		// on r�cup�re la session en cours
		HttpSession session=request.getSession();
		
		//On r�cup�re et traite l'action
		String action = getRequestParameter(request,"action");
		switch(getActionId(action))
		{
			case 0:
				Parser parser = new Parser();
				WorkflowPackage wp = parser.parsePackage();
				session.setAttribute("workflowPackage", wp);
				break;
			case 1:
				this.forward("package.jsp", request, response);
				break;
			case 2:
				this.forward("workflow.jsp", request, response);
				break;
			case 3:
				this.forward("activity.jsp", request, response);
				break;
			case 4:
				this.forward("participant.jsp", request, response);
				break;
		}
		
		if(session.getAttribute("workflowPackage")==null)
		{
			PrintWriter writer = response.getWriter();
			writer.println("Erreur : Aucun Package � explorer !");
		}/*else{
			String a = getRequestParameter(request,"action");
			if(a!=null)
			{
				request.setAttribute("testAfficheur", session.getAttribute("testAfficheur"));
				this.forward("/aff.jsp", request, response);
			}
				
		}*/
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
	
	private int getActionId(String action)
	{
		Map<String, Integer> map = new TreeMap<String, Integer>();
		map.put("doParse", 0);
		map.put("doGetPackage", 1);
		map.put("doGetWorkflow", 2);
		map.put("doGetActivity", 3);
		map.put("doGetParticipant", 4);
		
		return map.get(action);
	}
	
}
