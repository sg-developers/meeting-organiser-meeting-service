package pl.sg.meetingorganiser.meetingorganiserservice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sg.meetingorganiser.meetingorganiserservice.infrastructure.exception.ResourceNotFoundException;
import pl.sg.meetingorganiser.meetingorganiserservice.infrastructure.security.UserPrincipal;
import pl.sg.meetingorganiser.meetingorganiserservice.service.user.UserEntity;
import pl.sg.meetingorganiser.meetingorganiserservice.service.user.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingOrganiserUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserPrincipal.create(user);
    }

}
