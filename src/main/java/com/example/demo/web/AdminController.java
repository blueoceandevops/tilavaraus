package com.example.demo.web;

import com.example.demo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ReservationRepository reservationRepository;

    @Autowired
    public AdminController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin/reservations")
    public String listOrders(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "admin/reservations";
    }
}
