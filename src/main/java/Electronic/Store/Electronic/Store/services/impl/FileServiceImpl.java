package Electronic.Store.Electronic.Store.services.impl;

import Electronic.Store.Electronic.Store.Exceptions.BadApiRequest;
import Electronic.Store.Electronic.Store.services.FileServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileServices {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        //abc.png
        String originalFileName = file.getOriginalFilename();
        logger.info("FileName: {}", originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extensions = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtensions = fileName + extensions;
        String fullPathWithFileName = path + fileNameWithExtensions;

        if (extensions.equalsIgnoreCase(".png") || extensions.equalsIgnoreCase(".jpg") || extensions.equalsIgnoreCase(".jpeg")) {

            //file save

            File folder = new File(path);

            if (!folder.exists()) {
                //create the folder
                folder.mkdirs();
            }

            //upload file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            return fileNameWithExtensions;

        } else {
            throw new BadApiRequest(" File with this " + extensions + " not allowed !!");
        }

    }

    @Override
    public InputStream getResources(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator + name;

        return new FileInputStream(fullPath);
    }
}
