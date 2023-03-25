package oversecured.ovaa.objects;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import oversecured.ovaa.utils.FileUtils;



public class DeleteFilesSerializable implements Serializable {


    private String fileName;


    //getStringExtra("url");将进行反序列化，会根据接口调用构造函数
    public DeleteFilesSerializable(String fileName) {
        this.fileName = fileName;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        fileName = in.readUTF();
        File file = new File(fileName);
        if(file.exists()) {
            FileUtils.deleteRecursive(file);
        }
    }
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeUTF(fileName);
    }
}

//反序列化
/*
public class DeleteFilesSerializable implements Serializable {
    private void readObject(ObjectInputStream in) throws IOException {
        File file = new File(in.readUTF());
        if(file.exists()) {
            FileUtils.deleteRecursive(file);
        }
    }
}

*/