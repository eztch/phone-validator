package com.example.phoneapi;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PhoneController {

    @PostMapping("/validate")
    public Map<String, Object> validatePhone(@RequestBody Map<String, String> input) {
        Map<String, Object> response = new HashMap<>();
        String number = input.get("phoneNumber");
        String region = input.get("region");

        PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        try {
            PhoneNumber phoneNumber = util.parse(number, region.toUpperCase());
            response.put("valid", util.isValidNumber(phoneNumber));
            response.put("e164", util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
            response.put("national", util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
            response.put("international", util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            response.put("regionCode", util.getRegionCodeForNumber(phoneNumber));
            response.put("type", util.getNumberType(phoneNumber).toString());
        } catch (NumberParseException e) {
            response.put("valid", false);
            response.put("error", e.getMessage());
        }

        return response;
    }
}
