import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Part1 implements Behavior {
	private boolean suppressed = false; // pour synchroniser la suppression
	private DifferentialPilot pilote;
	private UltrasonicSensor sonarAr;
	private UltrasonicSensor sonarAv;
	private TouchSensor button;

	public Part1(DifferentialPilot pilote, UltrasonicSensor sonarAr, UltrasonicSensor sonarAv, TouchSensor button) {
		super();
		this.pilote = pilote;
		this.sonarAr = sonarAr;
		this.sonarAv = sonarAv;
		this.button = button;
	}

	public boolean takeControl() {
		return !MartinController.Part1IsDone && MartinController.BalayageIsDone;

	}

	public void action() {
		pilote.setTravelSpeed(10);
		pilote.setRotateSpeed(45);
		pilote.rotate(90);
		MartinController.departY = sonarAr.getDistance();

		while (sonarAv.getDistance() < 43 - sonarAr.getDistance()) {
			pilote.rotate(-90);
			pilote.travel(20);
			pilote.rotate(90);
		}
		suppressed = false; // remise a zero
		// en attendant

		// pilote.arc(10.0, 90.0, true);
		// pilote.arc(-10.0, 90.0, true); // entame un virage arriere (sans attendre la
		// fin)
		// while (!suppressed && pilote.isMoving()) { // tant que {suppress} n'a pas ete
		// appele
		// et que le virage n'est pas termine...
		// Thread.yield(); // ... liberer le processeur
		// } // sortie de boucle: {suppress} a ete appele ou le virage est termine
		// pilote.stop(); // arreter le robot
		if (sonarAr.getDistance() <= 42) {
			pilote.forward(); // marche arriere
			while (!suppressed && sonarAr.getDistance() < 42) {
				Thread.yield();// libere le processeur
			}
		} else {
			pilote.backward(); // marche arriere
			while (!suppressed && sonarAr.getDistance() > 42) {
				Thread.yield();// libere le processeur
			}
		}
		pilote.stop();

		pilote.arc(0.0, -90.0, true);
		while (!suppressed && pilote.isMoving()) { // tant que {suppress} n'a pas ete appele
			// et que le virage n'est pas termine...
			Thread.yield(); // ... liberer le processeur
		} // sortie de boucle: {suppress} a ete appele ou le virage est termine
		pilote.stop(); // arreter le robot
		pilote.backward(); // marche arriere
		while (!suppressed && !button.isPressed()) {
			Thread.yield();// libere le processeur
		}
		pilote.stop(); // arrete le robot
		MartinController.Part1IsDone = true;
	}

	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
		suppressed = true;
	}

}
