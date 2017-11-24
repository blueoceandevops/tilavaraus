package fi.xamk.tilavaraus.domain;

import java.time.LocalDateTime;

public class FullCalendarEvent {

	private String id;
	private String title;
	private LocalDateTime start;
	private LocalDateTime end;

	public FullCalendarEvent(String id, String title, LocalDateTime start, LocalDateTime end) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
