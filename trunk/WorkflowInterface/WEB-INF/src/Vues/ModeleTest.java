package Vues;
import XPDLInterface.*;

import java.util.*;

public class ModeleTest{
	
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

	public static String activity(Activity a, int x, int y)
	{
		String s=" ";
		String html=" ";
		
		html = "<table border=0><tr>";
		html += "<td>Id : </td><td>"+ a.getId()+ "</td></tr>"; 
		html += "<tr><td> Description : </td><td>"+a.getDescription()+"</td></tr>";
		html += "</table>";
		s+="\n<script>";
		s+="\nactivityWindow('" + a.getName() + "'," + x + "," + y + ",'" +html + "');";
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
		s+="</table>";
		return s;
		
		
		/* Test WorflowPackage dans une box /*
		/* 
		String s=" ";
		s+="\n<div id=\"content\" style=\"display:none\">";
		//s+="\n<a href=\"#\" onclick=\"insideWindow()\">open a window inside this window</a>";
		
		s+="\n</div>";
		s+="<script>";
		s+="\nfunction openPopup(){";
		s+="\nvar master = new Window(\"master\", {className: \"alphacube\", width:1000, height:300,title:\"WorkflowPackage\"});";
		s+="\nmaster.setContent(\"content\");";
		s+="\nmaster.setDestroyOnClose();";
		s+="\nmaster.showCenter();";
		s+="\n}";
		
		s+="\nfunction insideWindow(name, l) {";
		s+="\n    var win = new Window(name, {className: \"alphacube\", top:40, left:l, width:100, height:50,title:name,";
		s+="\n                          maximizable: false, minimizable: false, parent: Windows.getWindow(\"master\").getContent()});";
		s+="\n   win.setDestroyOnClose();";
		s+="\n   win.show();";
		s+="\n  }";
		s+="\nopenPopup();";
		s+=ModeleTest.listeWorkflows(wp.getWorkflows());
		s+="\n</script>";
		
		return s; */
		 
		 
	}

	public static String workflow(Workflow wf)
	{

		String s=" ";
		String html =" ";
		int x =0;
		int y =0;
		int dimX =0;
		int dimY =0;
	//	s+="\n<div id=\"content\" style=\"display:none\">";
		s+="<script>";
		//s+="\n<a href=\"#\" onclick=\"insideWindow()\">open a window inside this window</a>";
		
		/*fenetre principal
		s+="\n</div>";
		s+="<script>";
		s+="\nvar master = new Window(\"master\", {className: \"alphacube\", width:1000, height:300,title:\""+wf.getName()+"\"});";
		s+="\nfunction openPopup(){";
		s+="\nmaster.setContent(\"content\");";
		s+="\nmaster.setDestroyOnClose();";
		s+="\nmaster.showCenter();";
		s+="\n}";
		
		s+="\nopenPopup();";*/
		
		//fenetre d'activité
		s+="\nfunction activityWindow(name, x, y, html) {";
		s+="\n    var win = new Window(name, {className: \"alphacube\", top:40, right:x, bottom:y, width:220, height:100,title:name,";
		s+="\n                          maximizable: false, draggable: false, closable: false, minimizable: false});";
		s+="\n   win.setLocation(x, y);";
		s+="\n   win.setDestroyOnClose();";
		//s+="\n   winsetContent(html, true, true);";
		s+="\n   win.setHTMLContent(html);";
		s+="\n   win.show();";
		s+="\n  }";
		s+="\n</script>";
		
		//affichage tableau des participants
		html = "<table border=1  height=\"80%\" width=\"75%\">";
		List<Participant> performers;
		performers = wf.getActivityPerformers();
		Iterator<Participant> it = performers.iterator();
		if(performers.size()==0) return "&nbsp";
		html += "\n<tr><td width=\"25%\">"+wf.getName()+"</td></tr>";
		while(it.hasNext())
		{
			y+=1;
			x=0;
			dimY = y*180;
			
			Participant pa = it.next();
			html += "\n<tr><td width=\"25%\">"+pa.getName()+"</td>";
			List<Activity> activities= wf.getActivitiesByPerformer(pa);
			Iterator<Activity> it2 = activities.iterator();
			
			while(it2.hasNext())
			{
				x+=1;
				dimX = x*260;
				Activity ac = it2.next();
				//System.out.println(ac.getName());
				html += "\n		<td>";
				html += "		" + ModeleTest.activity(ac,dimY,dimX);
				html += "\n		</td>";
			}
			html += "\n</tr>";		
			
		}
		
		//html += "<tr><td></td><td></td></tr>";	
		html += "\n</table>";	
		s+=html;
		
		//s+= "\nmaster.setHTMLContent("+html+");";
		

		//s+=ModeleTest.listeActivity(wf.getActivities(), "&workflow="+wf.getId());
		
		//s+="\n</script>";
		
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
		s+="\n    var win = new Window(name, {className: \"alphacube\", top:40, left:l, width:100, height:50,title:name,";
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
			s+="<a href='Afficheur2?action=doGetExtendedAttribute"+args+"&name="+ea.getName()+"'>"+ea.getName()+"</a><br>\n";	
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
}
