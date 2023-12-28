package fact.it.preferenceservice.service;

import fact.it.preferenceservice.dto.PreferenceRequest;
import fact.it.preferenceservice.dto.PreferenceResponse;
import fact.it.preferenceservice.model.Preference;
import fact.it.preferenceservice.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    public void createPreference(PreferenceRequest preferenceRequest){
        Preference preference = Preference.builder()
                .name(preferenceRequest.getName())
                .userId(preferenceRequest.getUserId())
                .code((UUID.randomUUID().toString()))
                .build();

        preferenceRepository.save(preference);
    }
    //Users kunnen enkel hun eigen preferences opvragen
    public List<PreferenceResponse> getPreferencesByUserId(String userId) {
        List<Preference> preferences = preferenceRepository.findByUserId(userId);

        return preferences.stream().map(this::mapToPreferenceResponse).toList();
    }


    private PreferenceResponse mapToPreferenceResponse(Preference preference) {
        return PreferenceResponse.builder()
                .id(preference.getId())
                .name(preference.getName())
                .userId(preference.getUserId())
                .code(preference.getCode())
                .build();
    }



}
