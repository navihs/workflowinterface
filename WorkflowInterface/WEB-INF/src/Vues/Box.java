package Vues;
import XPDLInterface.*;

import java.util.*;
/**
 * Classe Box
 * @author Julien
 *
 */
public class Box{
	
	private Activity act;
	public Activity getAct(){return this.act;}
	
	private int positionX;
	public int getPositionX(){return this.positionX;}
	
	private int positionY;
	public void setPositionY(int boxPositionY){this.positionY=boxPositionY;}
	public int getPositionY(){return this.positionY;}
	
	
/**
 * Contructeur de Boite contenant l'activité concerné ainsi que ca position sur la page à l'affichage
 * @param act
 * @param x
 * @param y
 */
	public Box(Activity act,int x,int y)
	{
		this.act=act;
		this.positionX=x;
		this.positionY=y;
	}
	
}

