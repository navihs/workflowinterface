package shark;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.ConnectFailed;
import org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid;
import org.enhydra.shark.api.client.wfservice.PackageInvalid;

public class InitComponent
{
  // -----------------------------------------------------------------------------
  /**
   * R�pertoire contenant l'ensemble des fichiers n�cessaires � l'application : fichiers
   * de conf., BD HSQL, reposotory XPDL, etc. Sa valeur vaut : realPath + "WEB-INF/files/".
   */
  private static String filesDir;

  /**
   * Nom du fichier XPDL � prendre en compte.
   */
  private static String XPDLFile = "soumission_article.xpdl";
  /**
   * Valeur de l'attibut "Id" de l'�l�ment "Package". 
   */
  private static String packageId = "soumission_article";
  /**
   * Valeur de l'attibut "Id" de l'�l�ment "Package/WorkflowProcesses/WorkflowProcess".
   * (un "Package" peut contenir pluusieurs processus)
   */
  private static String workflowProcessId = "soumission_article_processus";
  /**
   * Valeur de l'attribut "enginename" du fichier "Shark.conf".
   */
  private static String engineName = "SoumissionArticleShark";
  //-----------------------------------------------------------------------------
  /**
   * Initialisation de l'application Web. M�thode appel�e par la servlet
   * d'initialisation "InitServlet" lanc�e automatique au d�marrage de Tomcat.
   * La m�thode initialise les chemins dans le syt�me de fichier, initialise 
   * Shark puis initialise l'acc�s � la base de donn�es.
   * 
   * @param realPath le chemin d'installation de l'application Web obtenu par
   *        "InitServlet".
   *        
   * @throws NamingException 
   * @throws IOException 
   * @throws ExternalPackageInvalid 
   * @throws PackageInvalid 
   * @throws ConnectFailed 
   * @throws BaseException 
   * @throws FileNotFoundException 
   */
  public static void init(String realPath)
      throws NamingException, FileNotFoundException, BaseException, 
             ConnectFailed, PackageInvalid, ExternalPackageInvalid, IOException
  {
      // Mise � jour du chemin d'installation de l'application Web.
      realPath = realPath.replace('\\', '/');
      filesDir = realPath + "WEB-INF/files/";

      initShark();
  }
  //-----------------------------------------------------------------------------
  /**
   * Initialisation de l'instance de Shark.
   * 
   * @throws IOException 
   * @throws ConnectFailed 
   * @throws BaseException 
   * @throws FileNotFoundException 
   * @throws ExternalPackageInvalid 
   * @throws PackageInvalid 
   * 
   */
  private static void initShark()
      throws FileNotFoundException, BaseException, ConnectFailed, 
             IOException, PackageInvalid, ExternalPackageInvalid
  {
      // Initialisation et lancement de Shark
      
      System.out.println("####### Initialisation du moteur de worflow Shark");
      WorkflowWrapper.init(filesDir + "SharkConfiguration/Shark.conf");
      // Note : on ne fournit que le bom du fichier XPDL, le r�pertoire le contenant
      // est param�tr� au sein du fichier "Shark.conf" par l'attribut
      // EXTERNAL_PACKAGES_REPOSITORY (ligne 126).
      WorkflowWrapper.openPackage(XPDLFile, packageId);

      //ArrayList<String[]> groups = DBComponent.getAllGroups();
      ArrayList<String[]> groups = new ArrayList<String[]>();
      String[] group1 = {"auteur",              "R�le auteur"}; 
      String[] group2 = {"valideur_interne",    "R�le valideur interne"}; 
      String[] group3 = {"valideur_conference", "R�le valideur conf�rence"}; 
      groups.add(group1);
      groups.add(group2);
      groups.add(group3);
      WorkflowWrapper.createGroups(groups);

      ArrayList<String[]> users = new ArrayList<String[]>();
      // Note : chaque type (groupe) d'utilisateurs partage le m�me utilisateur
      // Shark. Aux trois auteurs (auteur1, 2 et 3) correspond l'utilisateur
      // Shark "auteur_delegue".
      // Contenu du table :
      //   - groupe
      //   - login Shark 
      //   - mot de passe Shark 
      //   - nom (vide)
      //   - pr�nom (vide)
      //   - adresse email (vide)
      String[] userSharkAuteur = {"auteur",
                                  "auteur_delegue",
                                  "a_password",
                                  "",
                                  "",
                                  ""}; 
      String[] userSharkValideurInterne = {"valideur_interne",
                                           "valideur_interne_delegue",
                                           "vi_password",
                                           "",
                                           "",
                                           ""}; 
      String[] userSharkValideurConference = {"valideur_conference",
                                              "valideur_conference_delegue",
                                              "vc_password",
                                              "",
                                              "",
                                              ""}; 
      users.add(userSharkAuteur);
      users.add(userSharkValideurInterne);
      users.add(userSharkValideurConference);
      WorkflowWrapper.createUsers(users);
      //WorkflowWrapper.connectUsers(users,engineName);
  }
  //-----------------------------------------------------------------------------
}
