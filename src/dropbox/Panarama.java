package dropbox;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

// too large to store in memory, store it to file system: root/x/y/imagePath
public class Panarama {
    int numOfRows;
    int numOfColumns;
    String basePath;
    Map<Sector, String> map;//String is the path of the image

    class Sector {
        int column;
        int row;
        public Sector(int column, int row) {
            this.column = column;
            this.row = row;
        }
    }

    class Image {
        int size;
        String path;
        byte[] bytes;
        public Image(byte[] bytes) {
            this.bytes = bytes;
        }
        byte[] getBytes() {return bytes;}
    }

    void update(Sector sector, Image image) throws NoSuchAlgorithmException {
        String imagePath = basePath + hash(image, sector);
        map.put(sector, imagePath);
    }

    String fetch(Sector sector) {
        return map.get(sector);
    }

    /* return unique string given image */
    String hash(Image image, Sector sector) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String fileName = String.valueOf(sector.row + sector.column + image.size);
        md.update(fileName.getBytes());
        return DatatypeConverter
                .printHexBinary(md.digest());
    }

    /* 假设如果已经给了api用来read/write image */
    void readFile(String pathOfImage) {
    }
    void saveFile(Image image){

    }

    // follow up
    class SectorNode {
        Sector sector;
        SectorNode prev;
        SectorNode next;
        SectorNode(Sector sector) {
            this.sector = sector;
        }
    }
    // int capacity;
    SectorNode head;
    SectorNode tail;

    Panarama() {
        head = new SectorNode(new Sector(-1, -1));
        tail = new SectorNode(new Sector(-1, -1));
        head.next = tail;
        tail.prev = head;
    }

    Sector getStalest() {
        if (head.next != tail) {
            Sector stalest = head.next.sector;
            return stalest;
        }
        return new Sector(0,0);
    }

    // when update and fetch, call moveToTail
    // also update map to store <Sector, SectorNode>
    private void moveToTail(SectorNode node) {
        node.prev = tail.prev;
        tail.prev = node;
        node.prev.next = node;
        node.next= tail;
    }

}


