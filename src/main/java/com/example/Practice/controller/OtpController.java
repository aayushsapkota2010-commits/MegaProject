package com.example.Practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Practice.service.EmailService;
import com.example.Practice.util.OtpUtil;
import com.example.Practice.store.OtpStore;
import com.example.Practice.dto.OtpRequest;
import com.example.Practice.dto.OtpVerifyRequest;


@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private EmailService emailService;

  @PostMapping("/send")
public ResponseEntity<String> sendOtp(

        @RequestBody OtpRequest request
) {

    String email = request.getEmail();

    String otp = OtpUtil.generateOtp();

    OtpStore.otpMap.put(email, otp);

    emailService.sendOtpEmail(email, otp);

    return ResponseEntity.ok(
            "OTP sent successfully"
    );
}
   @PostMapping("/verify")
public ResponseEntity<String> verifyOtp(

        @RequestBody OtpVerifyRequest request
) {

    String email = request.getEmail();

    String otp = request.getOtp();

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