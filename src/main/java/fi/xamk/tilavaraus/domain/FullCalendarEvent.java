package fi.xamk.tilavaraus.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FullCalendarEvent {

	private String id;
	private String title;
	private String url;
	private LocalDateTime start;
	private LocalDateTime end;

}
