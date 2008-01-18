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
	}
	
	private List parseParticipants(Element participants)
	{
		/*Tant qui existe des participants (noeud Participants, balise Participant)
		 * on cr�er un objet Participant
		 * on r�cup�re l'�l�ment <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable l�gal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * fin tant_ue
		 * On renvoit une liste de participant
		 */
		return null;
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
		 */
		return null;
	}
	
	private List parseActivities(Element activities)
	{
		/*Tant qui existe des activities (noeud Activities, balise Activity)
		 * 	on lance parseActivity("Activity")
		 * 	on r�cup�re une Activity
		 *  si (Activity.subflow non null)
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
		return null;
	}
	
	
	
	private List Activity(Element activity)
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
		 * <StartMode><Automatic/></StartMode> dans Activity.startMode
		 * <FinishMode><Automatic/></FinishMode> dans Activity.finishMode
		 * <TransitionRestriction> <Join Type="XOR"/>  dans Activity.join
		 * <Split Type="XOR"> </Split> dans Activity.split
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on r�cup�re une liste d'ExtendedAttribute que l'on ajoute � Activity.extendedAttributes
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
