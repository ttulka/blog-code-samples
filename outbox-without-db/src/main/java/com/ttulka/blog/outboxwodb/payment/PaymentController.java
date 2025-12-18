package com.ttulka.blog.outboxwodb.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(PaymentController.ENDPOINT)
@RequiredArgsConstructor
@Slf4j
class PaymentController {

    final static String ENDPOINT = "/payment";

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<?> collectPayment(String reference, BigDecimal amount) {
        var payment = new Payment(reference, amount);
        service.collectPayment(payment);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error(e.getMessage());
    }
}
