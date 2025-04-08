package views;

import java.awt.event.ActionEvent;

public class CloningAidActionEvent extends ActionEvent {
    private final Object parameter;
    public CloningAidActionEvent(Object source, int id, String command,
                                 Object param) {
        super(source, id, command);
        this.parameter = param;
    }

    public CloningAidActionEvent(Object source, int id, String command, int modifiers, Object parameter) {
        super(source, id, command, modifiers);
        this.parameter = parameter;
    }

    public CloningAidActionEvent(Object source, int id, String command, long when, int modifiers, Object parameter) {
        super(source, id, command, when, modifiers);
        this.parameter = parameter;
    }

    public Object getParameter() {
        return parameter;
    }
}
