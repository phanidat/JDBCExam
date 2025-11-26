import java.io.*;

class SonnetReader extends BufferedReader {

    public SonnetReader(Reader reader) {
        super(reader);
    }

    public SonnetReader(InputStream inputStream) {
        this(new InputStreamReader(inputStream));
    }

    public void skipLines(int lines) throws IOException {
        for (int i = 0; i < lines; i++) {
            readLine();
        }
    }

    private String skipSonnetHeader() throws IOException {
        String line = readLine();
        while (line.isBlank()) {
            line = readLine();
        }
        if (line.startsWith("*** END OF THE PROJECT GUTENBERG EBOOK")) {
            return null;
        }
        line = readLine();
        while (line.isBlank()) {
            line = readLine();
        }
        return line;
    }

    public Sonnet readNextSonnet() throws IOException {
        String line = skipSonnetHeader();
        if (line == null) {
            return null;
        } else {
            var sonnet = new Sonnet();
            while (!line.isBlank()) {
                sonnet.add(line);
                line = readLine();
            }
            return sonnet;
        }
    }
}
