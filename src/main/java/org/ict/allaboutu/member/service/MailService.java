package org.ict.allaboutu.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MailRepository;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String fromAddress;
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final JavaMailSender mailSender;
    private final MailRepository mailRepository;
    private final MemberRepository memberRepository;

    public MailDto generateTokenAndSendMail(String userId, String userEmail) {
        String token = generateRandomString(6);

        String subject = "AllAboutU 비밀번호 찾기 인증 메일입니다.";
        String msgBody = "인증번호는 " + token + " 입니다.";
        sendEmail(userEmail, fromAddress, subject, msgBody);

        MailDto mailDto = MailDto.builder()
                .mailNum(mailRepository.findMaxMailNum().orElse(0L) + 1L)
                .userEmail(userEmail)
                .token(token)
                .issuedAt(LocalDateTime.now())
                .expired(0L)
                .build();

        return mailRepository.save(mailDto);
    }

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder(length);
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(CHAR_SET.length());
            char randomChar = CHAR_SET.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public void sendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(fromAddress);
        smm.setTo(toAddress);
        smm.setSubject(subject);
        smm.setText(msgBody);

        mailSender.send(smm);
    }

    @Transactional
    public boolean verifyCode(String userId, String userEmail, String verificationCode) throws Exception {
        Member member = memberRepository.findByUserId(userId);

        // 회원 정보가 없거나, 회원 정보가 있더라도 이메일이 일치하지 않으면 false 반환
        if (member == null || !userEmail.equals(member.getUserEmail())) {
            return false;
        }

        List<MailDto> mailDtoList = mailRepository.findByUserEmail(userEmail);
        for (int i = 0; i < mailDtoList.size(); i++) {
            System.out.println("mailDto[" + i + "] : " + mailDtoList.get(i).toString());
        }
        MailDto mailDto = mailDtoList.stream().reduce((a, b) -> a.getIssuedAt().isAfter(b.getIssuedAt()) ? a : b).orElse(null);

        if (mailDto == null || mailDto.getExpired() == 1L || !verificationCode.equals(mailDto.getToken())) {
            // 인증번호가 일치하지 않거나, 인증번호가 만료되었으면 false 반환
            return false;
        } else {
            // 인증번호가 일치하고, 인증번호가 만료되지 않았으면 인증번호 만료 처리 후 true 반환
            mailDto.setExpired(1L);
            mailRepository.save(mailDto);
            return true;
        }
    }
}
