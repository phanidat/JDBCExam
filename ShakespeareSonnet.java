import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() throws IOException, InterruptedException {
    URI sonnetsURI = URI.create("https://www.gutenberg.org/cache/epub/1041/pg1041.txt");
    HttpRequest request = HttpRequest.newBuilder(sonnetsURI).GET().build();
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    InputStream inputStream = response.body();

    int start = 33;

    List<Sonnet> sonnets = new ArrayList<>();

    try (var reader = new SonnetReader(inputStream)
    ) {
        reader.skipLines(start);
        Sonnet sonnet = reader.readNextSonnet();
        while (sonnet != null) {
            sonnets.add(sonnet);
            sonnet = reader.readNextSonnet();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    int numberOfSonnets = sonnets.size();
    IO.println("# sonnets = " + numberOfSonnets);

    Path path = Path.of("/Users/phanidatana/files/sonnets.bin");
    try (var sonnetFile = Files.newOutputStream(path);
         var dos = new DataOutputStream(sonnetFile)) {

        List<Integer> offsets = new ArrayList<>();
        List<Integer> lengths = new ArrayList<>();
        byte[] encodeSonnetsBytesArray;

        try (ByteArrayOutputStream encodedSonnets = new ByteArrayOutputStream()) {
            for (Sonnet sonnet : sonnets) {
                byte[] sonnetCompressedBytes = sonnet.getCompressedBytes();

                offsets.add(encodedSonnets.size());
                lengths.add(sonnetCompressedBytes.length);
                encodedSonnets.write(sonnetCompressedBytes);
            }

            dos.writeInt(numberOfSonnets);
            for (int index = 0; index < numberOfSonnets; index++) {
                dos.writeInt(offsets.get(index));
                dos.writeInt(lengths.get(index));
            }
            encodeSonnetsBytesArray = encodedSonnets.toByteArray();
        }
        sonnetFile.write(encodeSonnetsBytesArray);

    } catch (IOException e) {
        e.printStackTrace();
    }

    try (var file = Files.newInputStream(path);
         var bis = new BufferedInputStream(file);
         var dos = new DataInputStream(file)) {

        numberOfSonnets = dos.readInt();
        IO.println("numberOfSonnets = " + numberOfSonnets);
        List<Integer> offsets = new ArrayList<>();
        List<Integer> lengths = new ArrayList<>();
        for (int i = 0; i < numberOfSonnets; i++) {
            offsets.add(dos.readInt());
            lengths.add(dos.readInt());
        }

        // At this point, you have the offsets and the lengths of
        // all the sonnets
        int sonnet = 75; // the sonnet you are reading
        int offset = offsets.get(sonnet - 1);
        int length = lengths.get(sonnet - 1);

        skip(bis, offset);
        byte[] bytes = readBytes(bis, length);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             GZIPInputStream gzbais = new GZIPInputStream(bais);
             InputStreamReader isr = new InputStreamReader(gzbais);
             BufferedReader reader = new BufferedReader(isr);) {

            List<String> sonnetLines = reader.lines().toList();
            sonnetLines.forEach(IO::println);
        }

    }
}

long skip(BufferedInputStream bis, int offset) throws IOException {
    long skip = 0L;
    while (skip < offset) {
        skip += bis.skip(offset - skip);
    }
    return skip;
}

byte[] readBytes(BufferedInputStream bis, int length) throws IOException {
    byte[] bytes = new byte[length];
    byte[] buffer = new byte[length];
    int read = bis.read(buffer);
    int copied = 0;
    while (copied < length) {
        System.arraycopy(buffer, 0, bytes, copied, read);
        copied += read;
        read = bis.read(buffer);
    }
    return bytes;
}

