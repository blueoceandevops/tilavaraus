package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.FullCalendarEvent;
import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import fi.xamk.tilavaraus.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class FooController {

    private final List<Room> rooms;
    private final ReservationRepository reservationRepository;

    @Autowired
    public FooController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        Room r1 = new Room();
        r1.setId(1L);
        r1.setName("Mikpoli Sali");
        r1.setCapacity(5);
        r1.setPrice(10000.00);
        Room r2 = new Room();
        r2.setName("Vintti");
        r2.setPrice(999.0);
        r2.setId(2L);
        rooms = Arrays.asList(r1, r2);
    }

    @GetMapping("/{id}/events")
    @ResponseBody
    public Iterable<FullCalendarEvent> getEvents(@PathVariable("id") Long id) {
        List<FullCalendarEvent> events = StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                .map(reservation -> new FullCalendarEvent(reservation.getId().toString(),
                        reservation.getId().toString(),
                        reservation.getStartTime(),
                        reservation.getEndTime())).collect(Collectors.toList());
        return events;
    }

    @PostMapping("/{id}/reserve")
    @ResponseBody
    public String reserveRoom(@PathVariable("id") Long id,
                              @RequestParam("count") Integer count,
                              @RequestParam("startTime")String startTime,
                              @RequestParam("endTime")String endTime,
                              @RequestParam("additionalServices")List<String> additionalServices) {
        Reservation reservation = new Reservation();
        reservation.setPersonCount(count);
        reservation.setStartTime(Instant.parse(startTime+":00.00Z"));
        reservation.setEndTime(Instant.parse(endTime+":00.00Z"));
        reservation.setUser("foo");
        reservation.setAdditionalServices(additionalServices);
        reservation.setRoom(rooms.stream().filter(room -> room.getId().equals(id)).findFirst().get());
        reservationRepository.save(reservation);
        return "Varattu tila " + id + " " + count + " henkilölle!";
    }

    @RequestMapping("/{id}")
    public String roomDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("room", rooms.stream().filter(room -> room.getId().equals(id)).findFirst().get());
        return "detail";
    }

    @RequestMapping("/")
    public String foo(Model model) {

        model.addAttribute("rooms", rooms);
        return "foo";
    }
}
