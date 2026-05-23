package com.example.Practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Practice.service.EmailService;
import com.example.Practice.util.OtpUtil;
import com.example.Practice.store.OtpStore;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(
            @RequestParam String email) {

        String otp = OtpUtil.generateOtp();
        OtpStore.otpMap.put(email, otp);

        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok(
                "OTP sent successfully: " + otp
        );
    }
    @PostMapping("/verify")
public ResponseEntity<String> verifyOtp(

        @RequestParam String email,

        @RequestParam String otp) {

    String storedOtp =
            OtpStore.otpMap.get(email);

    if (storedOtp == null) {

        return ResponseEntity.badRequest()
                .body("No OTP found");
    }

    if (storedOtp.equals(otp)) {

        OtpStore.otpMap.remove(email);
        OtpStore.verifiedEmails.put(email, true);

        return ResponseEntity.ok(
                "OTP verified successfully"
        );
    }

    return ResponseEntity.badRequest()
            .body("Invalid OTP");
}
}