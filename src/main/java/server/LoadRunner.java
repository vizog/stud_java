package server;

import repository.OfferingRepository;
import server.request.CheckPassedCourseRequest;
import server.request.GPARequest;
import server.request.IRequest;
import server.request.LockTermOfferingsRequest;
import server.request.TakeCourseRequest;
import domain.Offering;


@SuppressWarnings("unused")
public class LoadRunner {
	
	public static void main(String[] args) throws InterruptedException {
		passCourse();
//		GPA();
//		takeCourse();
//		lockOfferings();
//		test();
	}

	private static void lockOfferings() throws InterruptedException {
		IRequest req = new LockTermOfferingsRequest("req-1" ,"term-1");
		for(int i = 0; i < 1000; i++) {
			req.start();
			Thread.sleep(2000);
		}

	}

	private static void test() {
		System.out.println("start -> " + System.currentTimeMillis());
		for(int i = 0; i < 1000; i++) {
		    Offering offering = OfferingRepository.getInstance().findById("course-5-3");
		}
		System.out.println("end -> " +  System.currentTimeMillis());
	}

	private static void takeCourse() throws InterruptedException {
//		System.out.println("start -> " + new Date());
//		System.out.println("start = " + System.currentTimeMillis());
		for(int i = 0; i < 1000; i++) {
			IRequest req = new TakeCourseRequest("req-" + (i+1),"student-" + (i+1));
			req.start();
			Thread.sleep(100);
		}
	}
	
	private static void passCourse() throws InterruptedException {
//		System.out.println("start -> " + new Date());
//		System.out.println("start = " + System.currentTimeMillis());
		for(int i = 0; i < 1000; i++) {
			IRequest req = new CheckPassedCourseRequest("req-" + (i+1),"student-" + (i+1));
			req.start();
			Thread.sleep(100);
		}
	}

	private static void GPA() throws InterruptedException {
		System.out.println("start -> " + System.currentTimeMillis());
		for(int i = 0; i < 1000; i++) {
			IRequest req = new GPARequest("req-" + (i+1),"student-" + (i+1));
			req.start();
			Thread.sleep(100);
		}
	}
	
}
