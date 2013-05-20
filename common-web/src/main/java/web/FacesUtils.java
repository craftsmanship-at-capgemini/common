package web;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.HttpParam.Name;

/**
 * Set of producer methods wrapping static {@link FacesContext} methods.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@ApplicationScoped
public class FacesUtils {
    
    @Produces
    @Messages
    public Collection<FacesMessage> createMessagesForID(InjectionPoint injectionPoint) {
        String forID = injectionPoint.getAnnotated()
                .getAnnotation(Messages.class).forID();
        if (forID == null || forID.isEmpty()) {
            return new FacesMessagesCollection();
        } else {
            return new FacesMessagesCollection(forID);
        }
    }
    
    @Produces
    public ResourceBundle createResourceBundle(InjectionPoint injectionPoint) {
        String name = injectionPoint.getBean().getName() + "Bundle";
        return FacesContext.getCurrentInstance().
                getApplication().getResourceBundle(FacesContext.getCurrentInstance(), name);
    }
    
    @Produces
    @CookieParam("")
    public Cookie createCookie(InjectionPoint injectionPoint) {
        Map<String, Object> cookieMap = FacesContext
                .getCurrentInstance().getExternalContext()
                .getRequestCookieMap();
        String name = injectionPoint.getAnnotated()
                .getAnnotation(CookieParam.class).value();
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }
    
    @Produces
    @CookieParam("")
    public String createCookieValue(InjectionPoint injectionPoint) {
        Map<String, Object> cookieMap = FacesContext
                .getCurrentInstance().getExternalContext()
                .getRequestCookieMap();
        String name = injectionPoint.getAnnotated()
                .getAnnotation(CookieParam.class).value();
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie == null ? null : cookie.getValue();
        } else {
            return null;
        }
    }
    
    @Produces
    @HttpParam(Name.Accept)
    public String createHttpParam(InjectionPoint injectionPoint) {
        ServletRequest request = (ServletRequest) FacesContext
                .getCurrentInstance().getExternalContext()
                .getRequest();
        String name = injectionPoint.getAnnotated()
                .getAnnotation(HttpParam.class).value().toString();
        String value = request.getParameter(name);
        return value;
    }
    
    @Produces
    @ContextPath
    public String createContextPath() {
        String value = FacesContext
                .getCurrentInstance().getExternalContext()
                .getRequestContextPath();
        return value;
    }
    
    @Produces
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext()
                .getRequest();
        return request;
    }
    
    @Produces
    public HttpServletResponse getResponse() {
        HttpServletResponse response = (HttpServletResponse) FacesContext
                .getCurrentInstance().getExternalContext()
                .getResponse();
        return response;
    }
}

class FacesMessagesCollection implements Collection<FacesMessage> {
    
    private final String forId;
    
    public FacesMessagesCollection() {
        this.forId = null;
    }
    
    public FacesMessagesCollection(String forId) {
        this.forId = forId;
    }
    
    @Override
    public boolean add(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(forId, message);
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends FacesMessage> messages) {
        for (FacesMessage message : messages) {
            FacesContext.getCurrentInstance().addMessage(forId, message);
        }
        return true;
    }
    
    @Override
    public void clear() {
    }
    
    @Override
    public boolean contains(Object arg0) {
        return false;
    }
    
    @Override
    public boolean containsAll(Collection<?> arg0) {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public Iterator<FacesMessage> iterator() {
        return Collections.<FacesMessage> emptySet().iterator();
    }
    
    @Override
    public boolean remove(Object arg0) {
        return false;
    }
    
    @Override
    public boolean removeAll(Collection<?> arg0) {
        return false;
    }
    
    @Override
    public boolean retainAll(Collection<?> arg0) {
        return false;
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {};
    }
    
    @Override
    public <T> T[] toArray(T[] arg0) {
        return arg0;
    }
}
