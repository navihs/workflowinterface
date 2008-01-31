package shark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfbase.InvalidQuery;
import org.enhydra.shark.api.client.wfmodel.AlreadyRunning;
import org.enhydra.shark.api.client.wfmodel.CannotAcceptSuspended;
import org.enhydra.shark.api.client.wfmodel.CannotComplete;
import org.enhydra.shark.api.client.wfmodel.CannotStart;
import org.enhydra.shark.api.client.wfmodel.InvalidData;
import org.enhydra.shark.api.client.wfmodel.NotEnabled;
import org.enhydra.shark.api.client.wfmodel.UpdateNotAllowed;
import org.enhydra.shark.api.client.wfmodel.WfActivity;
import org.enhydra.shark.api.client.wfmodel.WfAssignment;
import org.enhydra.shark.api.client.wfmodel.WfAssignmentIterator;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfProcessMgr;
import org.enhydra.shark.api.client.wfmodel.WfResource;
import org.enhydra.shark.api.client.wfservice.AdminMisc;
import org.enhydra.shark.api.client.wfservice.ConnectFailed;
import org.enhydra.shark.api.client.wfservice.ExecutionAdministration;
import org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid;
import org.enhydra.shark.api.client.wfservice.NotConnected;
import org.enhydra.shark.api.client.wfservice.PackageAdministration;
import org.enhydra.shark.api.client.wfservice.PackageInvalid;
import org.enhydra.shark.api.client.wfservice.ParticipantMappingAdministration;
import org.enhydra.shark.api.client.wfservice.SharkConnection;
import org.enhydra.shark.api.client.wfservice.UserGroupAdministration;

/**
 * <b>Cette classe constitue une surcouche � l�API Shark qu�elle 
 * permet d�utiliser de mani�re plus simple. On factorise ici 
 * toutes les m�thodes permettant la gestion du workflow.</b><br/>
 * Voir : <a href="http://www.enhydra.org/workflow/shark/" target="new">http://www.enhydra.org/workflow/shark/</a>
 * 
 * <p align="center">
 *   <img src="../../resources/WorkflowWrapper.png" alt="DBComponent schema"/>
 * </p>
 * @author Philippe TANGUY
 */
public class WorkflowWrapper
{
  // TODO : mettre � jour les commentaires Javadoc
  // -----------------------------------------------------------------------------
  /**
   * Instance de Shark pour l'acc�s au gestionnaire de workflow.
   */
  private static Shark shark;
  //private static SharkInterface aha;
  /**
   * Instance unique de la connexion Shark. La dur�e de vie de cette connexion est �gale
   * � la dur�e de vie de l'application. L'ensemble des utilisateurs Shark partagent donc
   * cette m�me connexion et ont donc les m�me droits pour la r�cup�ration des t�ches.
   * L'appartenance d'une t�che � un utilisateur doit donc �tre g�r�e en interne.
   */
  private static SharkConnection sConn;
  /**
   * Pour des t�che d'administration (consultation des processus lanc�s, etc.)
   */
  private static ExecutionAdministration executionAdministration;
  //-----------------------------------------------------------------------------
  /**
   * Initialisation de du moteur Shark en fournissant le fichier de configuration.
   * La m�thode masque un appel � la m�thode init(Properties p)
   * 
   * @param sharkConfigurationFile chemin complet + nom du fichier de configuration
   *                               de Shark
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ConnectFailed 
   * @throws BaseException 
   * @see #init(Properties)
   */
  public static void init(String sharkConfigurationFile)
    throws FileNotFoundException, IOException, BaseException, ConnectFailed
  {
    // EVOLUTION : Voir pour une autre m�thode qui prendrait en param�tre une structure de donn�es avec des valeurs � appliquer sur un fichier de conf g�n�rique.
    
    // Initialisation et lancement de Shark
    Properties p = new Properties();
    p.load(new FileInputStream(sharkConfigurationFile));
    init(p);
  }
  //-----------------------------------------------------------------------------
  /**
   * @param p propri�t�s pour la configuration du moteur Shark
   * @throws ConnectFailed 
   * @throws BaseException 
   */
  public static void init(Properties p)
  {
    Shark.configure(p);
    System.out.println("####### Shark est maintenant configure...");
    shark = Shark.getInstance();
    // Cr�ation de la connexion
    sConn = shark.getSharkConnection();
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation et d�marrage d'un processus cod� au sein d'un fichier XPDL.<br/>
   * Cette m�thode ne permet pas l'initialisation d'un param�tre formel.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param XPDLFile le fichier XPDL qui code le/les processus
   * @param workflowProcessId l'ID du processus cod� dans le
   *                          XPDL que l'on veur lancer (un 
   *                          fichier peut contenir plusieurs 
   *                          processus).
   * @return le processus (instance de <code>WfProcess</code>) cr��.
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled
   * @throws CannotStart
   * @throws AlreadyRunning
   */
  public static WfProcess createAndStartProcess(SharkTransaction st,
                                                String XPDLFile,
                                                String workflowProcessId)
    throws BaseException, NotConnected, NotEnabled, CannotStart, AlreadyRunning
  {
    String packageId = shark.getRepositoryManager().getPackageId(XPDLFile);
    // Cr�ation du processus
    WfProcess process = sConn.createProcess(st,packageId,workflowProcessId);
    // D�marage du processus
    process.start(st);

    return process;
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation et d�marrage d'un processus cod� au sein d'un fichier XPDL.<br/>
   * Cette m�thode ne permet pas l'initialisation d'un param�tre formel.
   * 
   * @param XPDLFile le fichier XPDL qui code le/les processus
   * @param workflowProcessId l'ID du processus cod� dans le
   *                          XPDL que l'on veur lancer (un 
   *                          fichier peut contenir plusieurs 
   *                          processus).
   * @return le processus (instance de <code>WfProcess</code>) cr��.
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled
   * @throws CannotStart
   * @throws AlreadyRunning
   */
  public static WfProcess createAndStartProcess(String XPDLFile,
                                                String workflowProcessId)
    throws BaseException, NotConnected, NotEnabled, CannotStart, AlreadyRunning
  {
    String packageId = shark.getRepositoryManager().getPackageId(XPDLFile);
    // Cr�ation du processus
    WfProcess process = sConn.createProcess(packageId,workflowProcessId);
    // D�marage du processus
    process.start();

    return process;
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation, initialisation du param�tre formel et d�marrage d'un processus cod� 
   * au sein d'un fichier XPDL.<br/>
   * Pour l'instant, l'initialisation de plusieurs param�tres formels n'est pas
   * possible.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param XPDLFile le fichier XPDL qui code le/les processus
   * @param workflowProcessId l'ID du processus cod� dans le
   *                          XPDL que l'on veur lancer (un 
   *                          fichier peut contenir plusieurs 
   *                          processus).
   * @param formalParameterId l'id du param�tre formel que l'on veut initialiser
   * @param formalParameterValue la valeur du param�tre formel que l'on veut initialiser
   * @return le processus (instance de <code>WfProcess</code>) cr��.
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled
   * @throws InvalidData
   * @throws UpdateNotAllowed
   * @throws CannotStart
   * @throws AlreadyRunning
   */
  public static synchronized WfProcess createAndStartProcess(SharkTransaction st,
                                                             String XPDLFile,
                                                             String workflowProcessId,
                                                             String formalParameterId,
                                                             String formalParameterValue)
    throws BaseException, NotConnected, NotEnabled, InvalidData, UpdateNotAllowed, CannotStart, AlreadyRunning
  {
    String packageId = shark.getRepositoryManager().getPackageId(XPDLFile);
    // Cr�ation du processus
    WfProcess process = sConn.createProcess(st,packageId,workflowProcessId);
    // Mise � jour du formal parameter
    
    // TODO : tester "setFormalParameterValue" � la place de "setWorkflowVariableValue" 
    //setFormalParameterValue(st,process,formalParameterId,formalParameterValue);
    setWorkflowVariableValueTopProcess(st,process,formalParameterId,formalParameterValue);

    // D�marage du processus
    process.start(st);

    return process;
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation, initialisation du param�tre formel et d�marrage d'un processus cod� 
   * au sein d'un fichier XPDL.<br/>
   * Pour l'instant, l'initialisation de plusieurs param�tres formels n'est pas
   * possible.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param XPDLFile le fichier XPDL qui code le/les processus
   * @param workflowProcessId l'ID du processus cod� dans le
   *                          XPDL que l'on veur lancer (un 
   *                          fichier peut contenir plusieurs 
   *                          processus).
   * @param formalParameterId l'id du param�tre formel que l'on veut initialiser
   * @param formalParameterValue la valeur du param�tre formel que l'on veut initialiser
   * @return le processus (instance de <code>WfProcess</code>) cr��.
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled
   * @throws InvalidData
   * @throws UpdateNotAllowed
   * @throws CannotStart
   * @throws AlreadyRunning
   */
  public static WfProcess createAndStartProcess(String XPDLFile,
                                                String workflowProcessId,
                                                String formalParameterId,
                                                String formalParameterValue)
    throws BaseException, NotConnected, NotEnabled, InvalidData, UpdateNotAllowed, CannotStart, AlreadyRunning
  {
    String packageId = shark.getRepositoryManager().getPackageId(XPDLFile);
    // Cr�ation du processus
    WfProcess process = sConn.createProcess(packageId,workflowProcessId);
    
    // D�marage du processus
    // Mise � jour du formal parameter
    setFormalParameterValue(process,formalParameterId,formalParameterValue);
    process.start();

    return process;
  }
  //-----------------------------------------------------------------------------
  /**
   * Ouverture du package si n�cessaire.
   * 
   * @param XPDLFile chemin et nom du fichier XPDL qui code le workflow.
   * @param packageId attribut "Id" de l'�l�ment XML "Package" du fichier XPDL.
   * @throws ExternalPackageInvalid 
   * @throws PackageInvalid 
   * @throws BaseException 
   */
  public static void openPackage(String XPDLFile,String packageId)
    throws BaseException, PackageInvalid, ExternalPackageInvalid
  {
    PackageAdministration packageAdministration = shark.getAdminInterface().getPackageAdministration();
    // Open package if necessary.
    if(!packageAdministration.isPackageOpened(packageId))
      packageAdministration.openPackage(XPDLFile);
  }
  //-----------------------------------------------------------------------------
  /**
   * Les groupes ont-ils d�j� �t� cr��s ?
   * 
   * @return true si les groupes ont d�j� �t� cr��s, false sinon
   * @throws BaseException 
   */
  public static boolean isGroupsCreated() throws BaseException
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    return userGroupAdministration.getAllGroupnames().length == 0;
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation des groupes si necessaire. La m�thode teste si les groupes existent
   * d�j� (s'ils n'existent pas c'est que la base du moteur est lanc�e pour la
   * premi�re fois). S'il n'existent pas, alors les groupes sont cr��s.
   * 
   * @param groups la liste des groupes (un ArrayList de String[] content chacun
   *               le nom du groupe et sa description).
   * @throws BaseException
   * @throws SQLException
   */
  //public static void createGroups(ArrayList<Object[]> groups) throws BaseException
  public static void createGroups(ArrayList<String[]> groups) throws BaseException
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    if(isGroupsCreated())
    {
      //for(Object[] group : groups)
      for(String[] group : groups)
      {
        //String groupName   = (String)group[0];
        //String description = (String)group[1];
        String groupName   = group[0];
        String description = group[1];
        userGroupAdministration.createGroup(groupName,description);
        System.out.println("####### Groupe " + groupName + " (" + description + ") cree");
      }
      System.out.println("####### Tous les groupes ont ete crees");
    }
    else
      System.out.println("####### Les groupes etaient deja crees");
  }
  //-----------------------------------------------------------------------------
  /**
   * Les utilisateurs ont-ils d�j� �t� cr��s ?
   * 
   * @return true si les utilisateurs ont d�j� �t� cr��s, false sinon
   * @throws BaseException 
   */
  public static boolean isUsersCreated() throws BaseException
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    return userGroupAdministration.getAllUsers().length == 0;
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation des utilisateurs si necessaire. La m�thode teste si les utilisateurs
   * existent d�j� (s'ils n'existent pas c'est que la base du moteur est lanc�e pour la
   * premi�re fois). S'il n'existent pas, alors les utilisateurs sont cr��s.
   * 
   * @param users
   * @throws BaseException
   */
  //public static void createUsers(ArrayList<Object[]> groups) throws BaseException
  public static void createUsers(ArrayList<String[]> groups) throws BaseException
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    ParticipantMappingAdministration mapAdmin = shark.getAdminInterface().getParticipantMappingAdministration();
  
    if(isUsersCreated())
    {
      //for(Object[] group : groups)
      for(String[] group : groups)
      {
        //String groupName   = (String)group[0];
        String groupName   = group[0];
        userGroupAdministration.createUser(groupName,  // role
                                           groupName,  // login
                                           groupName,  // password
                                           "",         // first name
                                           "",         // last name
                                           "");        // mail
        System.out.println("####### Utilisateur " + groupName + " cree");
      }
      System.out.println("####### Tous les utilisateurs ont ete crees");
    }
    else
      System.out.println("####### Les utilisateurs etaient deja crees");
  }
  //-----------------------------------------------------------------------------
  /**
   * Cr�ation d'un utilisateur unique.
   * 
   * @param role
   * @param login
   * @param password
   * @param firstName
   * @param lastName
   * @param email
   * @throws BaseException
   */
  public static void createUser(String role,String login,String password,
                                String firstName, String lastName, String email)
    throws BaseException
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    userGroupAdministration.createUser(role,login,password,firstName,lastName,email);
  }
  //-----------------------------------------------------------------------------
  /**
   * Connexion de la liste des utilisateurs.
   * 
   * @param users ArrayList de String[] contenant la liste des utilisateurs.
   * @param engineName
   * @throws ConnectFailed 
   * @throws BaseException 
   */
  //public static void connectUsers(ArrayList<Object[]> groups, String engineName)
  public static void connectUsers(ArrayList<String[]> users, String engineName)
    throws BaseException, ConnectFailed
  {
    for (String[] user : users)
    {
      System.out.print("####### Connexion de " + user[1] + "/" + user[2]);
      sConn.connect(user[1], user[2], engineName, null);
      System.out.println(" OK");
    }


    executionAdministration = shark.getAdminInterface().getExecutionAdministration();
    executionAdministration.connect(users.get(0)[1],users.get(0)[2],engineName,null);

    System.out.println("####### Tous les utilisateurs sont connectes");
  }
  //-----------------------------------------------------------------------------
  /**
   * Connexion de la liste des utilisateurs.
   * 
   * @param users ArrayList de String[] contenant la liste des utilisateurs.
   * @param engineName
   * @throws ConnectFailed 
   * @throws BaseException 
   * @throws ConnectFailed 
   * @throws BaseException 
   */
  public static void connectUsers(SharkTransaction st,ArrayList<Object[]> users,String engineName)
    throws BaseException, ConnectFailed
  {
    for(Object[] user : users)
    {
      String login    = (String)user[1];
      String password = (String)user[2];
      // TODO : le 4�me param�tre de la m�thode connect(...) est une String nomm�e scope. A voir pour le probl�me de connexion.
      sConn.connect(st,login,password,engineName, null);
    }
    System.out.println("####### Tous les utilisateurs sont connectes");
  }
  //-----------------------------------------------------------------------------
  /**
   * Connexion d'un utilisateur.
   * 
   * @param engineName
   * @throws ConnectFailed 
   * @throws BaseException 
   */
  public static void connectUser(String login, String password,String engineName)
    throws BaseException, ConnectFailed
  {
    sConn.connect(login,password,engineName, null);
  }
  //-----------------------------------------------------------------------------
  /**
   * Connexion d'un utilisateur.
   * 
   * @param engineName
   * @throws ConnectFailed 
   * @throws BaseException 
   * @throws ConnectFailed 
   * @throws BaseException 
   */
  public static void connectUser(SharkTransaction st,String login, String password,String engineName)
    throws BaseException, ConnectFailed
  {
    sConn.connect(st,login,password,engineName, null);
  }
  //-----------------------------------------------------------------------------
  public static void disconnect() throws BaseException, NotConnected
  {
    sConn.disconnect();
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la liste des activit�s pour une connexion Shark donn�e. Attention,
   * cette liste contient l'ensemble des activit� quels que soient les utilisateurs
   * Shark connect�s.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @return un ArrayList d'instances de WfActivity (ArrayList<WfActivity>).
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled 
   */
  @SuppressWarnings("unchecked")
  public static ArrayList<WfActivity> getAllActivitiesList(SharkTransaction st)
    throws BaseException, NotConnected, NotEnabled
  {
    ArrayList<WfActivity> works = new ArrayList();
    WfResource ress = sConn.getResourceObject(st);
    WfAssignment[] wItems = ress.get_sequence_work_item(st,0);
    for(int i=0;i<wItems.length;i++)
    {
      works.add(wItems[i].activity(st));
    }
    
    return works;
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la liste des activit�s pour une connexion Shark donn�e. Attention,
   * cette liste contient l'ensemble des activit� quels que soient les utilisateurs
   * Shark connect�s.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @return un ArrayList d'instances de WfActivity (ArrayList<WfActivity>).
   * @throws NotConnected 
   * @throws BaseException 
   */
  @SuppressWarnings("unchecked")
  public static ArrayList<WfActivity> getAllActivitiesList()
    throws BaseException, NotConnected
  {
    ArrayList<WfActivity> works = new ArrayList();
    WfResource ress = sConn.getResourceObject();
    WfAssignment[] wItems = ress.get_sequence_work_item(0);
    for(int i=0;i<wItems.length;i++)
    {
      works.add(wItems[i].activity());
    }
    
    return works;
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la liste des activit�s pour une connexion Shark et un utilisateur
   * donn�s.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param login login de l'utilisateur dont on veut r�cup�rer la liste d'activit�.
   * @return un ArrayList d'instances de WfActivity (ArrayList<WfActivity>).
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled 
   */
  @SuppressWarnings("unchecked")
  public static synchronized ArrayList<WfActivity> getActivitiesListByLogin(SharkTransaction st,String login)
    throws BaseException, NotConnected, NotEnabled
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    ArrayList<WfActivity> works = new ArrayList();

    WfResource ress = sConn.getResourceObject(st);
    WfAssignment[] wItems = ress.get_sequence_work_item(st,0);
    for(int i=0;i<wItems.length;i++)
    {
      WfActivity activity = wItems[i].activity(st);
      // FIXME : obtenir le r�le associ� � une activit� par l'�l�ment -> <performer> et non "JaWE_GRAPH_PARTICIPANT_ID" qui est d�pendant de JaWE
      //String participantID = getExtendedAttributeValue(st,activity,"JaWE_GRAPH_PARTICIPANT_ID");
      String participantID = getExtendedAttributeValue(activity,"JaWE_GRAPH_PARTICIPANT_ID");
      if(userGroupAdministration.doesUserBelongToGroup(participantID,login))
      {
        works.add(activity);
      }
    }
    return works;
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la liste des activit�s pour une connexion Shark et un utilisateur
   * donn�s.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param login login de l'utilisateur dont on veut r�cup�rer la liste d'activit�.
   * @return un ArrayList d'instances de WfActivity (ArrayList<WfActivity>).
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled 
   * @throws TransactionException 
   * @throws InvalidQuery 
   */
  public static synchronized ArrayList<WfActivity> getActivitiesListByLogin(String login,
                                                                            String[] activitiesName,
                                                                            String objectIdColumnName)
    throws BaseException, NotConnected, NotEnabled, TransactionException, InvalidQuery
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    ArrayList<WfActivity> works = new ArrayList<WfActivity>();
    String queryExpression;
    if(activitiesName.length==0)
    {
      // Forc�ment rien � renvoyer
      return works;
    }
    else if(activitiesName.length==1)
      queryExpression = "/*sql activity in (select " + objectIdColumnName + " from activities where name='" + activitiesName[0] + "') sql*/";
    else
    {
      queryExpression = "/*sql activity in (select " + objectIdColumnName + " from activities where name='" + activitiesName[0] + "' ";
      for(int i = 1;i<activitiesName.length;i++)
        queryExpression += "or name='" + activitiesName[i] + "' ";
      queryExpression += ") sql*/";
    }
    WfResource ress = sConn.getResourceObject();

    // ExpressionBuilderManager expressionBuilderManager = shark.getExpressionBuilderManager();
    // ActivityIteratorExpressionBuilder aieb = expressionBuilderManager.getActivityIteratorExpressionBuilder();
    // aieb.addStateEquals(SharkConstants.STATE_OPEN_RUNNING);
    // aieb.addStateEquals(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED);
    // aieb.addExpression("activitydefinitionid_ = TCLSH_proposition_CLSH");
    // aieb.addDefinitionId("TCLSH_proposition_CLSH");
    // aieb.addActivitySetDefId("TCLSH_proposition_CLSH");
    // aieb.addIsAccepted();
    // aieb.addResourceUsername("acteur_passi");
    // System.out.println("******* aieb.toExpression() = " + aieb.toExpression());
    // assignmentIterator.set_query_expression(aieb.toExpression());
    WfAssignmentIterator assignmentIterator = ress.get_iterator_work_item();
    assignmentIterator.set_query_expression(queryExpression);

    int size = assignmentIterator.how_many();
    for(int i=0;i<size;i++)
    {
      WfActivity activity = assignmentIterator.get_next_object().activity();
      works.add(activity);
    }
    return works;

    
//    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
//    ArrayList<WfActivity> works = new ArrayList<WfActivity>();
//    WfResource ress = sConn.getResourceObject();
//    WfAssignment[] wItems = ress.get_sequence_work_item(0);
//    for (int i = 0; i < wItems.length; i++)
//    {
//      WfActivity activity = wItems[i].activity();
//      // FIXME : obtenir le r�le associ� � une activit� par l'�l�ment <performer> et non "JaWE_GRAPH_PARTICIPANT_ID"
//      String participantID = getExtendedAttributeValue(activity,"JaWE_GRAPH_PARTICIPANT_ID");
//      if (userGroupAdministration.doesUserBelongToGroup(participantID, login))
//      {
//        works.add(activity);
//      }
//    }
//    return works;
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la liste des activit�s pour une connexion Shark et un utilisateur
   * donn�s.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param login login de l'utilisateur dont on veut r�cup�rer la liste d'activit�.
   * @return un ArrayList d'instances de WfActivity (ArrayList<WfActivity>).
   * @throws BaseException
   * @throws NotConnected
   * @throws NotEnabled 
   * @throws InvalidQuery 
   */
  public static ArrayList<WfActivity> getActivitiesListByQueryExpression(String queryExpression)
    throws BaseException, NotConnected, NotEnabled, InvalidQuery
  {
    UserGroupAdministration userGroupAdministration = shark.getAdminInterface().getUserGroupAdministration();
    ArrayList<WfActivity> works = new ArrayList<WfActivity>();
    WfResource ress = sConn.getResourceObject();
    WfAssignmentIterator assignmentIterator = ress.get_iterator_work_item();
    assignmentIterator.set_query_expression(queryExpression);

    int size = assignmentIterator.how_many();
    for(int i=0;i<size;i++)
    {
      WfActivity activity = assignmentIterator.get_next_object().activity();
      works.add(activity);
    }
    return works;
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'un extended attribute associ� � une actvit�.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param extendedAttributeName le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static String getExtendedAttributeValue(SharkTransaction st,WfActivity activity,String extendedAttributeName)
    throws BaseException
  {
    AdminMisc adminMisc = shark.getAdminInterface().getAdminMisc();
    // activity.container() renvoie l'instance de wfProcess qui contient l'actvit�
    // key() renvoie l'identifiant unique du WfExecutionObject (sur classe de
    // WfActivity et de wfProcess)
    return adminMisc.getActivitiesExtendedAttributeValue(st,
                                                         activity.container().key(),
                                                         activity.key(),
                                                         extendedAttributeName);
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'un extended attribute associ� � une actvit�.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param extendedAttributeName le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static String getExtendedAttributeValue(WfActivity activity,String extendedAttributeName)
    throws BaseException
  {
//    System.out.println("******* getExtendedAttributeValue("+activity.name()+","+extendedAttributeName+")");
    AdminMisc adminMisc = shark.getAdminInterface().getAdminMisc();
    // activity.container() renvoie l'instance de wfProcess qui contient l'actvit�
    // key() renvoie l'identifiant unique du WfExecutionObject (sur classe de
    // WfActivity et de wfProcess)
    return adminMisc.getActivitiesExtendedAttributeValue(activity.container().key(),
                                                         activity.key(),
                                                         extendedAttributeName);
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'un formal parameter stock� dans un processus.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus
   * @param formalParameterId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getFormalParameterValue(SharkTransaction st,WfProcess process,String formalParameterId)
    throws BaseException
  {
    // process.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).

    // ATTENTION : la visibilit� du formal parameter n'est possible que dans le processus
    // racine, elle n'est pas propag�e dans les sous-processus !!! Une journ�e la dessus 
    // pour 5 lignes de code...
    // A partir du processus donn� en param�tre, on remonte r�cursivement jusqu'� trouver
    // le processus racine. Si le processus est aussi une activit�, il s'agit alors d'un 
    // sous-processus (c'est une activit� ins�r�e dans le processus imm�diatement
    // sup�rieur). On r�cup�re alors le processus parent et on recommence...
    // Voir http://mail-archive.objectweb.org/shark/2004-08/msg00137.html

    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester(st) instanceof WfActivity)
      {
        act = (WfActivity)_process.requester(st);
        _process = act.container(st);
      }
      else
      {
        return _process.process_context(st).get(formalParameterId);
      }
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'un formal parameter stock� dans un processus.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus
   * @param formalParameterId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getFormalParameterValue(WfProcess process,String formalParameterId)
    throws BaseException
  {
    // process.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).

    // ATTENTION : la visibilit� du formal parameter n'est possible que dans le processus
    // racine, elle n'est pas propag�e dans les sous-processus !!! Une journ�e la dessus 
    // pour 5 lignes de code...
    // A partir du processus donn� en param�tre, on remonte r�cursivement jusqu'� trouver
    // le processus racine. Si le processus est aussi une activit�, il s'agit alors d'un 
    // sous-processus (c'est une activit� ins�r�e dans le processus imm�diatement
    // sup�rieur). On r�cup�re alors le processus parent et on recommence...
    // Voir http://mail-archive.objectweb.org/shark/2004-08/msg00137.html

    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester() instanceof WfActivity)
      {
        act = (WfActivity)_process.requester();
        _process = act.container();
      }
      else
      {
        return _process.process_context().get(formalParameterId);
      }
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'un formal parameter stock� dans un processus � partir
   * de l'activit�.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param formalParameterId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getFormalParameterValue(SharkTransaction st,WfActivity activity,String formalParameterId)
    throws BaseException
  {
    // activity.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).
    //return (String)activity.process_context(st).get(formalParameterId);

    // ou �quivalent :
    WfProcess process = activity.container(st);
    return getFormalParameterValue(st,process,formalParameterId); 
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'un formal parameter stock� dans un processus � partir
   * de l'activit�.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param formalParameterId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getFormalParameterValue(WfActivity activity,String formalParameterId)
    throws BaseException
  {
    // activity.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).
    //return (String)activity.process_context(st).get(formalParameterId);

    // ou �quivalent :
    WfProcess process = activity.container();
    return WorkflowWrapper.getFormalParameterValue(process,formalParameterId); 
  }
  //-----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'un formal parameter stock� dans un processus.<br/>
   * <b>
   *   ATTENTION : LA MISE A JOUR D'UN FORMAL PARAMETER NE PEUT SE FAIRE QU'
   *   AVANT LE DEMARRAGE DU PROCESSUS !!!!!!! A VERIFIER CEPENDANT.
   * </b> 
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus.
   * @param formalParameterId le nom de l'attribut.
   * @param formalParameterValue la valeur de l'attribut � mettre � jour.
   * @throws UpdateNotAllowed 
   * @throws InvalidData 
   * @throws BaseException 
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setFormalParameterValue(SharkTransaction st,
                                             WfProcess process,
                                             String formalParameterId,
                                             String formalParameterValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // Les sous-processus ne poss�dent pas de visibilit� sur les param�tres
    // formels, on recherche r�cursivement jusqu'� trouver le processus racine.
    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester(st) instanceof WfActivity)
      {
        act = (WfActivity)_process.requester(st);
        _process = act.container(st);
      }
      else
      {
        Map m = new HashMap();
        m.put(formalParameterId,formalParameterValue);
        _process.set_process_context(st,m);
        break;
      }
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'un formal parameter stock� dans un processus.<br/>
   * <b>
   *   ATTENTION : LA MISE A JOUR D'UN FORMAL PARAMETER NE PEUT SE FAIRE QU'
   *   AVANT LE DEMARRAGE DU PROCESSUS !!!!!!! A VERIFIER CEPENDANT.
   * </b> 
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus.
   * @param formalParameterId le nom de l'attribut.
   * @param formalParameterValue la valeur de l'attribut � mettre � jour.
   * @throws UpdateNotAllowed 
   * @throws InvalidData 
   * @throws BaseException 
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setFormalParameterValue(WfProcess process,
                                             String formalParameterId,
                                             String formalParameterValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // Les sous-processus ne poss�dent pas de visibilit� sur les param�tres
    // formels, on recherche r�cursivement jusqu'� trouver le processus racine.
    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester() instanceof WfActivity)
      {
        act = (WfActivity)_process.requester();
        _process = act.container();
      }
      else
      {
        Map m = new HashMap();
        m.put(formalParameterId,formalParameterValue);
        _process.set_process_context(m);
        break;
      }
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'un formal parameter stock� dans un processus �
   * partir de l'activit�.<br/>
   * <b>
   *   ATTENTION : LA MISE A JOUR D'UN FORMAL PARAMETER NE PEUT SE FAIRE QU'
   *   AVANT LE DEMARRAGE DU PROCESSUS !!!!!!! A VERIFIER CEPENDANT.
   * </b> 
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param formalParameterId le nom de l'attribut.
   * @param formalParameterValue la valeur de l'attribut � mettre � jour.
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setFormalParameterValue(SharkTransaction st,WfActivity activity,String formalParameterId,String formalParameterValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // R�cup�ration du wfProcess qui contient l'actvit�
    WfProcess process = activity.container(st);
    setFormalParameterValue(st,process,formalParameterId,formalParameterValue);
  }
  //-----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'un formal parameter stock� dans un processus �
   * partir de l'activit�.<br/>
   * <b>
   *   ATTENTION : LA MISE A JOUR D'UN FORMAL PARAMETER NE PEUT SE FAIRE QU'
   *   AVANT LE DEMARRAGE DU PROCESSUS !!!!!!! A VERIFIER CEPENDANT.
   * </b> 
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param formalParameterId le nom de l'attribut.
   * @param formalParameterValue la valeur de l'attribut � mettre � jour.
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setFormalParameterValue(WfActivity activity,String formalParameterId,String formalParameterValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // R�cup�ration du wfProcess qui contient l'actvit�
    WfProcess process = activity.container();
    setFormalParameterValue(process,formalParameterId,formalParameterValue);
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'une workflow variable stock� dans un processus.<br/>
   * <i>note : m�thode strictement identique � getFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus.
   * @param workflowVariableId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getWorkflowVariableValueCurrentProcess(SharkTransaction st,WfProcess process,String workflowVariableId)
    throws BaseException
  {
    // process.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).
    //return (String)process.process_context(st).get(workflowVariableId);
    return process.process_context(st).get(workflowVariableId);
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'une workflow variable stock� dans un processus.<br/>
   * <i>note : m�thode strictement identique � getFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus.
   * @param workflowVariableId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getWorkflowVariableValueCurrentProcess(WfProcess process,String workflowVariableId)
    throws BaseException
  {
    // process.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).
    return process.process_context().get(workflowVariableId);
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'une workflow variable stock� dans un processus � partir
   * de l'activit�.<br/>
   * <i>note : m�thode strictement identique � getFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param workflowVariableId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getWorkflowVariableValueCurrentProcess(SharkTransaction st,WfActivity activity,String workflowVariableId)
    throws BaseException
  {
    // activity.process_context() renvoie une Map contenant l'ensemble des couples
    // formalParameterId -> valeur accessible � un contexte donn�.
    // process_context() est une m�thode de WfExecutionObject (sur classe de
    // WfActivity et de wfProcess).

    // R�cup�ration du wfProcess qui contient l'actvit�
    //Map m1 = activity.process_context();
    WfProcess process = activity.container(st);
    //Map m2 = process.process_context();
    return getWorkflowVariableValueCurrentProcess(st,process,workflowVariableId);
  }
  //-----------------------------------------------------------------------------
  public static Object getWorkflowVariableValueTopProcess(SharkTransaction st,WfActivity activity,String workflowVariableId)
    throws BaseException
  {
    WfProcess process = activity.container(st);
    return getWorkflowVariableValueTopProcess(st,process,workflowVariableId);
  }
  //-----------------------------------------------------------------------------
  public static Object getWorkflowVariableValueTopProcess(SharkTransaction st,WfProcess process,String workflowVariableId)
  throws BaseException
  {
    Object value;
    // Les sous-processus ne poss�dent pas de visibilit� sur les variables,
    // on recherche r�cursivement jusqu'� trouver le processus racine.
    
    // TODO : offrir tout de m�me la possibilit� d'op�rer la mise � jour dans le processus courant pour le cas o� la variable n'est pas factoris�e dans le processus racine
    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester(st) instanceof WfActivity)
      {
        act = (WfActivity)_process.requester(st);
        _process = act.container(st);
      }
      else
      {
        value = _process.process_context(st).get(workflowVariableId);
        break;
      }
    }
    return value;
  }
  //-----------------------------------------------------------------------------
  public static Object getWorkflowVariableValueTopProcess(WfActivity activity,String workflowVariableId)
    throws BaseException
  {
    WfProcess process = activity.container();
    return getWorkflowVariableValueTopProcess(process,workflowVariableId);
  }
  //-----------------------------------------------------------------------------
  public static Object getWorkflowVariableValueTopProcess(WfProcess process,String workflowVariableId)
  throws BaseException
  {
    Object value;
    // Les sous-processus ne poss�dent pas de visibilit� sur les variables,
    // on recherche r�cursivement jusqu'� trouver le processus racine.
    
    // TODO : offrir tout de m�me la possibilit� d'op�rer la mise � jour dans le processus courant pour le cas o� la variable n'est pas factoris�e dans le processus racine
    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester() instanceof WfActivity)
      {
        act = (WfActivity)_process.requester();
        _process = act.container();
      }
      else
      {
        value = _process.process_context().get(workflowVariableId);
        break;
      }
    }
    return value;
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie la liste des noms des "Workflow Variables" relatives � un process. Cette
   * liste est renvoy�e au sein d'un tableau tri�.
   *  
   * @param process
   * @return
   * @throws BaseException
   */
  public static String[] getWorkflowVariablesList(WfProcess process) throws BaseException
  {
    //ArrayList<String> variableNames = new ArrayList<String>();
    Map m = process.process_context();
    int size = m.size();
    String[] variableNames = new String[size];
    Object[] it = m.keySet().toArray();
    for(int i=0; i<size;i++)
      variableNames[i] = (String)it[i];
    Arrays.sort(variableNames);
    
    return variableNames;
  }
  //-----------------------------------------------------------------------------
  /**
   * Obtention de la valeur d'une workflow variable stock� dans un processus � partir
   * de l'activit�.<br/>
   * <i>note : m�thode strictement identique � getFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param workflowVariableId le nom de l'attribut.
   * @return la valeur de l'attribut associ�e dans l'instance du workflow.
   * @throws BaseException
   */
  public static Object getWorkflowVariableValueCurrentProcess(WfActivity activity,String workflowVariableId)
    throws BaseException
  {
    // R�cup�ration du wfProcess qui contient l'actvit�
    WfProcess process = activity.container();
    return getWorkflowVariableValueCurrentProcess(process,workflowVariableId);
  }
  //-----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'une workflow variable stock� dans le processus
   * racine.<br/>
   * <i>note : m�thode strictement identique � setFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus.
   * @param workflowVariableId le nom de l'attribut.
   * @param workflowVariableValue la valeur de l'attribut � mettre � jour.
   * @throws UpdateNotAllowed 
   * @throws InvalidData 
   * @throws BaseException 
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setWorkflowVariableValueTopProcess(SharkTransaction st,
                                                        WfProcess process,
                                                        String workflowVariableId,
                                                        Object workflowVariableValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // Les sous-processus ne poss�dent pas de visibilit� sur les variables,
    // on recherche r�cursivement jusqu'� trouver le processus racine.
    
    // TODO : offrir tout de m�me la possibilit� d'op�rer la mise � jour dans le processus courant pour le cas o� la variable n'est pas factoris�e dans le processus racine
    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester(st) instanceof WfActivity)
      {
        act = (WfActivity)_process.requester(st);
        _process = act.container(st);
      }
      else
      {
        Map m = new HashMap();
        m.put(workflowVariableId,workflowVariableValue);
        _process.set_process_context(st,m);
        break;
      }
    }
  }
  //-----------------------------------------------------------------------------
  @SuppressWarnings("unchecked")
  public static void setWorkflowVariableValueCurrentProcess(SharkTransaction st,
                                                            WfProcess process,
                                                            String workflowVariableId,
                                                            Object workflowVariableValue)
  throws BaseException, InvalidData, UpdateNotAllowed
  {
    Map m = new HashMap();
    m.put(workflowVariableId, workflowVariableValue);
    process.set_process_context(st, m);
  }
  // -----------------------------------------------------------------------------
  @SuppressWarnings("unchecked")
  public static void setWorkflowVariableValueCurrentProcess(WfProcess process, 
                                                            String workflowVariableId,
                                                            Object workflowVariableValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    Map m = new HashMap();
    m.put(workflowVariableId, workflowVariableValue);
    process.set_process_context(m);
  }

  // -----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'une workflow variable stock� dans un processus.<br/>
   * <i>note : m�thode strictement identique � setFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param process le processus.
   * @param workflowVariableId le nom de l'attribut.
   * @param workflowVariableValue la valeur de l'attribut � mettre � jour.
   * @throws UpdateNotAllowed 
   * @throws InvalidData 
   * @throws BaseException 
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setWorkflowVariableValueTopProcess(WfProcess process,
                                                        String workflowVariableId,
                                                        Object workflowVariableValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // Les sous-processus ne poss�dent pas de visibilit� sur les variables,
    // on recherche r�cursivement jusqu'� trouver le processus racine.
    
    // TODO : offrir tout de m�me la possibilit� d'op�rer la mise � jour dans le processus courant pour le cas o� la variable n'est pas factoris�e dans le processus racine
    WfProcess _process = process;
    WfActivity act = null;
    while(true)
    {
      if(_process.requester() instanceof WfActivity)
      {
        act = (WfActivity)_process.requester();
        _process = act.container();
      }
      else
      {
        Map m = new HashMap();
        m.put(workflowVariableId,workflowVariableValue);
        _process.set_process_context(m);
        break;
      }
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'une workflow variable stock� dans un processus �
   * partir de l'activit�.<br/>
   * <i>note : m�thode strictement identique � setFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param workflowVariableId le nom de l'attribut.
   * @param workflowVariableValue la valeur de l'attribut � mettre � jour.
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setWorkflowVariableValueTopProcess(SharkTransaction st,
                                                        WfActivity activity,
                                                        String workflowVariableId,
                                                        Object workflowVariableValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // R�cup�ration du wfProcess qui contient l'actvit�
    WfProcess process = activity.container(st);
    setWorkflowVariableValueTopProcess(st,process,workflowVariableId,workflowVariableValue);
  }
  //-----------------------------------------------------------------------------
  public static void setWorkflowVariableValueCurrentProcess(
      SharkTransaction st, WfActivity activity, String workflowVariableId,
      Object workflowVariableValue) throws BaseException, InvalidData,
      UpdateNotAllowed
  {
    // R�cup�ration du wfProcess qui contient l'actvit�
    WfProcess process = activity.container(st);
    setWorkflowVariableValueCurrentProcess(st, process, workflowVariableId,
        workflowVariableValue);
  }
  //-----------------------------------------------------------------------------
  public static void setWorkflowVariableValueCurrentProcess(WfActivity activity,
                                                            String workflowVariableId,
                                                            Object workflowVariableValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // R�cup�ration du wfProcess qui contient l'activit�
    WfProcess process = activity.container();
    setWorkflowVariableValueCurrentProcess(process, workflowVariableId,workflowVariableValue);
  }
  // -----------------------------------------------------------------------------
  /**
   * Mise � jour de la valeur d'une workflow variable stock� dans un processus �
   * partir de l'activit�.<br/>
   * <i>note : m�thode strictement identique � setFormalParameterValue, l'acc�s
   * via l'API Shark se faisant de la m�me mani�re.</i>
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @param workflowVariableId le nom de l'attribut.
   * @param workflowVariableValue la valeur de l'attribut � mettre � jour.
   * @throws BaseException
   * @throws InvalidData
   * @throws UpdateNotAllowed
   */
  @SuppressWarnings("unchecked")
  public static void setWorkflowVariableValueTopProcess(WfActivity activity,
                                                        String workflowVariableId,
                                                        Object workflowVariableValue)
    throws BaseException, InvalidData, UpdateNotAllowed
  {
    // R�cup�ration du wfProcess qui contient l'actvit�
    WfProcess process = activity.container();
    setWorkflowVariableValueTopProcess(process,workflowVariableId,workflowVariableValue);
  }
  //-----------------------------------------------------------------------------
  /**
   * Acceptation d'une activit� : on r�cup�re le WfAssignment associ� � l'activit�
   * et on l'accepte.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @throws BaseException
   * @throws CannotAcceptSuspended
   */
  public static void acceptActivity(SharkTransaction st,WfActivity activity)
    throws BaseException, CannotAcceptSuspended
  {
    WfAssignment[] assignments = activity.get_sequence_assignment(st,1);
    for(WfAssignment assignment : assignments)
    {
      assignment.set_accepted_status(st,true);
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Acceptation d'une activit� : on r�cup�re le WfAssignment associ� � l'activit�
   * et on l'accepte.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @throws BaseException
   * @throws CannotAcceptSuspended
   */
  public static void acceptActivity(WfActivity activity)
    throws BaseException, CannotAcceptSuspended
  {
    WfAssignment[] assignments = activity.get_sequence_assignment(1);
    for(WfAssignment assignment : assignments)
    {
      assignment.set_accepted_status(true);
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Terminaion d'une activit�.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @throws BaseException
   * @throws CannotComplete
   */
  public static boolean activityComplete(SharkTransaction st,WfActivity activity)
    throws BaseException, CannotComplete
  {
    activity.complete(st);
    return true;
//    if(activity.state().equals("open.running"))
//    {
//      activity.complete(st);
//      return true;
//    }
//    else
//    {
//      try
//      {
//        activity.abort(st);
//        return false;
//      }
//      catch (Exception e)
//      {
//        return false;
//      }
//    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Terminaion d'une activit�.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @param activity l'activit�.
   * @throws BaseException
   * @throws CannotComplete
   */
  public static boolean activityComplete(WfActivity activity)
    throws BaseException, CannotComplete
  {
    if(activity.state().equals("open.running"))
    {
      activity.complete();
      return true;
    }
    else
    {
      try
      {
        activity.abort();
        return false;
      }
      catch (Exception e)
      {
        return false;
      }
    }
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie une transaction Shark. M�thode pr�sente pour des raisons utilitaires
   * uniquement.
   * 
   * @return la transaction Shark demand�e.
   * @throws TransactionException
   */
  public static SharkTransaction getSharkTransaction() throws TransactionException
  {
    return shark.createTransaction();
  }
  //-----------------------------------------------------------------------------
  /**
   * Vide les caches Shark. M�thode pr�sente pour des raisons utilitaires uniquement.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   */
  public static void emptyCaches(SharkTransaction st)
  {
    shark.emptyCaches(st);
  }
  //-----------------------------------------------------------------------------
  /**
   * D�blockage des processus. M�thode pr�sente pour des raisons utilitaires
   * uniquement.
   * 
   * @param st transactionShark au sein de laquelle s'ex�cute la m�thode.
   * @throws TransactionException
   */
  public static void unlockProcesses(SharkTransaction st) throws TransactionException
  {
    shark.unlockProcesses(st);
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie la liste des processus dans un �tat particulier.

   * @param state �tat du processus ("open.running", "open.not_running.not_started",
   *              "open.not_running.suspended", "closed.completed", "closed.terminated"
   *              ou "closed.aborted")
   * 
   * @return
   * @throws BaseException
   * @throws NotConnected
   */
  public static ArrayList<WfProcess> processList(String state)
    throws BaseException, NotConnected
  {
    // "open.running"
    // "open.not_running.not_started"
    // "open.not_running.suspended"
    // "closed.completed"
    // "closed.terminated"
    // "closed.aborted"
    ArrayList<WfProcess> processes = new ArrayList<WfProcess>();
    WfProcessMgr[] mgrs = executionAdministration.get_sequence_processmgr(0);
    WfProcess[] processesTab = null;
          
    for (int i = 0; i<mgrs.length;i++)
    {
      processesTab = mgrs[i].get_sequence_process(0);
      for (WfProcess process : processesTab)
      {
        if(process.state().equals(state))
          processes.add(process);
      }
    }
    return processes;
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie la liste des processus quel que soit leur �tat : "open.running",
   * "open.not_running.not_started", "open.not_running.suspended",
   * "closed.completed", "closed.terminated" ou "closed.aborted".
   * 
   * @return
   * @throws BaseException
   * @throws NotConnected
   */
  public static ArrayList<WfProcess> processList()
    throws BaseException, NotConnected
  {
    ArrayList<WfProcess> processes = new ArrayList<WfProcess>();
    WfProcessMgr[] mgrs = executionAdministration.get_sequence_processmgr(0);
    WfProcess[] processesTab = null;
          
    for (int i = 0; i<mgrs.length;i++)
    {
      processesTab = mgrs[i].get_sequence_process(0);
      for (WfProcess process : processesTab)
      {
        processes.add(process);
      }
    }
    return processes;
  }
  //-----------------------------------------------------------------------------
  /**
   * La m�thode s'appelle "getAll" parce que je n'ai pas r�ussi � lui trouver un
   * autre nom...<br/>
   * 
   * @param all true si on veut tous les processus et activit�s m�me termin�s,
   *            false si on ne veut que ceux qui sont en cours. 
   * @return un HashMap dont les cl�s sont des instances de processus (WfProcess)
   *         et les valeurs associ�es des tableaux d'activit�s (WfActivity)
   * @throws NotConnected 
   * @throws BaseException 
   */
  public static HashMap<WfProcess, WfActivity[]> getAll(boolean all)
    throws BaseException, NotConnected
  {
    //HashMap<String, HashMap<String,ArrayList<String[]>>> p = new HashMap<String, HashMap<String,ArrayList<String[]>>>();
    
    HashMap<WfProcess,WfActivity[]> result = new HashMap<WfProcess,WfActivity[]>(); 

    // Il y a un "WfProcessMgr" par processus d�clar� dans Shark
    WfProcessMgr[] mgrs = getProcessMgr();
    WfProcess[] processesTab = null;
    
    for (WfProcessMgr mgr : mgrs)
    {
      processesTab = mgr.get_sequence_process(0);
      for (WfProcess process : processesTab)
      {
        if(!process.state().equals("closed.completed") || all)
        {
          // Note 1 : pour chaque processus trouv�, la m�thode "getWorkflowVariablesList(process)"
          // de r�cup�rer la liste des noms de variables associ�es � ce processus.
          // Par la suite, la m�thode "getWorkflowVariableValueCurrentProcess(process, variableName)"
          // permet d'en r�cup�rer la valeur.
          
          // Note 2 : pour chaque processus et activit�, le nom et l'�tat peuvent �tre connus
          // � l'aide des m�thodes getName(...) et getState(...). La date de la derni�re modif
          // peut se trouver avec la m�thode getLastStateTime(...)

          WfActivity[] activities = process.get_sequence_step(0);
          result.put(process, activities);
        }
      }
    }
    
    return result;
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie l'�tat d'un processus :
   * <ul>
   *   <li>open.running</li>
   *   <li>open.not_running.not_started</li>
   *   <li>open.not_running.suspended</li>
   *   <li>closed.completed</li>
   *   <li>closed.terminated</li>
   *   <li>closed.aborted</li>
   * </ul>
   *  
   * @param process le processus dont on veut connaitre l'�tat.
   * @return
   * @throws BaseException 
   */
  public static String getState(WfProcess process)
    throws BaseException
  {
    return process.state();
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie l'�tat d'une activit� :
   * <ul>
   *   <li>open.running</li>
   *   <li>open.not_running.not_started</li>
   *   <li>open.not_running.suspended</li>
   *   <li>closed.completed</li>
   *   <li>closed.terminated</li>
   *   <li>closed.aborted</li>
   * </ul>
   *  
   * @param activity l'activit� dont on veut connaitre l'�tat.
   * @return
   * @throws BaseException 
   */
  public static String getState(WfActivity activity)
    throws BaseException
  {
    return activity.state();
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie le nom d'un processus.
   * 
   * @param process le processus dont on veut connaitre le nom
   * @return
   * @throws BaseException 
   */
  public static String getName(WfProcess process)
    throws BaseException
  {
    return process.name();
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie le nom d'une activit�.
   * 
   * @param activity l'activit� dont on veut connaitre le nom
   * @return
   * @throws BaseException 
   */
  public static String getName(WfActivity activity)
    throws BaseException
  {
    return activity.name();
  }
  //-----------------------------------------------------------------------------
  public static Date getLastStateTime(WfProcess process)
    throws BaseException
  {
    return process.last_state_time().getTimestamp();
  }
  //-----------------------------------------------------------------------------
  public static WfProcessMgr[] getProcessMgr() throws BaseException, NotConnected
  {
    return executionAdministration.get_sequence_processmgr(0);
  }
  //-----------------------------------------------------------------------------
  public static WfProcess getWfProcess(String idEnfant) throws BaseException, NotConnected
  {
    WfProcessMgr[] mgrs = getProcessMgr();
    WfProcess[] processesTab = null;
    WfProcess findedProcess = null;    
    for(WfProcessMgr processMgr : mgrs)
    {
      processesTab = processMgr.get_sequence_process(0);
      for (WfProcess process : processesTab)
      {
        String idEnfantProcess = (String)WorkflowWrapper.getFormalParameterValue(process, "idEnfant");
        if(idEnfantProcess.equals(idEnfant))
        {
          findedProcess = process;
          break;
        }
      }
    }
    
    return findedProcess;
  }
  //-----------------------------------------------------------------------------
  /**
   * Renvoie la liste des processus qui ne sont pas termin�s (ceux dont l'�tat)
   * est diff�rent de "closed.completed"
   * 
   * @return
   * @throws BaseException
   * @throws NotConnected
   */
  public static ArrayList<WfProcess> processRunningList()
    throws BaseException, NotConnected
  {
    // "open.running"
    // "open.not_running.not_started"
    // "open.not_running.suspended"
    // "closed.completed"
    // "closed.terminated"
    // "closed.aborted"
    System.out.println();
    System.out.println("Les processus attribes aux 6 WfProcessMgr (nombre attribue au depart dependant du nbre de processus dans le XPDL ?)");
    ArrayList<WfProcess> processes = new ArrayList<WfProcess>();
    WfProcessMgr[] mgrs = executionAdministration.get_sequence_processmgr(0);
    WfProcess[] processesTab = null;
          
    for (int i = 0; i<mgrs.length;i++)
    {
      System.out.println("####### WfProcessMgr numero : " + i);
      processesTab = mgrs[i].get_sequence_process(0);
      for (WfProcess process : processesTab)
      {
        if (!process.state().equals("closed.completed"))
        {
          System.out.println("#######   " + process.name() + " -> "+process.state());
          WfActivity[] activities = process.get_sequence_step(0);
          for (WfActivity act : activities)
          {
            if (!act.state().equals("closed.completed"))
            {
              System.out.println("#######     -> " + act.name() + " -> "+act.state());
            }
          }
          
          processes.add(process);
        }
      }
    }
    
//    System.out.println();
//    System.out.println("Les ressources = les utilisateurs Shark ?");
//    WfResource[] resources = executionAdministration.get_sequence_resource(0);
//    for (int i = 0; i<resources.length;i++)
//    {
//      WfResource resource =resources[i]; 
//      System.out.println("####### WfResource numero : " + i);
//      System.out.println("#######   resource_key()  : " + resource.resource_key());
//      System.out.println("#######   resource_name() : " + resource.resource_name());
//      System.out.println("#######   toString()      : " + resource.toString());
//    }
    
    System.out.println();
    System.out.println("Les assignments = ???");
    WfAssignmentIterator it = executionAdministration.get_iterator_assignment();
    for (int i = 0; i<it.how_many();i++)
    {
      WfAssignment assignment = it.get_next_object();
      System.out.println("####### WfAssignment numero : " + i);
      
    }

      return processes;
  }
  //-----------------------------------------------------------------------------
}
