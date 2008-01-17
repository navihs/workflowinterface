package XPDLInterface;

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
		 * on lance parseParticipant("Participants")
		 * on récupère une liste de participant que l'on ajoute à WorkflowPackage.participants
		 * on lance parseDatafields("DataFields")
		 * on récupère une liste de dataFields que l'on ajoute à WorkflowPackage.dataFields
		 * on lance parseWorkflowProcesses("WorkflowProcesses")
		 * on récupère une liste de Worflow que l'on ajoute à WorkflowPackage.workflows
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on récupère une liste d'ExtendedAttribute que l'on ajoute à WorkflowPackage.extendedAttributes
		 */
		
	}
	
	
	
}
