package Vues;
import XPDLInterface.*;

import java.util.*;

/**
 * La classe ModeleTest permet de générer l'affichage HTML graphique des objets du modèle.<BR>
 * Chaque fonction statique permet l'affichage d'un objet en particulier ou d'une liste le représentant.<br>
 * Chaque activity est représentée par un objet "Box" dont une liste est générée au moment de l'affichage d'un workflow
 * @author Laurent
 */
public class ModeleTest{
	/**
	 * Longueur du tableau d'affichage d'un workflow
	 */
	private static int longTab =0;
	/**
	 * largeur du tableau d'affichage d'un workflow
	 */
	private static int largTab =0;
	/**
	 * Largeur d'une boite affichant une activity
	 */
	private static int boxWidth =300;
	/**
	 * Hauteur d'une boite affichant une activity
	 */
	private static int boxHeight=120;
	/**
	 * Largeur de l'espacement de base
	 */
	private static int spacingWidth =50;
	/**
	 * Hauteur de l'espacement de base
	 */
	private static int spacingHeight =45;
	/**
	 * Point de référence en largeur
	 */
	private static int x =boxWidth+spacingWidth;
	/**
	 * Point de référence en hauteur
	 */
	private static int y =boxHeight+spacingHeight;
	
	/**
	 * Largeur de la colone des performers
	 */
	private static int performersWidth = 200;
	private static int worflowHeight = 30;
	
	private static int dimX =performersWidth + spacingWidth;
	private static int dimY =worflowHeight + 15;
	//+spacingHeight;
	
	private static int i =0;
	
	public static String listeParticipants(List<Participant> participants)
	{
		String p=" ";
		Iterator<Participant> it = participants.iterator();
		while(it.hasNext())
		{
			Participant part = it.next();
			p+="<a href='Afficheur2?action=doGetParticipant&id="+part.getId()+"'>"+part.getName()+"</a><br>\n";	
		}
		return p;
	}

	public static String activity(Activity a,int x, int y)
	{
		String s=" ";
		String html=" ";
		spacingHeight =45;
			
		html= "<table border=0 cellpadding=0 cellspacing=5>";
		html+="<tr>";
		html+="   <td align=right><b>Name :</b></td><td>"+ a.getName()+ "</td>"; 
		html+="</tr>";
		html+="<tr>";
		html+="   <td align=right><b>Descripion :</b></td><td>"+a.getDescription()+"</td>";
		html+="</tr>";
		html+="<tr>";
		html+="   <td align=center><b>Extended<br>Attributes ("+a.getExtendedAttributes().size()+")</td>";
		html+="   <td>"+ModeleTest.listeExtendedAttributes(a.getExtendedAttributes(),"&workflow="+a.getWorkflowParent().getId()+"&activity="+a.getId())+"</td>";
		html+="</tr>";
		if(a.getImplementation())
		{
			html+="<tr>";
			html+="   <td align=right><b>Implementation</b></td>";
			html+="	  <td><a href='Afficheur2?action=doGetWorkflow&id="+a.getSubflow().getId()+"'>"+a.getSubflow().getName()+"</td>";
			html+="</tr>";
		}
		html+="</table>";
		s+="\n<script>";
		
		s+="\nactivityWindowDefault('" + a.getName() + "'," + x + "," + y + ",'" +html + "');";
		s+="\n</script>";
		
		return s;
	}
	
	/**
	 * Génère le contenu d'une boite affichant une instance d'activity
	 * @param wfProcessKey Attribut Key du workflow process recherché
	 * @param a Activity a afficher
	 * @param x Position x de la box
	 * @param y Position y de la box
	 * @return contenu d'une box activity
	 */
	public static String activityInstance(String wfProcessKey, Activity a, int x, int y)
	{
		String s=" ";
		String html=" ";
		spacingHeight =45;
			
		html= "<table border=0 cellpadding=0 cellspacing=5>";
		html+="<tr>";
		html+="   <td align=right><b>Name :</b></td><td>"+ a.getName()+ "</td>"; 
		html+="</tr>";
		html+="<tr>";
		html+="   <td align=right><b>Descripion :</b></td><td>"+a.getDescription()+"</td>";
		html+="</tr>";
		html+="<tr>";
		html+="   <td align=center><b>Extended<br>Attributes ("+a.getExtendedAttributes().size()+")</td>";
		html+="   <td>"+ModeleTest.listeExtendedAttributes(a.getExtendedAttributes(),"&workflow="+a.getWorkflowParent().getId()+"&activity="+a.getId())+"</td>";
		html+="</tr>";
		if(a.getImplementation())
		{
			html+="<tr>";
			html+="   <td align=right><b>Implementation</b></td>";
			html+="	  <td><a href='Afficheur2?action=doGetWorkflow&id="+a.getSubflow().getId()+"'>"+a.getSubflow().getName()+"</td>";
			html+="</tr>";
		}
		html+="</table>";
		s+="\n<script>";
		
		// Ici est testé l'attribut status de l'instance de l'activity
		String status="Default";
		String activityStatus = WorkflowPackage.getWorkflowInstancesStates(a.getWorkflowParent()).get(WorkflowPackage.getWfProcessByKey(wfProcessKey)).get(a);
		if(activityStatus!=null)
		{
			if(activityStatus.equals("open.running"))
				status="Running";
			if(activityStatus.equals("open.not_running.not_started")||activityStatus.equals("open.not_running.suspended"))
				status="Waiting";
			if(activityStatus.equals("closed.terminated")||activityStatus.equals("closed.completed"))
				status="Terminated";
		}

		// La box appelée est celle qui correspond au status
		s+="\nactivityWindow"+status+"('" + a.getName() + "'," + x + "," + y + ",'" +html + "');";
		s+="\n</script>";
		
		return s;
	}
	
	
	public static String participant(Participant p)
	{
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>Participant</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+p.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Name</td>";
		s+="<td>"+p.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Type</td>";
		s+="<td>"+p.getType()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Description</td>";
		s+="<td>"+p.getDescription()+"</td>";
		s+="</tr>";
		s+="</table>";
		return s;
	}
	
	public static String extendedAttribute(ExtendedAttribute ea)
	{
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>ExtendedAttribute</td></tr>";
		s+="<tr>";
		s+="<td>Name</td>";
		s+="<td>"+ea.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Value</td>";
		s+="<td>"+ea.getValue()+"</td>";
		s+="</tr>";
		s+="</table>";
		return s;
	}
	
	public static String formalParameter(FormalParameter fp)
	{
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>FormalParameter</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+fp.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>DataType</td>";
		s+="<td>"+fp.getDataType()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<tr>";
		s+="<td>Description</td>";
		s+="<td>"+fp.getDescription()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Mode</td>";
		s+="<td>"+fp.getMode()+"</td>";
		s+="</tr>";
		s+="</table>";
		return s;
	}
	
	public static String transition(Transition t)
	{
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>Transition</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+t.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Condition</td>";
		s+="<td>"+t.getCondition()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<tr>";
		s+="<td>Condition type</td>";
		s+="<td>"+t.getConditionType()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Extended Attributes</td>";
		s+="<td>"+ModeleTest.listeExtendedAttributes(t.getExtendedAttributes(),"&transition="+t.getId()+"")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>From</td>";
		s+="<td><a href='Afficheur2?action=doGetActivity&id="+t.getFrom().getId()+"'>"+t.getFrom().getName()+"</a></td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>To</td>";
		s+="<td><a href='Afficheur2?action=doGetActivity&id="+t.getTo().getId()+"'>"+t.getTo().getName()+"</a></td>";
		s+="</tr>";
		s+="</table>";
		return s;
	}
	
	public static String dataField(DataField df)
	{
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>DataField</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+df.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Name</td>";
		s+="<td>"+df.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>DataType</td>";
		s+="<td>"+df.getDataType()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>isArray</td>";
		s+="<td>"+df.getIsArray()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Initial Value</td>";
		s+="<td>"+df.getInitialValue()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Length</td>";
		s+="<td>"+df.getLength()+"</td>";
		s+="</tr>";
		s+="</table>";
		return s;
	}
	
	public static String workflowPackage(WorkflowPackage wp)
	{
		
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>WorkflowPackage</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+wp.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Name</td>";
		s+="<td>"+wp.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Created</td>";
		s+="<td>"+((wp.getCreated()!=null)?wp.getCreated().toString():"?")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>DataFields ("+wp.getDataFields().size()+")</td>";
		s+="<td> "+ModeleTest.listeDataFields(wp.getDataFields(),"")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>ExtendedAttributes ("+wp.getExtendedAttributes().size()+")</td>";
		s+="<td> "+ModeleTest.listeExtendedAttributes(wp.getExtendedAttributes(),"")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Participants ("+wp.getParticipants().size()+")</td>";
		s+="<td> "+ModeleTest.listeParticipants(wp.getParticipants())+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Workflows ("+wp.getWorkflows().size()+")</td>";
		s+="<td> "+ModeleTest.listeWorkflows(wp.getWorkflows())+"</td>";
		s+="</tr>";
		s+=ModeleTest.listeWorkflowInstances(wp.getWorkflows())+"</td>";
		s+="</table>";
		
		/*pour s'amuser*/
		s+="\n</div>";
		s+="<script>";
		s+="\nfunction openPopup(){";
		s+="\nvar master = new Window(\"master\", {className: \"alphacube\", width:700, height:500,title:\"WorkflowPackage\", top:200, right:700, url:\"http://en.wikipedia.org/wiki/Workflow/\", showEffectOptions: {duration:1.5}});";
		s+="\nmaster.setDestroyOnClose();";
		s+="\nmaster.showCenter();";
		s+="\n}";
		s+="\nopenPopup();";
		s+="\n</script>";
		
		return s;		 
	}
	
	/**
	 * Permet de générer une liste de Box destinées à êtres placées (Pour l'affichage d'un workflow)
	 * @param wf Workflow dont on doit générer les box d'activity
	 * @return Liste de box correspondante
	 */
	public static List<Box> createBox(Workflow wf)
	{

		List<Box> boxes =new ArrayList<Box>();

		dimX =performersWidth + spacingWidth;
		dimY =worflowHeight + 15; //+spacingHeight;

		largTab =0;
		y =boxHeight+spacingHeight;
		
		//affichage tableau des participants
		List<Participant> performers;
		performers = wf.getActivityPerformers();
		Iterator<Participant> it = performers.iterator();
		if(performers.size()==0) return null;
		
		//Calcul de la Largeur du tableau en fonction du nombre de participants
		largTab=(y*performers.size())+worflowHeight+spacingHeight;;
		
		//Calcul de la largeur d'une ligne
		y=(largTab-worflowHeight)/performers.size();
			
		//Pour chaque performer, on récupère la liste de ses activités au sein du workflow
		it = performers.iterator();
		while(it.hasNext())
		{			
			Participant pa = it.next();
			List<Activity> activities= wf.getActivitiesByPerformer(pa);
			Iterator<Activity> it2 = activities.iterator();
			
			while(it2.hasNext())
			{
				Activity ac = it2.next();
				boxes.add(new Box(ac,dimY,dimX));
				dimX+=x;
			}	
			
			dimY+=y;
			
			//remise à la ligne des activités des autres participants
			//dimX=x-performersWidth;
			dimX=performersWidth+ spacingWidth;
		}
		
		
		return boxes;
	}
	
	/**
	 * Permet de générer une liste de Box destinées à êtres placées (Pour l'affichage d'une instance de workflow)
	 * @param boxes Liste de box à afficher
	 * @param wf Workflow dont on doit générer les box d'activity
	 * @param wfProcessKey Attribut Key du WfProcess à afficher
	 * @return Liste de box correspondante
	 */
	public static String displayBoxInstance(List<Box> boxes, Workflow wf, String wfProcessKey)
	{
		String s=" ";
		String html =" ";
		longTab =0;
		largTab =0;
		
		y =boxHeight+spacingHeight;
		
		//Dans un premier temps, il faut générer des fonctions d'affichage
		s+="<script>";
		
		//fenetre d'activité completed
		s+="\nfunction activityWindowTerminated(name, x, y, html) {";
		s+="\n    var win = new Window(name, {className: \"greenlighting\", top:0, right:x, bottom:y, width:"+boxWidth+", height:"+boxHeight+",title:name,";
		s+="\n                          maximizable: false, draggable: false, closable: false, minimizable: false, resizable:false});";
		s+="\n   win.setLocation(x, y);";
		s+="\n   win.setDestroyOnClose();";
		//s+="\n   winsetContent(html, true, true);";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		
		//fenetre d'activité non démarrable
		s+="\nfunction activityWindowDefault(name, x, y, html) {";
		s+="\n    var win = new Window(name, {className: \"greylighting\", top:0, right:x, bottom:y, width:"+boxWidth+", height:"+boxHeight+",title:name,";
		s+="\n                          maximizable: false, draggable: false, closable: false, minimizable: false, resizable:false});";
		s+="\n   win.setLocation(x, y);";
		s+="\n   win.setDestroyOnClose();";
		//s+="\n   winsetContent(html, true, true);";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		
		//fenetre d'activité en attente
		s+="\nfunction activityWindowWaiting(name, x, y, html) {";
		s+="\n    var win = new Window(name, {className: \"bluelighting\", top:0, right:x, bottom:y, width:"+boxWidth+", height:"+boxHeight+",title:name,";
		s+="\n                          maximizable: false, draggable: false, closable: false, minimizable: false, resizable:false});";
		s+="\n   win.setLocation(x, y);";
		s+="\n   win.setDestroyOnClose();";
		//s+="\n   winsetContent(html, true, true);";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		
		//fenetre d'activité en cours
		s+="\nfunction activityWindowRunning(name, x, y, html) {";
		s+="\n    var win = new Window(name, {className: \"orangelighting\", top:0, right:x, bottom:y, width:"+boxWidth+", height:"+boxHeight+",title:name,";
		s+="\n                          maximizable: false, draggable: false, closable: false, minimizable: false, resizable:false});";
		s+="\n   win.setLocation(x, y);";
		s+="\n   win.setDestroyOnClose();";
		//s+="\n   winsetContent(html, true, true);";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		s+="\n</script>";

		
		//affichage tableau des participants
		List<Participant> performers;
		performers = wf.getActivityPerformers();
		Iterator<Participant> it = performers.iterator();
		if(performers.size()==0) return "&nbsp";
		
		//Calcul de la longeur du tableau en fonction du nombre d'activités
		while(it.hasNext())
		{
			Participant pa = it.next();
			List<Activity> activities= wf.getActivitiesByPerformer(pa);
			if (activities.size()>longTab)
				longTab=(x*activities.size())+ performersWidth + spacingWidth;
		}
		//Calcul de la Largeur du tableau en fonction du nombre de participants
		largTab=(y*performers.size())+worflowHeight+spacingHeight;;
		
		//initialisation du tableau
		s+= "<table border=1  height="+largTab+" width="+longTab+">";
		s+= "\n<tr><td width="+performersWidth+" height="+worflowHeight+"><b>"+wf.getName()+"<b></td>";
		s+= "<td><font color=#BFDBFF size=3><b>Actitivity en attente</b></font>, <font color=#CDCDCD size=3><b>Actitivity non demarrée</b></font>, <font color=#ACFCAF size=3><b>Actitivity terminée</b></font>, <font color=#fde1bd size=3><b>Actitivity en cours</b></font></td></tr>";
		
		//affichage des participants
		it = performers.iterator();
		while(it.hasNext())
		{			
			Participant pa = it.next();
			s+="\n<tr><td>"+((pa!=null)?"<a href='Afficheur?action=doGetParticipant&id="+pa.getId()+"'>"+pa.getName()+"</td>":"&nbsp</td>");
			s+= "\n<td>&nbsp </td></tr>";
		}
		s+= "\n</table>";
		
		
		Iterator<Box> it2 = boxes.iterator();
		while (it2.hasNext())
		{
			Box b = it2.next();
			//affichage des boites
			s+= "		" + ModeleTest.activityInstance(wfProcessKey,b.getAct(),b.getPositionX(),b.getPositionY());
			
			//affichage des transitions
			List<Transition> transitions = b.getAct().getTransitionsSortantes();
			Iterator<Transition> it3 = transitions.iterator();
			while (it3.hasNext())
			{
				Transition t = it3.next();
				
				Iterator<Box> it4 = boxes.iterator();
				while (it4.hasNext())
				{
					Box boite =it4.next();
					if (t.getTo()==boite.getAct())
					{
						html+="\n<script>";
						html+=displayTransition(b, boite);
						html+="\n</script>";
					}
				}
				
			}
			//html+="    " + ModeleTest.displayTransition(b.act);	
		}
		
		s+=html;
		
		return s;
	}

	/**
	 * Génération des traits pour les transitions
	 * @param bFrom box de départ de la transition
	 * @param bTo box de fin de la transition
	 * @return String permettant l'affichage HTML(js) des transitions
	 */
	public static String displayTransition(Box bFrom, Box bTo)
	{
		String s=" ";
		int x1 = bFrom.getPositionX();
		int y1 = bFrom.getPositionY();
		int x2 = bTo.getPositionX();
		int y2 = bTo.getPositionY();
		i+=1;
		x1=x1+boxHeight/2;
		x2=x2+boxHeight/2;
		y1=y1+boxWidth/2;
		y2=y2+boxWidth/2;
		
		s+="\nvar jg"+i+" = new jsGraphics();";
		s+="\njg"+i+".setColor(\"#ff0000\"); ";
		s+="\njg"+i+".setStroke(\"5\"); ";	
		s+="\njg"+i+".drawLine("+y1+","+x1+","+y2+","+x2+"); ";
		s+="\njg"+i+".paint();";

		
		return s;
	}
	
	/**
	 * Génération du code HTML/JS des box activity
	 * @param boxes Liste des box
	 * @param wf Workflow dont on veut afficher les activity
	 * @return String permettant l'affichage HTML(js) des Box
	 */
	public static String displayBox(List<Box> boxes, Workflow wf)
	{
		String s=" ";
		String html =" ";
		longTab =0;
		largTab =0;
		
		y =boxHeight+spacingHeight;
		
		s+="<script>";

		//fenetre d'activité non démarrable
		s+="\nfunction activityWindowDefault(name, x, y, html) {";
		s+="\n    var win = new Window(name, {className: \"greylighting\", top:0, right:x, bottom:y, width:"+boxWidth+", height:"+boxHeight+",title:name,";
		s+="\n                          maximizable: false, draggable: false, closable: false, minimizable: false, resizable:false});";
		s+="\n   win.setLocation(x, y);";
		s+="\n   win.setDestroyOnClose();";
		//s+="\n   winsetContent(html, true, true);";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		s+="\n</script>";

		
		//affichage tableau des participants
		List<Participant> performers;
		performers = wf.getActivityPerformers();
		Iterator<Participant> it = performers.iterator();
		if(performers.size()==0) return "&nbsp";
		
		//Calcul de la longeur du tableau en fonction du nombre d'activités
		while(it.hasNext())
		{
			Participant pa = it.next();
			List<Activity> activities= wf.getActivitiesByPerformer(pa);
			if (activities.size()>longTab)
				longTab=(x*activities.size())+ performersWidth + spacingWidth;
		}
		//Calcul de la Largeur du tableau en fonction du nombre de participants
		largTab=(y*performers.size())+worflowHeight+spacingHeight;;
		
		//initialisation du tableau
		s+= "<table border=1  height="+largTab+" width="+longTab+">";
		s+= "\n<tr><td width="+performersWidth+" height="+worflowHeight+"><b>"+wf.getName()+"<b></td>";
		s+= "<td><font color=#CDCDCD size=3><b>Actitivity</b></font></td></tr>";
		
		//affichage des participants
		it = performers.iterator();
		while(it.hasNext())
		{			
			Participant pa = it.next();
			s+="\n<tr><td>"+((pa!=null)?"<a href='Afficheur?action=doGetParticipant&id="+pa.getId()+"'>"+pa.getName()+"</td>":"&nbsp</td>");
			s+= "\n<td>&nbsp </td></tr>";
		}
		s+= "</table>";
		
		s+= "<table border=1 width="+longTab+">";
		s+="\n<tr><td colspan=2><b>Paramètres divers</b></td></tr>";
		s+= "\n<tr><td>Id</td><td>"+wf.getId()+"</td></tr>";
		s+="<tr>";
		s+="<td width="+performersWidth+" >Name</td>";
		s+="<td>"+wf.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Created</td>";
		s+="<td>"+((wf.getCreated()!=null)?wf.getCreated().toString():"?")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<tr>";
		s+="<td>ExtendedAttributes ("+wf.getExtendedAttributes().size()+")</td>";
		s+="<td> "+ModeleTest.listeExtendedAttributes(wf.getExtendedAttributes(),"&workflow="+wf.getId())+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>DataFields ("+wf.getDataFields().size()+")</td>";
		s+="<td> "+ModeleTest.listeDataFields(wf.getDataFields(), "&workflow="+wf.getId())+"</td>";
		s+="</tr>";
		s+= "\n</table>";
		
		Iterator<Box> it2 = boxes.iterator();
		while (it2.hasNext())
		{
			Box b = it2.next();
			//affichage des boites
			s+= "		" + ModeleTest.activity(b.getAct(),b.getPositionX(),b.getPositionY());
			
			//affichage des transitions
			List<Transition> transitions = b.getAct().getTransitionsSortantes();
			Iterator<Transition> it3 = transitions.iterator();
			while (it3.hasNext())
			{
				Transition t = it3.next();
				
				Iterator<Box> it4 = boxes.iterator();
				while (it4.hasNext())
				{
					Box boite =it4.next();
					if (t.getTo()==boite.getAct())
					{
						html+="\n<script>";
						html+=displayTransition(b, boite);
						html+="\n</script>";
					}
				}
			}
		}
		
		s+=html;
		
		return s;
	}

	
	public static String listeDataFields(List<DataField> dataFields, String args)
	{
		String s=" ";
		
		Iterator<DataField> it = dataFields.iterator();
		if(dataFields.size()==0) return "&nbsp";
		while(it.hasNext())
		{
			DataField df = it.next();
			s+="<a href='Afficheur2?action=doGetDataField"+args+"&id="+df.getId()+"'>"+df.getName()+"</a><br>\n";	
		}
		return s;
	}
	
	public static String listeActivity(List<Activity> activities, String args)
	{
		String s=" ";
		String html=" ";
		int l= 0;
		
		s+="\nfunction activityWindow(name, l, html) {";
		s+="\n    var win = new Window(name, {className: \"bluelighting\", top:40, left:l, width:100, height:50,title:name,";
		s+="\n                          maximizable: false, closable: false, minimizable: false, parent: Windows.getWindow(\"master\").getContent()});";
		s+="\n   win.setDestroyOnClose();";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		

		////Dans la box Activity
		Iterator<Activity> it = activities.iterator();
		if(activities.size()==0) return "&nbsp";
		
		while(it.hasNext())
		{
			Activity ac = it.next();
			
			//s+="<a href='Afficheur2?action=doGetActivity"+args+"&id="+ac.getId()+"'>"+((ac.getName()!=null)?ac.getId():ac.getName())+"</a><br>\n";	
			//+ac.isSubflow();
			html = "<table border=0><tr>";
			html += "<td>Id : </td><td>"+ ac.getId()+ "</td></tr>"; 
			html += "<tr><td> Description : </td><td>"+ac.getDescription()+"</td></tr>";
			html += "</table>";
			
			s+="\nactivityWindow('" + ac.getName() + "'," + l + ",'" +html + "');";
			l+=120;
		}
		return s;
	}
	
	public static String listeExtendedAttributes(List<ExtendedAttribute> extendedAttributes,String args)
	{
		String s=" ";
		
		Iterator<ExtendedAttribute> it = extendedAttributes.iterator();
		if(extendedAttributes.size()==0) return "&nbsp";
		while(it.hasNext())
		{
			ExtendedAttribute ea = it.next();
			s+="<a href=Afficheur2?action=doGetExtendedAttribute"+args+"&name="+ea.getName()+">"+ea.getName()+"</a><br>";	
		}
		return s;
	}
	
	public static String listeWorkflows(List<Workflow> workflows)
	{
		String s=" ";
		//int l= 0;
		
		Iterator<Workflow> it = workflows.iterator();
		if(workflows.size()==0) return "&nbsp";
		while(it.hasNext())
		{
			Workflow wf = it.next();
			
			s+="<a href='Afficheur2?action=doGetWorkflow&id="+wf.getId()+"'>"+wf.getName()+"</a><br>\n";	
			//s+="insideWindow(\"" + wf.getName() + "\","+l+");";
			//l+=120;
		}
		return s;
	}	
	
	/**
	 * Liste des instances Shark d'un workflow
	 * @param workflows Workflow dont on cherche les instances
	 * @return Affichage HTML d'une liste d'instances de workflows
	 */
	public static String listeWorkflowInstances(List<Workflow> workflows)
	{
		String s=" ";

		Iterator<Workflow> it = workflows.iterator();
		if(workflows.size()==0) return "&nbsp";
		
		while(it.hasNext())
		{
			Workflow wf = it.next();
			HashMap<String,HashMap<Activity,String>> map = WorkflowPackage.getWorkflowInstancesStates(wf);
			
			Set<String> set = map.keySet();
			Iterator<String> wfProcessMap = set.iterator();
			
			s+="<tr><td>Instances de "+wf.getName()+" ("+map.size()+")</td><td>";
			
			while(wfProcessMap.hasNext())
			{
				String wfProcessKey = wfProcessMap.next();
				try{
					s+="<a href='Afficheur2?action=doGetWorkflowInstance&workflow="+wf.getId()+"&id="+wfProcessKey+"'>"+wfProcessKey+"</a><br>\n";	
				}catch(Exception err){err.printStackTrace();}
			}
			
			s+="</td></tr>";
		}
		return s;
	}
}
