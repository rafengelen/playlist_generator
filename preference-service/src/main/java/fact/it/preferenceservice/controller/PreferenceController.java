package fact.it.preferenceservice.controller;

import fact.it.preferenceservice.decoder.JwtDecoder;
import fact.it.preferenceservice.dto.PreferenceRequest;
import fact.it.preferenceservice.dto.PreferenceResponse;
import fact.it.preferenceservice.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preference")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createPreference
            (@RequestParam String name, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String userId = JwtDecoder.getUserId(jwtToken);

        PreferenceRequest preferenceRequest = new PreferenceRequest(name, userId);

        //preferenceRequest.setUserId(userId);

        preferenceService.createPreference(preferenceRequest);


    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PreferenceResponse> getPreferences
            (@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String userId = JwtDecoder.getUserId(jwtToken);
        return preferenceService.getPreferencesByUserId(userId);
    }
//Enkel gebruik door andere service, gaat niet door gateway
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<PreferenceResponse> getPreferencesByUserId
            (@RequestParam String userId) {;

        return preferenceService.getPreferencesByUserId(userId);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PreferenceResponse updatePreference
            (@RequestBody PreferenceRequest preferenceRequest,
             @RequestParam String code,
             @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String userId = JwtDecoder.getUserId(jwtToken);
        if (preferenceRequest.getUserId().equals(userId)){
            return preferenceService.updatePreference(preferenceRequest, code);
        }

        return null;
    }

}

