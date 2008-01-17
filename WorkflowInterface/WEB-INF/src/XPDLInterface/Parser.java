package XPDLInterface;
import java.util.*;
import org.jdom.*;

public class Parser {

	public Parser()
	{
		
	}
	
	public void parsePackage()
	{
		/*on lit le Header,
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
	}
	
	private List parseParticipants(Element participants)
	{
		/*Tant qui existe des participants (balise participants)
		 * on cr�er un objet Participant
		 * on r�cup�re l'�l�ment <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable l�gal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * fin tant_ue
		 * On renvoit la liste de participant
		 */
		return null;
	}
	
	private List parseDataFields(Element datafields)
	{
		/*Tant qui existe des datafields (balise datafields)
		 * on cr�� un objet DataField
		 * on r�cup�re l'�l�ment <DataField Id="TRS_validation_proposition_medecin_institutionnel" dans DataField.id
		 * IsArray="FALSE" dans DataField.isArray
		 * Name="TRS Validation proposition medecin institutionnel"> dans DataField.name
		 * InitialValue dans DataField.initialValue
		 * 	<DataType> <BasicType Type="BOOLEAN"/> </DataType> </DataField> dans DataField.dataType
		 * fin tant_que
		 * On renvoit la liste de datafield
		 */
		return null;
	}
	
	private List parseWorkflowProcess(Element workflowProcess)
	{
		/*Tant qu'il existe des workflows (balise workflowProcess)
		 * 	Tant que WorkflowPackage.workflows[i] =! null) // ou .isnext();
		 * 		id = <WorkflowProcess Id="initialisation"
		 * 		si (!WorkflowPackage.workflowExist(id))
		 * 				parseWorkflow(Element workflowProcess);
		 * 		finsi
		 * 		i++;
		 * 	fin tant_que
		 * 
		 */
		return null;
	}
	
	private List parseWorkflow(Element workflowProcess)
	{
		 /* on cr�� un objet Workflow
		 * <WorkflowProcess Id="initialisation"  dans Workflow.id
		 * Name="Initialisation DAI"> dans Workflow.name
		 * 	<ProcessHeader> <Created>2006-07-24 15:10:03</Created> </ProcessHeader> dans Workflow.created
		 * on lance parseActivity("Activities")
		 * on r�cup�re une liste d'Activity que l'on ajoute � Workflow.activities
		 * on lance parseDataFields("DataFields")
		 * on r�cup�re une liste de DataFields que l'on ajoute � Workflow.dataFields
		 * on lance parseFormalParameters("FormalParameters")
		 * on r�cup�re une liste de FormalParameters que l'on ajoute � Workflow.dataFields
		 * 
		 * on r�cup�re l'�l�ment <DataField Id="TRS_validation_proposition_medecin_institutionnel" dans DataField.id
		 * IsArray="FALSE" dans DataField.isArray
		 * Name="TRS Validation proposition medecin institutionnel"> dans DataField.name
		 * InitialValue dans DataField.initialValue
		 * 	<DataType> <BasicType Type="BOOLEAN"/> </DataType> </DataField> dans DataField.dataType
		 * On renvoit la liste de datafield
		 */
		return null;
	}
	
	private List Activities(Element Activities)
	{
		return null;
	}
	
	private List parseFormalParameters(Element FormalParameters)
	{
		return null;
	}
	
	
}
