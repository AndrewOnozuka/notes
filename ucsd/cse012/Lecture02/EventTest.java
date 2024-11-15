import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.time.LocalDateTime;

public class EventTest {

	@Test
	public void testEventConstructor() {
		LocalDateTime s1 = LocalDateTime.of(2020, 10, 1, 9, 45);
		LocalDateTime s2 = LocalDateTime.of(2020, 10, 1, 10, 45);
		
        // Lecture Quiz 1 - Q1
		LocalDateTime time1 = LocalDateTime.of(2020, 9, 27, 8, 0);
		LocalDateTime time2 = LocalDateTime.of(2020, 9, 27, 9, 30);
		LocalDateTime time3 = LocalDateTime.of(2020, 9, 27, 10, 0);
		LocalDateTime time4 = LocalDateTime.of(2020, 9, 27, 8, 30);
		LocalDateTime time5 = LocalDateTime.of(2020, 9, 27, 9, 0);
		LocalDateTime time6 = LocalDateTime.of(2020, 9, 27, 7, 0);
		LocalDateTime time7 = LocalDateTime.of(2020, 9, 27, 7, 30);
		Event event1 = new Event(time6, time5, "WLH2005");
		Event event2 = new Event(time4, time3, "YORK2622");
		Event event3 = new Event(time1, time2, "PCYNH109");
		Event event4 = new Event(time7, time4, "EBU3B3206");
		
		Event e1 = new Event(s1, s2, "WLH 2005");
		
		assertEquals(e1.start, s1);
		assertEquals(e1.end, s2);
		assertEquals(e1.location, "WLH 2005");
	}

	@Test
	public void testConflict() {
		LocalDateTime s1 = LocalDateTime.of(2020, 10, 1, 9, 45);
		LocalDateTime s2 = LocalDateTime.of(2020, 10, 1, 10, 45);
		

		
		LocalDateTime longAgo = LocalDateTime.of(2019, 10, 1, 9, 45);
		LocalDateTime inTheFuture = LocalDateTime.of(2021, 10, 1, 10, 45);
				
		Event e1 = new Event(s1, s2, "WLH 2005");
		Event e2 = new Event(longAgo, inTheFuture, "WLH 2005");
		
		assertEquals(e1.conflict(e2), false);
		
		//event completely outside another event
		//event completely contained within an event
		//events in different places
	}
}