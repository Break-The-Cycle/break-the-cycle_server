package brave.btc.service.app.auth;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

import brave.btc.domain.temporary.SmsCertification;
import brave.btc.exception.sms.SmsCertificationNumberExpiredException;
import brave.btc.exception.sms.SmsCertificationNumberNotSameException;
import brave.btc.exception.sms.SmsSendFailedException;
import brave.btc.repository.temporary.SmsCertificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    private final SmsCertificationRepository smsCertificationRepository;

    @Value("${sms.publicKey}")
    private String publicKey;

    @Value("${sms.secretKey}")
    private String secretKey;

    @Value("${sms.domain}")
    private String smsDomain;

    @Override
    public String sendAuthNumber(String phoneNumber) {

        log.info("[register] 인증번호 요청");
        String authNumber = RandomStringUtils.randomNumeric(6);

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(publicKey, secretKey, smsDomain);

        Message message = new Message();
        message.setFrom("01099236825");
        message.setTo(phoneNumber);
        message.setText("[로즈 데이즈] 인증번호는 " + authNumber + "입니다.");

        //인증 번호 저장하고 check에서 비교할 때 사용

        try {
            messageService.send(message);
            saveCertificationNumber(phoneNumber, authNumber);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SmsSendFailedException(e.getMessage());
        }

        return "인증번호 전송이 완료되었습니다.";
    }

    @Override
    public void saveCertificationNumber(String phoneNumber, String authNumber) {

        log.info("[register] 인증번호 저장");
        SmsCertification smsCertification = smsCertificationRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
        if (smsCertification != null) {
            smsCertificationRepository.updateCertificationNum(authNumber, getNowTimeStamp(), phoneNumber);
        } else {
            SmsCertification requestDto = SmsCertification.builder()
                    .phoneNumber(phoneNumber)
                    .certificationNumber(authNumber)
                    .build();
            smsCertificationRepository.save(requestDto);
        }

    }

    @Override
    public String checkAuthNumber(String authNumber, String phoneNumber) {

        log.info("[register] 인증번호 확인 요청");
        SmsCertification smsCertification = smsCertificationRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
        if (smsCertification != null) {
            checkCertificationTime(smsCertification.getCreated(), 3);
            if (smsCertification.getCertificationNumber().equals(authNumber)) {
                log.info("smsCertification.getCertificationNumber() = " + smsCertification.getCertificationNumber());
                log.info("authNumber = " + authNumber);
                return "인증번호 인증이 완료되었습니다.";
            }
        }

        throw new SmsCertificationNumberNotSameException("인증번호가 일치하지 않습니다.");

    }

    @Override
    public void checkCertificationTime(Timestamp timestamp, int validationTime) {

        LocalDateTime localDateTime = timestamp.toLocalDateTime()
                .plusMinutes(validationTime);
        LocalDateTime now = LocalDateTime.now();
        if (localDateTime.isBefore(now)) {
            throw new SmsCertificationNumberExpiredException("인증번호가 만료되었습니다.");
        }
    }

    public Timestamp getNowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
