package brave.btc.service.common.auth;

import java.sql.Timestamp;

public interface SmsService {

    /**
     * 인증번호를 전송한다.
     * @param phoneNumber 인증할 전화번호
     * @return 응답 메세지
     */
    String sendAuthNumber(String phoneNumber);

    /**
     * 인증번호를 저장한다.
     * @param phoneNumber 저장할 전화번호
     * @param authNumber 저장할 인증번호
     */
    void saveCertificationNumber(String phoneNumber, String authNumber);

    /**
     * 인증번호를 확인한다.
     * @param phoneNumber 전화번호로 서버애 저장된 인증번호를 불러온다
     * @param authNumber 요청받은 인증번호
     */
    String checkAuthNumber(String authNumber, String phoneNumber);

    /**
     * 인증번호 만료시간을 확인한다.
     * @param timestamp 인증을 요청한 시각
     * @param validationTime 인증 유효 시간
     */
    void checkCertificationTime(Timestamp timestamp, int validationTime);
}
