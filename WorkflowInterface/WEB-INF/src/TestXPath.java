import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;


public class TestXPath
{
  @SuppressWarnings("unchecked")
  public static void main(String[] args)
  {

    try
    {
      SAXBuilder sxb = new SAXBuilder();
      Document document = sxb.build(new File("D:/user/ptanguy/Developpement/JACOT/jacotbis/WEB-INF/files/XPDLrepository/DAI3.1.xpdl"));
      
      
      XPath xpath = XPath.newInstance("// namespaceParDefaut:ExtendedAttribute");
      xpath.addNamespace("namespaceParDefaut","http://www.wfmc.org/2002/XPDL1.0");
      List elements = xpath.selectNodes(document);

      System.out.println("Document        = " + document);
      System.out.println("elements.size() = " + elements.size());
      
      Iterator it = elements.iterator();
      while(it.hasNext())
      {
        Element elem = (Element)it.next();
        Element parent = (Element)elem.getParent().getParent();
        System.out.println("ExtendedAttribute - Name =  : " + elem.getAttributeValue("Name") + "   -> parent = " + parent.getName() + "(Id = " + parent.getAttributeValue("Id") + ")");
      }
    }
    catch (JDOMException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  // QUELQUES EXEMPLES D'EXPRESSIONS XPath
  // -------------------------------------
  //
  // Récupérer tous les élément "ExtendedAttribute".
  //   ExtendedAttribute
  //
  // Récupérer la valeur de l'attribut "Name" de tous les éléments "ExtendedAttribute"
  //   ExtendedAttribute/@Name
  //
  // Récupérer tous les éléments "Transition" dont l'attribut from est "temps_restauration_scolaire"
  //   Transition[@from='temps_restauration_scolaire']

}
