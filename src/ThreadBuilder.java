/**
 * build Thread{@link Thread}
 */
public final class ThreadBuilder {
    private String nameFormat = null;
    private Boolean daemon = null;

    public ThreadBuilder setNameFormat(String nameFormat){
        this.nameFormat=nameFormat;
        return this;
    }

    public ThreadBuilder setDaemon(Boolean daemon){
        this.daemon=daemon;
        return this;
    }

    public static void main(String[] args) {
        //TODO
        ThreadBuilder threadBuilder =new ThreadBuilder().setNameFormat("test").setDaemon(true);
    }
}
