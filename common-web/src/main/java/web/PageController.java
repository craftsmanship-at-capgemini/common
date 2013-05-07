package web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

/**
 * Stereotype of web page controller class.
 * 
 * <pre>
 * &#064;PageController(view = &quot;/change-password&quot;)
 * public class ChangePasswordPageController {
 *     
 *     public static String outcome() {
 *         return new Outcome(ChangePasswordPageController.class).build();
 *     }
 *     
 *     &#064;EJB TestedPersonInformationsRemote testedPersonInformationsRemote;
 *     &#064;Inject ChangePasswordPageControllerI18n i18n;
 *     &#064;Inject TestedPersonModel testedPersonModel;
 *     
 *     private String password;
 *     private @Password String newPassword;
 *     private String repeatPassword;
 *     private String message;
 *     
 *     public String changePassword() {
 *         try {
 *             if (newPassword.equals(repeatPassword)) {
 *                 testedPersonInformationsRemote.changePassword(
 *                         testedPersonModel.getTestedPersonInformations().getCode(),
 *                         password, newPassword);
 *                 
 *                 return TestSummaryPageController.outcome();
 *             } else {
 *                 message = i18n.repeatedPasswordNotMacht();
 *                 return Outcome.stayOnPage();
 *             }
 *         } catch (TestedPersonAuthenticationException e) {
 *             message = i18n.authenticationFailedMessage();
 *             return Outcome.stayOnPage();
 *         }
 *     }
 *     
 *     // getters and setters
 * }
 * 
 * </pre>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@RequestScoped
@Named
@Stereotype
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface PageController {
    public String view();
}
