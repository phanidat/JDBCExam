import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

class Sonnet {
    private List<String> lines = new ArrayList<>();

    public void add(String line) {
        lines.add(line);
    }

    byte[] getCompressedBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzos = new GZIPOutputStream(bos);
             PrintWriter printWriter = new PrintWriter(gzos);) {

            for (String line : lines) {
                printWriter.println(line);
            }
        }

        return bos.toByteArray();
    }
}
