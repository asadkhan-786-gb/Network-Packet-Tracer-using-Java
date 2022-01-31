import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NetMonitor {

    private String source;
    private String target;
    private int port;

    public static void main(String[] args) throws UnknownHostException, IOException {

        if (args.length != 3){
            System.out.printf("Usage:\n\tjava -jar net-monitor.jar src_ip dest_ip port\n");
            return;
        }

        NetMonitor m = new NetMonitor();

        m.setSource(args[0]);
        m.setTarget(args[1]);
        m.setPort(Integer.valueOf(args[2]));

        m.start();
    }

    private void setSource(String s) {
        source = s;
    }

    private void start() throws UnknownHostException, IOException {
        Timer t = new Timer(false);
        t.schedule(new TesteConexaoTask(), 0, 1000);
    }

    private void setPort(int p) {
        port = p;
    }

    private void setTarget(String t) {
        target = t;
    }

    class TesteConexaoTask extends TimerTask {

        @Override
        public void run() {
            try{
                Socket s = new Socket();
                InetSocketAddress sourceAddr = new InetSocketAddress(source, 0);
                InetSocketAddress destAddr = new InetSocketAddress(target, port);
                s.bind(sourceAddr);
                s.connect(destAddr, 1000);
                if (s.isConnected()){
                    System.out.println(new Date() + " " + target + ":" + port + " [CONNECTED]");
                    s.close();
                }else{
                    System.out.println(new Date() + " " + target + ":" + port + " [DISCONNECTED]");
                };
            }catch(Exception e){
                System.out.println(new Date() + " "+ target + ":" + port + " [DISCONNECTED]");
            }

        }



    }
}