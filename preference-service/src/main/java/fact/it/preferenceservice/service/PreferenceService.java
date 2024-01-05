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

    public Preference createPreference(PreferenceRequest preferenceRequest){
        Preference preference = Preference.builder()
                .name(preferenceRequest.getName())
                .userId(preferenceRequest.getUserId())
                .code((UUID.randomUUID().toString()))
                .build();

        preferenceRepository.save(preference);
        return preference;
    }
    //Users kunnen enkel hun eigen preferences opvragen
    public List<PreferenceResponse> getPreferencesByUserId(String userId) {
        List<Preference> preferences = preferenceRepository.findByUserId(userId);

        return preferences.stream().map(this::mapToPreferenceResponse).toList();
    }

    public PreferenceResponse updatePreference(PreferenceRequest preferenceRequest, String code) {
        Preference preference = preferenceRepository.findByCode(code);

        preference.setName(preferenceRequest.getName());

        preferenceRepository.save(preference);

        return this.mapToPreferenceResponse(preference);
    }

    private PreferenceResponse mapToPreferenceResponse(Preference preference) {
        return PreferenceResponse.builder()
                .name(preference.getName())
                .userId(preference.getUserId())
                .code(preference.getCode())
                .build();
    }



}
