package by.java.enterprise.micrometer.service;

import by.java.enterprise.micrometer.model.User;
import by.java.enterprise.micrometer.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final MeterRegistry registry;

    public UserService(UserRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.registry = registry;

        Gauge.builder("user.active.count", repository, UserRepository::count)
                .description("Total users in DB")
                .register(registry);
    }

    @Transactional
    public User register(String username, String displayName) {
        return registry.timer("user.registration.time", "operation", "register")
                .record(() -> {
                    if (repository.existsByUsername(username)) {
                        registry.counter("user.registrations", "status", "duplicate").increment();
                        throw new IllegalStateException("Username already exists: " + username);
                    }
                    User saved = repository.save(new User(username, displayName));
                    registry.counter("user.registrations", "status", "success").increment();

                    registry.counter("user.debug.internal.calls").increment();

                    return saved;
                });
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        Optional<User> result = repository.findById(id);
        registry.counter("user.lookups", "result", result.isPresent() ? "found" : "not_found").increment();
        return result;
    }

    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return repository.findAll();
    }
}
