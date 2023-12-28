package fact.it.preferenceservice.repository;

import fact.it.preferenceservice.model.Preference;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PreferenceRepository extends MongoRepository<Preference, String> {
    List<Preference> findByUserId(String userId);

    Preference findByCode(String code);
}
