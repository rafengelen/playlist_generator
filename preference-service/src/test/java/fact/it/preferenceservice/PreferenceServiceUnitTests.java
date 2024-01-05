package fact.it.preferenceservice;

import fact.it.preferenceservice.dto.PreferenceRequest;
import fact.it.preferenceservice.dto.PreferenceResponse;
import fact.it.preferenceservice.model.Preference;
import fact.it.preferenceservice.repository.PreferenceRepository;
import fact.it.preferenceservice.service.PreferenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class PreferenceServiceUnitTests {
    @InjectMocks
    private PreferenceService preferenceService;

    @Mock
    private PreferenceRepository preferenceRepository;

    @Test
    public void testCreatePreference(){
        //ARRANGE
        String name = "name";
        String user = "user";
        PreferenceRequest preferenceRequest = new PreferenceRequest(name, user);

        //ACT
        Preference preference = preferenceService.createPreference(preferenceRequest);

        //ASSERT
        assertEquals(name, preference.getName());
        assertEquals(user, preference.getUserId());
        assertNotNull(preference.getCode());
    }

    @Test
    public void testGetPreferencesByUserId(){
        //ARRANGE
        String user = "user";
        Preference preference1 = new Preference("id1", "code1", "name1", user);
        Preference preference2 = new Preference("id2", "code2","name2", user);

        List<Preference> preferences = Arrays.asList(preference1, preference2);

        when(preferenceRepository.findByUserId(user)).thenReturn(preferences);

        //ACT
        List<PreferenceResponse> response = preferenceService.getPreferencesByUserId(user);

        //ASSERT
        assertEquals(2, response.size());
        assertEquals(preference1.getCode(), response.get(0).getCode());
        assertEquals(preference1.getName(), response.get(0).getName());
        assertEquals(preference2.getCode(), response.get(1).getCode());
        assertEquals(preference2.getName(), response.get(1).getName());
        //Alle userId's moeten gelijk zijn, omdat assertEquals geen 4 strings kan argumenten kan aannemen assert ik 2 keer, als beide asserts positief zijn doorlopen, dat betekent dat alle userId's hetzelfde zijn
        assertEquals(preference1.getUserId(), response.get(0).getUserId(),response.get(1).getUserId());
        assertEquals(preference2.getUserId(), response.get(1).getUserId());
    }
    @Test
    public void testUpdatePreference(){
        //ARRANGE
        String code = "code";
        String name = "name";
        String user = "user";
        PreferenceRequest preferenceRequest = new PreferenceRequest(name, user);
        Preference preference = new Preference("id", code,name, user);
        when(preferenceRepository.findByCode(code)).thenReturn(preference);

        //ACT
        PreferenceResponse response = preferenceService.updatePreference(preferenceRequest, code);

        //ASSERT
        assertEquals(response.getCode(),code);
        assertEquals(response.getName(),name);
        assertEquals(response.getUserId(), user);
    }


}


