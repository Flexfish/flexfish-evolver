package peterfajdiga.flexfish.evolver.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CommandOutput {

    private CommandOutput() {}

    public static InputStream getOutputStream(final String command) throws IOException {
        final Process process = Runtime.getRuntime().exec(command);
        return process.getInputStream();
    }

    public static String getOutput(final String command) throws IOException {
        final BufferedInputStream reader = new BufferedInputStream(getOutputStream(command));
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        while (true) {
            final int result = reader.read();
            if (result == -1) {
                break;
            }
            buffer.write(result);
        }
        return buffer.toString();
    }

    public static void saveOutput(final String command, final String filename) throws IOException {
        Files.copy(getOutputStream(command), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
    }

    public static int getInt(final String command) throws IOException, InputMismatchException {
        final Process process = Runtime.getRuntime().exec(command);
        final Scanner processStdout = new Scanner(process.getInputStream());
        processStdout.useLocale(Locale.US);
        final int outputInt = processStdout.nextInt();
        processStdout.close();
        return outputInt;
    }
}
