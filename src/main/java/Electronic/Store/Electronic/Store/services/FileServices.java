package Electronic.Store.Electronic.Store.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileServices {

    String uploadFile(MultipartFile file, String path);

    InputStream getResources(String path, String name);


}
