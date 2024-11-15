package cse12pa4student;

import java.util.ArrayList;
import java.util.List;
import cse12pa4mysteries.Mysteries;
import static cse12pa4mysteries.Mysteries.*;

public class Measure {

	public static List<Measurement> measure(String[] toRun, int startN, int stopN) {

		List<Measurement> data = new ArrayList<Measurement>();
		
		for (int i = 0; i < toRun.length; i++) {
			if (toRun[i].equals("A")) {
				for (int j = startN; j <= stopN; j++) {
					long timer = System.nanoTime();
					mysteryA(j);
					Measurement runtime = new Measurement("A", j, System.nanoTime()-timer);
					data.add(runtime);
				}
			}
			else if (toRun[i].equals("B")) {
				for (int j = startN; j <= stopN; j++) {
					long timer = System.nanoTime();
					mysteryB(j);
					Measurement runtime = new Measurement("B", j, System.nanoTime()-timer);
					data.add(runtime);
				}
			}
			else if(toRun[i].equals("C")) {
				for (int j = startN; j <= stopN; j++) {
					long timer = System.nanoTime();
					mysteryC(j);
					Measurement runtime = new Measurement("C", j, System.nanoTime()-timer);
					data.add(runtime);
				}			
			}
			else if (toRun[i].equals("D")) {
				for (int j = startN; j <= stopN; j++) {
					long timer = System.nanoTime();
					mysteryD(j);
					Measurement runtime = new Measurement("D", j, System.nanoTime()-timer);
					data.add(runtime);
				}
			}
			else if (toRun[i].equals("E")) {
				for (int j = startN; j <= stopN; j++)
				{
					long timer = System.nanoTime();
					mysteryE(j);
					Measurement runtime = new Measurement("E", j, System.nanoTime()-timer);
					data.add(runtime);
				}
			}
			else if (toRun[i].equals("F")) {
				for (int j = startN; j <= stopN; j++) {
					long timer = System.nanoTime();
					mysteryF(j);
					Measurement runtime = new Measurement("F", j, System.nanoTime()-timer);
					data.add(runtime);
				}
			}
		}
		return data;
	}

	public static String measurementsToCSV(List<Measurement> toConvert) {
		String csv = "name, n, nanoseconds\n";
		for (Measurement m : toConvert) {
			csv += m.name + "," + m.valueOfN + "," + m.nanosecondsToRun + "\n";
		}
		return csv;
	}
	
	/* Add any helper methods you find useful */
		
}
