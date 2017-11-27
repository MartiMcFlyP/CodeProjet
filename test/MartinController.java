import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;

public class MartinController {

	// Toutes les distances sont en cm.
	private static final float DIAM_ROUE = 5.6f; // Diametre des roues
	private static final float DIST_ROUE = 13.8f; // Empattement entre les roues
	public static boolean Part1IsDone = false;
	public static boolean Part2IsDone = false;
	/**
	 * Cree et demarre la liste de taches
	 */

	public static void main(String[] args) {

		// Le pilote pour les roues
		DifferentialPilot pilote = new DifferentialPilot(DIAM_ROUE, DIST_ROUE, Motor.A, Motor.C);
		// le senseur de distance d'ultrasons
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S1);
		TouchSensor button = new TouchSensor(SensorPort.S2);
		
		// Les trois taches, par ordre croissant de priorite
		Behavior partie1 = new Part1(pilote, sonar, button);
		Behavior stop = new StopBehavior(pilote);
		Behavior[] taches = { partie1, stop };

		// Initialiser et activer le controle par les trois taches
		Arbitrator arbitre = new Arbitrator(taches);
		arbitre.start();

	}

}
