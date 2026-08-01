package edu.jhu.mrm.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.jhu.mrm.model.MaintRequest;
import edu.jhu.mrm.repository.MaintRequestRepository;

/**
 * File: MetricsService.java
 * Service layer for computing the metrics for the requests
 * 
 * @author Cory Drangel and Matthew Kim
 */
public class MetricsService {
	
	private static final long MILLIS_IN_WEEK = 7 * 24 * 60 * 60 * 1000;
	
	@Autowired
	private MaintRequestRepository requestRepository;
	
	/**
	 * The number of requests submitted in the last week.
	 * 
	 * @return requests in the last week.
	 */
	public int getRequestsInLastWeek() {
		List<MaintRequest> requests = requestRepository.findAll();
		
		int requestsLastWeek = 0;
		
		long time = System.currentTimeMillis() - MILLIS_IN_WEEK;
		Timestamp lastWeekTime = new Timestamp(time);
		for(MaintRequest req : requests) {
			
			Timestamp createdTime = Timestamp.valueOf(req.getCreatedDate());
			
			if (createdTime.after(lastWeekTime)) {
				requestsLastWeek++;
			}
		}
		
		return requestsLastWeek;
	}
	
	
	/**
	 * Computes the average completion time of all requests marked as "Complete".
	 * 
	 * @return a string representing the days, hours, and minutes of the average completed request.
	 */
	public String getAvgCompleteTime() {
		List<MaintRequest> requests = requestRepository.findAll();
		
		long completedTime = 0;
		int completedReq = 0;
		
		for (MaintRequest req : requests) {
			if ("Complete".equals(req.getStatus())) {
				long timeDiff = Timestamp.valueOf(req.getCompletedDate()).getTime()
						- Timestamp.valueOf(req.getCreatedDate()).getTime();
				
				completedTime += timeDiff;
				completedReq++;
			}
		}
		
		long avgTimeMillis = completedTime / completedReq;
		Duration duration = Duration.ofMillis(avgTimeMillis);
		
		long days = duration.toDays();
		long hours = duration.toHoursPart();
		long minutes = duration.toMinutesPart();
		
		return days + " days : " + hours + " hours : " + minutes + " minutes";
	}
}