package com.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rent")
    public void rentBook(@RequestBody RentalRequest request) {
        rentalService.rentBook(request.getBookId(), request.getEmployeeId());
    }

    @PostMapping("/return")
    public void returnBook(@RequestBody List<Long> bookIds) {
        rentalService.returnBook(bookIds);
    }

    @GetMapping("/books")
    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rentals/{employeeId}")
    public List<RentalResponse> getRentalsByEmployee(@PathVariable Long employeeId) {
        List<Rental> rentals = rentalRepository.findByEmployeeId(employeeId);
        return rentals.stream()
                .map(rental -> new RentalResponse(rental.getId(), rental.getBookId(), rental.getEmployeeId(), rental.getRentalDate(), rental.getReturnDate()))
                .collect(Collectors.toList());
    }

}
