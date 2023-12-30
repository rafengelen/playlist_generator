package fact.it.preferenceservice.controller;

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
            (@RequestBody PreferenceRequest preferenceRequest) {
        preferenceService.createPreference(preferenceRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PreferenceResponse> getPreferencesByUserId
            (@RequestParam String userId) {
        return preferenceService.getPreferencesByUserId(userId);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PreferenceResponse updatePreference
            (@RequestBody PreferenceRequest preferenceRequest, @RequestParam String code){
        return preferenceService.updatePreference(preferenceRequest, code);
    }

}

