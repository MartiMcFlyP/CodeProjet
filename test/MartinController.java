import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;

public class MartinController {

	// Toutes les distances sont en cm.
	public static final float DIAM_ROUE = 5.6f; // Diametre des roues
	public static final float DIST_ROUE = 13.8f; // Empattement entre les roues
	public static double alleeX = 0;
	public static double arc0 = 75;
	public static double arc1 = 75;
	public static double arc2 = 80;
	public static double arc3 = 97;
	public static double arc4 = 101;
	public static double arc5 = 105;
	public static double arc6 = 105;
	public static double arc7 = 105;
	public static double arc8 = 105;
	public static double arc9 = 105;
	public static double[] obstales = new double[] { 200, 200, 200, 200, 200, 200 };
	public static double avionX = 0;
	public static double departY = 0;
	public static boolean Part2Variante = false;
	public static boolean BalayageIsDone = true;
	public static boolean ReperageIsDone = false;
	public static boolean Part1IsDone = false;
	public static boolean Part2IsDone = false;
	public static boolean Part3IsDone = false;
	public static boolean PisteIsDone = false;
	public static boolean RetourIsDone = false;
	public double piloteX = 0;
	public double piloteY = 0;
	public static double angle = 0;
	public static double angleR = 0;

	/**
	 * Cree et demarre la liste de taches
	 */

	public static void main(String[] args) {

		// Le pilote pour les roues
		DifferentialPilot pilote = new DifferentialPilot(DIAM_ROUE, DIST_ROUE, Motor.C, Motor.A);
		// le senseur de distance d'ultrasons
		UltrasonicSensor sonarAv = new UltrasonicSensor(SensorPort.S1);
		TouchSensor button = new TouchSensor(SensorPort.S2);
		LightSensor lightSensor = new LightSensor(SensorPort.S3);
		UltrasonicSensor sonarAr = new UltrasonicSensor(SensorPort.S4);
		// Les trois taches, par ordre croissant de priorite
		Behavior balayage = new Balayage(pilote, sonarAv, sonarAr);
		Behavior reperage = new Reperage(pilote, sonarAv, sonarAr, lightSensor);
		Behavior partie1 = new Part1(pilote, sonarAr, sonarAv, button);
		Behavior partie2 = new Part2(pilote, sonarAv, lightSensor);
		Behavior partie3 = new Part3(pilote, sonarAv, lightSensor);
		Behavior piste = new Piste(pilote, sonarAv);
		Behavior retour = new Retour(pilote, sonarAv);
		Behavior stop = new StopBehavior(pilote);
		Behavior[] taches = { balayage, partie1, reperage, partie2, partie3, piste, retour, stop };

		// Initialiser et activer le controle par les trois taches
		Arbitrator arbitre = new Arbitrator(taches);
		arbitre.start();

	}

}
