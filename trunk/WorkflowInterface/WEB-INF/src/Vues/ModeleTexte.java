package Vues;
import XPDLInterface.*;
import shark.WorkflowWrapper;
import java.util.*;

import org.enhydra.shark.api.client.wfmodel.WfActivity;
import org.enhydra.shark.api.client.wfmodel.WfProcess;

public class ModeleTexte{
	
	public static String listeParticipants(List<Participant> participants)
	{
		String p=" ";
		Iterator<Participant> it = participants.iterator();
		while(it.hasNext())
		{
			Participant part = it.next();
			p+="<a href='Afficheur?action=doGetParticipant&id="+part.getId()+"'>"+part.getName()+"</a><br>\n";	
		}
		return p;
	}

	public static String activity(Activity a)
	{
		HashMap<Activity,String> mapEtats = ModeleTexte.getWorkflowState(a.getWorkflowParent());
		
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>Activity</font>";
		s+="</td></tr>";
		s+="<tr><td>Etat</td><td>"+mapEtats.get(a)+"</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+a.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Name</td>";
		s+="<td>"+a.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Descripion</td>";
		s+="<td>"+a.getDescription()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>ExtendedAttributes ("+a.getExtendedAttributes().size()+")</td>";
		s+="<td>"+ModeleTexte.listeExtendedAttributes(a.getExtendedAttributes(),"&workflow="+a.getWorkflowParent().getId()+"&activity="+a.getId())+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Implementation</td>";
		s+="<td>"+a.getImplementation()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Limit</td>";
		s+="<td>"+a.getLimit()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Performer</td>";
		s+="<td>"+((a.getPerformer()!=null)?"<a href='Afficheur?action=doGetParticipant&id="+a.getPerformer().getId()+"'>"+a.getPerformer().getName()+"</td>":"&nbsp</td>");
		s+="</tr>";
		s+="<tr>";
		s+="<td>Route</td>";
		s+="<td>"+a.getRoute()+"</td>";
		s+="</tr>";
		if(a.isSubflow())
		{
			s+="<tr>";
			s+="<td>Subflow</td>";
			s+="<td><a href='Afficheur?action=doGetWorkflow&id="+a.getSubflow().getId()+"'>"+a.getSubflow().getName()+"</td>";
			s+="</tr>";
		}
		s+="<tr>";
		s+="<td>Transition Restriction Join</td>";
		s+="<td>"+a.getTransitionRestrictionJoin()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Transition Restriction Split</td>";
		s+="<td>"+a.getTransitionRestrictionSplit()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Transitions ("+a.getTranstions().size()+")</td>";
		s+="<td>"+((a.getTranstions()!=null)?listeTransitions(a.getTranstions()):":&nbsp")+"</td>";
		s+="</table>";
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
		s+="<td>"+ModeleTexte.listeExtendedAttributes(t.getExtendedAttributes(),"&transition="+t.getId()+"")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>From</td>";
		s+="<td><a href='Afficheur?action=doGetActivity&workflow="+t.getFrom().getWorkflowParent().getId()+"&id="+t.getFrom().getId()+"'>"+t.getFrom().getName()+"</a></td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>To</td>";
		s+="<td><a href='Afficheur?action=doGetActivity&workflow="+t.getFrom().getWorkflowParent().getId()+"&id="+t.getTo().getId()+"'>"+t.getTo().getName()+"</a></td>";
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
		s+="<td> "+ModeleTexte.listeDataFields(wp.getDataFields(),"")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>ExtendedAttributes ("+wp.getExtendedAttributes().size()+")</td>";
		s+="<td> "+ModeleTexte.listeExtendedAttributes(wp.getExtendedAttributes(),"")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Participants ("+wp.getParticipants().size()+")</td>";
		s+="<td> "+ModeleTexte.listeParticipants(wp.getParticipants())+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Workflows ("+wp.getWorkflows().size()+")</td>";
		s+="<td> "+ModeleTexte.listeWorkflows(wp.getWorkflows())+"</td>";
		s+="</tr>";
		s+="</table>";
		return s;
	}

	public static String workflow(Workflow wf)
	{
		String s=" ";
		s+="<table border=1 cellspacing=0 cellspacing=0>";
		s+="<tr><td colspan=2 align=center>Workflow</td></tr>";
		s+="<tr>";
		s+="<td>Id</td>";
		s+="<td>"+wf.getId()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Name</td>";
		s+="<td>"+wf.getName()+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Created</td>";
		s+="<td>"+((wf.getCreated()!=null)?wf.getCreated().toString():"?")+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>Activities ("+wf.getActivities().size()+")</td>";
		s+="<td> "+ModeleTexte.listeActivity(wf.getActivities(), "&workflow="+wf.getId())+"</td>";
		s+="</tr>";
		s+="<tr>";
		s+="<td>ExtendedAttributes ("+wf.getExtendedAttributes().size()+")</td>";
		s+="<td> "+ModeleTexte.listeExtendedAttributes(wf.getExtendedAttributes(),"&workflow="+wf.getId())+"</td>";
		s+="</tr>";
		s+="</table>";
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
			s+="<a href='Afficheur?action=doGetDataField"+args+"&id="+df.getId()+"'>"+df.getName()+"</a><br>\n";	
		}
		return s;
	}
	
	public static String listeTransitions(List<Transition> transitions)
	{
		String s=" ";
		
		Iterator<Transition> it = transitions.iterator();
		if(transitions.size()==0) return "&nbsp";
		while(it.hasNext())
		{
			Transition tr = it.next();
			s+="<a href='Afficheur?action=doGetTransition&id="+tr.getId()+"'>"+tr.getId()+"</a><br>\n";	
		}
		return s;
	}
	
	public static String listeActivity(List<Activity> activities, String args)
	{
		String s=" ";
		
		Iterator<Activity> it = activities.iterator();
		if(activities.size()==0) return "&nbsp";
		while(it.hasNext())
		{
			Activity ac = it.next();
			s+="<a href='Afficheur?action=doGetActivity"+args+"&id="+ac.getId()+"'>"+ac.getName()+"</a><br>\n";	
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
			s+="<a href='Afficheur?action=doGetExtendedAttribute"+args+"&name="+ea.getName()+"'>"+ea.getName()+"</a><br>\n";	
		}
		return s;
	}
	
	public static String listeWorkflows(List<Workflow> workflows)
	{
		String s=" ";
		
		Iterator<Workflow> it = workflows.iterator();
		if(workflows.size()==0) return "&nbsp";
		while(it.hasNext())
		{
			Workflow wf = it.next();
			s+="<a href='Afficheur?action=doGetWorkflow&id="+wf.getId()+"'>"+wf.getName()+"</a><br>\n";	
		}
		return s;
	}
	
	/**
	 * Permet d'obtenir un map faisant correspondre une Activity � une String donnant son �tat Shark � partir d'un Workflow donn�
	 * liste des �tats possibles :	 
	 * <ul>
	 *   <li>open.running</li>
	 *   <li>open.not_running.not_started</li>
	 *   <li>open.not_running.suspended</li>
	 *   <li>closed.completed</li>
	 *   <li>closed.terminated</li>
	 *   <li>closed.aborted</li>
   	 * </ul>
   	 * @param w Workflow dont on veut connaitre l'�tat des activit�s;
	 * @return Map ou une Activity correspond � un �tat sous formede String
	 */
	public static HashMap<Activity, String> getWorkflowState(Workflow w)
	{
		HashMap<Activity, String> activitiesMap = new HashMap<Activity, String>();
		/*Iterator<Activity> itA = w.getActivities().iterator();
		while(itA.hasNext())
		{
			activitiesMap.put(itA.next(), "open.running");
		}*/
		
		try{
			System.out.println("AllActivities");
			
			//On lit le map de toutes les activit�s et workflow de shark
			HashMap<WfProcess, WfActivity[]> allActivities = WorkflowWrapper.getAll(true);
			
			System.out.println(allActivities.size());
			
			Set<WfProcess> s = allActivities.keySet();
			Iterator<WfProcess> it = s.iterator();
			
			//On parcourt chacun des WfProcess � la recherche de celui qui nous interesse
			while(it.hasNext())
			{
				WfProcess wf = it.next();
				//Si on a trouv� le bon WfProcess
				if(WorkflowWrapper.getName(wf)==w.getName())
				{
					//On r�cup�re le tableau de WfActivity correspondant et on le parcourt
					WfActivity[] wfA = allActivities.get(wf);
					for(int i=0;i<wfA.length;i++)
					{
						WfActivity wfActivity = wfA[i];
						Activity activity = w.getActivityByName(WorkflowWrapper.getName(wfActivity));
						String state = WorkflowWrapper.getState(wfActivity);
						
						//On ajoute � notre map l'objet Activity et la String state correspondante
						activitiesMap.put(activity, state);
					}
					break;
				}
			}
			
		}catch(Exception err)
		{
			System.out.println(err.toString());
			System.out.println("Erreur dans la cr�ation du Map");
		}
		
		
		return activitiesMap;
	}
}
