package Common.requestSystem;

import Common.requestSystem.requests.Request;
import Common.requestSystem.responses.Response;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {

    public synchronized ByteBuffer serializeRequest(Request request) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(request);
        oos.flush();
        ByteBuffer bufferToSend = ByteBuffer.wrap(bytes.toByteArray());
        oos.close();
        bytes.close();
        return bufferToSend;
    }

    public static synchronized Response deserializeResponse(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }

    public synchronized ByteBuffer serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(response);
        oos.flush();
        ByteBuffer bufferToSend = ByteBuffer.wrap(bytes.toByteArray());
        oos.close();
        bytes.close();
        return bufferToSend;
    }

    public synchronized Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }
}
