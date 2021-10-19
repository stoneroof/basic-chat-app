package basic_chat_app.shared;

import java.io.Serializable;

public abstract class Request implements Serializable {
    public abstract boolean isValid();
}
