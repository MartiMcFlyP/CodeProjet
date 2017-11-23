import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;

/**
 * Un controleur de robot simple. Utilise un robot de type "chaise roulante"
 * avec le detecteur d'ultrasons. Le robot avance tout droit mais fait une
 * manoeuvre arriere si le detecteur percoit un obstacle. Le robot s'arrete
 * immediatement si on appuye sur le bouton Escape.
 * 
 * @author Charles Pecheur, Viet Trong Ho
 * @version 2011
 */
public class SimpleController {

	// Toutes les distances sont en cm.   
	private static final float DIAM_ROUE = 5.5f;  // Diametre des roues
	private static final float DIST_ROUE = 11f;   // Empattement entre les roues
	
	/**
	 * Cree et demarre la liste de taches
	 */
	public static void main(String[] args) {

		// Le pilote pour les roues
		DifferentialPilot pilote = new DifferentialPilot(DIAM_ROUE, DIST_ROUE, Motor.A, Motor.C);
		// le senseur de distance d'ultrasons
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S1);                             

		// Les trois taches, par ordre croissant de priorite
		Behavior avancer = new AvancerBehavior(pilote);
		Behavior eviter = new EviterBehavior(pilote, sonar);
		Behavior stop = new StopBehavior(pilote);
		Behavior[] taches = { avancer, eviter, stop };

		// Initialiser et activer le controle par les trois taches
		Arbitrator arbitre = new Arbitrator(taches);
		arbitre.start();
	}

}
