package uz.md.leetcode.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.ActivationCode;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.repository.ActivationCodeRepository;
import uz.md.leetcode.utils.BaseUtils;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Me: muhammadqodir
 * Project: CodingCompiler/IntelliJ IDEA
 * Date:Sat 17/09/22 22:56
 */

@Service
public class ActivationCodeService {

    private final BaseUtils baseUtils;
    private final ActivationCodeRepository repository;

    @Value("${activation.link.expiry.in.minutes}")
    private long activationLinkValidTillInMinutes;

    public ActivationCodeService(BaseUtils baseUtils,
                                 ActivationCodeRepository repository) {
        this.baseUtils = baseUtils;
        this.repository = repository;
    }

    public ActivationCode generateCode(@NonNull User user) {
        String codeForEncoding = "" + UUID.randomUUID() + System.currentTimeMillis();
        String encodedActivationCode = baseUtils.encode(codeForEncoding);
        ActivationCode activationCode = ActivationCode.builder()
                .activationCode(encodedActivationCode)
                .userId(user.getId())
                .validTill(LocalDateTime.now().plusMinutes(activationLinkValidTillInMinutes))
                .build();
        return repository.save(activationCode);
    }

    public ActivationCode findByActivationCode(@NonNull String activationCode) {
        return repository.findByActivationCode(activationCode).orElseThrow(() ->
        {
            throw new RestException("ACTIVATION LINK NOT FOUND", HttpStatus.NOT_FOUND);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
