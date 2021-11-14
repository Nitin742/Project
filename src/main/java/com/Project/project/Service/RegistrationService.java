package com.Project.project.Service;


import com.Project.project.config.UserDetailsServiceImpl;
import com.Project.project.dao.userRepo;
import com.Project.project.email.EmailSender;
import com.Project.project.services.Customer;
import com.Project.project.services.Seller;
import com.Project.project.services.User;
import com.Project.project.token.confirmationToken;
import com.Project.project.token.confirmationTokenService;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {


    @Autowired
    userRepo repo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    confirmationTokenService tokenService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    UserDetailsServiceImpl detailsService;

    @Autowired
    EmailValidator emailValidator;


//--------------------------------Seller Registration--------------------------------------
    public String registerSeller(Seller seller) throws IllegalAccessException {
        List<User> users = repo.findAll();

        for (User user : users) {
            if (user.getEmail().equals(seller.getEmail())) {
                throw new IllegalAccessException("Email already present");
            }
        }
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        repo.save(seller);


        String token = UUID.randomUUID().toString();
        confirmationToken confer_Token = new confirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                seller
        );
        tokenService.saveConfirmationToken(confer_Token);

        //ToDo email
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;

    //  tokenService.saveConfirmationToken(confer_Token);
        emailSender.send(
                seller.getEmail(),
                buildEmail(seller.getFirstName(), link));

        return token;
    }
    //--------------------------------Customer Registration--------------------------------------
    public String registerCustomer(Customer customer) throws IllegalAccessException {
        List<User> users = repo.findAll();

        for (User user : users) {
            if (user.getEmail().equals(customer.getEmail())) {
                throw new IllegalAccessException("Email already present");
            }
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        repo.save(customer);


        String token = UUID.randomUUID().toString();
        confirmationToken confer_Token1 = new confirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                customer
        );
        tokenService.saveConfirmationToken(confer_Token1);

        //ToDo email
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;

       tokenService.saveConfirmationToken(confer_Token1);
        emailSender.send(
                customer.getEmail(),
                buildEmail(customer.getFirstName(), link));

        return token;
    }


    @Transactional
    public String confirmToken(String token) {
        confirmationToken confirmation_Token = tokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmation_Token.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt =  confirmation_Token.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        tokenService.setConfirmedAt(token);
        detailsService.enableUser(confirmation_Token.getUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 3 Hour. <p>See you soon </p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


}