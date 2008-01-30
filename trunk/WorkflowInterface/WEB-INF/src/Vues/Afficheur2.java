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
public class Afficheur2 extends HttpServlet 
{
	//-------- GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException
	{
		
		// on récupère la session en cours
		HttpSession session=request.getSession();
		
		PrintWriter writer = response.getWriter();
		
		//On récupère et traite l'action
		String action = getRequestParameter(request,"action");
		
		switch(getActionId(action))
		{
			case 0:
				Parser parser = new Parser();
				WorkflowPackage wp = parser.parsePackage();
				session.setAttribute("workflowPackage", wp);
				this.forward("/package2.jsp", request, response);
				break;
			case 1:
				this.forward("/package2.jsp", request, response);
				break;
			case 2:
				this.forward("/workflow2.jsp", request, response);
				break;
			case 3:
				this.forward("/activity2.jsp", request, response);
				break;
			case 4:
				this.forward("/participant2.jsp", request, response);
				break;
			case 5:
				this.forward("/datafield2.jsp", request, response);
				break;
			case 6:
				this.forward("/extendedattribute2.jsp", request, response);
				break;
			case 7:
				this.forward("/formalparameter2.jsp", request, response);
				break;
			case 8:
				this.forward("/transition2.jsp", request, response);
				break;
			default:
				writer.println("<html><body>");
				writer.println("<a href=\"Afficheur2?action=doParse\">Lancer le parsing du fichier XPDL Afficheur2</a><br>");
				writer.println("</body></html>");
		}
		
		if(session.getAttribute("workflowPackage")==null)
		{
			writer.println("Erreur : Aucun Package à explorer !");
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
	
	private int getActionId(String action)
	{
		if(action==null) return -1;
		Map<String, Integer> map = new TreeMap<String, Integer>();
		map.put("doParse", 0);
		map.put("doGetPackage", 1);
		map.put("doGetWorkflow", 2);
		map.put("doGetActivity", 3);
		map.put("doGetParticipant", 4);
		map.put("doGetDataField", 5);
		map.put("doGetExtendedAttribute", 6);
		map.put("doGetFormalParameter", 7);
		map.put("doGetTransition", 8);
		
		return (map.get(action)!=null)?map.get(action):-1;
	}
	
}
