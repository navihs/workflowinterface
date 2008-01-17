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
		 * on créer un objet WorkflowPackage
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
	}
	
	private List parseParticipants(Element participants)
	{
		/*Tant qui existe des participants (balise participants)
		 * on créer un objet Participant
		 * on récupère l'élément <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable légal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * On renvoit la liste de participant
		 */
		return null;
	}
	
	private List parseDataFields(Element datafields)
	{
		/*Tant qui existe des datafields (balise datafields)
		 * on créer un objet DataField
		 * on récupère l'élément <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable légal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * On renvoit la liste de participant
		 */
		return null;
	}
	
}
