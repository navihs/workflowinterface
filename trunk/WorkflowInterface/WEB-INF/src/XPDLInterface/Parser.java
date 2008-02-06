package XPDLInterface;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;

import shark.InitComponent;

/**
 * Classe qui parse avec JDOM le fichier XPDL et qui l'intègre au modèle objet
 * @author Laurent
 *
 */
public class Parser {
	
	/**
	 * racine du domcument XPDL
	 */
	private org.jdom.Element racine;
	
	/**
	 * Document XPDL
	 */
	private org.jdom.Document document;
	
	/**
	 * Objet conteneur des workflow (représenté par l'élement Package dans le XPDL)
	 */
	private WorkflowPackage workflowPackage;
	
	private Namespace ns = Namespace.getNamespace("http://www.wfmc.org/2002/XPDL1.0");
	
	public Parser()
	{
		initParser();
	}
	
	/**
	 * Créé le document à partir du fichier xpdl<br>
	 * Initialise la racine
	 */
	private void initParser()
	{
		 SAXBuilder sxb = new SAXBuilder();
	     try
	     {
           document = sxb.build(new File(InitComponent.getFilesDir()+"repositoryXPDL/soumission_article.xpdl"));
           //document = sxb.build(new File(InitComponent.getFilesDir()+"repositoryXPDL/DAI3.1.xpdl"));
	     }
	     catch(Exception e){}
	     racine = document.getRootElement();
	}
	
	/**
	 * Lance le parsing du fichier XPDL permettant de remplir le modèle objet
	 * @return le WorkflowPackage contenant tout le modèle objet
	 */
	public WorkflowPackage parsePackage()
	{
		//Création de l'objet WorkflowPackage
		this.workflowPackage = new WorkflowPackage(racine.getAttribute("Id").getValue(),racine.getAttribute("Name").getValue());
		
		//Lecture du header
		Element packageHeader = racine.getChild("PackageHeader",ns);
		
		//Lecture de la date de l'élément CREATED
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		try{
				Element created = packageHeader.getChild("Created",ns);
				workflowPackage.setCreated(dateFormat.parse(created.getValue()));
		}catch(Exception err){}
		
		//Parsing de l'élément Participants
		if(racine.getChild("Participants",ns)!=null)
			workflowPackage.setParticipants(parseParticipants(racine.getChild("Participants",ns)));

		//Parsing de l'élément DataFields
		if(racine.getChild("DataFields",ns)!=null)
			workflowPackage.setDataFields(parseDataFields(racine.getChild("DataFields",ns)));
		
		//Parsing de l'élément WorkflowProcesses
		if(racine.getChild("WorkflowProcesses",ns)!=null)
			workflowPackage.setWorkflows(parseWorkflowProcesses(racine.getChild("WorkflowProcesses",ns)));
		
		//Parsing de l'élément ExtendedAttributes
		if(racine.getChild("ExtendedAttributes",ns)!=null)
			workflowPackage.setExtendedAttributes(parseExtendedAttributes(racine.getChild("ExtendedAttributes",ns)));
	
		return workflowPackage;
	}
	
	/**
	 * Lance le parsing d'un noeud "Participants"<br>
	 * Celui-ci peut-être contenu dans un noeud "Package" ou "WorkflowProcess"
	 * @param participants Element XML "Participants" à parser
	 * @return Liste des "Participant" parsés
	 */
	private List<Participant> parseParticipants(Element participants)
	{
		//Liste des participants à retourner
		List<Participant> participantsReturn= new ArrayList();
		
		//Liste des noeuds XML "Participant" sous le noeud "Participants"
		List<Element> noeudsParticipant = participants.getChildren("Participant",ns);
		Iterator<Element> itParticipant = noeudsParticipant.iterator();
		
		//Parcourt de tous les noeuds "Participant"
		while (itParticipant.hasNext())
		{
			Element participant = itParticipant.next();
			String type = participant.getChild("ParticipantType",ns).getAttribute("Type").getValue();
			String desc="";
			if(participant.getChild("Description",ns)!=null)
				desc = participant.getChild("Description",ns).getText();
			
			Participant p = new Participant(participant.getAttribute("Id").getValue(), participant.getAttribute("Name").getValue(), type, desc);
			
			participantsReturn.add(p);			
		}
		
		return participantsReturn;
	}

	/**
	 * Lance le parsing d'un noeud "DataFields"<br>
	 * Celui-ci peut-être contenu dans un noeud "Package" ou "WorkflowProcess"
	 * @param datafields Element XML "DataFields" à parser
	 * @return Liste des "DataField" parsés
	 */
	private List<DataField> parseDataFields(Element datafields)
	{
		//Liste des DataField à retourner
		List<DataField> datafieldsReturn = new ArrayList<DataField>();
		
		//Liste des noeuds DataField à parser
		List<Element> noeudsDataField = datafields.getChildren("DataField",ns);
		Iterator<Element> itDataField = noeudsDataField.iterator();
			
		//Parcourt et parsing des noeuds DataField
		while (itDataField.hasNext()) 
		{
			Element dataField = itDataField.next();

			String idValue      = dataField.getAttribute("Id").getValue();
            String isArrayValue = dataField.getAttribute("IsArray").getValue();
            String nameValue    = (dataField.getAttribute("Name")==null?idValue+" (no name)":dataField.getAttribute("Name").getValue());
			
			//DataField d = new DataField(dataField.getAttribute("Id").getValue(),
			//                            dataField.getAttribute("Name").getValue(),
			//                            dataField.getAttribute("IsArray").getValue());
			DataField d = new DataField(idValue,nameValue,isArrayValue);

			if(dataField.getChild("Description",ns)!=null)
				d.setDescription(dataField.getChild("Description",ns).getText());
			datafieldsReturn.add(d);		
		}
		
		return datafieldsReturn;	
	}
	
	/**
	 * Lance le parsing d'un noeud "WorkflowProcesses"<br>
	 * Celui-ci est contenu dans un noeud "Package" (racine)
	 * @param workflowProcesses Noeud XML "WorkflowProcesses" à parser
	 * @return Liste de "Workflow" parsés
	 */
	private List<Workflow> parseWorkflowProcesses(Element workflowProcesses)
	{
		//Liste des Workflowà retourner
		List<Workflow> workflowProcessesReturn = new ArrayList<Workflow>();
		
		//Liste des noeuds WorkflowProcess à parser
		List<Element> noeudsWorkflowProcess = workflowProcesses.getChildren("WorkflowProcess",ns);
		Iterator<Element> itWorkflowProcess = noeudsWorkflowProcess.iterator();
			
		//Parcourt de tous les noeuds "WorkflowProcess"
		while(itWorkflowProcess.hasNext())
		{
			Element workflowProcess = itWorkflowProcess.next();
			
			Workflow workflow = workflowPackage.workflowExist(workflowProcess.getAttribute("Id").getValue());
			
			//Si le workflow n'existe pas, on le parse
			if (workflow==null)
			{
				workflow = parseWorkflow(workflowProcess);
				workflowProcessesReturn.add(workflow);
			}
			//Si le workflow existe déjà , on ne l'ajoute pas à la liste des workflow du package;
		}
		
		return workflowProcessesReturn;
	}
	
	/**
	 * Lance le parsing d'un noeud "WorkflowProcess"<br>
	 * Celui-ci est contenu dans un noeud "WorkflowProcesses"
	 * @param workflowProcess Noeud XML "WorkflowProcess" à parser
	 * @return Retourne un Workflow parsé et construit
	 */
	private Workflow parseWorkflow(Element workflowProcess)
	{
		//Attribute created du workflow
		String created= workflowProcess.getChild("ProcessHeader",ns).getChild("Created",ns).getText();
		
		//Création du nouvel objet Workflow
		Workflow workflow = new Workflow(workflowProcess.getAttribute("Id").getValue(),workflowProcess.getAttribute("Name").getValue(), created);
		
		//Parsing des Activities si le noeud existe
		if(workflowProcess.getChild("Activities",ns)!=null)
			workflow.setActivities(parseActivities(workflowProcess.getChild("Activities",ns),workflow));
		
		//Parsing des Participants si le noeud existe
		if(workflowProcess.getChild("Participants",ns)!=null)
			this.workflowPackage.setParticipants(parseParticipants(workflowProcess.getChild("Participants",ns)));
		
		//Parsing des DataFields si le noeud existe
		if(workflowProcess.getChild("DataFields",ns)!=null)
			workflow.setDataFields(parseDataFields(workflowProcess.getChild("DataFields",ns)));
		
		//Parsing des FormalParameters si le noeud existe
		if(workflowProcess.getChild("FormalParameters",ns)!=null)
			workflow.setFormalParameters(parseFormalParameters(workflowProcess.getChild("FormalParameters",ns)));
		
		//Parsing des Transitions si le noeud existe
		if(workflowProcess.getChild("Transitions",ns)!=null)
			workflow.setTransitions(parseTransitions(workflowProcess.getChild("Transitions",ns),workflow));
		
		//Parsing des ExtendedAttributes si le noeud existe
		if(workflowProcess.getChild("ExtendedAttributes",ns)!=null)
			workflow.setExtendedAttributes(parseExtendedAttributes(workflowProcess.getChild("ExtendedAttributes",ns)));
		
		return workflow;
	}
	
	/**
	 * Lance le parsing d'un noeud "Activities"<br>
	 * Celui-ci est contenu dans un noeud "WorkflowProcess"
	 * @param activities Noeud XML "Activities" à parser
	 * @param workflowParent Workflow conteneur des activités à parser
	 * @return Liste des "Activity" du workflow donné
	 */
	private List<Activity> parseActivities(Element activities,Workflow workflowParent)
	{
		//Liste des Activity à retourner
		List<Activity> activitiesReturn = new ArrayList<Activity>();
		
		//Liste des noeuds Activity
		List<Element> noeudsActivity = activities.getChildren("Activity",ns);
		Iterator<Element> itActivity = noeudsActivity.iterator();
		
		//Parcourt des noeuds Activities
		while(itActivity.hasNext())
		{
			Element noeudActivity = itActivity.next();
			
			//On parse le noeud activity passé en paramètre
			Activity activity = parseActivity(noeudActivity,workflowParent);
			
			//On test si l'Activity implemente un subflow
			if (activity.isSubflow())
			{	
				String workflowId = noeudActivity.getChild("Implementation",ns).getChild("SubFlow",ns).getAttribute("Id").getValue();
				
				//On test si un Workflow avec le même ID que celui de l'Activity existe 
				Workflow workflow = workflowPackage.workflowExist(workflowId);
				
				//Si le workflow lié à l'activité n'existe pas encore, il faut le trouver et le parser
				if(workflow==null)
				{
					//on recupère le workflow à parser
					List<Element> listeWPs = racine.getChild("WorkflowProcesses",ns).getChildren("WorkflowProcess",ns);
					Iterator<Element> itWP = listeWPs.iterator();
					
					//parcours des Workflows
					while(itWP.hasNext())
					{
						Element workflowProcess = itWP.next();
						
						//Si l'attribut id de l'element Workflow est égal à l'attribut du Worflow à parser
						if(workflowProcess.getAttribute("Id").getValue().equals(workflowId))
						{
							workflow = parseWorkflow(workflowProcess);
							break;
						}
					}				
				}
				
				//On lie le workflow à l'activité une fois celui-ci récupéré
				activity.setSubflow(workflow);
			}
			activitiesReturn.add(activity);
		}
		return activitiesReturn;
	}
	
	
	/**
	 * Lance le parsing d'un noeud "Activity"<br>
	 * Celui-ci est contenu dans un noeud "Activities"
	 * @param activity Noeud XML "Activity" à parser
	 * @param workflowParent Workflow conteneur de l'activity à parser
	 * @return Retourne l'objet Activity parsé
	 */
	private Activity parseActivity(Element noeudActivity,Workflow workflowParent)
	{
		//Activity à retourner
		Activity activity;

		//Permet de savoir si l'activité implémente un subflow
		boolean implementation = (noeudActivity.getChild("Implementation",ns).getChild("SubFlow",ns)!=null);
		
		//Création de l'objet Activity
		if(noeudActivity.getAttribute("Name")==null)
			activity = new Activity(workflowParent,noeudActivity.getAttribute("Id").getValue(),implementation);
		else
			activity = new Activity(workflowParent,noeudActivity.getAttribute("Id").getValue(),noeudActivity.getAttribute("Name").getValue(),implementation);
		
		//Si l'activity possède un Performer on le lui ajoute
		if(noeudActivity.getChild("Performer",ns)!=null)
		{
			Participant p=workflowPackage.getParticipantById(noeudActivity.getChild("Performer",ns).getText());
			activity.setPerformer(p); 
		}
		
		//Si l'activity possède des TransitionRestrictions, on les lui ajoute
		if(noeudActivity.getChild("TransitionRestrictions",ns)!=null)
		{
			if(noeudActivity.getChild("TransitionRestrictions",ns).getChild("TransitionRestriction",ns).getChild("Split",ns)!=null)
				activity.setTransitionRestrictionSplit(noeudActivity.getChild("TransitionRestrictions",ns).getChild("TransitionRestriction",ns).getChild("Split",ns).getAttributeValue("Type"));
		
			if(noeudActivity.getChild("TransitionRestrictions",ns).getChild("TransitionRestriction",ns).getChild("Join",ns)!=null)
				activity.setTransitionRestrictionJoin(noeudActivity.getChild("TransitionRestrictions",ns).getChild("TransitionRestriction",ns).getChild("Join",ns).getAttributeValue("Type"));
		}
		
		//Ajout des ExtendedAttributes s'ils existent
		if(noeudActivity.getChild("ExtendedAttributes",ns)!=null)
			activity.setExtendedAttributes(parseExtendedAttributes(noeudActivity.getChild("ExtendedAttributes",ns)));
		
		return activity;
	}
	
	/**
	 * Lance le parsing d'un noeud "FormalParameter"<br>
	 * Celui-ci est contenu dans un noeud "WorkflowProcess"
	 * @param formalParameters Noeud XML "FormalParameters" à parser
	 * @return Liste des objets FormalParameter parsés
	 */
	private List<FormalParameter> parseFormalParameters(Element formalParameters)
	{
		//Liste de FormalParameter à retourner
		List<FormalParameter> formalParametersReturn=new ArrayList<FormalParameter>();
		
		//Liste des noeuds FormalParameter
		List<Element> noeudFormalParameters = formalParameters.getChildren("FormalParameter",ns); //.getChild("FormalParameter");
		Iterator<Element> itFormalParameter = noeudFormalParameters.iterator();
		
		//Parcourt de la liste des FormalParameter
		while (itFormalParameter.hasNext()) 
		{
			Element noeudFormalparameter = itFormalParameter.next();
			
			String dataType = noeudFormalparameter.getChild("DataType",ns).getChild("BasicType",ns).getAttribute("Type").getValue();
			String description = noeudFormalparameter.getChild("Description",ns).getText();
			
			FormalParameter formalParameter = new FormalParameter(noeudFormalparameter.getAttribute("Id").getValue(), noeudFormalparameter.getAttribute("Mode").getValue(), dataType, description);
			
			formalParametersReturn.add(formalParameter);	
		}
		return formalParametersReturn;	
	}
	
	/**
	 * Lance le parsing d'un noeud "Transitions"<br>
	 * Celui-ci est contenu dans un noeud "WorkflowProcess"
	 * @param transitions Noeud XML "Transitions" à parser
	 * @param workflow Workflow qui permettra de retrouver l'activité
	 * @return Retourne une liste de Transitions parsées
	 */
	private List<Transition> parseTransitions(Element transitions,Workflow workflow)
	{
		List<Transition> transitionsReturn=new ArrayList<Transition>();
		List<Element> noeudsTransition = transitions.getChildren("Transition",ns);
		Iterator<Element> itTransition = noeudsTransition.iterator();

		String conditionType;
		String condition;
			
		while (itTransition.hasNext()) 
		{
			Element noeudTransition = itTransition.next();
			conditionType=null;
			condition=null;
			if(noeudTransition.getChild("Condition",ns)!=null)
			{
				conditionType= noeudTransition.getChild("Condition",ns).getAttribute("Type").getValue();
				condition = noeudTransition.getChild("Condition",ns).getText();
			}
			//On ajoute aux activités cibles la nouvelle transition crée
			Activity from =workflow.getActivityById(noeudTransition.getAttribute("From").getValue());
			Activity to = workflow.getActivityById(noeudTransition.getAttribute("To").getValue());
			
			Transition transition = new Transition(noeudTransition.getAttribute("Id").getValue(),conditionType,condition,from,to);
			
			transitionsReturn.add(transition);
		}
		return transitionsReturn;
	}
	
	/**
	 * Lance le parsing d'un noeud "ExtendedAttributes"<br>
	 * Celui-ci est contenu dans un noeud "WorkflowProcess", "Activity" ou "Package"
	 * @param extendedAttributes Noeud XML "ExtendedAttributes" à parser
	 * @return Liste d'ExtendedAttribute parsés
	 */
	private List<ExtendedAttribute> parseExtendedAttributes(Element extendedAttributes)
	{
		List<ExtendedAttribute> extendedAttributesReturn = new ArrayList<ExtendedAttribute>();
		List<Element> noeudsEAs = extendedAttributes.getChildren("ExtendedAttribute",ns);
		Iterator<Element> itEA = noeudsEAs.iterator();
					
		while (itEA.hasNext()) 
		{
			Element noeudEA = itEA.next();
			ExtendedAttribute eA = new ExtendedAttribute(noeudEA.getAttribute("Name").getValue(),noeudEA.getAttribute("Value").getValue());
			extendedAttributesReturn.add(eA);
		}
		return extendedAttributesReturn;
	}
	

}
