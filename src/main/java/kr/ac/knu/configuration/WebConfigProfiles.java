package kr.ac.knu.configuration;

import kr.ac.knu.domain.KnuUser;
import kr.ac.knu.domain.facebook.FacebookUser;
import kr.ac.knu.restclient.FacebookClient;
import kr.ac.knu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Configuration
@Slf4j
public class WebConfigProfiles {
    @Autowired private FacebookClient facebookClient;
    @Autowired private UserService userService;

    @Bean
    @Profile({"local"})
    public UserOperator facebookUserOperatorLocal() {
        log.info("Created UserOperator for LOCAL");
        return new UserOperator() {
            @Override
            public KnuUser getKnuUserFromAccessToken(String accessToken) {
                log.info("LOCAL!!");
                FacebookUser facebookUser = createFacebookUser(accessToken);
                if (facebookUser == null) {
                    facebookUser = new FacebookUser();
                    facebookUser.setUserId(accessToken);
                    facebookUser.setName(accessToken);
                    facebookUser.setGender("남성");
                    facebookUser.setEmail(accessToken + "@naver.com");
                    facebookUser.setPicture(null);
                }

                return userService.findAndCreateKnuUser(facebookUser);
            }
        };
    }

    @Bean
    @Profile({"!local"})
    public UserOperator facebookUserOperatorDev() {
        log.info("Created UserOperator for DEV");
        return new UserOperator() {
            @Override
            public KnuUser getKnuUserFromAccessToken(String accessToken) {
                // facebook 로그인 토큰 에러 로그
                log.info("INFO : Authenticating login token: EAACE");
                log.info("DEV!! : {}", accessToken);
                FacebookUser facebookUser = createFacebookUser(accessToken);
                if (facebookUser == null) {
                    return null;
                }

                return userService.findAndCreateKnuUser(facebookUser);
            }
        };
    }

    private FacebookUser createFacebookUser(String accessToken) {
        FacebookUser facebookUser = null;
        try {
            facebookUser = facebookClient.callFacebookProfile(accessToken);
        } catch (Exception e) {
            // facebook 로그인 토큰 에러 로그
            log.error("Facebook Login fail [{}] : {}", accessToken, e.toString());
            log.info("INFO : Authentication fail: token is invalid: EAACE");
        }
        return facebookUser;
    }
}
