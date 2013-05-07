package persistence;

import java.io.Serializable;

/**
 * Checked variant of {@link javax.persistence.NoResultException}.
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
public class NotFoundException extends Exception implements Serializable {
    
    private static final long serialVersionUID = 2836210453423996541L;
}
