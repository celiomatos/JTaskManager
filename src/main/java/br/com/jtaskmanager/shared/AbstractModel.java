package br.com.jtaskmanager.shared;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AbstractModel {

    private PropertyChangeSupport ptyChgSupport;

    protected AbstractModel() {
        ptyChgSupport = new PropertyChangeSupport(this);
    }

    public final PropertyChangeSupport getPropertyChangeSupport() {
        return ptyChgSupport;
    }

    public final void setPropertyChangeSupport(PropertyChangeSupport ptyChgSupExt) {
        this.ptyChgSupport = ptyChgSupExt;
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        ptyChgSupport.addPropertyChangeListener(listener);
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        ptyChgSupport.removePropertyChangeListener(listener);
    }

    public void fireProperty(String fieldName, Object oldValue, Object newValue) {
        getPropertyChangeSupport().firePropertyChange(fieldName, oldValue, newValue);
    }

}
