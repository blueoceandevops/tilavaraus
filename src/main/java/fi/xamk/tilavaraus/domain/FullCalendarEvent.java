package fi.xamk.tilavaraus.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FullCalendarEvent {
	private String id;
	private String title;
	private String url;
	private LocalDateTime start;
	private LocalDateTime end;
}
