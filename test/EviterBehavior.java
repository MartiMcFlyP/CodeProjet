import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * Une tache qui fait un virage d'1/4 tour a gauche en arriere quand un obstacle est
 * detecte.
 * 
 * @author Charles Pecheur, Viet Trong Ho
 * @version 2011
 */
public class EviterBehavior implements Behavior {

	private boolean suppressed = false; // pour synchroniser la suppression
	private DifferentialPilot pilote;
	private UltrasonicSensor sonar;

	/**
	 * @pre {pilote} permet de piloter le robot, {sonar} permet de detecter les
	 *      obstacles.
	 * @post la tache a ete cree.
	 */
	public EviterBehavior(DifferentialPilot pilote, UltrasonicSensor sonar) {
		super();
		this.pilote = pilote;
		this.sonar = sonar;
	}

	/**
	 * @pre --
	 * @post le robot a fait un virage d'1/4 tour en arrire, et s'est ensuite
	 *       arret. La manoeuvre est interrompue si {suppress} a ete appel.
	 */
	public void action() {
		suppressed = false;                // remise a zero
		pilote.arc(20.0, -90.0, true);     // entame un virage arriere (sans attendre la fin)
		while (!suppressed && pilote.isMoving()) {  // tant que {suppress} n'a pas ete appele
			                                        // et que le virage n'est pas termine...
			Thread.yield();                // ... liberer le processeur
		}                // sortie de boucle: {suppress} a ete appele ou le virage est termine
		pilote.stop();   // arreter le robot
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
	 * @post retourne {true} ssi un obstacle est detecte a moins de ~25 cm.
	 */
	public boolean takeControl() {
		return sonar.getDistance() < 25;
	}

}
