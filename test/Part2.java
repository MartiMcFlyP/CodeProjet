import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Part2 implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilote;
	private LightSensor lightSensor;
	private UltrasonicSensor sonar;
	private UltrasonicSensor sonarAr;
	private boolean evite = false;

	public Part2(DifferentialPilot pilote, UltrasonicSensor sonar, UltrasonicSensor sonarAr, LightSensor lightSensor) {
		this.pilote = pilote;
		this.sonar = sonar;
		this.lightSensor = lightSensor;
		this.sonarAr = sonarAr;
	}

	public boolean takeControl() {
		return MartinController.ReperageIsDone && !MartinController.Part2IsDone;
	}

	public void action() {
		suppressed = false;
		if (!MartinController.Part2Variante) {
			pilote.forward();
			while (!suppressed && lightSensor.getLightValue() > 44 && sonar.getDistance() > 12.5) {
				Thread.yield();// libere le processeur
			}
			pilote.stop();
			if (sonar.getDistance() <= 15) {
				evite = true;
				pilote.rotate(-90);
				pilote.travel(20);
				pilote.rotate(90);
//				if (sonar.getDistance() < 15) {
//					pilote.rotate(-90);
//					pilote.travel(12.5);
//					pilote.rotate(90);
//				}
				pilote.forward();
				while (lightSensor.getLightValue() > 44) {
					Thread.yield();
				}
				pilote.stop();
			}
			MartinController.avionX = 119 - sonar.getDistance();
			if ((sonarAr.getDistance() + 23) > 119 - sonar.getDistance()) {
				MartinController.avionX = sonarAr.getDistance() + 23;
			}
			if (sonar.getDistance() > 30 && !evite) {
				pilote.travel(24);// avance pour pouvoir s'aligner
				pilote.arc(-10, 90, true); // se positionne sur la ligne
				while (!suppressed && pilote.isMoving()) {
					Thread.yield();// libere le processeur
				}
				pilote.stop();
			} else {
				pilote.travel(5);
				pilote.arc(10, 90);
				while (!suppressed && pilote.isMoving()) {
					Thread.yield();
				}
			}
		} else {
			pilote.forward();
			while (!suppressed && lightSensor.getLightValue() > 44 && sonar.getDistance() > 12.5) {
				Thread.yield();// libere le processeur
			}
			pilote.stop();
			if (sonar.getDistance() <= 15) {
				evite = true;
				pilote.rotate(90);
				pilote.travel(20);
				pilote.rotate(-90);
//				if (sonar.getDistance() < 15) {
//					pilote.rotate(90);
//					pilote.travel(12.5);
//					pilote.rotate(-90);
//				}

				pilote.forward();
				while (!suppressed && lightSensor.getLightValue() > 44) {
					Thread.yield();
				}
				pilote.stop();
			}
			MartinController.avionX = sonar.getDistance();
			if (119 - (sonarAr.getDistance() + 23) > sonar.getDistance()) {
				MartinController.avionX = 119 - (sonarAr.getDistance() + 23);
			}
			if (sonar.getDistance() > 30 && !evite) {
				pilote.travel(24);// avance pour pouvoir s'aligner
				pilote.arc(10, -90, true); // se positionne sur la ligne
				while (!suppressed && pilote.isMoving()) {
					Thread.yield();// libere le processeur
				}
				pilote.stop();
			} else {
				pilote.travel(5);
				pilote.arc(-10, -90);
				while (!suppressed && pilote.isMoving()) {
					Thread.yield();
				}
			}
		}
		pilote.backward(); // le robot recule jussqua l avion
		while (!suppressed && ((int) lightSensor.getLightValue()) > 36) {
			Thread.yield();
		}
		pilote.stop();
		pilote.travel(-4);
		Motor.B.rotate(830); // ferme la pince
		LCD.drawInt((int)MartinController.alleeX, 0, 0);
		LCD.drawInt((int)MartinController.avionX, 0, 1);
		MartinController.Part2IsDone = true; // part2 est finie
	}

	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
		suppressed = true;
	}

}
