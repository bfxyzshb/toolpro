package exception;

public class MongodbException extends Exception {
    public MongodbException(){
        super();
    }
    public MongodbException(String msg){
        super(msg);
    }

    public MongodbException(String msg,Throwable ex){
        super(msg,ex);
    }


    public MongodbException(Throwable ex){
        super(ex);
    }

}
