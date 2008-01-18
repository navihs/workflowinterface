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
		return null;
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
		return null;
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
		 */
		return null;
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
		 */
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
		return null;
	}
	
	private List parseTransitions(Element transitions)
	{
		/*
		 * Parse dans parseWorkflowProcess
		 */
		return null;
	}
	
	private List parseExtendedAttributes(Element extendedAttributes)
	{
		return null;
	}
	
	private Workflow getWorkflowById(String id)
	{
		return null;
	
	}
}
