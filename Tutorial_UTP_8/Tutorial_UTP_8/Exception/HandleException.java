package Exception;

import java.io.IOException;

public class HandleException extends IOException {
    private static final long serialVersionUID = 1L;

    private String code;

    public HandleException(Throwable cause){
    }

    public HandleException(String code, String message){
        super(message);
        this.setCode(code);
    }

    public HandleException(String code, String message, Throwable cause){
        super(message,cause);
        this.setCode(code);
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

}

