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
	
	public void parsePackage()
	{
		/* on lit le Header,
		 * on créé un objet WorkflowPackage
		 * on récupère l'élément <Package Id="dossier_accueil_individualise" Name="Dossier d'accueil individualisé" 
		 * que l'on met dans WorkflowPackage.id et WorkflowPackage.name
		 * on récupère l'élément <Created>2006-07-24 13:57:13</Created> que l'on met dans WorkflowPackage.date
		 * on lance parseParticipants("Participants")
		 * on récupère une liste de participant que l'on ajoute à WorkflowPackage.participants
		 * on lance parseDatafields("DataFields")
		 * on récupère une liste de dataFields que l'on ajoute à WorkflowPackage.dataFields
		 * on lance parseWorkflowProcesses("WorkflowProcesses")
		 * on récupère une liste de Worflow que l'on ajoute à WorkflowPackage.workflows
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on récupère une liste d'ExtendedAttribute que l'on ajoute à WorkflowPackage.extendedAttributes
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
	}
	
	private List parseParticipants(Element participants)
	{
		/*Tant qui existe des participants (noeud Participants, balise Participant)
		 * on créer un objet Participant
		 * on récupère l'élément <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable légal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * fin tant_ue
		 * On renvoit une liste de participant
		 */
		
		List participantsReturn;
		List participant = participants.getChildren("Participant");
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
			type = a.getChild("ParticipantType").getAttribute("Type").getvalue();
			desc = a.getChild("Description").getTextContent()
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
		 * on créé un objet DataField
		 * on récupère l'élément <DataField Id="TRS_validation_proposition_medecin_institutionnel" dans DataField.id
		 * IsArray="FALSE" dans DataField.isArray
		 * Name="TRS Validation proposition medecin institutionnel"> dans DataField.name
		 * InitialValue dans DataField.initialValue
		 * 	<DataType> <BasicType Type="BOOLEAN"/> </DataType> </DataField> dans DataField.dataType
		 * fin tant_que
		 * On renvoit une liste de datafield
		 */
		
		List datafieldsReturn;
		List datafield = datafields.getChildren("Datafield");//datafields.getChild("DataField");
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
		List workflowProcessesReturn;
		List workflowProcess = workflowProcesses.getChildren("WorkflowProcess");
		Iterator it = workflowProcess.iterator();
		Element a;
			
		while(it.hasNext())
		{
			a=(Element)it.next();
			workflowProcessesReturn.add(parseWorkflow(a));
			
		}
		
		return workflowProcessesReturn;
	}
	
	private List parseWorkflow(Element workflowProcess)
	{
		 /* on créé un objet Workflow
		 * <WorkflowProcess Id="initialisation"  dans Workflow.id
		 * Name="Initialisation DAI"> dans Workflow.name
		 * 	<ProcessHeader> <Created>2006-07-24 15:10:03</Created> </ProcessHeader> dans Workflow.created
		 * on lance parseActivities("Activities")
		 * on récupère une liste d'Activity que l'on ajoute à Workflow.activities
		 * on lance parseDataFields("DataFields")
		 * on récupère une liste de DataFields que l'on ajoute à Workflow.dataFields
		 * on lance parseFormalParameters("FormalParameters")
		 * on récupère une liste de FormalParameters que l'on ajoute à Workflow.dataFields
		 * on lance parseTransitions("Transitions")
		 * on récupère une liste de Transitions que l'on ajoute à Workflow.transitions
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on récupère une liste d'ExtendedAttribute que l'on ajoute à WorkflowPackage.extendedAttributes
		 * On renvoit un Workflow
		 * Element processHeader = workflowProcess.get
		Workflow w = new Workflow(workflowProcess.getAttribute("Id").getValue(),workflowProcess.getAttribute("Name").getValue())
		return w;
		 */
		String created;
		Element a = workflowProcess;
		
		created= a.getChild("ProcessHeader").getChild("Created").getText();
		Worflow w = new Workflow(a.getAttribute("Id").getValue(),a.getAttribute("Name").getValue(), created);
		
		return null;
	}
	
	private List parseActivities(Element activities)
	{
		/*Tant qui existe des activities (noeud Activities, balise Activity)
		 * 	on lance parseActivity("Activity")
		 * 	on récupère une Activity
		 *  si (Activity.subflow non null)
		 *  	si (WorkflowPackage.workflowExist =! null)
		 *  		Activity.subflow=WorkflowPackage.workflowExist;
		 * 		sinon
		 * 			workflowProcess = recupérer l'element avec getWorkflowById(id)
		 * 			parseWorkflow(Element workflowProcess);
		 * 		finsi
		 * 	si
		 *  
		 * fin tant_que
		 * On renvoit une liste d'Activity
		 */
		return null;
	}
	
	
	
	private List Activity(Element activity)
	{
		/* on créé un objet Activity
		 * <Activity Id="temps_scolaire" dans Activity.id
		 * Name="Temps scolaire">  dans Activity.name
		 * si Implementation existe
		 * 		<Implementation> <SubFlow Execution="SYNCHR" dans Activity.subflow
		 * 		Id="temps_scolaire"/> </Implementation> dans Activity.subflow.id
		 * sinon
		 * Activity.subflow =null;
		 * <Performer>medecin_institutionnel</Performer> Activity.performer
		 * <StartMode><Automatic/></StartMode> dans Activity.startMode
		 * <FinishMode><Automatic/></FinishMode> dans Activity.finishMode
		 * <TransitionRestriction> <Join Type="XOR"/>  dans Activity.join
		 * <Split Type="XOR"> </Split> dans Activity.split
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on récupère une liste d'ExtendedAttribute que l'on ajoute à Activity.extendedAttributes
		 * On renvoit une Activity
		 */
		return null;
	}
	
	private List parseFormalParameters(Element formalParameters)
	{
		/*Tant qui existe des formalParameters (balise FormalParameters, noeud FormalParameter)
		 * on récupère l'élément <FormalParameter Id="idEnfant" Mode="IN">
		 * on récupère <DataType><BasicType Type="STRING"/></DataType> le type noeud DataType
		 * on récupère <Description> table DAI.	</Description> la description
		 * </FormalParameter>
		 * on créé un objet FormalParameter
		 * fin tant_que
		 * On renvoit une liste de FormalParameter
		 */
		
		List formalParametersReturn;
		List formalParameter = formalParameters.getChildren("FormalParameter") //.getChild("FormalParameter");
		Iterator it = formalParameter.iterator();
		Element a;
		String dataType;
		String desc;
			
		while (it.hasNext()) 
		{
			dataType=null;
			desc=null;
			a = (Element)it.next();
			
			dataType = a.getChild("DataType").getChild("BasicType").getAttribute("Type").getvalue();
			desc = a.getChild("Description").getTextContent();
			
			FormalParameter f = new FormalParameter(a.getAttribute("Id").getValue(), a.getAttribute("Mode").getValue(), dataType, desc);
			formalParametersReturn.add(f);	
		}
		return formalParametersReturn;	
	}
	
	private List parseTransitions(Element transitions)
	{
		/*
		 * Parse dans parseWorkflowProcess
		 */
		List transitionsReturn;
		List transition = transitions.getChildren("Transition")
		Iterator it = transition.iterator();
		Element a;
		
		String conditionType;
		String condition;
			
		while (it.hasNext()) 
		{
			a = (Element)it.next();
			conditionType=null;
			condition=null;
			conditionType=a.getChild("Condition").getAttribute.getValue("Type");
			condition = a.getChild("Condition").getText();
			
			Transition t = new Transition(a.getAttribute("Id").getValue(),conditionType,condition);
			
			Activity from =getActivityById(a.getAttribute("From").getValue());
			Activity to = getActivityById(a.getAttribute("To").getValue()):
			t.setFrom(from);
			t.setTo(to);
			transitionsReturn.add(t);
		}
		return transitionsReturn;
	}
	
	private List parseExtendedAttributes(Element extendedAttributes)
	{
		/*Tant qui existe des ExtendedAttributes (balise ExtendedAttributes, noeud ExtendedAttribute )
		 * <ExtendedAttribute Name="EDITING_TOOL" Value="Together Workflow Editor Community Edition"/>
		 * on créé un objet ExtendedAttribute
		 * fin tant_que
		 * On renvoit une liste de ExtendedAttribute
		 */
		List extendedAttributesReturn;
		List extendedAttribute = extendedAttributes.getChildren("ExtendedAttribute");
		Iterator it = extendedAttribute.iterator();
		Element a;
					
		while (it.hasNext()) 
		{
			a = (Element)it.next();
			ExtendedAttribute e = new DataField(extendedAttribute.getAttribute("Name").getValue(),extendedAttribute.getAttribute("Value").getValue());
			extendedAttributesReturn.add(e);
		}
		return extendedAttributesReturn;
	}
	
	/*
	 * Récupère le Workflow dans le fichier Java en fonction de son id
	 * 
	 */
	private Workflow getWorkflowById(String id)
	{
		/*Tant qu'il existe des workflows (noeud WorkflowProcesses, balise WorkflowProcess)
		 * 	si workflow.id = WorkflowProcess
		 * On renvoit une liste de Workflow
		 * Element workflowProcesses = racine.getChild("WorkflowProcesses");
		NodeList listeWorkflowProcesses = workflowProcesses.getChildNodes();
		int tailleListWorkflowProcesses = listeWorkflowProcesses.getLength();
		int i= 0;
		
		while (i<=tailleListWorkflowProcesses)
		{
			if workflow
			
		}
		
		
		Element workflow = workflowProcesses.getChild("WorkflowProcess");
		 */
		
		return null;
	
	}
	private Activity getActivityById(String id)
	{
		return null;
		
	}
	}
}
