package org.example.managers;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;

public class AvatarsManager {
    private static String SAVE_DIR = "C:\\Users\\dalek\\IdeaProjects\\lab1\\storage\\";

    public static byte[] getAvatar(String uuid) throws IOException {
        var file = new File(SAVE_DIR + uuid + ".jpg");
        byte[] imageInByte = new byte[0];
        if (!UsersManager.checkIfExists(uuid)) {
            return imageInByte;
        }
        if (file.exists()) {
            var sourceFile = new FileInputStream(file);

            BufferedImage bufferedImage = ImageIO.read(sourceFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            sourceFile.close();
        }
        return imageInByte;
    }

    public static boolean saveAvatar(String uuid, Part portrait) throws IOException {
        if (!UsersManager.checkIfExists(uuid) || !portrait.getContentType().equals("image/jpeg")) {
            return false;
        }
        var file = new File(SAVE_DIR + uuid + ".jpg");
        if (!file.exists() && portrait != null) {
            BufferedImage bufferedImage = ImageIO.read(portrait.getInputStream());
            var destinationFile = new FileOutputStream(file);
            ImageIO.write(bufferedImage, "jpg", destinationFile);
            destinationFile.close();
            return true;
        }
        return false;
    }

    public static boolean overwriteAvatar(String uuid, Part portrait) throws IOException {
        if (!UsersManager.checkIfExists(uuid) || !portrait.getContentType().equals("image/jpeg")) {
            return false;
        }
        var file = new File(SAVE_DIR + uuid + ".jpg");
        if (file.exists() && portrait != null) {
            BufferedImage bufferedImage = ImageIO.read(portrait.getInputStream());
            try {
                var destinationFile = new FileOutputStream(file);
                ImageIO.write(bufferedImage, "jpg", destinationFile);
                destinationFile.close();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    public static boolean removeAvatar(String uuid) throws IOException {
        if (!UsersManager.checkIfExists(uuid)) {
            return false;
        }
        var file = new File(SAVE_DIR + uuid + ".jpg");
        if (file.exists() && file.delete()) {
            return true;
        }

        return false;
    }
}
