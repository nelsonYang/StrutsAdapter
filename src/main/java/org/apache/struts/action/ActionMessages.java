package org.apache.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Administrator
 */
public class ActionMessages {

    public static final String GLOBAL_MESSAGE = "com.struts.adapter.GLOBAL_MESSAGE";
    private final Map<String, List<ActionMessage>> actionMessageMap = new HashMap<String, List<ActionMessage>>();

    /**
     * @return the actionMessageMap
     */
    public Map<String, List<ActionMessage>> getActionMessageMap() {
        return actionMessageMap;
    }

    public int size() {
        int totalSize = 0;
        for (List<ActionMessage> actionMessages : actionMessageMap.values()) {
            for (ActionMessage actionMessage : actionMessages) {
                totalSize = totalSize + actionMessage.getErrorMessageList().size();
            }
        }
        return totalSize;
    }

    public ActionMessages() {
    }

    public ActionMessages(ActionMessages messages) {
        add(messages);
    }

    public void add(String key, ActionMessage message) {
        List< ActionMessage> items = this.actionMessageMap.get(key);
        if (items != null) {
            items.add(message);
        } else {
            items = new ArrayList<ActionMessage>(5);
            items.add(message);
            actionMessageMap.put(key, items);
        }
    }

    public void add(String key, List<ActionMessage> messages) {
        if (messages == null) {
            return;
        }
        List< ActionMessage> items = this.actionMessageMap.get(key);
        if (items != null) {
            items.addAll(messages);
        } else {
            actionMessageMap.put(key, messages);
        }
    }

    public final void add(ActionMessages messages) {
        if (messages == null) {
            return;
        }
        List<ActionMessage> items;
        Set<String> keySet = messages.getActionMessageMap().keySet();
        for (String key : keySet) {
            items = messages.getActionMessageMap().get(key);
            this.add(key, items);
        }
    }

    public boolean isEmpty() {
        return this.actionMessageMap.isEmpty();
    }
    
    public void clear(){
        this.actionMessageMap.clear();
    }

    public int size(String key) {
        int totalSize = 0;
        List<ActionMessage> items = this.actionMessageMap.get(key);
        if (items != null) {
            for (ActionMessage item : items) {
                totalSize = totalSize + item.getErrorMessageList().size();
            }
        }
        return totalSize;
    }
}
