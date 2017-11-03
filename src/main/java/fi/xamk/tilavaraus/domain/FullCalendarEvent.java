package fi.xamk.tilavaraus.domain;

import java.time.Instant;

public class FullCalendarEvent {

	private String id;
	private String title;
	private Instant start;
	private Instant end;

	public FullCalendarEvent(String id, String title, Instant start, Instant end) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instant getStart() {
		return start;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public Instant getEnd() {
		return end;
	}

	public void setEnd(Instant end) {
		this.end = end;
	}

}
