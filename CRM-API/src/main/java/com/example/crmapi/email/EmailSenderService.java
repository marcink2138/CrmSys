package com.example.crmapi.email;

import com.example.crmapi.contact.Contact;
import com.example.crmapi.email.DTO.EmailFormDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;
    Logger logger = LoggerFactory.getLogger(EmailSenderService.class);


    @Async
    public void sendBulkEmail(EmailFormDTO emailFormDTO, String... emails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@crmsys.pl");
        simpleMailMessage.setTo(emails);
        simpleMailMessage.setSubject(emailFormDTO.getTopic());
        simpleMailMessage.setText(emailFormDTO.getMessageContent());
        mailSender.send(simpleMailMessage);
        logger.info("Email to " + Arrays.toString(emails) + " has been sent!");
    }

    @Async
    public void sendEmail(EmailFormDTO emailFormDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@crmsys.pl");
        simpleMailMessage.setTo(emailFormDTO.getEmail());
        simpleMailMessage.setSubject(emailFormDTO.getTopic());
        simpleMailMessage.setText(emailFormDTO.getMessageContent());
        mailSender.send(simpleMailMessage);
        logger.info("Email to " + emailFormDTO.getEmail() + " has been sent!");
    }

    @Async
    public void sendForecastEmails(List<Contact> contacts) {
        contacts.forEach(contact -> {
            String address = "'" + contact.getAddressCity() + "," + contact.getAddressStreet() + "," +
                    contact.getAddressHouseNumber() + "," + contact.getAddressCountry() + "'";
            Float power = 8.3F;
            String emailBody = callForecastAPI(address, power);
            EmailFormDTO emailFormDTO = EmailFormDTO.builder()
                    .topic("Prognoza na najbliższy tydzień")
                    .email(contact.getEmailAddress())
                    .messageContent(emailBody)
                    .build();
            sendEmail(emailFormDTO);
        });
    }

    private String callForecastAPI(String address, Float power) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/api/EnergyProductionForecast?address={0}&power={1}";
        String powerAsString = power.toString();
        url = MessageFormat.format(url, address, powerAsString);
        try {
            ResponseEntity<Double[]> response;
            response = restTemplate.getForEntity(url, Double[].class);
            Double[] forecastData = response.getBody();
            Optional<String> emailBody = createForecastEmail();
            if (emailBody.isPresent() && Objects.requireNonNull(forecastData).length == 8) {
                return MessageFormat.format(emailBody.get(), forecastData[0], forecastData[1], forecastData[2],
                        forecastData[3], forecastData[4], forecastData[5], forecastData[6]);
            } else {
                logger.error("Empty forecast data, problem with forecast api");
                return "Chwilowa przerwa w działaniu przewidywań.";
            }
        } catch (HttpServerErrorException e) {
            logger.error("Forecast api exception. Probably incorrect address: " + url);
            return "Błąd na poziomie tłumaczenia miejsca elektrowni. Proszę skontaktować się z konsultantem w celu korekcji błędów!";
        }
    }

    private Optional<String> createForecastEmail() {
        StringBuilder sb = new StringBuilder();
        System.currentTimeMillis();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String today = sdf.format(date);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(today));
            sb.append("Przewidywana produkcja energi na aktualny tydzień \n");
            sb.append(sdf.format(calendar.getTime()));
            sb.append(": {0} kWh\n");
            for (int i = 1; i < 7; i++) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                sb.append(sdf.format(calendar.getTime()));
                sb.append(": {").append(i).append("} kWh\n");
            }
            sb.append("CRMSysTeam");
            return Optional.of(sb.toString());
        } catch (ParseException e) {
            logger.error("Cannot parse calendar. Forecast emails terminated!");
            return Optional.empty();
        }
    }


}
