package org.usfirst.frc.team5422.controller;

import org.usfirst.frc.team5422.DSIO.DSIO;

public class AutonomousController {
	public static void go() {
		DSIO.getInputFromAutoUI();

		String autoSequence;
		String[] moves;
		int[] defensePositions = new int[9];

		defensePositions[0] = 0;
		
		for (int i = 1; i < 9; i++) {
			defensePositions[i] = -1;
		}

		for (int i = 1; i < 5; i++) {
			for (int c = 1; c < 9; c++) {
				if (DSIO.pos[i] == c) {
					defensePositions[c] = DSIO.pos[i];
				}
			}//End inner for()
		}//End outer for()
		
		
		for (int i = 1; i < 9; i++) {
			for (int c = 1; c < 5; c++) {
				if (DSIO.pos[c] == i) {
					defensePositions[i] = c;
				}
			}
		}
		
		//Debugging for() loop 
		for (int i = 0; i < 9; i++) {
			System.out.println(i + ", " + defensePositions[i]);
		}

		autoSequence = DSIO.autoSequence;

		moves = autoSequence.split(",");

		if (autoSequence.charAt(0) != 'N') {
			for (int i = 0; i < moves.length; i++) {
				switch (moves[i].charAt(0)) {
					case 'C':
						switch (moves[i].charAt(1)) {
							case '0':
								//Cross Low Bar
								System.out.println("First: cross the low bar at position 0.");
								break;

							case '1':
								//Cross Portcullis
								System.out.println("First: cross the portcullis at position " + defensePositions[1] + ".");
								break;

							case '2':
								//Cross Chival de Frise
								System.out.println("First: cross the CDF at position " + defensePositions[2] + ".");
								break;

							case '3':
								//Cross Moat
								System.out.println("First: cross the moat at position " + defensePositions[3] + ".");
								break;

							case '4':
								//Cross Ramparts
								System.out.println("First: cross the ramparts at position " + defensePositions[4] + ".");
								break;

							case '5':
								//Cross Drawbridge
								System.out.println("First: cross the drawbridge at position " + defensePositions[5] + ".");
								break;

							case '6':
								//Cross Sallyport
								System.out.println("First: cross the sallyport at position " + defensePositions[6] + ".");
								break;

							case '7':
								//Cross Rock Wall
								System.out.println("First: cross the rock wall at position " + defensePositions[7] + ".");
								break;

							case '8':
								//Cross Rough Terrain
								System.out.println("First: cross the rough terrain at position " + defensePositions[8] + ".");
								break;
						}
						break;

					case 'S':
						switch (moves[i].charAt(1)) {
							case 'H':
								//Shoot high goal at position...
								switch (moves[i].charAt(2)) {
									case 'L':
										//Shoot high goal from left
										System.out.println("Second: shoot high left");
										break;
									case 'C':
										//Shoot high goal from center
										System.out.println("Second: shoot high center");
										break;
									case 'R':
										//Shoot high goal from right
										System.out.println("Second: shoot high right");
										break;
								}//End switch 2
								break; //Break H
							case 'L':
								//Shoot low goal at position...
								switch (moves[i].charAt(2)) {
									case 'L':
										//Shoot low goal from left
										System.out.println("Second: shoot low left");
										break;
									case 'R':
										//Shoot low goal from right
										System.out.println("Second: shoot low right");
										break;
								}//End switch 2
								break;//Break L
						}//End switch 1
						break; //Break S

					case 'T':
						//Drive to teleop starting position
						System.out.println((i + 1) + ": drive to teleop position");
						break;//Break T

					case 'N':
						//Stay in place
						System.out.println((i + 1) + ": do nothing");
						break;
				}//End switch 0
			}//End for
		}
		else {
			//Do literally nothing for autonomous; you must cross the defenses in order to shoot.
			System.out.println("Do absolutly nothing in autonomous.");
		}
	}
}
