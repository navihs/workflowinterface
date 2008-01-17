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
		 * on cr�er un objet WorkflowPackage
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
		 * On renvoit la liste de participant
		 */
		return null;
	}
	
	private List parseDataFields(Element datafields)
	{
		/*Tant qui existe des datafields (balise datafields)
		 * on cr�er un objet DataField
		 * on r�cup�re l'�l�ment <Participant Id="responsable_legal" dans Participant.id
		 * Name="Responsable l�gal"> dans Participant.name
		 * <ParticipantType Type="ROLE"/> </Participant> que l'on met dans Participant.type
		 * On renvoit la liste de participant
		 */
		return null;
	}
	
}
