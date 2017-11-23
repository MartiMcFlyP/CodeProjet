import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;

/**
 * Une tache qui arrete le robot et le programme si ESCAPE est presse.
 * 
 * @author Charles Pecheur, Detienne Nicolas
 * @version 2014
 */
public class StopBehavior implements Behavior {

	private DifferentialPilot pilote;	
	
	/**
	 * @pre {pilote} permet de piloter le robot
	 * @post la tache a ete cree  
	 */
	public StopBehavior(DifferentialPilot pilote) {
		super();
		this.pilote = pilote;
	}

	/**
	 * @pre --
	 * @post le robot est arrete et le programme est termine.
	 */
	public void action() {
	    pilote.stop();
	    System.exit(0);
	}

	/**
	 * @pre --
	 * @post aucun effet, l'action se termine immediatement d'elle-meme.
	 */
	public void suppress() {
	}

	/**
	 * @pre --
	 * @post retourne {true} si le bouton ESCAPE est enfonce.
	 */
	public boolean takeControl() {
	     return Button.ESCAPE.isDown();
	}

}
