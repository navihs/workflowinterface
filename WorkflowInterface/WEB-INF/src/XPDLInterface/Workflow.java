package XPDLInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

import org.jdom.Element;

/**
 * Classe représentant un noeud "WorkflowProcess"
 * @author Laurent
 *
 */
public class Workflow{

	/**
	 * Liste représentant un noeud "Activities"
	 */
	private List<Activity> activities;
	public List<Activity> getActivities(){return this.activities;}
	public void setActivities(List<Activity> wfActivities){this.activities = wfActivities;}

	/**
	 * Liste représentant un noeud "DataFields"
	 */
	private List<DataField> dataFields;
	public List<DataField> getDataFields(){return this.dataFields;}
	public void setDataFields(List<DataField> wfDataFields){this.dataFields = wfDataFields;}

	/**
	 * Liste représentant un noeud "Activities"
	 */
	private List<ExtendedAttribute> extendedAttributes;
	public List<ExtendedAttribute> getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List<ExtendedAttribute> wfExtendedAttributes){this.extendedAttributes = wfExtendedAttributes;}
	
	/**
	 * Liste représentant un noeud "FormalParameters"
	 */
	private List<FormalParameter> formalParameters;
	public List<FormalParameter> getFormalParameters(){return this.formalParameters;}
	public void setFormalParameters(List<FormalParameter> wfFormalParameters){this.formalParameters = wfFormalParameters;}
	
	/**
	 * Liste représentant un noeud "Transitions"
	 */
	private List<Transition> transitions;
	public List<Transition> getTransitions(){return this.transitions;}
	public void setTransitions(List<Transition> wfTransitions){this.transitions = wfTransitions;}
	
	/**
	 * Identifiant unique du workflow
	 */
	private String id;
	public String getId(){return this.id;}

	/**
	 * Nom du workflow
	 */
	private String name;
	public String getName(){return this.name;}

	/**
	 * Date de création du workflow
	 */
	private Date created;
	public Date getCreated(){return this.created;}

	/**
	 * Création d'un workflow
	 * @param wfId Attribut Id du noeud Workflow
	 * @param wfName Attribut Name du noeud Workflow
	 * @param wfCreated Element Created du noeud Workflow
	 */
	public Workflow(String wfId, String wfName, String wfCreated)
	{
		this.id = wfId;
		this.name = wfName;
		
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		try{
			this.created = dateFormat.parse(wfCreated);
		}catch(Exception err)
		{
			this.created = null;
		}
	}
	
	/**
	 * Permet d'obtenir une activité à partir de son Id
	 * @param id Identifiant de l'activité à rechercher
	 * @return Retourne l'objet Activity correspondant
	 */
	public Activity getActivityById(String id)
	{
		Iterator<Activity> it = this.activities.iterator();
		while(it.hasNext())
		{
			Activity act = it.next();
			if(act.getId().equals(id))
				return act;
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir un DataField à partir de son Id
	 * @param id Identifiant du DataField à rechercher
	 * @return Retourne l'objet DataField correspondant
	 */
	public DataField getDataFieldById(String id)
	{
		Iterator<DataField> it = this.dataFields.iterator();
		while(it.hasNext())
		{
			DataField df = it.next();
			if(df.getId().equals(id))
				return df;
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir un ExtendedAttribute à partir de son Name
	 * @param name Name du ExtendedAttribute à rechercher
	 * @return Retourne l'objet ExtendedAttribute correspondant
	 */
	public ExtendedAttribute getExtendedAttributeByName(String name)
	{
		Iterator<ExtendedAttribute> it = this.extendedAttributes.iterator();
		while(it.hasNext())
		{
			ExtendedAttribute ea = it.next();
			if(ea.getName().equals(name))
				return ea;
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir la liste des Participants de toutes les activités du workflow
	 * @return Liste de "Participant"
	 */
	public List<Participant> getActivityPerformers()
	{
		List<Participant> performers=new ArrayList<Participant>();
		Iterator<Activity> itA = this.activities.iterator();
		while(itA.hasNext())
		{
			Activity act = itA.next();
			if(act.getPerformer()!=null && !performers.contains(act.getPerformer()))
				performers.add(act.getPerformer());
		}
		return performers;
	}
	
	/**
	 * Permet d'obtenir la liste des "Activity" des quelles un "Participant" est performer
	 * @param p Participant dont on recherche les "Activity"
	 * @return Liste d'Activity correspondantes
	 */
	public List<Activity> getActivitiesByPerformer(Participant p)
	{
		List<Activity> performerActivities=new ArrayList<Activity>();
		
		Iterator<Activity> itA = this.activities.iterator();
		while(itA.hasNext())
		{
			Activity act = itA.next();
			if(act.getPerformer()==p)
				performerActivities.add(act);
		}
		return performerActivities;
	}
}
