package peterfajdiga.flexfish.evolver.io;

import java.io.*;

public class StreamGobbler extends Thread {
    private final BufferedReader in;
    private final BufferedWriter out;

    public StreamGobbler(final InputStream in, final OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                    out.write(line);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
