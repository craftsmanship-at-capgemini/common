package testing;

import static org.fest.assertions.api.Assertions.assertThat;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.fest.assertions.core.Condition;

/**
 * Additional conditions for fest assertions.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class Conditions {
    
    public static class EqualsIgnoringFields extends Condition<Object> {
        
        private final Object expected;
        private final String[] fields;
        
        private EqualsIgnoringFields(Object expected, String... fields) {
            super("Equals to " + expected + " but ignoring " + fields + " field");
            this.expected = expected;
            this.fields = fields;
        }
        
        @Override
        public boolean matches(Object value) {
            try {
                assertThat(value).isLenientEqualsToByIgnoringFields(expected, fields);
            } catch (AssertionError e) {
                return false;
            }
            return true;
        }
    }
    
    public static class EqualsFacesMessage extends Condition<FacesMessage> {
        
        private final String expectedSummary;
        private final String expectedDetail;
        private final FacesMessage.Severity expectedSeverity;
        
        public EqualsFacesMessage(String expectedSummary, String expectedDetail, Severity expectedSeverity) {
            super("Equals to FacesMessage " +
                    "\nseverity: " + (expectedSeverity == null ? "<any severity>" : expectedSeverity) +
                    "\nsummary: " + (expectedSummary == null || expectedSummary.isEmpty() ? "<empty summary>" : expectedSummary) +
                    (expectedDetail == null ? "" : "\ndetails: " + (expectedDetail.isEmpty() ? "<empty detail>" : expectedDetail)));
            this.expectedSummary = expectedSummary;
            this.expectedDetail = expectedDetail;
            this.expectedSeverity = expectedSeverity;
        }
        
        @Override
        public boolean matches(FacesMessage value) {
            try {
                assertThat(value.getSummary()).isEqualTo(expectedSummary);
                if (expectedSeverity != null) {
                    assertThat(value.getSeverity()).isEqualTo(expectedSeverity);
                }
                if (expectedDetail != null) {
                    assertThat(value.getDetail()).isEqualTo(expectedDetail);
                }
            } catch (AssertionError e) {
                return false;
            }
            return true;
        }
    }
    
    public static EqualsIgnoringFields equalIgnoringVersion(Object expected) {
        return new EqualsIgnoringFields(expected, "version");
    }
    
    public static EqualsIgnoringFields equalIgnoringIdAndVersion(Object expected) {
        return new EqualsIgnoringFields(expected, "id", "version");
    }
    
    public static EqualsFacesMessage equalFacesMessage(String expectedSummary, String expectedDetail, Severity expectedSeverity) {
        return new EqualsFacesMessage(expectedSummary, expectedDetail, expectedSeverity);
    }
    
    public static EqualsFacesMessage equalFacesMessage(String expectedSummary, Severity expectedSeverity) {
        return new EqualsFacesMessage(expectedSummary, null, expectedSeverity);
    }
    
    public static EqualsFacesMessage equalFacesMessage(String expectedSummary) {
        return new EqualsFacesMessage(expectedSummary, null, null);
    }
}
