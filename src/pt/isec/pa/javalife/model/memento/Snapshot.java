package pt.isec.pa.javalife.model.memento;

import pt.isec.pa.javalife.model.data.Simulacao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Snapshot implements IMemento {
    byte[] snapshot;

    public Snapshot(Simulacao simulacao) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(simulacao);
            snapshot = baos.toByteArray();
        } catch (Exception e) {
            snapshot = null;
        }
    }

    @Override
    public Simulacao getSnapshot() {
        if (snapshot == null) return null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(snapshot); ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Simulacao) ois.readObject();

        } catch (Exception e) {
            return null;
        }
    }
}