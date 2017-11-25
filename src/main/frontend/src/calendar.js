import 'fullcalendar';
import 'fullcalendar/dist/locale-all';
import 'fullcalendar/dist/fullcalendar.css';
import moment from 'moment';

const FORMAT = 'YYYY-MM-DD[T]HH:mm';

export default ({container, events, locale, onSelect}) => {

	container.fullCalendar({
		defaultView: 'agendaWeek',
		events,
		locale,
		firstDay: 1,
		defaultDate: moment().add(8, 'days'),
		validRange: {
			start: moment().add(6, 'days')
		},
		minTime: '08:00:00',
		maxTime: '20:00:00',
		hiddenDays: [0],
		allDaySlot: false,
		selectable: true,
		selectHelper: true,
		selectOverlap: false,
		selectLongPressDelay: 500,
		selectMinDistance: 5,
		height: 'auto',
		unselectCancel: '#unselectCancel',
		selectAllow: ({start, end}) => start.isSame(end, 'day'),
		select: (start, end) => {
			onSelect(start.format(FORMAT), end.format(FORMAT));
		},
		dayClick: date => {
			onSelect(date.format(FORMAT), date.add({hours: 1}).format(FORMAT));
		}
	});

};