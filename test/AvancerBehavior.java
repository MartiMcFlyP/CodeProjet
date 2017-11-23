import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * Une tache qui fait avancer le robot indfiniment en ligne droite.
 * 
 * @author Charles Pecheur, Viet Trong Ho
 * @version 2011
 */
public class AvancerBehavior implements Behavior {

	private boolean suppressed = false;  // pour synchroniser la suppression
	private DifferentialPilot pilote;	
	
	/**
	 * @pre {pilote} permet de piloter le robot
	 * @post la tche a ete cree  
	 */
	public AvancerBehavior(DifferentialPilot pilote) {
		super();
		this.pilote = pilote;
	}

	/**
	 * @pre --
	 * @post le robot a avance jusqu'a ce que {suppress} ait ete appele, et
	 *       s'est ensuite arrete.
	 */
	public void action() {
	    suppressed = false;     // remise a zero
	    pilote.forward();       // mettre le robot en marche avant, sans limite
	    while (!suppressed) {   // tant que {suppress} n'a pas ete appele...
	    	Thread.yield();     // ... liberer le processeur
	    }                       // sortie de boucle: {suppress} a ete appele 
	    pilote.stop();          // arreter le robot
	}

	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
	     suppressed = true;
	}

	/**
	 * @pre --
	 * @post retourne {true}: la tache est toujours activable.
	 */
	public boolean takeControl() {
		return true;
	}

}
