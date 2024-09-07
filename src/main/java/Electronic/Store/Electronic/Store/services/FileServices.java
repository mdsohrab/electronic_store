package Electronic.Store.Electronic.Store.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileServices {

    public String uploadFile(MultipartFile file, String path) throws IOException;

    InputStream getResources(String path, String name) throws FileNotFoundException;


}
