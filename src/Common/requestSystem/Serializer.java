package Common.requestSystem;

import Common.commands.CommandAbstract;

import java.io.*;
import java.nio.ByteBuffer;

public final class Serializer {

    private Serializer() {
        //never used
    }

    public static ByteBuffer serializeCommand(CommandAbstract command) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(command);
        oos.flush();
        ByteBuffer bufferToSend = ByteBuffer.wrap(bytes.toByteArray());
        oos.close();
        bytes.close();
        return bufferToSend;
    }

    public static Response deserializeResponse(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }

    public static ByteBuffer serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(response);
        oos.flush();
        ByteBuffer bufferToSend = ByteBuffer.wrap(bytes.toByteArray());
        oos.close();
        bytes.close();
        return bufferToSend;
    }

    public static CommandAbstract deserializeCommand(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        CommandAbstract command = (CommandAbstract) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return command;
    }
}
