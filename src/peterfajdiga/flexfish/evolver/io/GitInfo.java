package peterfajdiga.flexfish.evolver.io;

import java.io.*;

public class GitInfo {
    private final String repoPath;

    public GitInfo(final String repoPath) {
        this.repoPath = repoPath;
    }

    private String buildCommand(final String gitCommand) {
        return String.format("git -C %s %s", repoPath, gitCommand);
    }

    public String getHeadHash() throws IOException {
        return CommandOutput.getOutput(buildCommand("log --pretty=format:%h -n 1"));
    }

    public String getHeadMessage() throws IOException {
        return CommandOutput.getOutput(buildCommand("log --pretty=format:%B -n 1"));
    }

    public InputStream streamDiff() throws IOException {
        return CommandOutput.getOutputStream(buildCommand("diff"));
    }

    public void saveDiff(final String filename) throws IOException {
        CommandOutput.saveOutput(buildCommand("diff"), filename);
    }
}
