package cse12pa4student;
public class Main {
	
	public static void main(String[] args) {
		System.out.println(Measure.measurementsToCSV(Measure.measure(new String[] {"A"}, 1, 10)));
		System.out.println(Measure.measurementsToCSV(Measure.measure(new String[] {"B"}, 1, 10)));
		System.out.println(Measure.measurementsToCSV(Measure.measure(new String[] {"C"}, 1, 10)));
		System.out.println(Measure.measurementsToCSV(Measure.measure(new String[] {"D"}, 1, 10)));
		System.out.println(Measure.measurementsToCSV(Measure.measure(new String[] {"E"}, 1, 3)));
		System.out.println(Measure.measurementsToCSV(Measure.measure(new String[] {"F"}, 1, 100)));
	}
}
