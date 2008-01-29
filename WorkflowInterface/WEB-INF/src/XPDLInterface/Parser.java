package XPDLInterface;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;

public class Parser {
	
	private org.jdom.Element racine;
	private org.jdom.Document document;
	private WorkflowPackage workflowPackage;
	
	public Parser()
	{
		initParser();
	}
	
	private void initParser()
	{
		 SAXBuilder sxb = new SAXBuilder();
	     try
	     {
	    	 document = sxb.build(new File("c:\\DAI3.1.xpdl"));
	     }
	     catch(Exception e){}
	     racine = document.getRootElement();
	}
	
	public WorkflowPackage parsePackage()
	{
		/* on lit le Header,
		 * on cr�� un objet WorkflowPackage
		 * on r�cup�re l'�l�ment <Package Id="dossier_accueil_individualise" Name="Dossier d'accueil individualis�" 
		 * que l'on met dans WorkflowPackage.id et WorkflowPackage.name
		 * on r�cup�re l'�l�ment <Created>2006-07-24 13:57:13</Created> que l'on met dans WorkflowPackage.date
		 * on lance parseParticipants("Participants")
		 * on r�cup�re une liste de participant que l'on ajoute � WorkflowPackage.participants
		 * on lance parseDatafields("DataFields")
		 * on r�cup�re une liste de dataFields que l'on ajoute � WorkflowPackage.dataFields
		 * on lance parseWorkflowProcesses("WorkflowProcesses")
		 * on r�cup�re une liste de Worflow que l'on ajoute � WorkflowPackage.workflows
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on r�cup�re une liste d'ExtendedAttribute que l'on ajoute � WorkflowPackage.extendedAttributes
		 */
		
		this.workflowPackage = new WorkflowPackage(racine.getAttribute("Id").getValue(),racine.getAttribute("Name").getValue());
		
		Element packageHeader = racine.getChild("PackageHeader");
		
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		Element created = packageHeader.getChild("Created");
		try{
			workflowPackage.setCreated(dateFormat.parse(created.getValue()));
		}catch(Exception err){}
		
		if(racine.getChild("Participants")!=null)
			workflowPackage.setParticipants(parseParticipants(racine.getChild("Participants")));
		
		if(racine.getChild("DataFields")!=null)
			workflowPackage.setDataFields(parseDataFields(racine.getChild("DataFields")));
		
		if(racine.getChild("WorkflowProcesses")!=null)
			workflowPackage.setWorkflows(parseWorkflowProcesses(racine.getChild("WorkflowProcesses")));
		
		if(racine.getChild("ExtendedAttributes")!=null)
			workflowPackage.setExtendedAttributes(parseExtendedAttributes(racine.getChild("ExtendedAttributes")));
	
		return workflowPackage;
	}
	
	private List<Participant> parseParticipants(Element participants)
	{
		/*Tant qui existe des participants (noeud Participants, balise Participant)
		 * on cr�er un objet Participant
		 * on r�cup�re l'�l�ment <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable l�gal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * fin tant_ue
		 * On renvoit une liste de participant
		 */
		
		List<Participant> participantsReturn= new ArrayList();
		List<Participant> participant = participants.getChildren("Participant");
		Iterator it = participant.iterator();
		Element a;
		//.getChild("Participant");
		String type=null;
		String desc=null;
		
		/*
		//Pour parcourir tous les noeuds
		NodeList listeParticipants = participants.getChildNodes();
		int tailleListParticipants = listeParticipants.getLength();
		int i =0;*/
		
		while (it.hasNext())
		{
			a=(Element)it.next();
			type = a.getChild("ParticipantType").getAttribute("Type").getValue();
			if(a.getChild("Description")!=null)
				desc = a.getChild("Description").getText();
			/*if((type = participant.getChild("ParticipantType").getAttribute("Type").getvalue())!=null)
			//if((type = participant.getChild("ParticipantType").getAttribute("Type").getvalue()!=null)
				//type = participant.getAttribute("Type").getvalue());
			if((desc = participant.getChild("Description").getTextContent())!=null)
			//if(participant.getChild("Description")!=null)
				//desc = participant.getTextContent();
			 */
			Participant p = new Participant(a.getAttribute("Id").getValue(), a.getAttribute("Name").getValue(), type, desc);
			participantsReturn.add(p);			
		}
		return participantsReturn;
	}
	
	private List parseDataFields(Element datafields)
	{
		/*Tant qui existe des datafields (balise Datafields, balise Datafield)
		 * on cr�� un objet DataField
		 * on r�cup�re l'�l�ment <DataField Id="TRS_validation_proposition_medecin_institutionnel" dans DataField.id
		 * IsArray="FALSE" dans DataField.isArray
		 * Name="TRS Validation proposition medecin institutionnel"> dans DataField.name
		 * InitialValue dans DataField.initialValue
		 * 	<DataType> <BasicType Type="BOOLEAN"/> </DataType> </DataField> dans DataField.dataType
		 * fin tant_que
		 * On renvoit une liste de datafield
		 */
		
		List<DataField> datafieldsReturn=new ArrayList();
		List<DataField> datafield = datafields.getChildren("Datafield");//datafields.getChild("DataField");
		Iterator it = datafield.iterator();
		Element a;
		
		/*//Pour parcourir tous les noeuds
		NodeList listeDatafields = datafields.getChildNodes();
		int tailleListDatafields = listeDatafields.getLength();
		int i =0;
		//Element dataType = datafields.getChild("DataType");
		*/
			
		while (it.hasNext()) 
		{
			a = (Element)it.next();
			DataField d = new DataField(a.getAttribute("Id").getValue(), a.getAttribute("Name").getValue(), a.getAttribute("IsArray").getValue());
			datafieldsReturn.add(d);			
		}
		return datafieldsReturn;	
	}
	
	private List parseWorkflowProcesses(Element workflowProcesses)
	{
		/*Tant qu'il existe des workflows (noeud WorkflowProcesses, balise WorkflowProcess)
		 * 	Tant que WorkflowPackage.workflows[i] =! null) // ou .isnext();
		 * 		id = <WorkflowProcess Id="initialisation"
		 * 		si (!WorkflowPackage.workflowExist(id))
		 * 				parseWorkflow(Element workflowProcess);
		 * 		finsi
		 * 		i++;
		 * 	fin tant_que
		 * On renvoit une liste de Workflow
		 * 		
		 */
		List<Workflow> workflowProcessesReturn= new ArrayList();
		List<Workflow> workflowProcess = workflowProcesses.getChildren("WorkflowProcess");
		Iterator it = workflowProcess.iterator();
		Element a;
		Workflow w, newW;
			
		while(it.hasNext())
		{
			a=(Element)it.next();
			w= workflowPackage.workflowExist(a.getAttribute("Id").getValue());
			//si le workflow n'existe pas
			if (w==null)
			{
				newW = parseWorkflow(a);
			}
			//sinon on renvoi le Worflow;
			else
			{
				newW=w;
			}
			workflowProcessesReturn.add(newW);
		}
		
		return workflowProcessesReturn;
	}
	
	private Workflow parseWorkflow(Element workflowProcess)
	{
		 /* on cr�� un objet Workflow
		 * <WorkflowProcess Id="initialisation"  dans Workflow.id
		 * Name="Initialisation DAI"> dans Workflow.name
		 * 	<ProcessHeader> <Created>2006-07-24 15:10:03</Created> </ProcessHeader> dans Workflow.created
		 * on lance parseActivities("Activities")
		 * on r�cup�re une liste d'Activity que l'on ajoute � Workflow.activities
		 * on lance parseDataFields("DataFields")
		 * on r�cup�re une liste de DataFields que l'on ajoute � Workflow.dataFields
		 * on lance parseFormalParameters("FormalParameters")
		 * on r�cup�re une liste de FormalParameters que l'on ajoute � Workflow.dataFields
		 * on lance parseTransitions("Transitions")
		 * on r�cup�re une liste de Transitions que l'on ajoute � Workflow.transitions
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on r�cup�re une liste d'ExtendedAttribute que l'on ajoute � WorkflowPackage.extendedAttributes
		 * On renvoit un Workflow
		 * 
		 */
		String created;
		Element a = workflowProcess;
		
		created= a.getChild("ProcessHeader").getChild("Created").getText();
		Workflow w = new Workflow(a.getAttribute("Id").getValue(),a.getAttribute("Name").getValue(), created);
		
		if(a.getChild("Activities")!=null)
			w.setActivities(parseActivities(a.getChild("Activities"),w));
		if(a.getChild("DataFields")!=null)
			w.setDataFields(parseDataFields(a.getChild("DataFields")));
		if(a.getChild("FormalParameters")!=null)
			w.setFormalParameters(parseFormalParameters(a.getChild("FormalParameters")));
		if(a.getChild("Transitions")!=null)
			w.setTransitions(parseTransitions(a.getChild("Transitions"),w));
		if(a.getChild("ExtendedAttributes")!=null)
			w.setExtendedAttributes(parseExtendedAttributes(a.getChild("ExtendedAttributes")));

		return w;
	}
	
	private List<Activity> parseActivities(Element activities,Workflow workflowParent)
	{
		/*Tant qui existe des activities (noeud Activities, balise Activity)
		 * 	on lance parseActivity("Activity")
		 * 	on r�cup�re une Activity
		 *  si l'activit� implemente un subflow
		 *  	si (WorkflowPackage.workflowExist =! null)
		 *  		Activity.subflow=WorkflowPackage.workflowExist;
		 * 		sinon
		 * 			workflowProcess = recup�rer l'element avec getWorkflowById(id)
		 * 			parseWorkflow(Element workflowProcess);
		 * 		finsi
		 * 	si
		 *  
		 * fin tant_que
		 * On renvoit une liste d'Activity
		 */
		List<Activity> activitiesReturn = new ArrayList();
		List<Element> activity = activities.getChildren("Activity");
		Iterator<Element> it = activity.iterator();
		Element a;
		Workflow w, wRecup = null;
		Activity act;
			
		while(it.hasNext())
		{
			a = it.next();
			act = parseActivity(a,workflowParent);
			//on test si l'activit�e implemente un subflow
			if (act.isSubflow())
			{	
				String workflowId = a.getChild("Implementation").getChild("SubFlow").getAttribute("Id").getValue();
				
				//on test si un Workflow avec le m�me ID que l'Activit� existe 
				w = workflowPackage.workflowExist(workflowId);
				//si un workflow existe avec le meme id que l'activit�
				if(w!=null)
				{
					//le subflow de l'activit� est le workflow avec le meme id
					act.setSubflow(w);
				}
				//sinon le workflow li� � l'activit� n'existe pas encore
				else
				{
					//on recup�re le workflow � parser
					
					//----------------------------------------------------------------------------------//
					//il faut identifier l'�l�ment workflow li� au SubFlow
					//----------------------------------------------------------------------------------//
					
					Element wkf;
					List listWkf = racine.getChild("WorkflowProcesses").getChildren("WorkflowProcess");
					Iterator it2 = listWkf.iterator();
					
					//parcours des Workflows
					while(it2.hasNext())
					{
						wkf = (Element)it2.next();
						//Si l'attribut id de l'element Workflow est �gal � l'attribut du Worflow � parser
						System.out.println(wkf.getAttribute("Id").getValue()+"     "+workflowId);
						if(wkf.getAttribute("Id").getValue().equals(workflowId))
						{
							System.out.println("OK");
							wRecup = parseWorkflow(wkf);
							break;
						}
						
					}				
					//----------------------------------------------------------------------------------//
					act.setSubflow(wRecup);
				}
			}
			activitiesReturn.add(act);
		}
		return activitiesReturn;
	}
	
	
	
	private Activity parseActivity(Element activity,Workflow workflowParent)
	{
		/* on cr�� un objet Activity
		 * <Activity Id="temps_scolaire" dans Activity.id
		 * Name="Temps scolaire">  dans Activity.name
		 * si Implementation existe
		 * 		<Implementation> <SubFlow Execution="SYNCHR" dans Activity.subflow
		 * 		Id="temps_scolaire"/> </Implementation> dans Activity.subflow.id
		 * sinon
		 * Activity.subflow =null;
		 * <Performer>medecin_institutionnel</Performer> Activity.performer
		 * <TransitionRestriction> <Join Type="XOR"/>  dans Activity.join
		 * <Split Type="XOR"> </Split> dans Activity.split
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on r�cup�re une liste d'ExtendedAttribute que l'on ajoute � Activity.extendedAttributes
		 * On renvoit une Activity
		 */
		
		boolean impl=false;
		Participant p;
		
		if (activity.getChild("Implementation").getChild("SubFlow")!=null)
			impl = true;
		
		Activity act;

		if(activity.getAttribute("Name")==null)
			act = new Activity(workflowParent,activity.getAttribute("Id").getValue(),impl);
		else
			act = new Activity(workflowParent,activity.getAttribute("Id").getValue(),activity.getAttribute("Name").getValue(),impl);
		
		if(activity.getChild("Performer")!=null)
		{
			p=workflowPackage.getParticipantById(activity.getChild("Performer").getText());
			act.setPerformer(p); 
		}
		if(activity.getChild("TransitionRestrictions")!=null)
		{
			if(activity.getChild("TransitionRestrictions").getChild("TransitionRestriction").getChild("Split")!=null)
				act.setTransitionRestrictionSplit(activity.getChild("TransitionRestrictions").getChild("TransitionRestriction").getChild("Split").getAttributeValue("Type"));
		
			if(activity.getChild("TransitionRestrictions").getChild("TransitionRestriction").getChild("Join")!=null)
				act.setTransitionRestrictionJoin(activity.getChild("TransitionRestrictions").getChild("TransitionRestriction").getChild("Join").getAttributeValue("Type"));
		}
		
		if(activity.getChild("ExtendedAttributes")!=null)
			act.setExtendedAttributes(parseExtendedAttributes(activity.getChild("ExtendedAttributes")));
		
		return act;
	}
	
	private List<FormalParameter> parseFormalParameters(Element formalParameters)
	{
		/*Tant qui existe des formalParameters (balise FormalParameters, noeud FormalParameter)
		 * on r�cup�re l'�l�ment <FormalParameter Id="idEnfant" Mode="IN">
		 * on r�cup�re <DataType><BasicType Type="STRING"/></DataType> le type noeud DataType
		 * on r�cup�re <Description> table DAI.	</Description> la description
		 * </FormalParameter>
		 * on cr�� un objet FormalParameter
		 * fin tant_que
		 * On renvoit une liste de FormalParameter
		 */
		
		List<FormalParameter> formalParametersReturn=new ArrayList();
		List formalParameter = formalParameters.getChildren("FormalParameter"); //.getChild("FormalParameter");
		Iterator<Element> it = formalParameter.iterator();
		Element a;
		String dataType;
		String desc;
			
		while (it.hasNext()) 
		{
			dataType=null;
			desc=null;
			a = (Element)it.next();
			
			dataType = a.getChild("DataType").getChild("BasicType").getAttribute("Type").getValue();
			desc = a.getChild("Description").getText();
			
			FormalParameter f = new FormalParameter(a.getAttribute("Id").getValue(), a.getAttribute("Mode").getValue(), dataType, desc);
			formalParametersReturn.add(f);	
		}
		return formalParametersReturn;	
	}
	
	private List<Transition> parseTransitions(Element transitions,Workflow workflow)//workflow en param�tre pour rechercher l'activit�
	{
		/*
		 * Parse dans parseWorkflowProcess
		 */
		List<Transition> transitionsReturn=new ArrayList();
		List<Element> transition = transitions.getChildren("Transition");
		Iterator<Element> it = transition.iterator();
		Element a;
		
		String conditionType;
		String condition;
			
		while (it.hasNext()) 
		{
			a = it.next();
			conditionType=null;
			condition=null;
			conditionType=a.getChild("Condition").getAttribute("Type").getValue();
			condition = a.getChild("Condition").getText();
			
			Transition t = new Transition(a.getAttribute("Id").getValue(),conditionType,condition);
			System.out.println("FROM : "+a.getAttribute("From").getValue());
			Activity from =workflow.getActivityById(a.getAttribute("From").getValue());
			Activity to = workflow.getActivityById(a.getAttribute("To").getValue());
			t.setFrom(from);
			t.setTo(to);
			transitionsReturn.add(t);
		}
		return transitionsReturn;
	}
	
	private List<ExtendedAttribute> parseExtendedAttributes(Element extendedAttributes)
	{
		/*Tant qui existe des ExtendedAttributes (balise ExtendedAttributes, noeud ExtendedAttribute )
		 * <ExtendedAttribute Name="EDITING_TOOL" Value="Together Workflow Editor Community Edition"/>
		 * on cr�� un objet ExtendedAttribute
		 * fin tant_que
		 * On renvoit une liste de ExtendedAttribute
		 */
		List<ExtendedAttribute> extendedAttributesReturn = new ArrayList();
		List<Element> extendedAttribute = extendedAttributes.getChildren("ExtendedAttribute");
		Iterator it = extendedAttribute.iterator();
		Element a;
					
		while (it.hasNext()) 
		{
			a = (Element)it.next();
			ExtendedAttribute e = new ExtendedAttribute(a.getAttribute("Name").getValue(),a.getAttribute("Value").getValue());
			extendedAttributesReturn.add(e);
		}
		return extendedAttributesReturn;
	}
	

}
